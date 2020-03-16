package com.anle.todomvp.tasks;

import com.anle.todomvp.data.Task;

import java.util.List;

public interface TasksContract {

    interface View{

        void setProgressIndicator(boolean active);

        void showAllTasks(List<Task> taskList);

        void showAddNewTaskUI();

        void showDetailTaskUI(String id);

        void showActivedTasks();

        void showCompletedTasks();

        void showLoadingTaskError();
    }

    interface UserActionListener{

        void loadAllTasks();

        void addNewTask();

        void openTaskDetail();

        void completeTask();

        void activateTask();

        void loadCompletedTask();

        void clearCompletedTask();
    }
}
