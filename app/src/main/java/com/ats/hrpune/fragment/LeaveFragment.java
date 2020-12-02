package com.ats.hrpune.fragment;


import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ats.hrpune.R;
import com.ats.hrpune.inerfaces.AddLeaveInterface;
import com.ats.hrpune.inerfaces.LeaveHistoryInterface;
import com.ats.hrpune.model.LeaveEmployeeModel;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class LeaveFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tab;
    FragmentPagerAdapter adapterViewPager;


    public static LeaveEmployeeModel staticEmpModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leave, container, false);
        getActivity().setTitle("Leave");

        viewPager = view.findViewById(R.id.viewPager);
        tab = view.findViewById(R.id.tab);

        adapterViewPager = new ViewPagerAdapter(getChildFragmentManager(), getContext());
        viewPager.setAdapter(adapterViewPager);
        tab.post(new Runnable() {
            @Override
            public void run() {
                try {
                    tab.setupWithViewPager(viewPager);
                } catch (Exception e) {
                }
            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ////Log.e("POSITION : ", "----------------------" + position);

                if (position == 0) {
                    AddLeaveInterface fragmentAddLeave = (AddLeaveInterface) adapterViewPager.instantiateItem(viewPager, position);
                    if (fragmentAddLeave != null) {
                        fragmentAddLeave.fragmentBecameVisible();
                    }
                } else if (position == 1) {
                    LeaveHistoryInterface fragmentLeaveHistory = (LeaveHistoryInterface) adapterViewPager.instantiateItem(viewPager, position);
                    if (fragmentLeaveHistory != null) {
                        fragmentLeaveHistory.fragmentBecameVisible();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        try {
            String json = getArguments().getString("empModel");
            Gson gsonPlant = new Gson();
            staticEmpModel = gsonPlant.fromJson(json, LeaveEmployeeModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }

    public static class ViewPagerAdapter extends FragmentPagerAdapter {

        private Context mContext;

        public ViewPagerAdapter(FragmentManager fm, Context mContext) {
            super(fm);
            this.mContext = mContext;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new AddLeaveFragment();
            } else {
                return new LeaveHistoryFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Add Leave";
                case 1:
                    return "Leave History";
                default:
                    return null;
            }
        }
    }

}

