package com.anle.todomvp.statistics;


import com.anle.todomvp.BasePresenter;
import com.anle.todomvp.BaseView;

public interface StatisticsContract {

    interface View extends BaseView<Presenter> {

        void setProgressIndicator(boolean active);

        void displayStatistics(int numberOfIncompleteTasks, int numberOfCompletedTasks);

        void showLoadingStatisticsError();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

    }
}
