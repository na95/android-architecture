package com.anle.todomvp.tasks;

import com.anle.todomvp.data.Task;

import java.util.List;

public interface TasksContract {

    interface View{

        void setProgressIndicator(boolean active);

        void showTasks(List<Task> taskList);

        void showAddEditTaskUI();

        void showDetailTaskUI(String taskId);

        void showTaskMarkedComplete();

        void showTaskMarkedActive();

        void showLoadingTasksError();

        boolean isInactive();

        void showCompletedTasksCleared();
    }

    interface UserActionListener{

        void loadAllTasks(boolean forceUpdate);

        void addNewTask();

        void openTaskDetail(Task task);

        void completeTask(Task task);

        void activateTask(Task task);

        void loadActiveTasks(boolean forceUpdate);

        void loadCompletedTasks(boolean forceUpdate);

        void clearCompletedTasks();
    }
}
