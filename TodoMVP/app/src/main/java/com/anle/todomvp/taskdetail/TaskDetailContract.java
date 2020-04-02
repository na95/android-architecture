package com.anle.todomvp.taskdetail;

import androidx.annotation.Nullable;

public interface TaskDetailContract {

    interface View {

        void setProgressIndicator(boolean active);

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

        boolean isInactive();
    }

    interface UserActionsListener {

        void openTask(@Nullable String taskId);

        void deleteTask(@Nullable String taskId);

        void completeTask(@Nullable String taskId);

        void activateTask(@Nullable String taskId);

        void editTask(@Nullable String taskId);
    }
}
