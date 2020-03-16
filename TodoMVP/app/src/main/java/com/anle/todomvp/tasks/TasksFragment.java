package com.anle.todomvp.tasks;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.anle.todomvp.data.Task;

import java.util.List;

public class TasksFragment extends Fragment implements TasksContract.View {

    Context mContext;
    TasksPresenter mPresenter;

    public TasksFragment (Context context) {
        mContext = context;
        mPresenter = new TasksPresenter(context);
    }

    @Override
    public void setProgressIndicator(boolean active) {

    }

    @Override
    public void showAllTasks(List<Task> taskList) {

    }

    @Override
    public void showAddNewTaskUI() {

    }

    @Override
    public void showDetailTaskUI(String id) {

    }

    @Override
    public void showActivedTasks() {

    }

    @Override
    public void showCompletedTasks() {

    }

    @Override
    public void showLoadingTaskError() {

    }
}
