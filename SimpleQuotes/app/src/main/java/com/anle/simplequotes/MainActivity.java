package com.anle.simplequotes;


import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.anle.simplequotes.data.local.QuotesDatabase;

public class MainActivity extends AppCompatActivity {

    public QuotesDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView tv = findViewById(R.id.tv);
    }
}
