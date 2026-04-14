package com.example.tp2;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {

    private ArrayList<Story> stories;

    public StoryAdapter(ArrayList<Story> stories) { this.stories = stories; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Story story = stories.get(position);
        holder.ivStory.setImageResource(story.getImage());
        holder.tvStoryTitle.setText(story.getTitle());

        // Saat story diklik, pindah ke DetailStoryActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailStoryActivity.class);
            intent.putExtra("STORY_IMAGE", story.getImage());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return stories.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView ivStory;
        TextView tvStoryTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivStory = itemView.findViewById(R.id.iv_story);
            tvStoryTitle = itemView.findViewById(R.id.tv_story_title);
        }
    }
}