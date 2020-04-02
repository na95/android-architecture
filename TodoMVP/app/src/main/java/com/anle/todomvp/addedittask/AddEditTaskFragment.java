package com.anle.todomvp.addedittask;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anle.todomvp.Injection;
import com.anle.todomvp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class AddEditTaskFragment extends Fragment implements AddEditTaskContract.View {

    public static final String ARGUMENT_EDIT_TASK_ID = "ARGUMENT_EDIT_TASK_ID";

    private AddEditTaskContract.UserActionsListener mUserActionListener;

    private TextView mTitle;

    private TextView mContent;

    public AddEditTaskFragment() {
    }

    public static AddEditTaskFragment newInstance() {
        return new AddEditTaskFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isNewTask()) {
            String taskId = getArguments().getString(ARGUMENT_EDIT_TASK_ID);
            mUserActionListener.populateTask(taskId);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mUserActionListener = new AddEditTaskPresenter(Injection.provideTasksRepository(getContext()), this);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab_edit_task_done);
        fab.setImageResource(R.drawable.ic_done);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNewTask()) {
                    mUserActionListener.createTask(
                            mTitle.getText().toString(),
                            mContent.getText().toString());
                } else {
                    mUserActionListener.updateTask(mTitle.getText().toString(),
                            mContent.getText().toString(),
                            getArguments().getString(ARGUMENT_EDIT_TASK_ID));
                }

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_edit_task_fragment, container, false);
        mTitle = root.findViewById(R.id.add_task_title);
        mContent = root.findViewById(R.id.add_task_description);

        setHasOptionsMenu(true);
        setRetainInstance(true);
        return root;
    }

    @Override
    public void showEmptyTaskError() {
        Snackbar.make(mTitle, getString(R.string.empty_task_message), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showTasksList() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void setUserActionListener(AddEditTaskContract.UserActionsListener listener) {
        mUserActionListener = listener;
    }

    @Override
    public void setTitle(String title) {
        mTitle.setText(title);
    }

    @Override
    public void setContent(String description) {
        mContent.setText(description);
    }

    private boolean isNewTask() {
        return getArguments() == null || !getArguments().containsKey(ARGUMENT_EDIT_TASK_ID);
    }

}
