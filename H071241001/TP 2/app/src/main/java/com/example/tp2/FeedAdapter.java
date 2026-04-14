package com.example.tp2;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private ArrayList<Post> posts;

    public FeedAdapter(ArrayList<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.tvUsername.setText(post.getUsername());
        holder.ivProfile.setImageResource(post.getProfileImage());
        holder.tvCaption.setText(post.getCaption());

        // LOGIKA GAMBAR: Cek apakah gambar dari galeri (URI) atau data dummy (Resource)
        if (post.getImageUri() != null) {
            holder.ivPostImage.setImageURI(Uri.parse(post.getImageUri()));
        } else {
            holder.ivPostImage.setImageResource(post.getPostImage());
        }

        // Klik bagian header (foto & nama) untuk pindah ke Profile
        holder.layoutHeader.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ProfileActivity.class);
            intent.putExtra("USERNAME", post.getUsername());
            intent.putExtra("PROFILE_PIC", post.getProfileImage());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return posts.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutHeader;
        ImageView ivProfile, ivPostImage;
        TextView tvUsername, tvCaption;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutHeader = itemView.findViewById(R.id.layout_header);
            ivProfile = itemView.findViewById(R.id.iv_profile);
            ivPostImage = itemView.findViewById(R.id.iv_post_image);
            tvUsername = itemView.findViewById(R.id.tv_username);
            tvCaption = itemView.findViewById(R.id.tv_caption);
        }
    }
}