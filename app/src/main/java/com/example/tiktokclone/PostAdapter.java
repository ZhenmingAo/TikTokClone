package com.example.tiktokclone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseFile;

import java.util.List;
import java.util.logging.Handler;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;

    public PostAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    // Using swiper to refresh
    public void clear (){
        posts.clear();
        notifyDataSetChanged();
    }

    // Using swiper to refresh
    public void addAll(List<Post> postList){
        posts.addAll(postList);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private VideoView videoView;
        private TextView tvUsername;
        private TextView tvDescription;
        private ProgressBar pbProgressbar;
        private SeekBar seekBar;
        private Runnable runnable;
        private Handler handler;
        private ImageButton like;
        Boolean clicked = true;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            videoView = itemView.findViewById(R.id.videoView);
            seekBar = itemView.findViewById(R.id.sbSeekbar);
            like = itemView.findViewById(R.id.like_btn);
        }
        @SuppressLint("ClickableViewAccessibility")
        public void bind(Post post) {
            // Bind the post data to the view elements
            tvDescription.setText(post.getDescription());
            tvUsername.setText("@"+ post.getUser().getUsername());
            ParseFile video = post.getVideo();
            if (video != null){
                videoView.setVideoPath(post.getVideo().getUrl());
            }
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    seekBar.setMax(videoView.getDuration());
                    mediaPlayer.start();
                    mediaPlayer.setLooping(true);
                    updateProgressBar();
                }

            });
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean input) {
                    if(input){
                        videoView.seekTo(progress);
                        seekBar.setProgress(progress);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            videoView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if(videoView.isPlaying()){
                        videoView.pause();
                        return false;
                    }
                    else {
                        videoView.start();
                        return false;
                    }
                }
            });

            //Temp like function
            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clicked) {
                        clicked = false;
                        like.setImageResource(R.drawable.ic_baseline_favorite_filled);
                    } else {
                        clicked = true;
                        like.setImageResource(R.drawable.ic_baseline_favorite);
                    }
                }
            });

        }

        public void updateProgressBar(){
            int progress = videoView.getCurrentPosition();
            seekBar.setProgress(progress);
            runnable = new Runnable() {
                @Override
                public void run() {
                    updateProgressBar();
                }
            };
            videoView.postDelayed(runnable, 10);
        }
    }
}
