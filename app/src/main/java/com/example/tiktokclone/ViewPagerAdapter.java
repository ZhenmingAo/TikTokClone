package com.example.tiktokclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private ArrayList<String> fragmentTitle = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager activity) {
        super(activity);
        this.fragmentArrayList = new ArrayList<Fragment>();
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return this.fragmentArrayList.size();
    }

    public void addFragment(Fragment fragment, String title){
        this.fragmentArrayList.add(fragment);
        this.fragmentTitle.add(title);

    }

    @Override
    public CharSequence getPageTitle(int position){
        return this.fragmentTitle.get(position);
    }
}
