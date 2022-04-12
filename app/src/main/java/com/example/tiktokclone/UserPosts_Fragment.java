package com.example.tiktokclone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UserPosts_Fragment extends Fragment implements ProfilePostsAdapter.OnPostListener{

    public static final String TAG = "ProfileFragment";
    ArrayList<Post> profilePosts;
    RecyclerView rvProfilePosts;
    ProfilePostsAdapter adapter;
    Context context;

    public UserPosts_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvProfilePosts = view.findViewById(R.id.rvProfilePosts);

        profilePosts = new ArrayList<>();
        adapter = new ProfilePostsAdapter(getContext(), profilePosts, this);
        rvProfilePosts.setAdapter(adapter);

        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rvProfilePosts.setLayoutManager(gridLayoutManager);
        getQuery();
    }

    private void getQuery() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null){
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for (Post post : posts ){
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }
                // Using swiper to refresh
                adapter.clear();
                adapter.addAll(posts);
                //allPosts.addAll(posts);
                //adapter.notifyDataSetChanged();
                //swipeContainer.setRefreshing(false);
            }
        });
    }

    //Sends the user to a single video view activity when they click on the item in the profile page
    @Override
    public void onPostClick(int position) {
        Intent i = new Intent(getActivity(), SingleViewActivity.class);
        i.putExtra("username", profilePosts.get(position).getUser().getUsername());
        i.putExtra("description", profilePosts.get(position).getDescription());
        i.putExtra("videoUrl", profilePosts.get(position).getVideo().getUrl());
        startActivity(i);
    }
}