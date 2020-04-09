package com.anle.todomvp.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.anle.todomvp.data.Task;
import com.anle.todomvp.data.source.TasksDataSource;
import com.anle.todomvp.data.source.local.TasksPersistenceContract.TaskEntry;
import com.anle.todomvp.util.schedulers.BaseSchedulerProvider;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.squareup.sqlbrite2.BriteDatabase;
import com.squareup.sqlbrite2.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;

import static androidx.core.util.Preconditions.checkNotNull;

public class TasksLocalDataSource implements TasksDataSource {
    @Nullable
    private static TasksLocalDataSource INSTANCE;

    @NonNull
    private final BriteDatabase mDatabaseHelper;

    @NonNull
    private Function<Cursor, Task> mTaskMapperFunction;

    // Prevent direct instantiation.
    private TasksLocalDataSource(@NonNull Context context,
                                 @NonNull BaseSchedulerProvider schedulerProvider) {
        Preconditions.checkNotNull(context, "context cannot be null");
        Preconditions.checkNotNull(schedulerProvider, "scheduleProvider cannot be null");
        TasksDbHelper dbHelper = new TasksDbHelper(context);
        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        mDatabaseHelper = sqlBrite.wrapDatabaseHelper(dbHelper, schedulerProvider.io());
        mTaskMapperFunction = this::getTask;
    }

    @NonNull
    private Task getTask(@NonNull Cursor c) {
        String itemId = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_ENTRY_ID));
        String title = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_TITLE));
        String description =
                c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_CONTENT));
        boolean completed =
                c.getInt(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_COMPLETED)) == 1;
        return new Task(title, description, itemId, completed);
    }

    public static TasksLocalDataSource getInstance(
            @NonNull Context context,
            @NonNull BaseSchedulerProvider schedulerProvider) {
        if (INSTANCE == null) {
            INSTANCE = new TasksLocalDataSource(context, schedulerProvider);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Flowable<List<Task>> getTasks() {
        String[] projection = {
                TaskEntry.COLUMN_NAME_ENTRY_ID,
                TaskEntry.COLUMN_NAME_TITLE,
                TaskEntry.COLUMN_NAME_CONTENT,
                TaskEntry.COLUMN_NAME_COMPLETED
        };
        String sql = String.format("SELECT %s FROM %s", TextUtils.join(",", projection), TaskEntry.TABLE_NAME);
        return mDatabaseHelper.createQuery(TaskEntry.TABLE_NAME, sql)
                .mapToList(mTaskMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<Optional<Task>> getTask(@NonNull String taskId) {
        String[] projection = {
                TaskEntry.COLUMN_NAME_ENTRY_ID,
                TaskEntry.COLUMN_NAME_TITLE,
                TaskEntry.COLUMN_NAME_CONTENT,
                TaskEntry.COLUMN_NAME_COMPLETED
        };
        String sql = String.format("SELECT %s FROM %s WHERE %s LIKE ?",
                TextUtils.join(",", projection), TaskEntry.TABLE_NAME, TaskEntry.COLUMN_NAME_ENTRY_ID);
        return mDatabaseHelper.createQuery(TaskEntry.TABLE_NAME, sql, taskId)
                .mapToOneOrDefault(cursor -> Optional.of(mTaskMapperFunction.apply(cursor)), Optional.<Task>absent())
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public void saveTask(@NonNull Task task) {
        Preconditions.checkNotNull(task);
        ContentValues values = new ContentValues();
        values.put(TaskEntry.COLUMN_NAME_ENTRY_ID, task.getId());
        values.put(TaskEntry.COLUMN_NAME_TITLE, task.getTitle());
        values.put(TaskEntry.COLUMN_NAME_CONTENT, task.getContent());
        values.put(TaskEntry.COLUMN_NAME_COMPLETED, task.isCompleted());
        mDatabaseHelper.insert(TaskEntry.TABLE_NAME, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public void completeTask(@NonNull Task task) {
        completeTask(task.getId());
    }

    @Override
    public void completeTask(@NonNull String taskId) {
        ContentValues values = new ContentValues();
        values.put(TaskEntry.COLUMN_NAME_COMPLETED, true);

        String selection = TaskEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = {taskId};
        mDatabaseHelper.update(TaskEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    @Override
    public void activateTask(@NonNull Task task) {
        activateTask(task.getId());
    }

    @Override
    public void activateTask(@NonNull String taskId) {
        ContentValues values = new ContentValues();
        values.put(TaskEntry.COLUMN_NAME_COMPLETED, false);

        String selection = TaskEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = {taskId};
        mDatabaseHelper.update(TaskEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    @Override
    public void clearCompletedTasks() {
        String selection = TaskEntry.COLUMN_NAME_COMPLETED + " LIKE ?";
        String[] selectionArgs = {"1"};
        mDatabaseHelper.delete(TaskEntry.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public void refreshTasks() {
        // Not required because the {@link TasksRepository} handles the logic of refreshing the
        // tasks from all the available data sources.
    }

    @Override
    public void deleteAllTasks() {
        mDatabaseHelper.delete(TaskEntry.TABLE_NAME, null);
    }

    @Override
    public void deleteTask(@NonNull String taskId) {
        String selection = TaskEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = {taskId};
        mDatabaseHelper.delete(TaskEntry.TABLE_NAME, selection, selectionArgs);
    }
}
