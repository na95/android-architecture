package com.anle.todomvp.addedittask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.anle.todomvp.data.Task;
import com.anle.todomvp.data.source.TasksDataSource;
import com.anle.todomvp.util.schedulers.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

import static com.google.common.base.Preconditions.checkNotNull;

public class AddEditTaskPresenter implements AddEditTaskContract.Presenter{

    private TasksDataSource mTasksRepository;

    private AddEditTaskContract.View mAddEditTaskView;

    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;

    @Nullable
    private String mTaskId;

    private boolean mIsDataMissing;

    @NonNull
    private CompositeDisposable mCompositeDisposable;

    /**
     * Creates a presenter for the add/edit view.
     *
     * @param taskId                 ID of the task to edit or null for a new task
     * @param tasksRepository        a repository of data for tasks
     * @param addTaskView            the add/edit view
     * @param shouldLoadDataFromRepo whether data needs to be loaded or not (for config changes)
     */
    public AddEditTaskPresenter(@Nullable String taskId, @NonNull TasksDataSource tasksRepository,
                                @NonNull AddEditTaskContract.View addTaskView, boolean shouldLoadDataFromRepo,
                                @NonNull BaseSchedulerProvider schedulerProvider) {
        mTaskId = taskId;
        mTasksRepository = checkNotNull(tasksRepository);
        mAddEditTaskView = checkNotNull(addTaskView);
        mIsDataMissing = shouldLoadDataFromRepo;

        mSchedulerProvider = checkNotNull(schedulerProvider, "schedulerProvider cannot be null!");

        mCompositeDisposable = new CompositeDisposable();
        mAddEditTaskView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        if (!isNewTask() && mIsDataMissing) {
            populateTask();
        }
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void saveTask(String title, String description) {
        if (isNewTask()) {
            createTask(title, description);
        } else {
            updateTask(title, description);
        }
    }

    @Override
    public void populateTask() {
        if (isNewTask()) {
            throw new RuntimeException("populateTask() was called but task is new.");
        }
        mCompositeDisposable.add(mTasksRepository
                .getTask(mTaskId)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(
                        // onNext
                        taskOptional -> {
                            if (taskOptional.isPresent()) {
                                Task task = taskOptional.get();
                                if (mAddEditTaskView.isActive()) {
                                    mAddEditTaskView.setTitle(task.getTitle());
                                    mAddEditTaskView.setContent(task.getContent());

                                    mIsDataMissing = false;
                                }
                            } else {
                                if (mAddEditTaskView.isActive()) {
                                    mAddEditTaskView.showEmptyTaskError();
                                }
                            }
                        },
                        // onError
                        throwable -> {
                            if (mAddEditTaskView.isActive()) {
                                mAddEditTaskView.showEmptyTaskError();
                            }
                        }));
    }

    @Override
    public boolean isDataMissing() {
        return mIsDataMissing;
    }

    private boolean isNewTask() {
        return mTaskId == null;
    }

    private void createTask(String title, String description) {
        Task newTask = new Task(title, description);
        if (newTask.isEmpty()) {
            mAddEditTaskView.showEmptyTaskError();
        } else {
            mTasksRepository.saveTask(newTask);
            mAddEditTaskView.showTasksList();
        }
    }

    private void updateTask(String title, String description) {
        if (isNewTask()) {
            throw new RuntimeException("updateTask() was called but task is new.");
        }
        mTasksRepository.saveTask(new Task(title, description, mTaskId));
        mAddEditTaskView.showTasksList(); // After an edit, go back to the list.
    }
}
