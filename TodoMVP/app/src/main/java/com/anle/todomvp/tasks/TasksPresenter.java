package com.anle.todomvp.tasks;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.anle.todomvp.addedittask.AddEditTaskActivity;
import com.anle.todomvp.data.Task;
import com.anle.todomvp.data.source.TasksDataSource;
import com.anle.todomvp.data.source.TasksRepository;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class TasksPresenter implements TasksContract.UserActionListener {

    private final TasksRepository mTasksRepository;

    private final TasksContract.View mTasksView;

    @NonNull
    private TasksFilterType mCurrentFiltering = TasksFilterType.ALL_TASKS;

    public TasksPresenter(@NonNull TasksRepository tasksRepository, @NonNull TasksContract.View tasksView) {
        mTasksRepository = checkNotNull(tasksRepository, "tasksRepository cannot be null");
        mTasksView = checkNotNull(tasksView, "tasksView cannot be null!");
        mTasksView.setPresenter(this);
    }

    @Override
    public void loadTasks(boolean forceUpdate) {
        loadTasks(forceUpdate, true);
    }

    @Override
    public void loadActiveTasks(boolean forceUpdate) {
        loadTasks(forceUpdate, true);
    }

    @Override
    public void loadCompletedTasks(boolean forceUpdate) {
        loadTasks(forceUpdate, true);
    }

    /**
     * @param forceUpdate   Pass in true to refresh the data in the {@link TasksDataSource}
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private void loadTasks(boolean forceUpdate, final boolean showLoadingUI) {

        if (showLoadingUI) {
            mTasksView.setProgressIndicator(true);
        }

        if (forceUpdate) {
            mTasksRepository.refreshTasks();
        }

        mTasksRepository.getTasks(new TasksDataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                List<Task> tasksToShow = new ArrayList<Task>();

                // We filter the tasks based on the requestType
                for (Task task : tasks) {
                    switch (mCurrentFiltering) {
                        case ALL_TASKS:
                            tasksToShow.add(task);
                            break;
                        case ACTIVE_TASKS:
                            if (task.isActive()) {
                                tasksToShow.add(task);
                            }
                            break;
                        case COMPLETED_TASKS:
                            if (task.isCompleted()) {
                                tasksToShow.add(task);
                            }
                            break;
                        default:
                            tasksToShow.add(task);
                            break;
                    }
                }

                // The View may not be on screen anymore when this callback is returned
                if (mTasksView.isActive()) {
                    return;
                }

                if (showLoadingUI) {
                    mTasksView.setProgressIndicator(false);
                }

                processTasks(tasksToShow);
            }

            @Override
            public void onDataNotAvailable() {
                // The View may not be on screen anymore when this callback is returned
                if (mTasksView.isActive()) {
                    return;
                }

                mTasksView.showLoadingTasksError();
            }
        });
    }

    @Override
    public void addNewTask() {
        mTasksView.showAddEditTaskUI();
    }

    @Override
    public void openTaskDetail(Task task) {
        mTasksView.showDetailTaskUI(task.getId());
    }

    @Override
    public void completeTask(Task task) {
        mTasksRepository.completeTask(task);
        mTasksView.showTaskMarkedComplete();
    }

    @Override
    public void activateTask(Task task) {
        mTasksRepository.activateTask(task);
        mTasksView.showTaskMarkedActive();
    }

    @Override
    public void clearCompletedTasks() {
        mTasksRepository.clearCompletedTasks();
        mTasksView.showCompletedTasksCleared();
    }

    @Override
    public TasksFilterType getFiltering() {
        return mCurrentFiltering;
    }

    /**
     * Sets the current task filtering type.
     *
     * @param requestType Can be {@link TasksFilterType#ALL_TASKS},
     *                    {@link TasksFilterType#COMPLETED_TASKS}, or
     *                    {@link TasksFilterType#ACTIVE_TASKS}
     */
    @Override
    public void setFiltering(@NonNull TasksFilterType requestType) {
        mCurrentFiltering = requestType;
    }

    private void processTasks(@NonNull List<Task> tasks) {
        if (tasks.isEmpty()) {
            // Show a message indicating there are no tasks for that filter type.
            processEmptyTasks();
        } else {
            // Show the list of tasks
            mTasksView.showTasks(tasks);
            // Set the filter label's text.
            showFilterLabel();
        }
    }

    private void showFilterLabel() {
        switch (mCurrentFiltering) {
            case ACTIVE_TASKS:
                mTasksView.showActiveFilterLabel();
                break;
            case COMPLETED_TASKS:
                mTasksView.showCompletedFilterLabel();
                break;
            default:
                mTasksView.showAllFilterLabel();
                break;
        }
    }

    private void processEmptyTasks() {
        switch (mCurrentFiltering) {
            case ACTIVE_TASKS:
                mTasksView.showNoActiveTasks();
                break;
            case COMPLETED_TASKS:
                mTasksView.showNoCompletedTasks();
                break;
            default:
                mTasksView.showNoTasks();
                break;
        }
    }

    @Override
    public void result(int requestCode, int resultCode) {
        // If a task was successfully added, show snackbar
        if (AddEditTaskActivity.REQUEST_ADD_TASK == requestCode && Activity.RESULT_OK == resultCode) {
            mTasksView.showSuccessfullySavedMessage();
        }
    }
}
