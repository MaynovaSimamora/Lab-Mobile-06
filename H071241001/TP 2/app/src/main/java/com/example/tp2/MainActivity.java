package com.example.tp2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private FeedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvFeed = findViewById(R.id.rv_feed);
        rvFeed.setLayoutManager(new LinearLayoutManager(this));

        // Mengambil data dari wadah static
        adapter = new FeedAdapter(DataSource.getAllPosts());
        rvFeed.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh data jika ada postingan baru dari halaman lain
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}