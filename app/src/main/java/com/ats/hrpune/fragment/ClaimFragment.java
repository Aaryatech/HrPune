package com.ats.hrpune.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ats.hrpune.R;
import com.ats.hrpune.inerfaces.AddClaimInterface;
import com.ats.hrpune.inerfaces.ClaimHistoryInterface;
import com.ats.hrpune.model.LeaveEmployeeModel;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

public class ClaimFragment extends Fragment {
    private ViewPager viewPager;
    private TabLayout tab;
    FragmentPagerAdapter adapterViewPager;
    public static LeaveEmployeeModel staticEmpClaimModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_claim, container, false);
        getActivity().setTitle("Claim");

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
                    AddClaimInterface fragmentAddCliam = (AddClaimInterface) adapterViewPager.instantiateItem(viewPager, position);
                    if (fragmentAddCliam != null) {
                        fragmentAddCliam.fragmentBecameVisible();
                    }
                } else if (position == 1) {
                    ClaimHistoryInterface fragmentClaimHistory = (ClaimHistoryInterface) adapterViewPager.instantiateItem(viewPager, position);
                    if (fragmentClaimHistory != null) {
                        fragmentClaimHistory.fragmentBecameVisible();
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
            staticEmpClaimModel = gsonPlant.fromJson(json, LeaveEmployeeModel.class);
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
                return new AddClaimFragment();
            } else {
                return new ClaimHistoryFragment();
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
                    return "Add Claim";
                case 1:
                    return "Claim History";
                default:
                    return null;
            }
        }
    }


}
