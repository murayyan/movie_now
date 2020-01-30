package com.murayyan.movietime.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.murayyan.movietime.R;
import com.murayyan.movietime.fragment.FavoriteMovieFragment;
import com.murayyan.movietime.fragment.FavoriteTvShowFragment;


public class SectionPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.tab_text_1,
            R.string.tab_text_2
    };

    public SectionPagerAdapter(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FavoriteMovieFragment();
                break;
            case 1:
                fragment = new FavoriteTvShowFragment();
                break;
        }
        return fragment;
    }
    @NonNull
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }
    @Override
    public int getCount() {
        return 2;
    }
}
