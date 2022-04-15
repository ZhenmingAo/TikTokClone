package com.example.tiktokclone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.parse.ParseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    public static final String TAG = "EditProfileActivity";
    private EditText etEditUsername;
    private EditText etEditPassword;
    private EditText etEditBio;
    private Button btnChangePhoto;
    private Button btnReset;
    private Button btnSave;
    private ImageButton imageButton;
    private CircleImageView editProfilePic;
    private EditText userBio;

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
        editProfilePic = findViewById(R.id.cvEditProfilePic);
        ParseFile image = ParseUser.getCurrentUser().getParseFile("userAvatar");
        if (image != null){
            Glide.with(this).load(ParseUser.getCurrentUser().getParseFile("userAvatar").getUrl()).into(editProfilePic);
        }
        userBio = findViewById(R.id.etEditBio);
        String bio = ParseUser.getCurrentUser().getString("userBio");
        if (bio != null){
            userBio.setText(ParseUser.getCurrentUser().getString("userBio"));
        }

        //Button to rest/clear all inputs
        btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etEditUsername.setText("");
                etEditPassword.setText("");
                etEditBio.setText("");
            }
        });

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Update user bio
                ParseUser.getCurrentUser().put("userBio", etEditBio.getText().toString());
                //Update username
                if (etEditUsername.getText() != null && !etEditUsername.getText().toString().isEmpty()){
                    ParseUser.getCurrentUser().setUsername(etEditUsername.getText().toString());
                }
                //Update password
                if (etEditPassword.getText() != null && !etEditPassword.getText().toString().isEmpty()){
                    ParseUser.getCurrentUser().setPassword(etEditPassword.getText().toString());
                }
                //Save the changes
                ParseUser.getCurrentUser().saveInBackground();
                Toast.makeText(EditProfileActivity.this, "Save Successful!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    //Closes the current activity and sends the user back to profile page
    private void closeActivity() {
        finish();
    }
}
