package com.anle.todomvp.data.source.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.anle.todomvp.data.Task;

@Database(entities = {Task.class}, version = 2)
public abstract class TasksDatabase extends RoomDatabase {

    private static final Object sLock = new Object();
    private static TasksDatabase INSTANCE;

    public static TasksDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        TasksDatabase.class, "Tasks.db")
                        .build();
            }
            return INSTANCE;
        }
    }

    public abstract TasksDao taskDao();
}
