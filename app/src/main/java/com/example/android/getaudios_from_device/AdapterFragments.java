package com.example.android.getaudios_from_device;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by sam on 11/06/2017.
 */

public class AdapterFragments extends FragmentPagerAdapter {
    //array that store the titles of the fragments
    final String Titles[]={"Songs","RecentP","Search"};
    static final int N_TABS=3;
    public AdapterFragments(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                // The first section of the app is the most interesting -- it offers
                // a launchpad into the other demonstrations in this example application.
                return new display_all_songs();
            case 1:
                return new FragmentRecentPlayed();
            case 2:
                return new FragmentSearchView();
        }
        return null;
    }

    @Override
    public int getCount() {
        return N_TABS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }
}
