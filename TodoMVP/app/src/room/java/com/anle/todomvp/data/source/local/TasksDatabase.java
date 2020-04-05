package com.anle.todomvp.data.source.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.anle.todomvp.data.Task;

@Database(entities = {Task.class}, version = 2 )
public abstract class TasksDatabase extends RoomDatabase {

    private static final Object sLock = new Object();
    private static TasksDatabase INSTANCE;

    public static TasksDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        TasksDatabase.class, "Tasks.db")
                        .addMigrations(MIGRATION_1_2)
                        .build();
            }
            return INSTANCE;
        }
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // SQLite supports a limited operations for ALTER.
            // Changing the type of a column is not directly supported, so this is what we need
            // to do:
            // Create the new table
            database.execSQL(
                    "CREATE TABLE tasks_new (entryid TEXT NOT NULL,"
                            + "title TEXT,"
                            + "content TEXT,"
                            + "completed INTEGER NOT NULL,"
                            + "PRIMARY KEY(entryid))");
            // Copy the data
            database.execSQL("INSERT INTO tasks_new (entryid, title, content, completed) "
                    + "SELECT entryid, title, content, completed "
                    + "FROM tasks");
            // Remove the old table
            database.execSQL("DROP TABLE tasks");
            // Change the table name to the correct one
            database.execSQL("ALTER TABLE tasks_new RENAME TO tasks");
        }
    };

    public abstract TasksDao taskDao();
}
