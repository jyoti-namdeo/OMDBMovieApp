package com.example.rtwm38.myomdbapp;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.rtwm38.myomdbapp.ui.FavouriteFragment;
import com.example.rtwm38.myomdbapp.ui.SearchFragment;

public class MainActivity extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Search"));
        tabLayout.addTab(tabLayout.newTab().setText("Favourites"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mViewPager = findViewById(R.id.container);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private int mPageCount;
        public SectionsPagerAdapter(FragmentManager fm, int tabCount) {
            super(fm);
            this.mPageCount = tabCount;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Fragment tab1 = new SearchFragment();
                    return tab1;
                case 1:
                    Fragment tab2 = new FavouriteFragment();
                    return tab2;
                default:
                    return new SearchFragment();
            }
        }
        @Override
        public int getCount() {
            return mPageCount;
        }
    }
}
