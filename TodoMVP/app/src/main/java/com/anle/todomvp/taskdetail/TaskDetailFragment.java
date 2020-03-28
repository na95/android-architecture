package com.anle.todomvp.taskdetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anle.todomvp.Injection;
import com.anle.todomvp.R;
import com.anle.todomvp.addedittask.AddEditTaskActivity;
import com.anle.todomvp.addedittask.AddEditTaskFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class TaskDetailFragment extends Fragment implements TaskDetailContract.View {

    public static final String EXTRA_TASK_ID = "TASK_ID";

    public static final int REQUEST_EDIT_TASK = 1;

    TaskDetailContract.UserActionsListener mUserActionsListener;

    private CheckBox mTaskDetailCompleteStatus;

    private TextView mTaskDetailTitle;

    private TextView mTaskDetailContent;


    public static TaskDetailFragment newInstance(String taskId) {
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_TASK_ID, taskId);
        TaskDetailFragment fragment = new TaskDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mUserActionsListener = new TaskDetailPresenter(Injection.provideTasksRepository(getContext()), this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_EDIT_TASK) {
            // If the task was edited successfully, go back to the list.
            if (resultCode == Activity.RESULT_OK) {
                getActivity().finish();
                return;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        openTask();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.task_detail_fragment, container, false);

        mTaskDetailCompleteStatus = root.findViewById(R.id.task_detail_checkbox);
        mTaskDetailTitle = root.findViewById(R.id.task_detail_title);
        mTaskDetailContent = root.findViewById(R.id.task_detail_desciption);

        setHasOptionsMenu(true);

        // Set up floating action button
        FloatingActionButton fab = getActivity().findViewById(R.id.fab_edit_task);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskId = getArguments().getString(EXTRA_TASK_ID);
                mUserActionsListener.editTask(taskId);
            }
        });

        return root;
    }

    private void openTask() {
        String taskId = getArguments().getString(EXTRA_TASK_ID);
        mUserActionsListener.openTask(taskId);
    }

    @Override
    public void setProgressIndicator(boolean active) {
        if (active) {
            mTaskDetailTitle.setText("");
            mTaskDetailContent.setText(getString(R.string.loading));
        }
    }

    @Override
    public void showMissingTask() {
        mTaskDetailTitle.setText("");
        mTaskDetailContent.setText(getString(R.string.no_data));
    }

    @Override
    public void hideTitle() {
        mTaskDetailTitle.setVisibility(View.GONE);
    }

    @Override
    public void showTitle(String title) {
        mTaskDetailTitle.setVisibility(View.VISIBLE);
        mTaskDetailTitle.setText(title);
    }

    @Override
    public void hideContent() {
        mTaskDetailContent.setVisibility(View.GONE);
    }

    @Override
    public void showContent(String content) {
        mTaskDetailContent.setVisibility(View.VISIBLE);
        mTaskDetailContent.setText(content);
    }

    @Override
    public void showCompletionStatus(final boolean complete) {
        mTaskDetailCompleteStatus.setChecked(complete);
        mTaskDetailCompleteStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (complete) {
                    mUserActionsListener.activateTask(getArguments().getString(EXTRA_TASK_ID));
                } else {
                    mUserActionsListener.completeTask(getArguments().getString(EXTRA_TASK_ID));
                }
            }
        });
    }

    @Override
    public void showEditTaskUI(String taskId) {
        Intent intent = new Intent(getContext(), AddEditTaskActivity.class);
        intent.putExtra(AddEditTaskFragment.ARGUMENT_EDIT_TASK_ID, taskId);
        startActivityForResult(intent, REQUEST_EDIT_TASK);
    }

    @Override
    public void showTaskDeleted() {
        getActivity().finish();
    }

    @Override
    public void showTaskMarkedComplete() {
        Snackbar.make(getView(), getString(R.string.task_marked_complete), Snackbar.LENGTH_LONG)
                .show();
        openTask();
    }

    @Override
    public void showTaskMarkedActive() {
        Snackbar.make(getView(), getString(R.string.task_marked_active), Snackbar.LENGTH_LONG)
                .show();
        openTask();
    }

    @Override
    public boolean isInactive() {
        return !isAdded();
    }

}
