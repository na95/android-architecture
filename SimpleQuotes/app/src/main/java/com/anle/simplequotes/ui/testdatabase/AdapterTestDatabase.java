package com.anle.simplequotes.ui.testdatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anle.simplequotes.R;
import com.anle.simplequotes.data.model.Quote;

import java.util.List;

public class AdapterTestDatabase extends RecyclerView.Adapter<AdapterTestDatabase.QuoteViewHolder> {

    Context context;
    private List<Quote> quoteList;

    public AdapterTestDatabase(Context context, List<Quote> quoteList) {
        this.context = context;
        this.quoteList = quoteList;
    }

    @NonNull
    @Override
    public QuoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new QuoteViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuoteViewHolder holder, int position) {
        Quote quote = quoteList.get(position);
        QuoteViewHolder viewHolder = holder;
        viewHolder.id.setText(quote.getAuthor());
        viewHolder.content.setText(quote.getContent());
    }

    @Override
    public int getItemCount() {
        return quoteList.size();
    }

    class QuoteViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        TextView content;

        public QuoteViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.idquote);
            content = itemView.findViewById(R.id.contentquote);
        }
    }
}
