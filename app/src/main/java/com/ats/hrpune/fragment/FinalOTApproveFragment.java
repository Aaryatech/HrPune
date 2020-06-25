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
import com.ats.hrpune.adapter.FinalApproveAdapter;
import com.ats.hrpune.adapter.InitalApproveAdapter;
import com.ats.hrpune.constant.Constants;
import com.ats.hrpune.model.ClaimProof;
import com.ats.hrpune.model.FinalApprove;
import com.ats.hrpune.model.Info;
import com.ats.hrpune.model.InitalApprove;
import com.ats.hrpune.model.Login;
import com.ats.hrpune.utils.CommonDialog;
import com.ats.hrpune.utils.CustomSharedPreference;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FinalOTApproveFragment extends Fragment implements View.OnClickListener {

    EditText edDate;
    TextView tvSearch,tvDate;
    RecyclerView recyclerView;
    Login loginUser;
    Button btnSubmit;
    private ArrayList<InitalApprove> initalApproveList = new ArrayList<>();
    public static ArrayList<InitalApprove> assignStaticInitalList = new ArrayList<>();

    long fromDateMillis, toDateMillis;
    int yyyy, mm, dd;
    int month = 0, yer = 0;

    final ArrayList<Integer> assignedEmpIdArray = new ArrayList<>();
    final ArrayList<Integer> empIdList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_final_otapprove, container, false);
        getActivity().setTitle("Final OT Approve");
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
        if (v.getId() == R.id.edDate) {
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
        } else if (v.getId() == R.id.tvSearch) {
            if (edDate.getText().toString().isEmpty()) {
                edDate.setError("Select Date");
                edDate.requestFocus();

            } else {
                edDate.setError(null);

                String date = tvDate.getText().toString();
                getFinalOTApprove(date, loginUser.getEmpId());

            }
        } else if (v.getId() == R.id.btnSubmit) {
            ArrayList<InitalApprove> assignedArray = new ArrayList<>();
//            final ArrayList<Integer> assignedEmpIdArray = new ArrayList<>();
//            final ArrayList<Integer> empIdList = new ArrayList<>();
            if (assignStaticInitalList != null) {
                if (assignStaticInitalList.size() > 0) {
                    assignedArray.clear();
                    for (int i = 0; i < assignStaticInitalList.size(); i++) {
                        if (assignStaticInitalList.get(i).getChecked()) {
                            assignedArray.add(assignStaticInitalList.get(i));
                            assignedEmpIdArray.add(assignStaticInitalList.get(i).getId());
                            empIdList.add(assignStaticInitalList.get(i).getEmpId());

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

                if (assignedEmpIdArray.size() == 0) {
                    Toast.makeText(getActivity(), "Please select Employee........", Toast.LENGTH_SHORT).show();
                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Do you want to Approve Final OT ?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            saveApproveEmp(assignedEmpIdArray, 2);

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

    private void saveApproveEmp(final ArrayList<Integer> assignedEmpIdArray, int status) {
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

                            String date = tvDate.getText().toString();
                          //  SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Log.e("Date","-----------------------------------"+date);
                            Date convertedDate = null;
                            try {
                                convertedDate = dateFormat.parse(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Log.e("Date Converter","-----------------------------------"+convertedDate);
                            Calendar c = Calendar.getInstance();
                            Calendar c1 = Calendar.getInstance();
                            c.setTime(convertedDate);
                            c1.setTime(convertedDate);
                            c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
                            c1.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
                            month = (c.get(Calendar.MONTH)+1);
                            yer = c.get(Calendar.YEAR);
                            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                            Log.e("Last Day","-----------------------------------"+sdf.format(c.getTime()));
                            Log.e("First Day","-----------------------------------"+sdf.format(c1.getTime()));
                            Log.e("Month","-----------------------------------"+month);
                            Log.e("Year","-----------------------------------"+yer);

                           // FinalApprove finalApprove = new FinalApprove(sdf.format(c.getTime()),sdf.format(c1.getTime()),loginUser.getUserId(),yer,month,loginUser.getEmpId());
                            getFinalApp(sdf.format(c1.getTime()),sdf.format(c.getTime()),loginUser.getUserId(),yer,month,empIdList);

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

    private void getFinalApp(String fromDate, String toDate, int userId, int yer, int month, ArrayList<Integer> empIdArray) {
        Log.e("PARAMETERS : ", "      ------------------------------------- FROM DATE------------------ " + fromDate+"        TO DATE        "+toDate+"              USER ID      "+userId+"        YEAR        "+yer+"        MONTH       "+month+"          EMP IDS       "+assignedEmpIdArray);
        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog1 = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog1.show();

            Call<Info> listCall = Constants.myInterface.updateAttendaceFinalRecordByempId(authHeader, fromDate,toDate,userId,yer,month,empIdArray);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("APPROVE FINAL : ", " ---------------------APPROVE FINAL---------------------- " + response.body());
                            Info model = response.body();
                            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, new FinalOTApproveFragment(), "HomeFragment");
                            ft.commit();

                            commonDialog1.dismiss();


                        } else {
                            commonDialog1.dismiss();
                            Log.e("Data Null : ", "-----------");

                        }
                    } catch (Exception e) {
                        commonDialog1.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();

                    }
                }

                @Override
                public void onFailure(Call<Info> call, Throwable t) {
                    commonDialog1.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }


    private void getFinalOTApprove(String date, int empId) {
        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getActivity())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<InitalApprove>> listCall = Constants.myInterface.getDailyDailyRecordForFinalOtApproval(authHeader, date,empId);
            listCall.enqueue(new Callback<ArrayList<InitalApprove>>() {
                @Override
                public void onResponse(Call<ArrayList<InitalApprove>> call, Response<ArrayList<InitalApprove>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("FINAL APPROVE  : ", " - " + response.body());
                            initalApproveList=response.body();
                            Log.e("FINAL APPROVE LIST : ", " - " + initalApproveList);

                            assignStaticInitalList = initalApproveList;

                            for (int i = 0; i < assignStaticInitalList.size(); i++) {
                                assignStaticInitalList.get(i).setChecked(false);
                            }

                            FinalApproveAdapter adapter = new FinalApproveAdapter(initalApproveList, getContext());
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
