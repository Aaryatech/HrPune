package com.ats.hrpune.fragment;


import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ats.hrpune.R;
import com.ats.hrpune.adapter.LeaveHistoryAdapter;
import com.ats.hrpune.constant.Constants;
import com.ats.hrpune.inerfaces.LeaveHistoryInterface;
import com.ats.hrpune.model.LeaveHistory;
import com.ats.hrpune.model.MyLeaveData;
import com.ats.hrpune.utils.CommonDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ats.hrpune.fragment.LeaveFragment.staticEmpModel;
//import static com.ats.hrpune.activity.LeaveActivity.staticEmpModel;

public class LeaveHistoryFragment extends Fragment implements LeaveHistoryInterface {
    public RecyclerView recyclerView;
    public TextView tv_empName, tv_empDesignation;
    public CircleImageView iv_empPhoto;
    private ArrayList<LeaveHistory> historyList = new ArrayList<>();
    private LeaveHistoryAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leave_history, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        tv_empName = (TextView) view.findViewById(R.id.tvEmpName);
        tv_empDesignation = (TextView) view.findViewById(R.id.tvEmpDesg);
        iv_empPhoto = view.findViewById(R.id.ivPhoto);

        try {
            if (staticEmpModel != null) {

                tv_empName.setText("" + staticEmpModel.getFirstName() + " " + staticEmpModel.getMiddleName() + " " + staticEmpModel.getSurname());
                tv_empDesignation.setText("" + staticEmpModel.getCmpCode());
                getLeaveList(staticEmpModel.getEmpId());

                String imageUri = String.valueOf(staticEmpModel.getShiftname());
                try {
                    Picasso.with(getContext()).load(Constants.IMAGE_URL + "" + imageUri).placeholder(getActivity().getResources().getDrawable(R.drawable.profile)).into(iv_empPhoto);

                } catch (Exception e) {
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        // prepareData();

        return view;
    }

    private void getLeaveList(Integer empId) {

        Log.e("PARAMETERS : ", "        EMP ID : " + empId);

        ArrayList<Integer> statusList = new ArrayList<>();
        statusList.add(1);
        statusList.add(2);
        statusList.add(3);
        statusList.add(7);
        statusList.add(8);
        statusList.add(9);

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<LeaveHistory>> listCall = Constants.myInterface.getLeaveListByEmpId(authHeader, empId);
            listCall.enqueue(new Callback<ArrayList<LeaveHistory>>() {
                @Override
                public void onResponse(Call<ArrayList<LeaveHistory>> call, Response<ArrayList<LeaveHistory>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("LEAVE LIST : ", " ************* " + response.body());

                            historyList.clear();
                            historyList = response.body();

                            mAdapter = new LeaveHistoryAdapter(historyList, getActivity());
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(mAdapter);


                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<LeaveHistory>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

//    private void prepareData() {
//        LeaveHistoryTemp leaveHistoryTemp = new LeaveHistoryTemp("Medical Leave","Half Day","15/4/2019 to 18/4/2019","3 Days","Pending");
//        historyList.add(leaveHistoryTemp);
//
//         leaveHistoryTemp = new LeaveHistoryTemp("Medical Leave","Half Day","15/4/2019 to 18/4/2019","1 Days","Pending");
//        historyList.add(leaveHistoryTemp);
//
//         leaveHistoryTemp = new LeaveHistoryTemp("Medical Leave","Full Day","15/4/2019 to 18/4/2019","3 Days","Approve");
//        historyList.add(leaveHistoryTemp);
//
//         leaveHistoryTemp = new LeaveHistoryTemp("Sick Leave","Half Day","15/4/2019 to 18/4/2019","4 Days","Pending");
//        historyList.add(leaveHistoryTemp);
//
//         leaveHistoryTemp = new LeaveHistoryTemp("Medical Leave","Full Day","15/4/2019 to 18/4/2019","3 Days","Rejected");
//        historyList.add(leaveHistoryTemp);
//
//        leaveHistoryTemp = new LeaveHistoryTemp("Casual Leave","Half Day","15/4/2019 to 18/4/2019","5 Days","Pending");
//        historyList.add(leaveHistoryTemp);
//
//        leaveHistoryTemp = new LeaveHistoryTemp("Medical Leave","Full Day","15/4/2019 to 18/4/2019","3 Days","Approve");
//        historyList.add(leaveHistoryTemp);
//
//        leaveHistoryTemp = new LeaveHistoryTemp("Medical Leave","Half Day","15/4/2019 to 18/4/2019","3 Days","Pending");
//        historyList.add(leaveHistoryTemp);
//
//        leaveHistoryTemp = new LeaveHistoryTemp("Medical Leave","Half Day","15/4/2019 to 18/4/2019","3 Days","Pending");
//        historyList.add(leaveHistoryTemp);
//    }

    @Override
    public void fragmentBecameVisible() {

    }
}
