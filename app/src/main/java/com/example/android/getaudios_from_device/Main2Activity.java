package com.example.android.getaudios_from_device;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    ViewPager mViewpager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //create a view pager to be able scroll right and left
        mViewpager =(ViewPager)findViewById(R.id.pager);
        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        AdapterFragments adapter=new AdapterFragments(getSupportFragmentManager());
        mViewpager.setAdapter(adapter);


        TabLayout tabLayout=(TabLayout)findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mViewpager);

    }
}
