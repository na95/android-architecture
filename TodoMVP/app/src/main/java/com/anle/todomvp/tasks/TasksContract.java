package com.anle.todomvp.tasks;

import com.anle.todomvp.BaseView;
import com.anle.todomvp.data.Task;

import java.util.List;

public interface TasksContract {

    interface View extends BaseView<UserActionListener> {

        void setProgressIndicator(boolean active);

        void showTasks(List<Task> taskList);

        void showAddEditTaskUI();

        void showDetailTaskUI(String taskId);

        void showTaskMarkedComplete();

        void showTaskMarkedActive();

        void showLoadingTasksError();

        void showCompletedTasksCleared();

        void showActiveFilterLabel();

        void showCompletedFilterLabel();

        void showAllFilterLabel();

        void showNoTasks();

        void showNoActiveTasks();

        void showNoCompletedTasks();

        void showSuccessfullySavedMessage();

        boolean isActive();

    }

    interface UserActionListener {

        void result(int requestCode, int resultCode);

        void loadTasks(boolean forceUpdate);

        void addNewTask();

        void openTaskDetail(Task task);

        void completeTask(Task task);

        void activateTask(Task task);

        void loadActiveTasks(boolean forceUpdate);

        void loadCompletedTasks(boolean forceUpdate);

        void clearCompletedTasks();

        TasksFilterType getFiltering();

        void setFiltering(TasksFilterType requestType);
    }
}
