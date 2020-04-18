package com.anle.todomvp;

import android.content.Context;

import androidx.annotation.NonNull;

import com.anle.todomvp.data.FakeTasksRemoteDataSource;
import com.anle.todomvp.data.source.TasksDataSource;
import com.anle.todomvp.data.source.TasksRepository;
import com.anle.todomvp.data.source.local.TasksDatabase;
import com.anle.todomvp.data.source.local.TasksLocalDataSource;
import com.anle.todomvp.data.source.remote.TasksRemoteDataSource;
import com.anle.todomvp.util.AppExecutors;

import static androidx.core.util.Preconditions.checkNotNull;

/**
 * Enables injection of mock implementations for
 * {@link TasksDataSource} at compile time. This is useful for testing, since it allows us to use
 * a fake instance of the class to isolate the dependencies and run a test hermetically.
 */
public class Injection {

    public static TasksRepository provideTasksRepository(@NonNull Context context) {
        checkNotNull(context);
        TasksDatabase database = TasksDatabase.getInstance(context);
        return TasksRepository.getInstance(TasksRemoteDataSource.getInstance(),
                TasksLocalDataSource.getInstance(new AppExecutors(), database.taskDao()));
    }
}
