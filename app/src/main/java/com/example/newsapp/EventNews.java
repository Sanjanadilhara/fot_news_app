package com.example.newsapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class EventNews extends Fragment {

    public EventNews() {
        // Required empty public constructor
    }


    public static EventNews newInstance(String param1, String param2) {
        EventNews fragment = new EventNews();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_news, container, false);
        ArrayList<NewsItem> newsItems = new ArrayList<>();
        newsItems.add(new NewsItem("Underwater archaeological team uncovers ancient city ruins near Mediterranean coast", "2025-05-25 09:10:00", "https://picsum.photos/id/11/600/400"));
        newsItems.add(new NewsItem("Breakthrough battery tech charges electric vehicles in under five minutes", "2025-05-26 11:45:00", "https://picsum.photos/id/12/600/400"));
        newsItems.add(new NewsItem("Local zoo welcomes rare panda cubs in successful international breeding program", "2025-05-27 14:30:00", "https://picsum.photos/id/13/600/400"));
        newsItems.add(new NewsItem("Musical prodigy stuns audience with orchestral symphony written at age 11", "2025-05-28 19:20:00", "https://picsum.photos/id/14/600/400"));
        newsItems.add(new NewsItem("Engineers develop solar road tiles to generate power from everyday traffic", "2025-05-29 08:05:00", "https://picsum.photos/id/15/600/400"));
        newsItems.add(new NewsItem("Historic peace accord signed between long-feuding border nations", "2025-05-30 17:55:00", "https://picsum.photos/id/16/600/400"));
        newsItems.add(new NewsItem("Futuristic wearable translates animal sounds into human speech in real time", "2025-05-31 13:15:00", "https://picsum.photos/id/17/600/400"));
        newsItems.add(new NewsItem("Massive coral reef restoration project sees first signs of ecosystem recovery", "2025-06-01 07:30:00", "https://picsum.photos/id/18/600/400"));
        newsItems.add(new NewsItem("Young inventor wins global award for eco-friendly water purification device", "2025-06-02 10:25:00", "https://picsum.photos/id/19/600/400"));
        newsItems.add(new NewsItem("National space agency announces mission to establish lunar greenhouse by 2032", "2025-06-03 15:40:00", "https://picsum.photos/id/20/600/400"));
        newsItems.add(new NewsItem("Community-run urban farm produces over 5 tons of fresh produce in first year", "2025-06-04 18:00:00", "https://picsum.photos/id/21/600/400"));
        newsItems.add(new NewsItem("International art exhibit explores artificial intelligence as a creative partner", "2025-06-05 16:45:00", "https://picsum.photos/id/23/600/400"));
        newsItems.add(new NewsItem("Renewable energy surpasses fossil fuels in global electricity generation", "2025-06-06 09:55:00", "https://picsum.photos/id/24/600/400"));
        newsItems.add(new NewsItem("Wildlife corridors reduce animal-vehicle collisions by 75%, new data shows", "2025-06-07 12:15:00", "https://picsum.photos/id/25/600/400"));
        newsItems.add(new NewsItem("Cutting-edge museum uses holograms and VR to bring history to life for visitors", "2025-06-08 20:40:00", "https://picsum.photos/id/26/600/400"));


        NewsAdapter newsAdapter = new NewsAdapter(newsItems, getContext());

        RecyclerView recyclerView = view.findViewById(R.id.eventNewsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(newsAdapter);
        return view;
    }
}