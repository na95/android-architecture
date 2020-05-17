package com.anle.simplequotes.data.local;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.anle.simplequotes.data.model.Quote;


/**
 * The Room Database that contains the Quote table.
 * <p>
 * Note that exportSchema should be true in production databases.
 */
@Database(entities = {Quote.class}, version = 1)
public abstract class QuotesDatabase extends RoomDatabase {

    public static final String DB_NAME = "quotes.db";
    public static QuotesDatabase INSTANCE;

    public static synchronized QuotesDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context,
                    QuotesDatabase.class,
                    DB_NAME)
                    .createFromAsset("/databases/" + DB_NAME)
                    .build();
        }

        return INSTANCE;
    }

    public abstract QuotesDAO quoteDao();
}