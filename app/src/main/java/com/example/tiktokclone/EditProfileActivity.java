package com.example.tiktokclone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    public static final String TAG = "EditProfileActivity";
    private EditText etEditUsername;
    private EditText etEditPassword;
    private EditText etEditBio;
    private Button btnChangePhoto;
    private Button btnReset;
    private Button btnSave;
    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_layout);

        //Image button for going back to profile page
        imageButton = findViewById(R.id.ibEditProfileGoBack);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeActivity();
            }
        });

        etEditUsername = findViewById(R.id.etEditUsername);
        etEditPassword = findViewById(R.id.etEditPassword);
        etEditBio = findViewById(R.id.etEditBio);

        //Temp button to rest/clear all inputs
        btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etEditUsername.setText("");
                etEditPassword.setText("");
                etEditBio.setText("");
            }
        });
    }

    //Closes the current activity and sends the user back to profile page
    private void closeActivity() {
        finish();
    }
}
