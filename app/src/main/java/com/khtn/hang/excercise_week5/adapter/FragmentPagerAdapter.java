package com.khtn.hang.excercise_week5.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.khtn.hang.excercise_week5.Constants;
import com.khtn.hang.excercise_week5.fragment.TaskFragment;

public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

    private static final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{Constants.TAG_TODO_LIST, Constants.TAG_DONE};

    private Context context;

    public FragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return TaskFragment.newInstance(tabTitles[position]);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
