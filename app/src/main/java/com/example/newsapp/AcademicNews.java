package com.example.newsapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AcademicNews extends Fragment {

    private DatabaseReference dbRef;

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
        dbRef = FirebaseDatabase.getInstance().getReference("news").child("academic_news");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_academic_news, container, false);

        ArrayList<NewsItem> newsItems = new ArrayList<>();

        NewsAdapter newsAdapter = new NewsAdapter(newsItems, getContext(), "news/academic_news");

        RecyclerView recyclerView = view.findViewById(R.id.academicNewsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(newsAdapter);


        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                newsItems.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    NewsItem newsItem = snapshot.getValue(NewsItem.class);
                    newsItem.id=snapshot.getKey();
                    newsItems.add(newsItem);
                    newsAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        return view;
    }
}