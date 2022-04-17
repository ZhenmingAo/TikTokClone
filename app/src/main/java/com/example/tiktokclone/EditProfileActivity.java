package com.example.tiktokclone;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

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
    private File photo;

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

        //Displaying the current user info/profiles
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

        //Button for the user to choose an image from the gallery
        btnChangePhoto = findViewById(R.id.btnChangePhoto);
        btnChangePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchGallery();
            }
        });

        //Button to save the changes
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

                //TODO: Update profile picture
                //ParseUser.getCurrentUser().put("userAvatar", photo);

                //Save the changes
                ParseUser.getCurrentUser().saveInBackground();
                Toast.makeText(EditProfileActivity.this, "Save Successful!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void launchGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 3);
    }

    protected Bitmap loadFromUri(Uri photoUri) {
        Bitmap image = null;
        try {
            // check version of Android on device
            if(Build.VERSION.SDK_INT > 27){
                // on newer versions of Android, use the new decodeBitmap method
                ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), photoUri);
                image = ImageDecoder.decodeBitmap(source);
            } else {
                // support older versions of Android by using getBitmap
                image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    //Choose image from gallery and put it in ImageView
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            Bitmap bitmapImage = loadFromUri(selectedImage);

            //File imageFile = new File(getExternalCacheDir(), selectedImage.toString());
            //Bitmap photoFile = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            editProfilePic.setImageBitmap(bitmapImage);

            //ParseUser.getCurrentUser().put("userAvatar", new ParseFile(bitmapImage));
            ParseUser.getCurrentUser().saveInBackground();
        }
    }

    //Closes the current activity and sends the user back to profile page
    private void closeActivity() {
        finish();
    }
}
