package com.anle.todomvp.tasks;

import android.content.Context;

import androidx.annotation.NonNull;

import com.anle.todomvp.data.source.TasksRepository;
import com.anle.todomvp.data.source.local.TasksDbHelper;

import static com.google.common.base.Preconditions.checkNotNull;

public class TasksPresenter implements TasksContract.UserActionListener {

    private final TasksRepository mTasksRepository;

    private final TasksContract.View mTasksView;

    public TasksPresenter(
            @NonNull TasksRepository tasksRepository, @NonNull TasksContract.View tasksView) {
        mTasksRepository = checkNotNull(tasksRepository, "tasksRepository cannot be null");
        mTasksView = checkNotNull(tasksView, "tasksView cannot be null!");
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
