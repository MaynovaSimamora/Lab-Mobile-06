package com.example.tp2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailStoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_story);

        ImageView ivStory = findViewById(R.id.iv_detail_story);

        // Mengambil gambar yang dikirim dari StoryAdapter
        int storyImage = getIntent().getIntExtra("STORY_IMAGE", 0);

        if (storyImage != 0) {
            ivStory.setImageResource(storyImage);
        }
    }

    // Fungsi untuk kembali ke profil saat tulisan "Tutup" diklik
    public void closeStory(View view) {
        finish();
    }
}