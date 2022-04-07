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

    public ProfilePostsAdapter(Context context, List<Post> profilePosts) {
        this.context = context;
        this.profilePosts = profilePosts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_profile_post, parent, false);
        return new ViewHolder(view, context);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumbnail;
        ImageView ivPlay;
        private Context context;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
            ivPlay = itemView.findViewById(R.id.ivPlay);

            this.context = context;
        }

        public void bind(Post post) {
            ParseFile video = post.getVideo();
            if (video != null) {
                String videoUrl = video.getUrl();
                Glide.with(context).load(videoUrl).into(ivThumbnail);
            }
        }

        /*TODO: Below is the logic to create the intent on the view that is in the RecyclerView.
        TODO: Need a way to send the selected item (the post) to a new activity that can show the video.
        TODO: The toast showed the correct description before making the intent.*/

        /*@Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Post post = profilePosts.get(position);
                //Toast.makeText(context, post.getDescription(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this.context, SinglePostActivity.class);
                //i.putExtra("post", post.getObjectId());
                context.startActivity(i);
            }
        }*/
    }
}
