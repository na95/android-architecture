package com.anle.todomvp.addedittask;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.anle.todomvp.R;
import com.anle.todomvp.util.ActivityUtils;

public class AddEditTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_task_activity);

        // Set up the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        if (null == savedInstanceState) {
            // If there's a Task ID in the Bundle, this is an edit. Otherwise it's a new task.
            if (getIntent().hasExtra(AddEditTaskFragment.ARGUMENT_EDIT_TASK_ID)) {
                String taskId = getIntent().getStringExtra(
                        AddEditTaskFragment.ARGUMENT_EDIT_TASK_ID);
                actionBar.setTitle(R.string.edit_task);
                AddEditTaskFragment addEditTaskFragment = AddEditTaskFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putString(AddEditTaskFragment.ARGUMENT_EDIT_TASK_ID, taskId);
                addEditTaskFragment.setArguments(bundle);
                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                        addEditTaskFragment, R.id.contentFrame);
            } else {
                actionBar.setTitle(R.string.add_task);
                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                        AddEditTaskFragment.newInstance(), R.id.contentFrame);
            }
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
