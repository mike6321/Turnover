package com.example.mesung.mission07;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


public class  MainActivity extends AppCompatActivity {
    Toolbar toolbar;

    Mission07Frag mMission07Frag;
    Mission07Frag2 mMission07Frag2;
    Mission07Frag3 mMission07Frag3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar mActoinBar = getSupportActionBar();
        mActoinBar.setDisplayShowTitleEnabled(false);

        mMission07Frag2 = new Mission07Frag2();
        mMission07Frag3 = new Mission07Frag3();
        mMission07Frag = new Mission07Frag();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, mMission07Frag).commit();

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("개인정보"));
        tabs.addTab(tabs.newTab().setText("통화기록"));
        tabs.addTab(tabs.newTab().setText("연락처"));

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                Fragment selected = null;

                if(position == 0) {
                    selected = mMission07Frag;
                } else if (position == 1) {
                    selected = mMission07Frag2;
                } else if (position == 2) {
                    selected = mMission07Frag3;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
