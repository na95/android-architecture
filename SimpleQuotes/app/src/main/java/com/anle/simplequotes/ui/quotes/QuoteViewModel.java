package com.anle.simplequotes.ui.quotes;


import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.anle.simplequotes.data.local.QuotesDAO;
import com.anle.simplequotes.data.local.QuotesDatabase;
import com.anle.simplequotes.data.model.Quote;

import java.util.List;

public class QuoteViewModel extends ViewModel {

    private QuotesDAO quotesDAO;

    public QuoteViewModel(Context context) {
        quotesDAO = QuotesDatabase.getInstance(context).quoteDao();
    }

    public LiveData<List<Quote>> getQuotes() {
        return quotesDAO.getQuotesList();
    }

    public LiveData<List<Quote>> getAllQuotes() {
        return quotesDAO.fetchAllQuotes();
    }
}
