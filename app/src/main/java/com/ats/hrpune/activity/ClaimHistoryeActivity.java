package com.ats.hrpune.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.hrpune.R;
import com.ats.hrpune.adapter.ClaimDetailAdapter;
import com.ats.hrpune.adapter.ClaimTrailAdapter;
import com.ats.hrpune.constant.Constants;
import com.ats.hrpune.model.ClaimApp;
import com.ats.hrpune.model.ClaimDetail;
import com.ats.hrpune.model.ClaimHeader;
import com.ats.hrpune.model.ClaimHistory;
import com.ats.hrpune.model.ClaimProof;
import com.ats.hrpune.model.ClaimTrailstatus;
import com.ats.hrpune.model.StructureData;
import com.ats.hrpune.utils.CommonDialog;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ats.hrpune.fragment.ClaimFragment.staticEmpClaimModel;

public class ClaimHistoryeActivity extends AppCompatActivity {
public TextView  tvEmpolyeeName,tvEmpCode,tvDate,tvAmount,tvTitle,tvEmpName,tvEmpDesg,tvDetail;
public RecyclerView recyclerView,recyclerViewDetail;
public LinearLayout llItems;
public ImageView ivPhoto,imageView;
//ClaimHistory claimHistory;
    ClaimApp claimHistory;
    ArrayList<ClaimProof> claimProofsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_historye);
        setTitle("Claim Detail");

        tvEmpolyeeName=(TextView)findViewById(R.id.tvNameEmp);
        tvEmpName=(TextView)findViewById(R.id.tvEmpName);
        tvEmpDesg=(TextView)findViewById(R.id.tvEmpDesg);
        tvEmpCode=(TextView)findViewById(R.id.tvEmpCode);
        tvDate=(TextView)findViewById(R.id.tvDate);
        tvAmount=(TextView)findViewById(R.id.tvAmount);
        tvTitle=(TextView)findViewById(R.id.tvTitle);
        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        recyclerViewDetail=(RecyclerView) findViewById(R.id.recyclerViewDetail);
        llItems=(LinearLayout) findViewById(R.id.llItems);
        ivPhoto=(ImageView) findViewById(R.id.ivPhoto);
        imageView=(ImageView) findViewById(R.id.imageView);
        tvDetail=(TextView)findViewById(R.id.tvDetail);


        String upcomingStr = getIntent().getStringExtra("model");
        Gson gson = new Gson();
        claimHistory = gson.fromJson(upcomingStr, ClaimApp.class);
        Log.e("responce", "-----------------------" + claimHistory);


        try {
            if (claimHistory != null) {

                tvEmpName.setText("" + claimHistory.getEmpName() );
               // tvEmpDesg.setText("" + claimHistory.getCmpCode());

                if (claimHistory.getClaimStatus() == 1) {
                    tvEmpDesg.setText("Initial & Final Approve Pending");
                    tvEmpDesg.setTextColor(getApplicationContext().getResources().getColor(R.color.colorPrimaryDark));
                } else if (claimHistory.getClaimStatus() == 2) {
                    tvEmpDesg.setText("Final Approve Pending");
                    tvEmpDesg.setTextColor(getApplicationContext().getResources().getColor(R.color.colorPrimaryDark));
                } else if (claimHistory.getClaimStatus() == 3) {
                    tvEmpDesg.setText("Final Approved");
                    tvEmpDesg.setTextColor(getApplicationContext().getResources().getColor(R.color.colorApproved));
                } else if (claimHistory.getClaimStatus() == 8) {
                    tvEmpDesg.setText("Initial Rejected");
                    tvEmpDesg.setTextColor(getApplicationContext().getResources().getColor(R.color.colorRejected));
                } else if (claimHistory.getClaimStatus() == 9) {
                    tvEmpDesg.setText("Final Rejected");
                    tvEmpDesg.setTextColor(getApplicationContext().getResources().getColor(R.color.colorRejected));
                } else if (claimHistory.getClaimStatus() == 7) {
                    tvEmpDesg.setText("Cancelled");
                    tvEmpDesg.setTextColor(getApplicationContext().getResources().getColor(R.color.colorPrimaryDark));
                }

                String imageUri = String.valueOf(staticEmpClaimModel.getShiftname());
                try {
                    Picasso.with(getApplicationContext()).load(Constants.IMAGE_URL+""+imageUri).placeholder(getApplicationContext().getResources().getDrawable(R.drawable.profile)).into(ivPhoto);

                } catch (Exception e) {
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        getProof(claimHistory.getCaHeadId());
        getClaimHeaderAndTrail(claimHistory.getCaHeadId());
        getStructure(claimHistory.getEmpId());



    }

    private void getProof(Integer caHeadId) {
        Log.e("PARAMETER","               CLAIM ID         "+caHeadId);
        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(ClaimHistoryeActivity.this)) {
            final CommonDialog commonDialog = new CommonDialog(ClaimHistoryeActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<ClaimProof>> listCall = Constants.myInterface.getClaimProofByClaimId(authHeader, caHeadId);
            listCall.enqueue(new Callback<ArrayList<ClaimProof>>() {
                @Override
                public void onResponse(Call<ArrayList<ClaimProof>> call, final Response<ArrayList<ClaimProof>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("CLAIM PROFF DATA : ", " - " + response.body());
                            claimProofsList.clear();
                            claimProofsList=response.body();


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
                public void onFailure(Call<ArrayList<ClaimProof>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                }
            });
        } else {
            Toast.makeText(ClaimHistoryeActivity.this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getStructure(Integer empId) {
        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(ClaimHistoryeActivity.this)) {
            final CommonDialog commonDialog = new CommonDialog(ClaimHistoryeActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<StructureData>> listCall = Constants.myInterface.getClaimStructureDetailByEmpId(authHeader, empId);
            listCall.enqueue(new Callback<ArrayList<StructureData>>() {
                @Override
                public void onResponse(Call<ArrayList<StructureData>> call, final Response<ArrayList<StructureData>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("CLAIM STRUCTURE DATA : ", " - " + response.body());
                            getClaimDetail(claimHistory.getCaHeadId(),response.body());


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
                public void onFailure(Call<ArrayList<StructureData>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                }
            });
        } else {
            Toast.makeText(ClaimHistoryeActivity.this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }


    private void getClaimDetail(Integer caHeadId, final ArrayList<StructureData> structureDataList) {
        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(ClaimHistoryeActivity.this)) {
            final CommonDialog commonDialog = new CommonDialog(ClaimHistoryeActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<ClaimDetail>> listCall = Constants.myInterface.getClaimDetailListByEmpId(authHeader, caHeadId);
            listCall.enqueue(new Callback<ArrayList<ClaimDetail>>() {
                @Override
                public void onResponse(Call<ArrayList<ClaimDetail>> call, final Response<ArrayList<ClaimDetail>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("CLAIM DETAIL DATA : ", " - " + response.body());

                            ClaimDetailAdapter adapter = new ClaimDetailAdapter(response.body(), ClaimHistoryeActivity.this,structureDataList,claimProofsList);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ClaimHistoryeActivity.this);
                            recyclerViewDetail.setLayoutManager(mLayoutManager);
                            recyclerViewDetail.setItemAnimator(new DefaultItemAnimator());
                            recyclerViewDetail.setAdapter(adapter);

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
                public void onFailure(Call<ArrayList<ClaimDetail>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                }
            });
        } else {
            Toast.makeText(ClaimHistoryeActivity.this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getClaimHeaderAndTrail(Integer caHeadId) {
        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(ClaimHistoryeActivity.this)) {
            final CommonDialog commonDialog = new CommonDialog(ClaimHistoryeActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<ClaimHeader>> listCall = Constants.myInterface.getEmpClaimInfoListByTrailEmpId(authHeader, caHeadId);
            listCall.enqueue(new Callback<ArrayList<ClaimHeader>>() {
                @Override
                public void onResponse(Call<ArrayList<ClaimHeader>> call, final Response<ArrayList<ClaimHeader>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("CLAIM TRAIL DATA : ", " - " + response.body());

                            tvEmpolyeeName.setText(response.body().get(0).getEmpFname()+" "+response.body().get(0).getEmpSname());
                            tvEmpCode.setText(response.body().get(0).getEmpCode());
                            tvAmount.setText(response.body().get(0).getClaimAmount());
                            tvTitle.setText(response.body().get(0).getClaimTitle());
                            tvDate.setText(response.body().get(0).getCaFromDt()+" to "+response.body().get(0).getCaToDt());

                            if (response.body().get(0).getVisibleStatus() == 1) {
                                llItems.setVisibility(View.VISIBLE);
                                imageView.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_up));
                            } else {
                                llItems.setVisibility(View.GONE);
                                imageView.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_down));
                            }

                            tvDetail.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (response.body().get(0).getVisibleStatus() == 0) {
                                        response.body().get(0).setVisibleStatus(1);
                                        llItems.setVisibility(View.VISIBLE);
                                        imageView.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_up));
                                    } else if (response.body().get(0).getVisibleStatus() == 1) {
                                        response.body().get(0).setVisibleStatus(0);
                                        llItems.setVisibility(View.GONE);
                                        imageView.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_down));
                                    }
                                }
                            });

                            ClaimTrailAdapter adapter = new ClaimTrailAdapter(response.body(), ClaimHistoryeActivity.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ClaimHistoryeActivity.this);
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
                public void onFailure(Call<ArrayList<ClaimHeader>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                }
            });
        } else {
            Toast.makeText(ClaimHistoryeActivity.this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }
}
