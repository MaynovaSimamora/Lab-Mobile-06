package com.example.tp2;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailFeedActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_feed);

        ImageView ivFeed = findViewById(R.id.iv_detail_feed);
        TextView tvCaption = findViewById(R.id.tv_detail_caption);
        ImageView btnBack = findViewById(R.id.btn_back_detail);

        // Fungsi Back
        btnBack.setOnClickListener(v -> finish());

        String caption = getIntent().getStringExtra("FEED_CAPTION");
        int imageRes = getIntent().getIntExtra("FEED_IMAGE", 0);
        String imageUri = getIntent().getStringExtra("FEED_URI");

        tvCaption.setText(caption);
        if (imageUri != null) {
            ivFeed.setImageURI(Uri.parse(imageUri));
        } else {
            ivFeed.setImageResource(imageRes);
        }
    }
}