package com.anle.simplequotes.data;

import androidx.lifecycle.LiveData;

import com.anle.simplequotes.data.model.Quote;

import java.util.List;

public interface QuoteDataSource {

    LiveData<List<Quote>> getQuotesList();

    LiveData<Quote> getQuote(String quoteId);

}
