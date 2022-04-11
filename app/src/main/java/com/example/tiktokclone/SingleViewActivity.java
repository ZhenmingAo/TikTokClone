package com.example.tiktokclone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseUser;

public class SingleViewActivity extends AppCompatActivity {

    public static final String TAG = "SingleViewActivity";
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate called");
        setContentView(R.layout.single_video_view);

        imageButton =  findViewById(R.id.ibGoBack);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeActivity();
            }
        });
    }

    //Closes the current activity and sends the user back to profile page
    private void closeActivity() {
        finish();
    }
}
