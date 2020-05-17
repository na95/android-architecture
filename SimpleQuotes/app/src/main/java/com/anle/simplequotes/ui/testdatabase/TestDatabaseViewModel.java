package com.anle.simplequotes.ui.testdatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.anle.simplequotes.data.local.QuotesDAO;
import com.anle.simplequotes.data.local.QuotesDatabase;
import com.anle.simplequotes.data.model.Quote;

import java.util.List;

public class TestDatabaseViewModel extends AndroidViewModel {

    private QuotesDAO quotesDAO;

    public TestDatabaseViewModel(@NonNull Application application) {
        super(application);
        quotesDAO = QuotesDatabase.getInstance(application).quoteDao();
    }

    public LiveData<List<Quote>> getQuotes() {
        return quotesDAO.getQuotesList();
    }

    public LiveData<List<Quote>> getAllQuotes() {
        return quotesDAO.fetchAllQuotes();
    }
}
