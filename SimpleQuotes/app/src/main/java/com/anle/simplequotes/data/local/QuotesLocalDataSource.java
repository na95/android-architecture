package com.anle.simplequotes.data.local;

import androidx.lifecycle.LiveData;

import com.anle.simplequotes.data.QuoteDataSource;
import com.anle.simplequotes.data.model.Quote;

import java.util.List;

public class QuotesLocalDataSource implements QuoteDataSource {

    @Override
    public LiveData<List<Quote>> getQuotesList() {
        return null;
    }

    @Override
    public LiveData<Quote> getQuote(String quoteId) {
        return null;
    }
}
