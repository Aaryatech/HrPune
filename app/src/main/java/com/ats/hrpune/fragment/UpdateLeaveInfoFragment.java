package com.ats.hrpune.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ats.hrpune.R;
import com.ats.hrpune.adapter.LeaveTrailAdapter;
import com.ats.hrpune.constant.Constants;
import com.ats.hrpune.model.ApplyLeaveDetail;
import com.ats.hrpune.model.Info;
import com.ats.hrpune.model.LeaveApp;
import com.ats.hrpune.model.Login;
import com.ats.hrpune.model.MyLeaveTrailData;
import com.ats.hrpune.model.SaveLeaveTrail;
import com.ats.hrpune.utils.CommonDialog;
import com.ats.hrpune.utils.CustomSharedPreference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateLeaveInfoFragment extends Fragment implements View.OnClickListener {

    LeaveApp leaveModel;

    private TextView tvEmpName, tvEmpDesg, tvLeaveType, tvDayType, tvDays, tvRemark, tvDate, tvStatus;
    private EditText edRemark;
    private Button btnApprove, btnReject;
    private CircleImageView ivPhoto;
    private LinearLayout llButton, llAction;

    private RecyclerView recyclerView;

    ArrayList<LeaveApp> leaveModelList = new ArrayList<>();
    LeaveApp leaveAppModel = new LeaveApp();
    ApplyLeaveDetail applyLeaveDetail;

    Login loginUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_leave_info, container, false);

        tvEmpName = view.findViewById(R.id.tvEmpName);
        tvEmpDesg = view.findViewById(R.id.tvEmpDesg);
        ivPhoto = view.findViewById(R.id.ivPhoto);
        tvLeaveType = view.findViewById(R.id.tvLeaveType);
        tvDayType = view.findViewById(R.id.tvDayType);
        tvDays = view.findViewById(R.id.tvDays);
        tvDate = view.findViewById(R.id.tvDate);
        tvRemark = view.findViewById(R.id.tvRemark);
        edRemark = view.findViewById(R.id.edRemark);
        btnApprove = view.findViewById(R.id.btnApprove);
        btnReject = view.findViewById(R.id.btnReject);
        recyclerView = view.findViewById(R.id.recyclerView);

        tvStatus = view.findViewById(R.id.tvStatus);

        llButton = view.findViewById(R.id.llButton);
        llAction = view.findViewById(R.id.llAction);

        btnApprove.setOnClickListener(this);
        btnReject.setOnClickListener(this);

        try {
            String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_USER);
            Gson gson = new Gson();
            loginUser = gson.fromJson(userStr, Login.class);
            Log.e("HOME_ACTIVITY : ", "--------USER-------" + loginUser);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String str = getArguments().getString("modelList");
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<LeaveApp>>() {
            }.getType();
            leaveModelList = gson.fromJson(str, type);

            String str1 = getArguments().getString("model");
            Gson gson1 = new Gson();
            leaveAppModel = gson1.fromJson(str1, LeaveApp.class);


            Log.e("MODEL LIST --------- ", "-------------------" + leaveModelList);

            setData();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }


    public void setData() {

        if (leaveModelList != null) {

            if (leaveModelList.size() > 0) {

                int pos = 0;
                for (int i = 0; i < leaveModelList.size(); i++) {

                    if (leaveAppModel.getLeaveId() == leaveModelList.get(i).getLeaveId()) {
                        pos = i;
                    }
                }

                leaveModel = leaveModelList.get(pos);


                try {
                    Log.e("FINAL AUTH ID : ", "------------------------ " + leaveModel.getFinAuthEmpId() + " ------------------------------- EMP ID : " + loginUser.getEmpId());

                    if (leaveModel.getFinAuthEmpId().equalsIgnoreCase(String.valueOf(loginUser.getEmpId()))) {

                        llAction.setVisibility(View.VISIBLE);

                    } else {
                        llAction.setVisibility(View.GONE);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                tvEmpName.setText("" + leaveModel.getEmpName());
                tvLeaveType.setText("" + leaveModel.getLeaveTitle());
                //tvDate.setText("" + leaveModel.getLeaveFromdt() + " to " + leaveModel.getLeaveTodt());
                tvDays.setText("" + leaveModel.getLeaveNumDays() + " days");
                tvRemark.setText("" + leaveModel.getLeaveEmpReason());

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");

                try {
                    Date d1 = sdf.parse(leaveModel.getLeaveFromdt());
                    Date d2 = sdf.parse(leaveModel.getLeaveTodt());

                    tvDate.setText("" + sdf1.format(d1.getTime()) + " to " + sdf1.format(d2.getTime()));

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String imageUri = String.valueOf(leaveModel.getEmpPhoto());
                try {
                    Picasso.with(getContext()).load(Constants.IMAGE_URL + "" + imageUri).placeholder(getActivity().getResources().getDrawable(R.drawable.profile)).into(ivPhoto);

                } catch (Exception e) {
                    e.printStackTrace();
                }


                getLeaveTrail(leaveModel.getLeaveId());
                getLeaveDetail(leaveModel.getLeaveId());

                if (leaveModel.getExInt1() == 1) {
                    tvStatus.setText("Initial & Final Approve Pending");
                    tvStatus.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
                } else if (leaveModel.getExInt1() == 2) {
                    tvStatus.setText("Final Approve Pending");
                    tvStatus.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
                } else if (leaveModel.getExInt1() == 3) {
                    tvStatus.setText("Final Approved");
                    tvStatus.setTextColor(getContext().getResources().getColor(R.color.colorApproved));
                } else if (leaveModel.getExInt1() == 8) {
                    tvStatus.setText("Initial Rejected");
                    tvStatus.setTextColor(getContext().getResources().getColor(R.color.colorRejected));
                } else if (leaveModel.getExInt1() == 9) {
                    tvStatus.setText("Final Rejected");
                    tvStatus.setTextColor(getContext().getResources().getColor(R.color.colorRejected));
                } else if (leaveModel.getExInt1() == 7) {
                    tvStatus.setText("Leave Cancelled");
                    tvStatus.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
                }


                if (leaveModel.getLeaveDuration().equals("1")) {
                    tvDayType.setText("Full Day");
                } else {
                    tvDayType.setText("Half Day");
                }



            } else {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new LeaveApprovalPendingFragment(), "HomeFragment");
                ft.commit();

            }
        }

    }


    private void getLeaveDetail(Integer leaveId) {
        Log.e("PARAMETERS : ", "      LEAVE ID    : " + leaveId );
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            String base = Constants.userName + ":" + Constants.password;
            String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

            Call<ApplyLeaveDetail> listCall = Constants.myInterface.getLeaveApplyDetailsByLeaveId(authHeader, leaveId);
            listCall.enqueue(new Callback<ApplyLeaveDetail>() {
                @Override
                public void onResponse(Call<ApplyLeaveDetail> call, Response<ApplyLeaveDetail> response) {
                    try {
                        if (response.body() != null) {
                            commonDialog.dismiss();

                            Log.e("Leave Detail : ", "------------" + response.body());

                            applyLeaveDetail=response.body();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                            Toast.makeText(getContext(), "Unable to process!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        Toast.makeText(getContext(), "Unable to process!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();

                    }
                }

                @Override
                public void onFailure(Call<ApplyLeaveDetail> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    Toast.makeText(getContext(), "Unable to process!", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnApprove) {

            final String remark = edRemark.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final String currDate = sdf.format(System.currentTimeMillis());

            final SimpleDateFormat sdf_ymd = new SimpleDateFormat("yyyy-MM-dd");
            final SimpleDateFormat sdf_dmy = new SimpleDateFormat("dd-MM-yyyy");

            String fromDate = "", toDate = "";

            try {
                Date d1 = sdf_ymd.parse(leaveModel.getLeaveFromdt());
                Date d2 = sdf_ymd.parse(leaveModel.getLeaveTodt());

                fromDate = sdf_dmy.format(d1.getTime());
                toDate = sdf_dmy.format(d2.getTime());


            } catch (ParseException e) {
                e.printStackTrace();

                fromDate = leaveModel.getLeaveFromdt();
                toDate = leaveModel.getLeaveTodt();
            }

            if (leaveModel != null && loginUser != null) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                builder.setTitle("Confirmation");
                builder.setMessage("Do you want to APPROVE the leave of employee " + leaveModel.getEmpName() + " from  " + fromDate + " to " + toDate + " for " + leaveModel.getLeaveNumDays() + " days.");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (leaveModel.getFinAuthEmpId().equalsIgnoreCase(String.valueOf(loginUser.getEmpId()))) {

                            SaveLeaveTrail saveLeaveTrail = new SaveLeaveTrail(0, leaveModel.getLeaveId(), leaveModel.getEmpId(), remark, 3, loginUser.getUserId(), "" + currDate);
                            String fromDate = null,toDate=null;

                            try {
                                Date d1 = sdf_ymd.parse(leaveModel.getLeaveFromdt());
                                Date d2 = sdf_ymd.parse(leaveModel.getLeaveTodt());

                                fromDate = sdf_dmy.format(d1.getTime());
                                toDate = sdf_dmy.format(d2.getTime());


                            } catch (ParseException e) {
                                e.printStackTrace();

                            }

                            getChangeInDaily(fromDate,toDate,leaveAppModel.getEmpId(),loginUser.getUserId(),saveLeaveTrail);

                           // updateLeaveStatus(leaveModel.getLeaveId(), 3, saveLeaveTrail);

                        } else if (leaveModel.getIniAuthEmpId().equalsIgnoreCase(String.valueOf(loginUser.getEmpId()))) {

                            SaveLeaveTrail saveLeaveTrail = new SaveLeaveTrail(0, leaveModel.getLeaveId(), leaveModel.getEmpId(), remark, 2, loginUser.getUserId(), "" + currDate);
                            updateLeaveStatus(leaveModel.getLeaveId(), 2, saveLeaveTrail);

                        }

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

            } else {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                builder.setTitle("Alert");
                builder.setMessage("Oops something went wrong!");

                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }

        } else if (v.getId() == R.id.btnReject) {

            final String remark = edRemark.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final String currDate = sdf.format(System.currentTimeMillis());

            SimpleDateFormat sdf_ymd = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf_dmy = new SimpleDateFormat("dd-MM-yyyy");

            String fromDate = "", toDate = "";

            try {
                Date d1 = sdf_ymd.parse(leaveModel.getLeaveFromdt());
                Date d2 = sdf_ymd.parse(leaveModel.getLeaveTodt());

                fromDate = sdf_dmy.format(d1.getTime());
                toDate = sdf_dmy.format(d2.getTime());


            } catch (ParseException e) {
                e.printStackTrace();

                fromDate = leaveModel.getLeaveFromdt();
                toDate = leaveModel.getLeaveTodt();
            }

            if (leaveModel != null && loginUser != null) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                builder.setTitle("Confirmation");
                builder.setMessage("Do you want to REJECT the leave of employee " + leaveModel.getEmpName() + " from  " + fromDate + " to " + toDate + " for " + leaveModel.getLeaveNumDays() + " days.");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (leaveModel.getFinAuthEmpId().equalsIgnoreCase(String.valueOf(loginUser.getEmpId()))) {

                            SaveLeaveTrail saveLeaveTrail = new SaveLeaveTrail(0, leaveModel.getLeaveId(), leaveModel.getEmpId(), remark, 9, loginUser.getUserId(), "" + currDate);
                            updateLeaveStatus(leaveModel.getLeaveId(), 9, saveLeaveTrail);

                        } else if (leaveModel.getIniAuthEmpId().equalsIgnoreCase(String.valueOf(loginUser.getEmpId()))) {

                            SaveLeaveTrail saveLeaveTrail = new SaveLeaveTrail(0, leaveModel.getLeaveId(), leaveModel.getEmpId(), remark, 8, loginUser.getUserId(), "" + currDate);
                            updateLeaveStatus(leaveModel.getLeaveId(), 8, saveLeaveTrail);

                        }

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

            } else {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                builder.setTitle("Alert");
                builder.setMessage("Oops something went wrong!");

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
    }


    private void getChangeInDaily(String strFromDate, String strTodate, Integer empId, int userId, final SaveLeaveTrail saveLeaveTrail) {

        Log.e("PARAMETERS : ", "      FROM DATE    : " + strFromDate + "      TO DATE    : " + strTodate+"      EMP ID    : " + empId+"      USER ID    : " + userId);
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            String base = Constants.userName + ":" + Constants.password;
            String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

            Call<Info> listCall = Constants.myInterface.changeInDailyDailyAfterLeaveTransactionByApp(authHeader, strFromDate ,strTodate,empId,userId);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {
                            commonDialog.dismiss();
                            Log.e("Data : ", "------------" + response.body());
                            Info info=response.body();
                            if (!info.getError()){

                                updateLeaveStatus(leaveModel.getLeaveId(), 3, saveLeaveTrail);



                            }else{

                                Log.e("ERROR ELSE","-------------------------------------"+info.getError());
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                                builder.setTitle(getResources().getString(R.string.app_name));
                                builder.setMessage(info.getMsg());
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }


                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                            Toast.makeText(getContext(), "Unable to process!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        Toast.makeText(getContext(), "Unable to process!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();


                    }
                }

                @Override
                public void onFailure(Call<Info> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    Toast.makeText(getContext(), "Unable to process!", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }


    private void getLeaveTrail(final Integer leaveId) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<MyLeaveTrailData>> listCall = Constants.myInterface.getLeaveTrail(authHeader, leaveId);
            listCall.enqueue(new Callback<ArrayList<MyLeaveTrailData>>() {
                @Override
                public void onResponse(Call<ArrayList<MyLeaveTrailData>> call, Response<ArrayList<MyLeaveTrailData>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("LEAVE TRAIL DATA : ", " - " + response.body());

                            LeaveTrailAdapter adapter = new LeaveTrailAdapter(response.body(), getContext());
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
                public void onFailure(Call<ArrayList<MyLeaveTrailData>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateLeaveStatus(final Integer leaveId, int status, final SaveLeaveTrail saveLeaveTrail) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.updateLeaveStatus(authHeader, leaveId, status);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("UPDATE LEAVE : ", " - " + response.body());

                            if (!response.body().getError()) {
                                saveLeaveTrail(leaveId, saveLeaveTrail);
                            }

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
                        builder.setMessage("Oops something went wrong!");

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
                    builder.setMessage("Oops something went wrong!");

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

    private void saveLeaveTrail(final Integer leaveId, final SaveLeaveTrail saveLeaveTrail) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<SaveLeaveTrail> listCall = Constants.myInterface.saveLeaveTrail(authHeader, saveLeaveTrail);
            listCall.enqueue(new Callback<SaveLeaveTrail>() {
                @Override
                public void onResponse(Call<SaveLeaveTrail> call, Response<SaveLeaveTrail> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("SAVE LEAVE TRAIL: ", " - " + response.body());

                            if (response.body().getTrailPkey() > 0) {

                                if(saveLeaveTrail.getLeaveStatus()==8 || saveLeaveTrail.getLeaveStatus()==9) {

                                    if(applyLeaveDetail.getLvTypeId()==1) {
                                        updateWeeklyOff(0, applyLeaveDetail.getExVar2(), leaveId, response.body().getTrailPkey());
                                    }
                                }

                                updateLeaveTrailId(leaveId, response.body().getTrailPkey());
                            }

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
                        builder.setMessage("Oops something went wrong!");

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
                public void onFailure(Call<SaveLeaveTrail> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                    builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                    builder.setMessage("Oops something went wrong!");

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

    private void updateWeeklyOff(int status, String compOff,final Integer leaveId, final int trailId) {
        Log.e("PARAMETERS : ", "      STATUS    : " + status + "      COMP OFF   : " + compOff);
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            String base = Constants.userName + ":" + Constants.password;
            String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

            Call<Info> listCall = Constants.myInterface.updateweeklyoffotStatutoused(authHeader, compOff,status);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {
                            commonDialog.dismiss();
                            Log.e("Data Weekly off : ", "------------" + response.body());

                            //  updateLeaveTrailId(leaveId, trailId);


                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                            Toast.makeText(getContext(), "Unable to process!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        Toast.makeText(getContext(), "Unable to process!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();


                    }
                }

                @Override
                public void onFailure(Call<Info> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    Toast.makeText(getContext(), "Unable to process!", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateLeaveTrailId(final Integer leaveId, int trailId) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.updateLeaveTrailId(authHeader, leaveId, trailId);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("UPDATE LEAVE ID : ", " - " + response.body());

                            if (!response.body().getError()) {

                                // Toast.makeText(getContext(), "SUCCESS", Toast.LENGTH_SHORT).show();

                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                                builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                                builder.setMessage("Success");

                                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        if (leaveModelList.size() > 0) {

                                            int pos = 0;
                                            for (int i = 0; i < leaveModelList.size(); i++) {

                                                if (leaveAppModel.getLeaveId() == leaveModelList.get(i).getLeaveId()) {
                                                    pos = i;
                                                }
                                            }

                                            leaveModelList.remove(pos);

                                            setData();

                                            edRemark.setText("");
                                        }

                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();


                            } else {

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

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                            Toast.makeText(getContext(), "FAILED", Toast.LENGTH_SHORT).show();

                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                        builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                        builder.setMessage("Oops something went wrong!");

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
                    builder.setMessage("Oops something went wrong!");

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

}
