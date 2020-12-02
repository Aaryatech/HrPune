package com.ats.hrpune.activity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ats.hrpune.R;
import com.ats.hrpune.adapter.LeaveTrailAdapter;
import com.ats.hrpune.adapter.LeaveTrailListAdapter;
import com.ats.hrpune.constant.Constants;
import com.ats.hrpune.model.Login;
import com.ats.hrpune.model.MyLeaveData;
import com.ats.hrpune.model.MyLeaveTrailData;
import com.ats.hrpune.utils.CommonDialog;
import com.ats.hrpune.utils.CustomSharedPreference;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaveHistoryDetailActivity extends AppCompatActivity {
    MyLeaveData leaveHistory;
    public TextView tvLeaveType, tvDayesType, tvDayes, tvDate, tvStatus, tvEmpRemark, tvEmpName, tvEmpDesignation;
    private RecyclerView recyclerView;
    Login loginUser;
    CircleImageView ivPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_history_detail);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        setTitle("Leave History");

        tvLeaveType = (TextView) findViewById(R.id.tvLeaveType);
        tvDayesType = (TextView) findViewById(R.id.tvDayType);
        tvDayes = (TextView) findViewById(R.id.tvDays);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvEmpRemark = (TextView) findViewById(R.id.tvEmpRemark);
        tvEmpName = (TextView) findViewById(R.id.tvEmpName);
        tvEmpDesignation = (TextView) findViewById(R.id.tvEmpDesg);
        recyclerView = findViewById(R.id.recyclerView);

        ivPhoto = findViewById(R.id.ivPhoto);

        String userStr = CustomSharedPreference.getString(getApplicationContext(), CustomSharedPreference.KEY_USER);
        Gson gson = new Gson();
        loginUser = gson.fromJson(userStr, Login.class);
        Log.e("HOME_ACTIVITY : ", "--------USER-------" + loginUser);

        String upcomingStr = getIntent().getStringExtra("model");
        leaveHistory = gson.fromJson(upcomingStr, MyLeaveData.class);
        Log.e("responce", "-----------------------" + leaveHistory);

        tvEmpName.setText(leaveHistory.getEmpFname() + " " + leaveHistory.getEmpSname());

        getLeaveTrail(leaveHistory.getLeaveId());

        if (leaveHistory != null) {
            tvLeaveType.setText(leaveHistory.getLvTitle());
            tvDayes.setText(leaveHistory.getLeaveNumDays() + " days");
           // tvDate.setText(leaveHistory.getLeaveFromdt() + " to " + leaveHistory.getLeaveTodt());
            tvEmpRemark.setText(leaveHistory.getLeaveEmpReason());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");

            try {
                Date dateFrom = sdf.parse(leaveHistory.getLeaveFromdt());
                Date dateTo = sdf.parse(leaveHistory.getLeaveTodt());
                tvDate.setText("" + sdf1.format(dateFrom.getTime()) + " to " + sdf1.format(dateTo.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String imageUri = String.valueOf(leaveHistory.getEmpPhoto());
            try {
                Picasso.with(getApplicationContext()).load(Constants.IMAGE_URL+""+imageUri).placeholder(getApplicationContext().getResources().getDrawable(R.drawable.profile)).into(ivPhoto);

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (leaveHistory.getExInt1() == 1) {
                tvStatus.setText("Initial & Final Approve Pending");
                tvStatus.setTextColor(this.getResources().getColor(R.color.colorPrimaryDark));
            } else if (leaveHistory.getExInt1() == 2) {
                tvStatus.setText("Final Approve Pending");
                tvStatus.setTextColor(this.getResources().getColor(R.color.colorPrimaryDark));
            } else if (leaveHistory.getExInt1() == 3) {
                tvStatus.setText("Final Approved");
                tvStatus.setTextColor(this.getResources().getColor(R.color.colorApproved));
            } else if (leaveHistory.getExInt1() == 8) {
                tvStatus.setText("Initial Rejected");
                tvStatus.setTextColor(this.getResources().getColor(R.color.colorRejected));
            } else if (leaveHistory.getExInt1() == 9) {
                tvStatus.setText("Final Rejected");
                tvStatus.setTextColor(this.getResources().getColor(R.color.colorRejected));
            } else if (leaveHistory.getExInt1() == 7) {
                tvStatus.setText("Leave Cancelled");
                tvStatus.setTextColor(this.getResources().getColor(R.color.colorPrimaryDark));
            }

            if (leaveHistory.getLeaveDuration().equals("1")) {
                tvDayesType.setText("Full Day");
            } else {
                tvDayesType.setText("Half Day");
            }

//            if (leaveHistory.getGetLeaveStatusList() != null) {
//                if (leaveHistory.getGetLeaveStatusList().size() > 0) {
//
//                    ArrayList<MyLeaveTrailData> leaveTrailTemps = new ArrayList<>();
//
//                    for (int i = 0; i < leaveHistory.getGetLeaveStatusList().size(); i++) {
//                        leaveTrailTemps.add(leaveHistory.getGetLeaveStatusList().get(i));
//                    }
//
//                    LeaveTrailListAdapter adapter = new LeaveTrailListAdapter(leaveTrailTemps, this);
//                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
//                    recyclerView.setLayoutManager(mLayoutManager);
//                    recyclerView.setItemAnimator(new DefaultItemAnimator());
//                    recyclerView.setAdapter(adapter);
//
//                }
//            }


        }


    }

    private void getLeaveTrail(final Integer leaveId) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(LeaveHistoryDetailActivity.this)) {
            final CommonDialog commonDialog = new CommonDialog(LeaveHistoryDetailActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<MyLeaveTrailData>> listCall = Constants.myInterface.getLeaveTrail(authHeader, leaveId);
            listCall.enqueue(new Callback<ArrayList<MyLeaveTrailData>>() {
                @Override
                public void onResponse(Call<ArrayList<MyLeaveTrailData>> call, Response<ArrayList<MyLeaveTrailData>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("LEAVE TRAIL DATA : ", " - " + response.body());
                            commonDialog.dismiss();

                            LeaveTrailListAdapter adapter = new LeaveTrailListAdapter(response.body(), LeaveHistoryDetailActivity.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(LeaveHistoryDetailActivity.this);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(adapter);

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
            Toast.makeText(LeaveHistoryDetailActivity.this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
