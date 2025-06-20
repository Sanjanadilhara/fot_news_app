package com.example.newsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<NewsItem> localDataSet;
    private Context context;
    private String dataPath;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView newsHeadline;
        private final ImageView newsImage;
        private final TextView newstime;
        private final LinearLayout newsCard;

        public ViewHolder( View view) {
            super(view);
            newsHeadline = (TextView) view.findViewById(R.id.newsHeadline);
            newsImage = (ImageView) view.findViewById(R.id.newsImage);
            newstime = (TextView) view.findViewById(R.id.newsTime);
            newsCard = (LinearLayout) view.findViewById(R.id.newsItemContainer);
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
        public LinearLayout getNewsCard(){return newsCard;}
    }

    public NewsAdapter(ArrayList<NewsItem> dataSet, Context context, String dataPath) {
        localDataSet = dataSet;
        this.context = context;
        this.dataPath=dataPath;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_card, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final NewsItem item=localDataSet.get(position);
        viewHolder.getNewsHeadline().setText(item.title);
        Glide.with(context).load(item.image).into(viewHolder.getNewsImage());
        viewHolder.getNewstime().setText(item.getTimeAgo());
        viewHolder.getNewsCard().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewsView.class);
                intent.putExtra("path", dataPath+"/"+item.id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}