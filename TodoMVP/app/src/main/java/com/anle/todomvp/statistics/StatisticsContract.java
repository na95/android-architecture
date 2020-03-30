package com.anle.todomvp.statistics;


public interface StatisticsContract {

    interface View {

        void setProgressIndicator(boolean active);

        void displayStatistics(int numberOfIncompleteTasks, int numberOfCompletedTasks);

        void showLoadingStatisticsError();

        boolean isInactive();
    }

    interface UserActionsListener {

        void loadStatistics();
    }
}
