package com.anle.todomvp.statistics;


import com.anle.todomvp.BaseView;

public interface StatisticsContract {

    interface View extends BaseView<UserActionsListener> {

        void setProgressIndicator(boolean active);

        void displayStatistics(int numberOfIncompleteTasks, int numberOfCompletedTasks);

        void showLoadingStatisticsError();

        boolean isInactive();
    }

    interface UserActionsListener {

        void loadStatistics();
    }
}
