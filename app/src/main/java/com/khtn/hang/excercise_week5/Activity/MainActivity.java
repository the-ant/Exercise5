package com.khtn.hang.excercise_week5.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.khtn.hang.excercise_week5.R;
import com.khtn.hang.excercise_week5.adapter.FragmentPagerAdapter;
import com.khtn.hang.excercise_week5.fragment.AddDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "main";

    @BindView(R.id.movie_tabs)
    TabLayout movieTabs;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private FragmentPagerAdapter fragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setSupportActionBar(toolbar);
        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        movieTabs.setupWithViewPager(viewPager);
    }

    @OnClick(R.id.btnAdd)
    public void btnAdd() {
        showAddTaskFragment();
    }

    private void showAddTaskFragment() {
        FragmentManager fm = getSupportFragmentManager();
        AddDialogFragment addDialogFragment = new AddDialogFragment();
        addDialogFragment.show(fm, "fragment_add");
    }


    public void reload(boolean isDoneTab) {
        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(fragmentPagerAdapter);
        if (isDoneTab)
            viewPager.setCurrentItem(1);
        else
            viewPager.setCurrentItem(0);
    }
}
