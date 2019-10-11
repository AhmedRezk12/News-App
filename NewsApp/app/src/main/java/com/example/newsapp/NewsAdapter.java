package com.example.newsapp;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {
    List<News> mNews;

    public NewsAdapter(@NonNull Context context, @NonNull List<News> news) {
        super(context, 0, news);

        mNews = news;

    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        News current = mNews.get(position);


        if(view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }


        TextView title = (TextView) view.findViewById(R.id.title_name);
        title.setText(current.getWeb_Title());


        TextView section = (TextView) view.findViewById(R.id.section_name);
        section.setText(current.getSection_Name());

        TextView date = (TextView) view.findViewById(R.id.published_date);
        date.setText(current.getDate_Published());

        TextView author = (TextView) view.findViewById(R.id.author_name);
        author.setText(current.getAuthor_Name());

        return view;
    }
}
