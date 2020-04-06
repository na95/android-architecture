package com.anle.todomvp.taskdetail;

import androidx.annotation.NonNull;

import com.anle.todomvp.data.Task;
import com.anle.todomvp.data.source.TasksDataSource;

public class TaskDetailPresenter implements TaskDetailContract.Presenter {

    private TasksDataSource mTasksRepository;

    private TaskDetailContract.View mTaskDetailView;

    public TaskDetailPresenter(@NonNull TasksDataSource taskRepository, @NonNull TaskDetailContract.View taskDetailView) {
        mTasksRepository = taskRepository;
        mTaskDetailView = taskDetailView;
        mTaskDetailView.setPresenter(this);
    }

    @Override
    public void openTask(String taskId) {
        if (null == taskId || taskId.isEmpty()) {
            mTaskDetailView.showMissingTask();
            return;
        }

        mTaskDetailView.setProgressIndicator(true);
        mTasksRepository.getTask(taskId, new TasksDataSource.GetTaskCallback() {
            @Override
            public void onTaskLoaded(Task task) {
                // The View may not be on screen anymore when this callback is returned
                if (mTaskDetailView.isInactive()) {
                    return;
                }
                mTaskDetailView.setProgressIndicator(false);
                if (null == task) {
                    mTaskDetailView.showMissingTask();
                } else {
                    showTask(task);
                }
            }

            @Override
            public void onDataNotAvailable() {
                // The View may not be on screen anymore when this callback is returned
                if (mTaskDetailView.isInactive()) {
                    return;
                }
                mTaskDetailView.showMissingTask();
            }
        });

    }

    @Override
    public void deleteTask(String taskId) {
        mTasksRepository.deleteTask(taskId);
        mTaskDetailView.showTaskDeleted();
    }

    @Override
    public void completeTask(String taskId) {
        if (null == taskId || taskId.isEmpty()) {
            mTaskDetailView.showMissingTask();
            return;
        }
        mTasksRepository.completeTask(taskId);
        mTaskDetailView.showTaskMarkedComplete();
    }

    @Override
    public void activateTask(String taskId) {
        if (null == taskId || taskId.isEmpty()) {
            mTaskDetailView.showMissingTask();
            return;
        }
        mTasksRepository.activateTask(taskId);
        mTaskDetailView.showTaskMarkedActive();
    }

    @Override
    public void editTask(String taskId) {
        if (null == taskId || taskId.isEmpty()) {
            mTaskDetailView.showMissingTask();
            return;
        }
        mTaskDetailView.showEditTaskUI(taskId);
    }

    private void showTask(Task task) {
        String title = task.getTitle();
        String description = task.getContent();

        if (title != null && title.isEmpty()) {
            mTaskDetailView.hideTitle();
        } else {
            mTaskDetailView.showTitle(title);
        }

        if (description != null && description.isEmpty()) {
            mTaskDetailView.hideContent();
        } else {
            mTaskDetailView.showContent(description);
        }
        mTaskDetailView.showCompletionStatus(task.isCompleted());
    }
}
