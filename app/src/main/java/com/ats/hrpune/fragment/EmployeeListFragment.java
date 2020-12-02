package com.ats.hrpune.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ats.hrpune.R;
import com.ats.hrpune.activity.LeaveActivity;
import com.ats.hrpune.activity.LeaveHistoryDetailActivity;
import com.ats.hrpune.adapter.EmployeeListAdapter;
import com.ats.hrpune.constant.Constants;
import com.ats.hrpune.model.LeaveEmployeeModel;
import com.ats.hrpune.model.Login;
import com.ats.hrpune.utils.CommonDialog;
import com.ats.hrpune.utils.CustomSharedPreference;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeListFragment extends Fragment {
    private ArrayList<LeaveEmployeeModel> empList = new ArrayList<>();
    ArrayList<LeaveEmployeeModel> temp;
    private RecyclerView recyclerView;
    private EmployeeListAdapter mAdapter;
    private EditText ed_search;
    static String type, isAuth;
    Login loginUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_employee_list, container, false);
        getActivity().setTitle("Team");

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        ed_search = (EditText) view.findViewById(R.id.ed_search);

        String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_USER);
        Gson gson = new Gson();
        loginUser = gson.fromJson(userStr, Login.class);
        Log.e("HOME_ACTIVITY : ", "--------USER-------" + loginUser);


        try {
            type = getArguments().getString("type");
            isAuth = getArguments().getString("isAuth");

            Log.e("TAG","---------------------Type--------------------"+type);
            Log.e("TAG","--------------------isAuth-------------------------"+isAuth);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (isAuth.equalsIgnoreCase("1")) {

            if (type.equalsIgnoreCase("claim")) {
                getEmployeeListClaim(loginUser.getEmpId());
            } else {
                getEmployeeListLeave(loginUser.getEmpId());
            }

        } else {

           // LeaveEmployeeModel leaveEmployeeModel = new LeaveEmployeeModel(loginUser.getEmpId(), loginUser.getEmpCode(), loginUser.getCompanyId(), loginUser.getEmpCatId(), loginUser.getEmpTypeId(), loginUser.getEmpDeptId(), loginUser.getLocId(), loginUser.getEmpFname(), loginUser.getEmpMname(), loginUser.getEmpSname(), loginUser.getEmpPhoto(), loginUser.getEmpMobile1(), loginUser.getEmpMobile2(), loginUser.getEmpEmail(), loginUser.getEmpAddressTemp(), loginUser.getEmpAddressPerm(), loginUser.getEmpBloodgrp(), loginUser.getEmpEmergencyPerson1(), loginUser.getEmpEmergencyNo1(), loginUser.getEmpEmergencyPerson2(), loginUser.getEmpEmergencyNo2(), loginUser.getEmpRatePerhr(), loginUser.getEmpJoiningDate(), loginUser.getEmpPrevExpYrs(), loginUser.getEmpPrevExpMonths(), loginUser.getEmpLeavingDate(), loginUser.getEmpLeavingReason(), loginUser.getDelStatus(), loginUser.getIsActive(), loginUser.getMakerUserId(), loginUser.getMakerEnterDatetime(), loginUser.getExInt1(), loginUser.getExInt2(), loginUser.getExInt3(), loginUser.getExVar1(), loginUser.getExVar2(), loginUser.getExVar3());
            LeaveEmployeeModel leaveEmployeeModel = new LeaveEmployeeModel(loginUser.getEmpId(),loginUser.getEmpCode(),"",0,"","","",0,1,0,0,loginUser.getFirstName(),loginUser.getMiddleName(),loginUser.getSurname(),"","","","","","","","",0,0,0,0,"",0,0,0,"",0,"","",0,0,"",0,0,0,0,1,0,0,"","","","","","",0,loginUser.getEmpPhoto(),"","","","","");
          //  LeaveEmployeeModel leaveEmployeeModel = new LeaveEmployeeModel(loginUser.getEmpId(), loginUser.getEmpCode(), 1, 0, 0, 0, 0, loginUser.getFirstName(), loginUser.getMiddleName(), loginUser.getSurname(),  loginUser.getEmpPhoto(), "", "", loginUser.getEmailId(), "", "","","", "", "", "", 0, "", 0, 0, "", "", 1, 1, 0, "", 0, 0, 0, "", "", "");

            Gson gson1 = new Gson();
            String json = gson1.toJson(leaveEmployeeModel);

            if (type.equalsIgnoreCase("claim")) {

                ClaimFragment adf = new ClaimFragment();
                Bundle args = new Bundle();
                args.putString("empModel", json);
                adf.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "HomeFragment").commit();

            } else {

//
//                Intent intent = new Intent(getActivity(), LeaveActivity.class);
//                Bundle args = new Bundle();
//                intent.putExtra("empModel", json);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);

                LeaveFragment adf = new LeaveFragment();
                Bundle args = new Bundle();
                args.putString("empModel", json);
                adf.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "HomeFragment").commit();

            }

        }

        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                FilterSearch(charSequence.toString());
                // empAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // prepareData();
        return view;
    }

    private void getEmployeeListLeave(int empId) {
        Log.e("PARAMETERS : ", "       EMPID : " + empId);
        if (Constants.isOnline(getActivity())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            String base = Constants.userName + ":" + Constants.password;
            String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

            Call<ArrayList<LeaveEmployeeModel>> listCall = Constants.myInterface.getEmployeeListByEmpId(authHeader, empId);
            listCall.enqueue(new Callback<ArrayList<LeaveEmployeeModel>>() {
                @Override
                public void onResponse(Call<ArrayList<LeaveEmployeeModel>> call, Response<ArrayList<LeaveEmployeeModel>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Employee List Leave : ", "------------" + response.body());

                            empList.clear();
                            // empList = response.body();

                           // LeaveEmployeeModel leaveEmployeeModel = new LeaveEmployeeModel(loginUser.getEmpId(), loginUser.getEmpCode(), 1, loginUser.getEmpCatId(), loginUser.getEmpTypeId(), loginUser.getEmpDeptId(), loginUser.getLocId(), loginUser.getEmpFname(), loginUser.getEmpMname(), loginUser.getEmpSname(), loginUser.getEmpPhoto(), loginUser.getEmpMobile1(), loginUser.getEmpMobile2(), loginUser.getEmpEmail(), loginUser.getEmpAddressTemp(), loginUser.getEmpAddressPerm(), loginUser.getEmpBloodgrp(), loginUser.getEmpEmergencyPerson1(), loginUser.getEmpEmergencyNo1(), loginUser.getEmpEmergencyPerson2(), loginUser.getEmpEmergencyNo2(), loginUser.getEmpRatePerhr(), loginUser.getEmpJoiningDate(), loginUser.getEmpPrevExpYrs(), loginUser.getEmpPrevExpMonths(), loginUser.getEmpLeavingDate(), loginUser.getEmpLeavingReason(), loginUser.getDelStatus(), loginUser.getIsActive(), loginUser.getMakerUserId(), loginUser.getMakerEnterDatetime(), loginUser.getExInt1(), loginUser.getExInt2(), loginUser.getExInt3(), loginUser.getExVar1(), loginUser.getExVar2(), loginUser.getExVar3());
                           // LeaveEmployeeModel leaveEmployeeModel = new LeaveEmployeeModel(loginUser.getEmpId(), loginUser.getEmpCode(), 1, 0, 0, 0, 0, loginUser.getFirstName(), loginUser.getMiddleName(), loginUser.getSurname(),  loginUser.getEmpPhoto(), "", "", loginUser.getEmailId(), "", "","","", "", "", "", 0, "", 0, 0, "", "", 1, 1, 0, "", 0, 0, 0, "", "", "");
                          //  LeaveEmployeeModel leaveEmployeeModel = new LeaveEmployeeModel(loginUser.getEmpId(), loginUser.getEmpCode(), 1, 0, 0, 0, 0, loginUser.getFirstName(), loginUser.getMiddleName(), loginUser.getSurname(),"", "", "", loginUser.getEmailId(), "", "", "", "", "","", "",0, "","", 0, 0, "", "", 1, 1, 0, "", 0, 0, 0, "", "");
                            LeaveEmployeeModel leaveEmployeeModel = new LeaveEmployeeModel(loginUser.getEmpId(),loginUser.getEmpCode(),"",0,"","","",0,1,0,0,loginUser.getFirstName(),loginUser.getMiddleName(),loginUser.getSurname(),"","","","","","","","",0,0,0,0,"",0,0,0,"",0,"","",0,0,"",0,0,0,0,1,0,0,"","","","","","",0,loginUser.getEmpPhoto(),"","","","","");

                            empList.add(0, leaveEmployeeModel);

                            if (response.body().size() > 0) {
                                for (int i = 0; i < response.body().size(); i++) {

                                    if (response.body().get(i).getEmpId() != loginUser.getEmpId()) {
                                        empList.add(response.body().get(i));
                                    }

                                }
                            }


                            Log.e("Employee List Model : ", "****************" + response.body());

                            mAdapter = new EmployeeListAdapter(empList, getActivity(), type);
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
                public void onFailure(Call<ArrayList<LeaveEmployeeModel>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getEmployeeListClaim(Integer empId) {
        Log.e("PARAMETERS : ", "       EMPID : " + empId);
        if (Constants.isOnline(getActivity())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            String base = Constants.userName + ":" + Constants.password;
            String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

            Call<ArrayList<LeaveEmployeeModel>> listCall = Constants.myInterface.getEmpListForClaimAuthByEmpId(authHeader, empId);
            listCall.enqueue(new Callback<ArrayList<LeaveEmployeeModel>>() {
                @Override
                public void onResponse(Call<ArrayList<LeaveEmployeeModel>> call, Response<ArrayList<LeaveEmployeeModel>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Employee List Claim: ", "------------" + response.body());
                            empList.clear();
                            // empList = response.body();

                           // LeaveEmployeeModel leaveEmployeeModel = new LeaveEmployeeModel(loginUser.getEmpId(), loginUser.getEmpCode(),1, 0, 0, 0, 0, loginUser.getFirstName(), loginUser.getMiddleName(), loginUser.getSurname(), loginUser.getEmpPhoto(), "", "", loginUser.getEmailId(), "", "","","", "", "", "", 0, "", 0, 0, "", "", 1, 1, 0, "", 0, 0, 0, "", "", "");
                           // LeaveEmployeeModel leaveEmployeeModel = new LeaveEmployeeModel(loginUser.getEmpId(), loginUser.getEmpCode(), 1, 0, 0, 0, 0, loginUser.getFirstName(), loginUser.getMiddleName(), loginUser.getSurname(), "", "", "", loginUser.getEmailId(), "", "", "", "","", "", 0, "","", 0, 0, "", "", 1, 1, 0, "", 0, 0, 0, "", "", "");
                            LeaveEmployeeModel leaveEmployeeModel = new LeaveEmployeeModel(loginUser.getEmpId(),loginUser.getEmpCode(),"",0,"","","",0,1,0,0,loginUser.getFirstName(),loginUser.getMiddleName(),loginUser.getSurname(),"","","","","","","","",0,0,0,0,"",0,0,0,"",0,"","",0,0,"",0,0,0,0,1,0,0,"","","","","","",0,loginUser.getEmpPhoto(),"","","","","");

                            empList.add(0, leaveEmployeeModel);

                            if (response.body().size() > 0) {
                                for (int i = 0; i < response.body().size(); i++) {

                                    if (response.body().get(i).getEmpId() != loginUser.getEmpId()) {
                                        empList.add(response.body().get(i));
                                    }

                                }
                            }

                            Log.e("Employee List Model : ", "****************" + response.body());

                            mAdapter = new EmployeeListAdapter(empList, getActivity(), type);
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
                public void onFailure(Call<ArrayList<LeaveEmployeeModel>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    private void FilterSearch(String s) {
        temp = new ArrayList();
        for (LeaveEmployeeModel d : empList) {
            if (d.getFirstName().toLowerCase().contains(s.toLowerCase()) || d.getSurname().toLowerCase().contains(s.toLowerCase()) || d.getMiddleName().toLowerCase().contains(s.toLowerCase())) {
                temp.add(d);
            }
        }
        mAdapter.updateList(temp);
    }

//    private void prepareData() {
//
//        EmpListTemp empListTemp = new EmpListTemp(R.drawable.profile, "Anmol Shirke", "Developer");
//        empList.add(empListTemp);
//
//        empListTemp = new EmpListTemp(R.drawable.profile, "Sachin Handge", "Student");
//        empList.add(empListTemp);
//
//        empListTemp = new EmpListTemp(R.drawable.profile, "Aditya Joshi", "Student");
//        empList.add(empListTemp);
//
//        empListTemp = new EmpListTemp(R.drawable.profile, "Anmol Shirke", "Student");
//        empList.add(empListTemp);
//
//        empListTemp = new EmpListTemp(R.drawable.profile, "Pravin Bhamre", "Student");
//        empList.add(empListTemp);
//
//        empListTemp = new EmpListTemp(R.drawable.profile, "Jayant Patil", "Student");
//        empList.add(empListTemp);
//
//        empListTemp = new EmpListTemp(R.drawable.profile, "Tejas Patil", "Student");
//        empList.add(empListTemp);
//
//        empListTemp = new EmpListTemp(R.drawable.profile, "Action & Adventure", "Student");
//        empList.add(empListTemp);
//
//        empListTemp = new EmpListTemp(R.drawable.profile, "Monika", "Student");
//        empList.add(empListTemp);
//
//        empListTemp = new EmpListTemp(R.drawable.profile, "Action & Adventure", "Student");
//        empList.add(empListTemp);
//
//
//    }

}
