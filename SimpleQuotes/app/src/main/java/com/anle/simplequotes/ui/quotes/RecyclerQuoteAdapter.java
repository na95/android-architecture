package com.anle.simplequotes.ui.quotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anle.simplequotes.R;
import com.anle.simplequotes.data.model.Quote;
import com.anle.simplequotes.ui.testdatabase.AdapterTestDatabase;

import java.util.List;

public class RecyclerQuoteAdapter extends RecyclerView.Adapter<RecyclerQuoteAdapter.ViewHolder> {

    private List<Quote> quotesList;
    private Context context;

    public RecyclerQuoteAdapter(List<Quote> quotesList, Context context) {
        this.quotesList = quotesList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.quote_item, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Quote quote = quotesList.get(position);
        holder.author.setText(quote.getAuthor());
        holder.content.setText(quote.getContent());
    }

    @Override
    public int getItemCount() {
        return quotesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView content;
        public TextView author;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.quote_content);
            author = itemView.findViewById(R.id.quote_author);
        }
    }
}
