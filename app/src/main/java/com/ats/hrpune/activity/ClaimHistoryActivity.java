package com.ats.hrpune.activity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ats.hrpune.R;
import com.ats.hrpune.adapter.ClaimAttachmentAdapter;
import com.ats.hrpune.adapter.ClaimTrailAdapter;
import com.ats.hrpune.constant.Constants;
import com.ats.hrpune.model.ClaimHistory;
import com.ats.hrpune.model.ClaimHistoryModel;
import com.ats.hrpune.model.ClaimProofList;
import com.ats.hrpune.model.ClaimTrailstatus;
import com.ats.hrpune.utils.CommonDialog;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ats.hrpune.fragment.ClaimFragment.staticEmpClaimModel;

public class ClaimHistoryActivity extends AppCompatActivity {
    ClaimHistory claimHistoryTemp;
    private TextView tvProject, tvClaimType, tvDate, tvAmount, tvRemark, tvStatus, tv_empName, tv_empDesignation;
    private ImageView tvPhoto1, ivPhoto2, ivPhoto3, iv_empPhoto;
    private RecyclerView recyclerView, rvAttachment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_history);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        setTitle("Claim History");

        tvProject = (TextView) findViewById(R.id.tvProject);
        tvClaimType = (TextView) findViewById(R.id.tvClaimType);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvAmount = (TextView) findViewById(R.id.tvAmount);
        tvRemark = (TextView) findViewById(R.id.tvRemark);
        tvStatus = (TextView) findViewById(R.id.tvStatus);

        tv_empName = (TextView) findViewById(R.id.tvEmpName);
        tv_empDesignation = (TextView) findViewById(R.id.tvEmpDesg);
        iv_empPhoto = (ImageView) findViewById(R.id.ivPhoto);

        recyclerView = findViewById(R.id.recyclerView);
        rvAttachment = findViewById(R.id.rvAttachment);

        try {
            if (staticEmpClaimModel != null) {

                tv_empName.setText("" + staticEmpClaimModel.getFirstName() + " " + staticEmpClaimModel.getMiddleName() + " " + staticEmpClaimModel.getSurname());
                tv_empDesignation.setText("" + staticEmpClaimModel.getCmpCode());

                String imageUri = String.valueOf(staticEmpClaimModel.getShiftname());
                try {
                    Picasso.with(getApplicationContext()).load(Constants.IMAGE_URL+""+imageUri).placeholder(getApplicationContext().getResources().getDrawable(R.drawable.profile)).into(iv_empPhoto);

                } catch (Exception e) {
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        String upcomingStr = getIntent().getStringExtra("model");
        Gson gson = new Gson();
        claimHistoryTemp = gson.fromJson(upcomingStr, ClaimHistory.class);
        Log.e("responce", "-----------------------" + claimHistoryTemp);

        tvProject.setText(claimHistoryTemp.getProjectTitle());
        tvClaimType.setText(claimHistoryTemp.getClaimTitle());
//        tvDate.setText(claimHistoryTemp.getClaimDate());
        tvAmount.setText("" + claimHistoryTemp.getClaimAmount());
       // tvRemark.setText(claimHistoryTemp.getR());

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1=new SimpleDateFormat("dd-MM-yyyy");

        try {
            Date date=sdf.parse(claimHistoryTemp.getClaimFromDate());
            tvDate.setText(""+sdf1.format(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (claimHistoryTemp.getClaimFinalStatus() == 1) {
            tvStatus.setText("Initial Pending");
            tvStatus.setTextColor(getApplicationContext().getResources().getColor(R.color.colorPrimaryDark));
        } else if (claimHistoryTemp.getClaimFinalStatus() == 2) {
            tvStatus.setText("Final Pending");
            tvStatus.setTextColor(getApplicationContext().getResources().getColor(R.color.colorPrimaryDark));
        } else if (claimHistoryTemp.getClaimFinalStatus() == 3) {
            tvStatus.setText("Final Approved");
            tvStatus.setTextColor(getApplicationContext().getResources().getColor(R.color.colorApproved));
        } else if (claimHistoryTemp.getClaimFinalStatus() == 8) {
            tvStatus.setText("Initial Rejected");
            tvStatus.setTextColor(getApplicationContext().getResources().getColor(R.color.colorRejected));
        } else if (claimHistoryTemp.getClaimFinalStatus() == 9) {
            tvStatus.setText("Final Rejected");
            tvStatus.setTextColor(getApplicationContext().getResources().getColor(R.color.colorRejected));
        } else if (claimHistoryTemp.getClaimFinalStatus() == 7) {
            tvStatus.setText("Leave Cancelled");
            tvStatus.setTextColor(getApplicationContext().getResources().getColor(R.color.colorPrimaryDark));
        }


       // getClaimTrail(claimHistoryTemp.getCaHeadId());
        getClaimProofList(claimHistoryTemp.getCaHeadId());


    }


//    private void getClaimTrail(final Integer claimId) {
//
//        String base = Constants.userName + ":" + Constants.password;
//        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
//
//        if (Constants.isOnline(ClaimHistoryActivity.this)) {
//            final CommonDialog commonDialog = new CommonDialog(ClaimHistoryActivity.this, "Loading", "Please Wait...");
//            commonDialog.show();
//
//            Call<ArrayList<ClaimTrailstatus>> listCall = Constants.myInterface.getClaimTrail(authHeader, claimId);
//            listCall.enqueue(new Callback<ArrayList<ClaimTrailstatus>>() {
//                @Override
//                public void onResponse(Call<ArrayList<ClaimTrailstatus>> call, Response<ArrayList<ClaimTrailstatus>> response) {
//                    try {
//                        if (response.body() != null) {
//
//                            Log.e("CLAIM TRAIL DATA : ", " - " + response.body());
//
//                            ClaimTrailAdapter adapter = new ClaimTrailAdapter(response.body(), ClaimHistoryActivity.this);
//                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ClaimHistoryActivity.this);
//                            recyclerView.setLayoutManager(mLayoutManager);
//                            recyclerView.setItemAnimator(new DefaultItemAnimator());
//                            recyclerView.setAdapter(adapter);
//
//                            commonDialog.dismiss();
//
//                        } else {
//                            commonDialog.dismiss();
//                            Log.e("Data Null : ", "-----------");
//
//                        }
//                    } catch (Exception e) {
//                        commonDialog.dismiss();
//                        Log.e("Exception : ", "-----------" + e.getMessage());
//                        e.printStackTrace();
//
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ArrayList<ClaimTrailstatus>> call, Throwable t) {
//                    commonDialog.dismiss();
//                    Log.e("onFailure : ", "-----------" + t.getMessage());
//                    t.printStackTrace();
//
//                }
//            });
//        } else {
//            Toast.makeText(ClaimHistoryActivity.this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void getClaimProofList(final Integer claimId) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(ClaimHistoryActivity.this)) {
            final CommonDialog commonDialog = new CommonDialog(ClaimHistoryActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<ClaimProofList>> listCall = Constants.myInterface.getClaimProofList(authHeader, claimId);
            listCall.enqueue(new Callback<ArrayList<ClaimProofList>>() {
                @Override
                public void onResponse(Call<ArrayList<ClaimProofList>> call, Response<ArrayList<ClaimProofList>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("CLAIM PROOF DATA : ", " - " + response.body());

                            ClaimAttachmentAdapter adapter = new ClaimAttachmentAdapter(response.body(), ClaimHistoryActivity.this);
//                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ClaimHistoryActivity.this);
//                            recyclerView.setLayoutManager(mLayoutManager);
//                            recyclerView.setItemAnimator(new DefaultItemAnimator());
//                            recyclerView.setAdapter(adapter);
                            rvAttachment.setLayoutManager(new LinearLayoutManager(ClaimHistoryActivity.this, LinearLayoutManager.HORIZONTAL, false));
                            rvAttachment.setAdapter(adapter);

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
                public void onFailure(Call<ArrayList<ClaimProofList>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                }
            });
        } else {
            Toast.makeText(ClaimHistoryActivity.this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
