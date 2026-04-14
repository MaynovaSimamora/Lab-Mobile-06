package com.example.tp2;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ProfileFeedAdapter extends RecyclerView.Adapter<ProfileFeedAdapter.ViewHolder> {

    private ArrayList<Post> feeds;

    public ProfileFeedAdapter(ArrayList<Post> feeds) {
        this.feeds = feeds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = feeds.get(position);

        // LOGIKA GAMBAR KOTAK PROFIL
        if (post.getImageUri() != null) {
            holder.ivProfileFeed.setImageURI(Uri.parse(post.getImageUri()));
        } else {
            holder.ivProfileFeed.setImageResource(post.getPostImage());
        }

        // Klik foto grid untuk pindah ke Detail Feed
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailFeedActivity.class);
            intent.putExtra("FEED_IMAGE", post.getPostImage());
            intent.putExtra("FEED_URI", post.getImageUri()); // Mengirim URI jika dari galeri
            intent.putExtra("FEED_CAPTION", post.getCaption());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return feeds.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProfileFeed;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileFeed = itemView.findViewById(R.id.iv_profile_feed);
        }
    }
}