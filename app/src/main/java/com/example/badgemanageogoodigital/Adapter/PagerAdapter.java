package com.example.badgemanageogoodigital.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.badgemanageogoodigital.Fragment.GridFragment;

import java.util.List;

public class PagerAdapter extends FragmentPagerAdapter {

    private List<GridFragment> fragments;

    public PagerAdapter(FragmentManager fm, List<GridFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
}
