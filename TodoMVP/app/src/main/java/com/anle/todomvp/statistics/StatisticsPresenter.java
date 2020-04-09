package com.anle.todomvp.statistics;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;

import com.anle.todomvp.data.Task;
import com.anle.todomvp.data.source.TasksDataSource;
import com.anle.todomvp.data.source.TasksRepository;
import com.anle.todomvp.util.schedulers.BaseSchedulerProvider;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static androidx.core.util.Preconditions.checkNotNull;

public class StatisticsPresenter implements StatisticsContract.Presenter {


    private final TasksRepository mTasksRepository;

    private final StatisticsContract.View mStatisticsView;

    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;

    @NonNull
    private CompositeDisposable mCompositeDisposable;

    public StatisticsPresenter(@NonNull TasksRepository tasksRepository,
                               @NonNull StatisticsContract.View statisticsView,
                               @NonNull BaseSchedulerProvider schedulerProvider) {
        mTasksRepository = Preconditions.checkNotNull(tasksRepository, "tasksRepository cannot be null");
        mStatisticsView = Preconditions.checkNotNull(statisticsView, "statisticsView cannot be null!");
        mSchedulerProvider = Preconditions.checkNotNull(schedulerProvider, "schedulerProvider cannot be null");

        mCompositeDisposable = new CompositeDisposable();
        mStatisticsView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadStatistics();
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    private void loadStatistics() {
        mStatisticsView.setProgressIndicator(true);

        Flowable<Task> tasks = mTasksRepository
                .getTasks()
                .flatMap(Flowable::fromIterable);
        Flowable<Long> completedTasks = tasks.filter(Task::isCompleted).count().toFlowable();
        Flowable<Long> activeTasks = tasks.filter(Task::isActive).count().toFlowable();
        Disposable disposable = Flowable
                .zip(completedTasks, activeTasks, (completed, active) -> Pair.create(active, completed))
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(
                        // onNext
                        stats -> mStatisticsView.displayStatistics(Ints.saturatedCast(stats.first), Ints.saturatedCast(stats.second)),
                        // onError
                        throwable -> mStatisticsView.showLoadingStatisticsError(),
                        // onCompleted
                        () -> mStatisticsView.setProgressIndicator(false));
        mCompositeDisposable.add(disposable);
    }
}
