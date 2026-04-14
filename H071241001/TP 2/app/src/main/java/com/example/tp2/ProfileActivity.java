package com.example.tp2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private String username;
    private int profilePic;
    private ProfileFeedAdapter feedAdapter;
    private RecyclerView rvProfileFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_profile);

            // Menerima data dari Intent
            Intent intent = getIntent();
            username = intent.getStringExtra("USERNAME");
            profilePic = intent.getIntExtra("PROFILE_PIC", R.drawable.profile1);

            // Pasang Header dan Foto Profil
            TextView tvUsername = findViewById(R.id.tv_profile_username_header);
            CircleImageView ivProfilePic = findViewById(R.id.iv_profile_pic);

            if (tvUsername != null && username != null) {
                tvUsername.setText(username);
            }
            if (ivProfilePic != null) {
                ivProfilePic.setImageResource(profilePic);
            }

            // 1. RecyclerView Story
            RecyclerView rvStory = findViewById(R.id.rv_story);
            if (rvStory != null) {
                rvStory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                StoryAdapter storyAdapter = new StoryAdapter(DataSource.generateDummyStories());
                rvStory.setAdapter(storyAdapter);
            }

            // 2. RecyclerView Grid Feed Profil
            rvProfileFeed = findViewById(R.id.rv_profile_feed);
            if (rvProfileFeed != null) {
                rvProfileFeed.setLayoutManager(new GridLayoutManager(this, 3));

                // --- INI YANG DIPERBAIKI ---
                // Memanggil getUserPosts() dari DataSource
                feedAdapter = new ProfileFeedAdapter(DataSource.getUserPosts(username));
                rvProfileFeed.setAdapter(feedAdapter);
            }

            // 3. Tombol Tambah Post
            ImageView btnAddPost = findViewById(R.id.btn_add_post);
            if (btnAddPost != null) {
                btnAddPost.setOnClickListener(v -> {
                    Intent intentAdd = new Intent(ProfileActivity.this, AddPostActivity.class);
                    intentAdd.putExtra("USERNAME", username);
                    intentAdd.putExtra("PROFILE_PIC", profilePic);
                    startActivity(intentAdd);
                });
            }

            // 4. Tombol BACK
            ImageView btnBack = findViewById(R.id.btn_back);
            if (btnBack != null) {
                btnBack.setOnClickListener(v -> finish()); // Menutup halaman profil
            }

        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Refresh halaman setiap kali kembali dari layar upload
    @Override
    protected void onResume() {
        super.onResume();

        if (rvProfileFeed != null && username != null) {
            // --- INI JUGA DIPERBAIKI ---
            feedAdapter = new ProfileFeedAdapter(DataSource.getUserPosts(username));
            rvProfileFeed.setAdapter(feedAdapter);
        }

        TextView tvPostCount = findViewById(R.id.tv_post_count);
        if (tvPostCount != null && username != null) {
            // --- INI JUGA DIPERBAIKI ---
            int jumlahPostingan = DataSource.getUserPosts(username).size();
            tvPostCount.setText(String.valueOf(jumlahPostingan));
        }
    }
}