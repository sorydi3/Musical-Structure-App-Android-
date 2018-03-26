package com.example.android.getaudios_from_device;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RecentlyPlayed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new FragmentRecentPlayed()).commit();
    }
}
