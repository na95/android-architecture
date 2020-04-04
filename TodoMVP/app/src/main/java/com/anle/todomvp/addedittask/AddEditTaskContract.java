package com.anle.todomvp.addedittask;

import com.anle.todomvp.BaseView;

public interface AddEditTaskContract {

    interface View extends BaseView<UserActionsListener> {

        void showTasksList();

        void showEmptyTaskError();

        void setTitle(String title);

        void setContent(String content);
    }

    interface UserActionsListener {

        void saveTask(String title, String content);

        void populateTask();
    }
}
