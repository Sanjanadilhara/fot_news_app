package com.example.newsapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewsView extends AppCompatActivity {

    private ImageView newsImage;
    private TextView title;
    private TextView description;
    private ImageView closeButton;

    private DatabaseReference dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_news_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.newsView), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String path = getIntent().getStringExtra("path");
        newsImage=findViewById(R.id.newsViewImage);
        title=findViewById(R.id.newsViewTitle);
        description=findViewById(R.id.newsViewDescription);
        closeButton=findViewById(R.id.closeButtonNewsView);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        DatabaseReference dbRef = FirebaseDatabase.getInstance()
                .getReference(path.split("/")[0])
                .child(path.split("/")[1])
                .child(path.split("/")[2]);

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    NewsItem newsItem = snapshot.getValue(NewsItem.class);

                    if (newsItem != null) {
                        Glide.with(NewsView.this).load(newsItem.image).into(newsImage);
                        title.setText(newsItem.title);
                        description.setText(newsItem.description);
                    } else {
                    }
                } else {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    }
}