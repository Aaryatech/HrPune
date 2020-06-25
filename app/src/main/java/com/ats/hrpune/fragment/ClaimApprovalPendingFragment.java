package com.ats.hrpune.fragment;


import android.content.Context;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ats.hrpune.R;
import com.ats.hrpune.constant.Constants;
import com.ats.hrpune.inerfaces.AllTaskLeaveInterface;
import com.ats.hrpune.inerfaces.MyTaskLeaveInterface;
import com.ats.hrpune.model.ClaimApp;
import com.ats.hrpune.model.Login;
import com.ats.hrpune.utils.CommonDialog;
import com.ats.hrpune.utils.CustomSharedPreference;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClaimApprovalPendingFragment extends Fragment {

    private ViewPager viewPager;
    public static TabLayout tab;
    FragmentPagerAdapter adapterViewPager;

    public static ArrayList<ClaimApp> staticPendingClaim = new ArrayList<>();
    public static ArrayList<ClaimApp> staticInfoClaim = new ArrayList<>();

    CommonDialog commonDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_claim_approval_pending, container, false);
        getActivity().setTitle("Claim Approval Pending");

        viewPager = view.findViewById(R.id.viewPager);
        tab = view.findViewById(R.id.tab);

        String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_USER);
        Gson gson = new Gson();
        Login loginUser = gson.fromJson(userStr, Login.class);
        Log.e("CLAIM APP PENDING FRG: ", "--------USER-------" + loginUser);

        if (loginUser != null) {
            getPendingClaimList(loginUser.getEmpId());
        }



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
                    MyTaskLeaveInterface fragmentMyTask = (MyTaskLeaveInterface) adapterViewPager.instantiateItem(viewPager, position);
                    if (fragmentMyTask != null) {
                        fragmentMyTask.fragmentBecameVisible();
                    }
                } else if (position == 1) {
                    AllTaskLeaveInterface fragmentAllTask = (AllTaskLeaveInterface) adapterViewPager.instantiateItem(viewPager, position);
                    if (fragmentAllTask != null) {
                        fragmentAllTask.fragmentBecameVisible();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




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
                return new MyTaskClaimFragment();
            } else {
                return new AllTaskClaimFragment();
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
                    return "Pending Task";
                case 1:
                    return "Info";
                default:
                    return null;
            }
        }
    }


    private void getPendingClaimList(final Integer empId) {
        Log.e("PARAMETERS : ", "        EMP ID : " + empId);

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<ClaimApp>> listCall = Constants.myInterface.getClaimApplyListForPending(authHeader, empId);
            listCall.enqueue(new Callback<ArrayList<ClaimApp>>() {
                @Override
                public void onResponse(Call<ArrayList<ClaimApp>> call, Response<ArrayList<ClaimApp>> response) {
                    try {

                        if (response.body() != null) {

                            Log.e("PENDING CLAIM LIST : ", " - " + response.body());

                            staticPendingClaim.clear();
                            staticPendingClaim = response.body();
                            // createQuotationPDF(quotList);

                            viewPager.setCurrentItem(1);
                            viewPager.setCurrentItem(0);

                            TabLayout.Tab tab0 = tab.getTabAt(0);
                            tab0.setText("Pending Task (" + staticPendingClaim.size() + ")");

                            //  commonDialog.dismiss();


                        } else {
                            //  commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");

                            viewPager.setCurrentItem(1);
                            viewPager.setCurrentItem(0);

                        }

                        getClaimInfoList(empId);

                    } catch (Exception e) {
                        // commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                        getClaimInfoList(empId);

                        viewPager.setCurrentItem(1);
                        viewPager.setCurrentItem(0);

                    }
                }

                @Override
                public void onFailure(Call<ArrayList<ClaimApp>> call, Throwable t) {
                    //  commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                    getClaimInfoList(empId);

                    viewPager.setCurrentItem(1);
                    viewPager.setCurrentItem(0);

                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }


    private void getClaimInfoList(Integer empId) {

        Log.e("PARAMETERS : ", "        EMP ID : " + empId );


        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
//            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
//            commonDialog.show();

            Call<ArrayList<ClaimApp>> listCall = Constants.myInterface.getClaimApplyListForInfo(authHeader, empId);
            listCall.enqueue(new Callback<ArrayList<ClaimApp>>() {
                @Override
                public void onResponse(Call<ArrayList<ClaimApp>> call, Response<ArrayList<ClaimApp>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("INFO LEAVE LIST : ", " - " + response.body());

                            staticInfoClaim.clear();
                            staticInfoClaim = response.body();
                            // createQuotationPDF(quotList);

                            TabLayout.Tab tab1 = tab.getTabAt(1);
                            tab1.setText("Info (" + staticInfoClaim.size() + ")");

                            viewPager.setCurrentItem(1);
                            viewPager.setCurrentItem(0);

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");

                            viewPager.setCurrentItem(1);
                            viewPager.setCurrentItem(0);

                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();

                        viewPager.setCurrentItem(1);
                        viewPager.setCurrentItem(0);

                    }
                }

                @Override
                public void onFailure(Call<ArrayList<ClaimApp>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                    viewPager.setCurrentItem(1);
                    viewPager.setCurrentItem(0);

                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }




}
