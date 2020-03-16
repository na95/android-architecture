package com.anle.todomvp.tasks;

import android.content.Context;

import com.anle.todomvp.data.source.local.TasksDbHelper;

public class TasksPresenter implements TasksContract.UserActionListener {

    private Context mContext;
    private TasksDbHelper mTaskDbHelper;

    public TasksPresenter(Context mContext) {
        this.mContext = mContext;
        mTaskDbHelper = new TasksDbHelper(mContext);
    }

    @Override
    public void loadAllTasks() {
        //TODO

    }

    @Override
    public void addNewTask() {
        //TODO
    }

    @Override
    public void openTaskDetail() {
        //TODO
    }

    @Override
    public void completeTask() {
        //TODO
    }

    @Override
    public void activateTask() {
        //TODO
    }

    @Override
    public void loadCompletedTask() {
        //TODO
    }

    @Override
    public void clearCompletedTask() {
        //TODO
    }
}
