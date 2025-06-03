package com.example.newsapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class AcademicNews extends Fragment {


    public AcademicNews() {
        // Required empty public constructor
    }

    public static AcademicNews newInstance(String param1, String param2) {
        AcademicNews fragment = new AcademicNews();
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
        View view=inflater.inflate(R.layout.fragment_academic_news, container, false);

        ArrayList<NewsItem> newsItems = new ArrayList<>();
        newsItems.add(new NewsItem("Massive solar flare disrupts satellite signals across multiple continents", "2025-05-10 20:10:00", "https://picsum.photos/id/1015/600/400"));
        newsItems.add(new NewsItem("Local artist unveils a groundbreaking sculpture that redefines modern art in downtown plaza", "2025-05-11 14:30:00", "https://picsum.photos/id/1025/600/400"));
        newsItems.add(new NewsItem("Tech startup launches AI assistant designed to help senior citizens live independently", "2025-05-12 09:45:00", "https://picsum.photos/id/1035/600/400"));
        newsItems.add(new NewsItem("Scientists discover a new species of bird deep within the untouched Amazon rainforest", "2025-05-13 08:00:00", "https://picsum.photos/id/1045/600/400"));
        newsItems.add(new NewsItem("City council unanimously approves ambitious green energy project to power 60% of homes", "2025-05-14 11:15:00", "https://picsum.photos/id/1055/600/400"));
        newsItems.add(new NewsItem("Breakthrough in cancer research shows promising results in early-stage clinical trials", "2025-05-15 17:50:00", "https://picsum.photos/id/1065/600/400"));
        newsItems.add(new NewsItem("Spectacular meteor shower to light up the night sky with over 100 visible meteors per hour", "2025-05-16 22:30:00", "https://picsum.photos/id/1075/600/400"));
        newsItems.add(new NewsItem("Renovated historic library reopens, offering digitized archives and community learning spaces", "2025-05-17 10:00:00", "https://picsum.photos/id/22/600/400"));
        newsItems.add(new NewsItem("Global cyberattack targets major tech firm, compromising millions of user accounts", "2025-05-18 19:25:00", "https://picsum.photos/id/4/600/400"));
        newsItems.add(new NewsItem("Local school robotics team wins national competition with autonomous search-and-rescue robot", "2025-05-19 13:40:00", "https://picsum.photos/id/5/600/400"));
        newsItems.add(new NewsItem("New climate report warns of accelerated sea level rise and extreme weather by 2030", "2025-05-20 07:55:00", "https://picsum.photos/id/6/600/400"));
        newsItems.add(new NewsItem("International film festival showcases award-winning entries from over 40 countries", "2025-05-21 21:10:00", "https://picsum.photos/id/7/600/400"));
        newsItems.add(new NewsItem("Farmers across the Midwest celebrate bountiful harvest after months of heavy rainfall", "2025-05-22 16:20:00", "https://picsum.photos/id/8/600/400"));
        newsItems.add(new NewsItem("Next-generation smartphone features transparent OLED display and built-in hologram projector", "2025-05-23 12:35:00", "https://picsum.photos/id/9/600/400"));
        newsItems.add(new NewsItem("Annual city marathon sees record turnout as over 15,000 runners participate despite heat", "2025-05-24 06:45:00", "https://picsum.photos/id/10/600/400"));

        newsItems.add(new NewsItem("City marathon attracts record number of runners", "2025-05-24 06:45:00", "https://picsum.photos/200"));


        NewsAdapter newsAdapter = new NewsAdapter(newsItems, getContext());

        RecyclerView recyclerView = view.findViewById(R.id.academicNewsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(newsAdapter);

        return view;
    }
}