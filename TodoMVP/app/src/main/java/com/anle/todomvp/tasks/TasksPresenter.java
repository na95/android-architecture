package com.anle.todomvp.tasks;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.anle.todomvp.data.Task;
import com.anle.todomvp.data.source.TasksDataSource;
import com.anle.todomvp.data.source.TasksRepository;
import com.anle.todomvp.data.source.local.TasksDbHelper;

import java.util.ArrayList;
import java.util.List;

import static com.anle.todomvp.tasks.TasksFragment.ACTIVE_TASKS;
import static com.anle.todomvp.tasks.TasksFragment.ALL_TASKS;
import static com.anle.todomvp.tasks.TasksFragment.COMPLETED_TASKS;
import static com.google.common.base.Preconditions.checkNotNull;

public class TasksPresenter implements TasksContract.UserActionListener {

    private final TasksRepository mTasksRepository;

    private final TasksContract.View mTasksView;

    public TasksPresenter(@NonNull TasksRepository tasksRepository, @NonNull TasksContract.View tasksView) {
        mTasksRepository = checkNotNull(tasksRepository, "tasksRepository cannot be null");
        mTasksView = checkNotNull(tasksView, "tasksView cannot be null!");
    }

    @Override
    public void loadAllTasks(boolean forceUpdate) {
        loadTasks(forceUpdate,true, ALL_TASKS);
    }

    @Override
    public void loadActiveTasks(boolean forceUpdate) {
        loadTasks(forceUpdate,true, ACTIVE_TASKS);
    }

    @Override
    public void loadCompletedTasks(boolean forceUpdate) {
        loadTasks(forceUpdate, true, COMPLETED_TASKS);
    }

    /**
     * @param forceUpdate Pass in true to refresh the data in the {@link TasksDataSource}
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     * @param requestType Corresponds to the position of the Navigation Spinner
     */
    private void loadTasks(boolean forceUpdate, final boolean showLoadingUI, final int requestType) {

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
                for (Task task: tasks) {
                    switch (requestType) {
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
                if (mTasksView.isInactive()) {
                    return;
                }

                if (showLoadingUI) {
                    mTasksView.setProgressIndicator(false);
                }

                mTasksView.showTasks(tasksToShow);
            }

            @Override
            public void onDataNotAvailable() {
                // The View may not be on screen anymore when this callback is returned
                if (mTasksView.isInactive()) {
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
        //TODO
        mTasksRepository.completeTask(task);
        mTasksView.showTaskMarkedComplete();
    }

    @Override
    public void activateTask(Task task) {
        //TODO
        mTasksRepository.activateTask(task);
        mTasksView.showTaskMarkedActive();
    }

    @Override
    public void clearCompletedTasks() {
        mTasksRepository.clearCompletedTasks();
        mTasksView.showCompletedTasksCleared();
    }

}
