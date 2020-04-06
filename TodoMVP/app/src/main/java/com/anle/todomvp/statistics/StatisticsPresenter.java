package com.anle.todomvp.statistics;

import androidx.annotation.NonNull;

import com.anle.todomvp.data.Task;
import com.anle.todomvp.data.source.TasksDataSource;
import com.anle.todomvp.data.source.TasksRepository;

import java.util.List;

import static androidx.core.util.Preconditions.checkNotNull;

public class StatisticsPresenter implements StatisticsContract.Presenter {


    private final TasksRepository mTasksRepository;

    private final StatisticsContract.View mStatisticsView;

    public StatisticsPresenter(@NonNull TasksRepository tasksRepository,
                               @NonNull StatisticsContract.View statisticsView) {
        mTasksRepository = checkNotNull(tasksRepository, "tasksRepository cannot be null");
        mStatisticsView = checkNotNull(statisticsView, "StatisticsView cannot be null!");
        mStatisticsView.setPresenter(this);
    }

    @Override
    public void loadStatistics() {
        mStatisticsView.setProgressIndicator(true);

        // The network request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.

        mTasksRepository.getTasks(new TasksDataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                int activeTasks = 0;
                int completedTasks = 0;

                // This callback may be called twice, once for the cache and once for loading
                // the data from the server API, so we check before decrementing, otherwise
                // it throws "Counter has been corrupted!" exception.

                // We calculate number of active and completed tasks
                for (Task task : tasks) {
                    if (task.isCompleted()) {
                        completedTasks += 1;
                    } else {
                        activeTasks += 1;
                    }
                }

                // The View may not be on screen anymore when this callback is returned
                if (mStatisticsView.isInactive()) {
                    return;
                }

                mStatisticsView.setProgressIndicator(false);

                mStatisticsView.displayStatistics(activeTasks, completedTasks);
            }

            @Override
            public void onDataNotAvailable() {
                if (mStatisticsView.isInactive()) {
                    return;
                }
                mStatisticsView.showLoadingStatisticsError();
            }
        });
    }
}
