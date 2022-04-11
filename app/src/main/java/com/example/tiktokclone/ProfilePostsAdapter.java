package com.example.tiktokclone;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.List;

public class ProfilePostsAdapter extends RecyclerView.Adapter<ProfilePostsAdapter.ViewHolder> {

    Context context;
    List<Post> profilePosts;
    OnPostListener onPostListener;

    public ProfilePostsAdapter(Context context, List<Post> profilePosts, OnPostListener onPostListener) {
        this.context = context;
        this.profilePosts = profilePosts;
        this.onPostListener = onPostListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_profile_post, parent, false);
        return new ViewHolder(view, context, onPostListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = profilePosts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount(){ return profilePosts.size(); }

    public void clear() {
        profilePosts.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Post> postList) {
        profilePosts.addAll(postList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView ivThumbnail;
        ImageView ivPlay;
        private Context context;
        OnPostListener onPostListener;

        public ViewHolder(@NonNull View itemView, Context context, OnPostListener onPostListener) {
            super(itemView);
            ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
            ivPlay = itemView.findViewById(R.id.ivPlay);
            this.onPostListener = onPostListener;

            itemView.setOnClickListener(this);

            this.context = context;
        }

        public void bind(Post post) {
            ParseFile video = post.getVideo();
            if (video != null) {
                String videoUrl = video.getUrl();
                Glide.with(context).load(videoUrl).into(ivThumbnail);
            }
        }

        @Override
        public void onClick(View view) {
            onPostListener.onPostClick(getAdapterPosition());
        }
    }

    public interface OnPostListener{
        void onPostClick(int position);
    }
}
