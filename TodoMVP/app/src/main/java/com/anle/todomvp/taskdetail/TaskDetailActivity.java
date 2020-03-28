package com.anle.todomvp.taskdetail;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.anle.todomvp.R;
import com.anle.todomvp.util.ActivityUtils;

public class TaskDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_detail_activity);

        // Set up the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        if (null == savedInstanceState) {

            String taskId = getIntent().getStringExtra(TaskDetailFragment.EXTRA_TASK_ID);
            TaskDetailFragment taskDetailFrag = TaskDetailFragment.newInstance(taskId);

            Bundle bundle = new Bundle();
            bundle.putString(TaskDetailFragment.EXTRA_TASK_ID, taskId);
            taskDetailFrag.setArguments(bundle);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    taskDetailFrag, R.id.contentFrame);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
