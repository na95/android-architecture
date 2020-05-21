package com.anle.simplequotes.ui.quotes;

import androidx.lifecycle.Observer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anle.simplequotes.R;
import com.anle.simplequotes.data.model.Quote;
import com.anle.simplequotes.ui.testdatabase.AdapterTestDatabase;


import java.util.List;

import static com.anle.simplequotes.ui.quotes.QuoteActivity.viewModel;

public class QuoteFragment extends Fragment {

    private QuoteViewModel mViewModel;
    private RecyclerView recyclerView;
    private RecyclerQuoteAdapter adapter;

    public static QuoteFragment newInstance() {
        return new QuoteFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View fragView = inflater.inflate(R.layout.quote_fragment, container, false);
        recyclerView = fragView.findViewById(R.id.recyclerQuotesListView);
        mViewModel = new QuoteViewModel(getActivity().getApplicationContext());
        updateData();
        return fragView;
    }

    void updateData() {
        mViewModel.getAllQuotes().observe(getActivity(), new Observer<List<Quote>>() {
            @Override
            public void onChanged(List<Quote> quotes) {
                adapter = new RecyclerQuoteAdapter(quotes, getContext());
                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                SnapHelper snapHelper = new PagerSnapHelper();
                snapHelper.attachToRecyclerView(recyclerView);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
}
