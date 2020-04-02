package com.anle.todomvp.addedittask;

public interface AddEditTaskContract {

    interface View {

        void setUserActionListener(UserActionsListener listener);

        void showTasksList();

        void showEmptyTaskError();

        void setTitle(String title);

        void setContent(String content);
    }

    interface UserActionsListener {

        void updateTask(String title, String content, String taskId);

        void createTask(String title, String content);

        void populateTask(String taskId);
    }
}
