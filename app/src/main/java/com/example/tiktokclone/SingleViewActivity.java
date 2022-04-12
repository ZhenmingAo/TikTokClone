package com.example.tiktokclone;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class SingleViewActivity extends AppCompatActivity {

    public static final String TAG = "SingleViewActivity";
    ImageButton imageButton;
    VideoView videoView;
    TextView userName;
    TextView desc;
    SeekBar seekBar;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate called");
        setContentView(R.layout.single_video_view);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String description = intent.getStringExtra("description");
        String videoUrl = intent.getStringExtra("videoUrl");

        imageButton =  findViewById(R.id.ibGoBack);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeActivity();
            }
        });

        videoView = findViewById(R.id.videoView);
        userName = findViewById(R.id.tvUsername);
        desc = findViewById(R.id.tvDescription);
        seekBar = findViewById(R.id.sbSeekbar);

        userName.setText("@"+ username);
        desc.setText(description);
        videoView.setVideoPath(videoUrl);
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

    //Closes the current activity and sends the user back to profile page
    private void closeActivity() {
        finish();
    }
}
