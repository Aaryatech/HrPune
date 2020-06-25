package com.ats.hrpune.fragment;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.hrpune.R;
import com.ats.hrpune.activity.ClaimHistoryActivity;
import com.ats.hrpune.adapter.ClaimAttachmentAdapter;
import com.ats.hrpune.adapter.ClaimTrailAdapter;
import com.ats.hrpune.adapter.InitalApproveAdapter;
import com.ats.hrpune.constant.Constants;
import com.ats.hrpune.model.ClaimProofList;
import com.ats.hrpune.model.Info;
import com.ats.hrpune.model.InitalApprove;
import com.ats.hrpune.model.LeaveEmployeeModel;
import com.ats.hrpune.model.Login;
import com.ats.hrpune.utils.CommonDialog;
import com.ats.hrpune.utils.CustomSharedPreference;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class InitalOTApproveFragment extends Fragment implements View.OnClickListener {

    EditText edDate;
    TextView tvSearch,tvDate;
    RecyclerView recyclerView;
    Login loginUser;
    Button btnSubmit;
    private ArrayList<InitalApprove> initalApproveList = new ArrayList<>();
    public static ArrayList<InitalApprove> assignStaticInitalList = new ArrayList<>();

    long fromDateMillis, toDateMillis;
    int yyyy, mm, dd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_inital_otapprove, container, false);
        getActivity().setTitle("Initial OT Approve");

        edDate=(EditText)view.findViewById(R.id.edDate);
        tvSearch=(TextView) view.findViewById(R.id.tvSearch);
        tvDate=(TextView) view.findViewById(R.id.tvDate);
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
        btnSubmit=(Button) view.findViewById(R.id.btnSubmit);

        String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_USER);
        Gson gson = new Gson();
        loginUser = gson.fromJson(userStr, Login.class);
        Log.e("HOME_ACTIVITY : ", "--------USER-------" + loginUser);

        edDate.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.edDate)
        {
            int yr, mn, dy;
            if (fromDateMillis > 0) {
                Calendar purchaseCal = Calendar.getInstance();
                purchaseCal.setTimeInMillis(fromDateMillis);
                yr = purchaseCal.get(Calendar.YEAR);
                mn = purchaseCal.get(Calendar.MONTH);
                dy = purchaseCal.get(Calendar.DAY_OF_MONTH);
            } else {
                Calendar purchaseCal = Calendar.getInstance();
                yr = purchaseCal.get(Calendar.YEAR);
                mn = purchaseCal.get(Calendar.MONTH);
                dy = purchaseCal.get(Calendar.DAY_OF_MONTH);
            }
            DatePickerDialog dialog = new DatePickerDialog(getContext(), fromDateListener, yr, mn, dy);
            dialog.show();
        }else if(v.getId()==R.id.tvSearch)
        {
            if (edDate.getText().toString().isEmpty()) {
                edDate.setError("Select Date");
                edDate.requestFocus();

            }else{
                edDate.setError(null);

                String date = tvDate.getText().toString();
                getInitalOTApprove(date,loginUser.getEmpId());

            }
        }else if(v.getId()==R.id.btnSubmit)
        {
            ArrayList<InitalApprove> assignedArray = new ArrayList<>();
            final ArrayList<Integer> assignedEmpIdArray = new ArrayList<>();
            if (assignStaticInitalList != null) {
                if (assignStaticInitalList.size() > 0) {
                    assignedArray.clear();
                    for (int i = 0; i < assignStaticInitalList.size(); i++) {
                        if (assignStaticInitalList.get(i).getChecked()) {
                            assignedArray.add(assignStaticInitalList.get(i));
                            assignedEmpIdArray.add(assignStaticInitalList.get(i).getId());

                        }
                    }
                }
                Log.e("ASSIGN EMP", "---------------------------------" + assignedArray);
                Log.e("ASSIGN EMP SIZE", "---------------------------------" + assignedArray.size());
                Log.e("ASSIGN EMP ID", "---------------------------------" + assignedEmpIdArray);

//                String empIds=assignedMaterialIdArray.toString().trim();
//                Log.e("ASSIGN EMP ID","---------------------------------"+empIds);
//
//                stringId = ""+empIds.substring(1, empIds.length()-1).replace("][", ",")+"";
//
//                Log.e("ASSIGN EMP ID STRING","---------------------------------"+stringId);

                if(assignedEmpIdArray.size()==0) {
                    Toast.makeText(getActivity(), "Please select Employee........", Toast.LENGTH_SHORT).show();
                }else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Do you want to Approve Inital OT ?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            saveApproveEmp(assignedEmpIdArray, 1);

                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        }
    }

    private void saveApproveEmp(ArrayList<Integer> assignedEmpIdArray, int status) {
        Log.e("PARAMETER","---------------------------------------ASSIGNE EMPLOYEE--------------------------"+assignedEmpIdArray +"           STATUS       "+status);
        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.updateOtApproveStatus(authHeader,assignedEmpIdArray,status);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("ASSIGNE EMPLOYEE : ", " ------------------------------ASSIGNE EMPLOYEE------------------------ " + response.body());
                            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, new InitalOTApproveFragment(), "HomeFragment");
                            ft.commit();

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                            builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                            builder.setMessage("Unable to process! please try again.");

                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();

                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                        builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                        builder.setMessage("Unable to process! please try again.");

                        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                }

                @Override
                public void onFailure(Call<Info> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                    builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                    builder.setMessage("Unable to process! please try again.");

                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getInitalOTApprove(String date, int empId) {
        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getActivity())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<InitalApprove>> listCall = Constants.myInterface.getDailyDailyRecordForOtApproval(authHeader, date,empId);
            listCall.enqueue(new Callback<ArrayList<InitalApprove>>() {
                @Override
                public void onResponse(Call<ArrayList<InitalApprove>> call, Response<ArrayList<InitalApprove>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("INITAL APPROVE  : ", " - " + response.body());
                            initalApproveList=response.body();
                            Log.e("INITAL APPROVE LIST : ", " - " + initalApproveList);

                            assignStaticInitalList = initalApproveList;

                            for (int i = 0; i < assignStaticInitalList.size(); i++) {
                                assignStaticInitalList.get(i).setChecked(false);
                            }

                            InitalApproveAdapter adapter = new InitalApproveAdapter(initalApproveList, getContext());
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(adapter);

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
                public void onFailure(Call<ArrayList<InitalApprove>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    DatePickerDialog.OnDateSetListener fromDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            yyyy = year;
            mm = month + 1;
            dd = dayOfMonth;
            edDate.setText(dd + "-" + mm + "-" + yyyy);
            tvDate.setText(yyyy + "-" + mm + "-" + dd);

            Calendar calendar = Calendar.getInstance();
            calendar.set(yyyy, mm - 1, dd);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR, 0);
            fromDateMillis = calendar.getTimeInMillis();
        }
    };
}
