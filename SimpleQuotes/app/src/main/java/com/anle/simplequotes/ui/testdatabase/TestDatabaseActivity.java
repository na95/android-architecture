package com.anle.simplequotes.ui.testdatabase;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anle.simplequotes.R;
import com.anle.simplequotes.data.model.Quote;

import java.util.List;

public class TestDatabaseActivity extends AppCompatActivity implements LifecycleOwner {

    Context context;
    RecyclerView recyclerView;
    AdapterTestDatabase testDatabaseAdapter;
    TestDatabaseViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_database_activity);
        recyclerView = findViewById(R.id.rv_database_test);
        viewModel = new TestDatabaseViewModel(this.getApplication());
        context = this;
        updateData();
    }

    void updateData() {
        viewModel.getAllQuotes().observe(this, new Observer<List<Quote>>() {
            @Override
            public void onChanged(List<Quote> quotes) {
                testDatabaseAdapter = new AdapterTestDatabase(context, quotes);
                recyclerView.setLayoutManager(new LinearLayoutManager((context)));
                recyclerView.setAdapter(testDatabaseAdapter);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
