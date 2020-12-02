package com.ats.hrpune.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import com.ats.hrpune.R;
import com.ats.hrpune.fragment.AddLeaveFragment;
import com.ats.hrpune.fragment.LeaveHistoryFragment;
import com.ats.hrpune.model.LeaveEmployeeModel;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class LeaveActivity extends AppCompatActivity {


    public TabLayout tabLayout;
    FragmentPagerAdapter adapterViewPager;
    private ViewPager viewPager;
    public static LeaveEmployeeModel staticEmpModel;

    private int[] tabIcons = {
            android.R.drawable.ic_menu_help,
            android.R.drawable.ic_menu_help,
            android.R.drawable.ic_menu_help
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);

        Log.e("Activity","------------------------Leave------------------");
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tab);

        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        try {
            String json = getIntent().getStringExtra("empModel");
            Gson gsonPlant = new Gson();
            staticEmpModel = gsonPlant.fromJson(json, LeaveEmployeeModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new AddLeaveFragment(), "Add Leave");
        adapter.addFrag(new LeaveHistoryFragment(), "Leave History");
        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

}
