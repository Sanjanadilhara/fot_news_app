package com.example.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<NewsItem> localDataSet;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView newsHeadline;
        private final ImageView newsImage;
        private final TextView newstime;

        public ViewHolder( View view) {
            super(view);
            newsHeadline = (TextView) view.findViewById(R.id.newsHeadline);
            newsImage = (ImageView) view.findViewById(R.id.newsImage);
            newstime = (TextView) view.findViewById(R.id.newsTime);
        }

        public TextView getNewsHeadline() {
            return newsHeadline;
        }
        public TextView getNewstime() {
            return newstime;
        }
        public ImageView getNewsImage(){
            return newsImage;
        }
    }

    public NewsAdapter(ArrayList<NewsItem> dataSet, Context context) {
        localDataSet = dataSet;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_card, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getNewsHeadline().setText(localDataSet.get(position).headline);
        Glide.with(context).load(localDataSet.get(position).imageUrl).into(viewHolder.getNewsImage());
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}