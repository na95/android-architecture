package com.anle.todomvp.addedittask;

import com.anle.todomvp.BaseView;

public interface AddEditTaskContract {

    interface View extends BaseView<Presenter> {

        void showTasksList();

        void showEmptyTaskError();

        void setTitle(String title);

        void setContent(String content);
    }

    interface Presenter {

        void saveTask(String title, String content);

        void populateTask();
    }
}
