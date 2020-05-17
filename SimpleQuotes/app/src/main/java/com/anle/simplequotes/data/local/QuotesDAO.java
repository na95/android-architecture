package com.anle.simplequotes.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.anle.simplequotes.data.model.Quote;

import java.util.List;

@Dao
public interface QuotesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertTask(Quote quote);

    @Query("SELECT * FROM Quote")
    LiveData<List<Quote>> fetchAllQuotes();

    @Query("SELECT * FROM Quote LIMIT 20")
    LiveData<List<Quote>> getQuotesList();

    @Query("SELECT * FROM Quote WHERE id=:quoteId")
    LiveData<Quote> getQuote(String quoteId);
}
