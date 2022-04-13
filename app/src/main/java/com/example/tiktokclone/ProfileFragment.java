package com.example.tiktokclone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";
    private Button btnLogout;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView profileName;
    private TextView userID;
    private Button btnEditProfile;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileName = view.findViewById(R.id.tvProfileName);
        profileName.setText("@"+ ParseUser.getCurrentUser().getUsername());
        userID = view.findViewById(R.id.tvUserID);
        userID.setText("TikTok Clone Account - ID: " + ParseUser.getCurrentUser().getObjectId());

        //This section is to create the TabLayout (switch between "Works" and "Favorites")
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewpager02);
        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new UserPosts_Fragment(), "Works");
        viewPagerAdapter.addFragment(new Favorite_Fragment(), "Favorites");
        viewPager.setAdapter(viewPagerAdapter);

        //Logout button to log out the current user, and sends the user back to Login Page
        btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
                Toast.makeText(getActivity(), "Logged Out!", Toast.LENGTH_SHORT).show();
                goLoginActivity();
            }
        });

        //Edit profile button sends the user to edit profile page
        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goEditProfileActivity();
            }
        });
    }

    private void goLoginActivity() {
        Intent i = new Intent(getActivity(), LoginActivity.class);
        startActivity(i);
    }

    private void goEditProfileActivity() {
        Intent i = new Intent(getActivity(), EditProfileActivity.class);
        startActivity(i);
    }
}
