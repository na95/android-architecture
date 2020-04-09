package com.anle.todomvp.taskdetail;

import androidx.annotation.Nullable;

import com.anle.todomvp.BasePresenter;
import com.anle.todomvp.BaseView;

public interface TaskDetailContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showMissingTask();

        void hideTitle();

        void showTitle(String title);

        void hideContent();

        void showContent(String content);

        void showCompletionStatus(boolean complete);

        void showEditTaskUI(String taskId);

        void showTaskDeleted();

        void showTaskMarkedComplete();

        void showTaskMarkedActive();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void editTask();

        void deleteTask();

        void completeTask();

        void activateTask();
    }
}
