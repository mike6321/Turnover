package com.example.joguk.geoquizguk;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/*
* PagerAdapter
* */
public class SamplePagerAdapter extends FragmentPagerAdapter {
    // item count
    private int itemCount = 0;

    public SamplePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return SampleFragment.getInstance(position);
    }

    @Override
    public int getCount() {
        return this.itemCount;
    }

    public int getItemCount() { return this.itemCount; }
    public void setItemCount(int itemCount) { this.itemCount = itemCount; }
}
