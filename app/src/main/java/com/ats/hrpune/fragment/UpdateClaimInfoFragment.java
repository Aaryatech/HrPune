package com.ats.hrpune.fragment;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.ats.hrpune.adapter.ClaimAttachmentAdapter;
import com.ats.hrpune.adapter.ClaimDetailAdapter;
import com.ats.hrpune.adapter.ClaimTrailAdapter;
import com.ats.hrpune.constant.Constants;
import com.ats.hrpune.model.ClaimApp;
import com.ats.hrpune.model.ClaimDetail;
import com.ats.hrpune.model.ClaimHeader;
import com.ats.hrpune.model.ClaimPayroll;
import com.ats.hrpune.model.ClaimProof;
import com.ats.hrpune.model.ClaimProofList;
import com.ats.hrpune.model.ClaimTrailstatus;
import com.ats.hrpune.model.Info;
import com.ats.hrpune.model.Login;
import com.ats.hrpune.model.SaveClaimTrail;
import com.ats.hrpune.model.StructureData;
import com.ats.hrpune.utils.CommonDialog;
import com.ats.hrpune.utils.CustomSharedPreference;
import com.ats.hrpune.utils.MonthYearPickerDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateClaimInfoFragment extends Fragment implements View.OnClickListener {

    private TextView tvEmpName, tvEmpDesg, tvProject, tvClaimType, tvDate, tvAmount, tvRemark, tvStatus,tvDetail,tv_click_me;
    private TextView tvEmpolyeeName,tvEmpCode,tvTitle;
    private ImageView ivPhoto1, ivPhoto2, ivPhoto3,imageView;
    private Button btnApprove, btnReject;
    private EditText edRemark;
    private LinearLayout llAction;
    private CircleImageView ivPhoto;
    private LinearLayout llItems,linearLayoutButton;

    ClaimPayroll claimPayroll;
    String monthYearStr;

    SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
    SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");

    private RecyclerView recyclerView, rvAttachment,recyclerViewDetail;

   // ClaimApp claimModel;

    ArrayList<ClaimApp> claimModelList = new ArrayList<>();
    ClaimApp claimAppModel = new ClaimApp();

    ArrayList<ClaimProof> claimProofsList = new ArrayList<>();

    Login loginUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_claim_info, container, false);

        tvEmpName = view.findViewById(R.id.tvEmpName);
        tvEmpDesg = view.findViewById(R.id.tvEmpDesg);

        tvStatus = view.findViewById(R.id.tvStatus);

        tvEmpolyeeName=(TextView)view.findViewById(R.id.tvNameEmp);
        tvEmpCode=(TextView)view.findViewById(R.id.tvEmpCode);
        tvDate=(TextView)view.findViewById(R.id.tvDate);
        tvAmount=(TextView)view.findViewById(R.id.tvAmount);
        tvTitle=(TextView)view.findViewById(R.id.tvTitle);

        tv_click_me=(TextView)view.findViewById(R.id.tv_click_me);


        llAction = view.findViewById(R.id.llAction);
        linearLayoutButton = view.findViewById(R.id.linearLayoutButton);

        btnApprove = view.findViewById(R.id.btnApprove);
        btnReject = view.findViewById(R.id.btnReject);

        btnApprove.setOnClickListener(this);
        btnReject.setOnClickListener(this);
        tv_click_me.setOnClickListener(this);

        recyclerView = view.findViewById(R.id.recyclerView);
        rvAttachment = view.findViewById(R.id.rvAttachment);

        recyclerViewDetail=(RecyclerView) view.findViewById(R.id.recyclerViewDetail);
        llItems=(LinearLayout) view.findViewById(R.id.llItems);
        imageView=(ImageView) view.findViewById(R.id.imageView);
        tvDetail=(TextView)view.findViewById(R.id.tvDetail);


        ivPhoto = view.findViewById(R.id.ivPhoto);

        ivPhoto1 = view.findViewById(R.id.ivPhoto1);
        ivPhoto2 = view.findViewById(R.id.ivPhoto2);
        ivPhoto3 = view.findViewById(R.id.ivPhoto3);

        btnApprove = view.findViewById(R.id.btnApprove);
        btnReject = view.findViewById(R.id.btnReject);

        edRemark = view.findViewById(R.id.edRemark);

        Calendar cal= Calendar.getInstance();
        String ma=sdf.format(cal.getTime());
        tv_click_me.setText(ma);


        try {
            String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_USER);
            Gson gson = new Gson();
            loginUser = gson.fromJson(userStr, Login.class);
            Log.e("UPDATE CLAIM INFO FRG: ", "--------USER-------" + loginUser);

        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            String str = getArguments().getString("modelList");
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<ClaimApp>>() {
            }.getType();
            claimModelList = gson.fromJson(str, type);

            String str1 = getArguments().getString("model");
            Gson gson1 = new Gson();
            claimAppModel = gson1.fromJson(str1, ClaimApp.class);


            if (claimAppModel.getCaFinAuthEmpId() == loginUser.getEmpId()) {
                Log.e("Final","******************************************");
                llAction.setVisibility(View.VISIBLE);
             //   tv_click_me.setVisibility(View.VISIBLE);

            } else if (claimAppModel.getCaIniAuthEmpId() == loginUser.getEmpId()) {
                Log.e("Intital","******************************************");
                llAction.setVisibility(View.GONE);
              //  tv_click_me.setVisibility(View.GONE);

            }


            Log.e("MODEL LIST --------- ", "-------------------" + claimModelList);

           // setData();
            getProof(claimAppModel.getCaHeadId());
            getClaimHeaderAndTrail(claimAppModel.getCaHeadId());
            getStructure(claimAppModel.getEmpId());
            getClaimPayroll("payroll_claim_show");

        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }


    private void getClaimPayroll(String payrollKey) {

        Log.e("PARAMETERS : ", "      PAYROLL KEY    : " + payrollKey );
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            String base = Constants.userName + ":" + Constants.password;
            String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

            Call<ClaimPayroll> listCall = Constants.myInterface.getSettingByKey(authHeader, payrollKey);
            listCall.enqueue(new Callback<ClaimPayroll>() {
                @Override
                public void onResponse(Call<ClaimPayroll> call, Response<ClaimPayroll> response) {
                    try {
                        if (response.body() != null) {
                            commonDialog.dismiss();

                            Log.e("Claim Payroll : ", "------------" + response.body());

                            claimPayroll = response.body();

                            if (claimAppModel.getCaFinAuthEmpId() == loginUser.getEmpId()) {
                                if(claimPayroll.equals("1"))
                                {
                                    tv_click_me.setVisibility(View.VISIBLE);
                                }else{
                                    tv_click_me.setVisibility(View.GONE);
                                }

                            } else if (claimAppModel.getCaIniAuthEmpId() == loginUser.getEmpId()) {

                                tv_click_me.setVisibility(View.GONE);

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
                public void onFailure(Call<ClaimPayroll> call, Throwable t) {
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


    public void setData() {
        if (claimModelList != null) {

            if (claimModelList.size() > 0) {

                int pos = 0;
                for (int i = 0; i < claimModelList.size(); i++) {

                    if (claimAppModel.getCaHeadId() == claimModelList.get(i).getCaHeadId()) {
                        pos = i;
                    }
                }

                claimAppModel = claimModelList.get(pos);

                tvEmpName.setText("" + claimAppModel.getEmpName());
                tvEmpName.setText("" + claimAppModel.getEmpName());
                tvProject.setText("" + claimAppModel.getProjectTitle());
                tvClaimType.setText("" + claimAppModel.getClaimTitle());
                tvDate.setText("" + claimAppModel.getCaFromDt()+" to "+claimAppModel.getCaToDt());
                tvAmount.setText("" + claimAppModel.getClaimAmount() + "/-");
               // tvRemark.setText("" + claimModel.getClaimRemarks());

                String imageUri = String.valueOf(claimAppModel.getEmpPhoto());
                try {
                    Picasso.with(getContext()).load(Constants.IMAGE_URL + "" + imageUri).placeholder(getActivity().getResources().getDrawable(R.drawable.profile)).into(ivPhoto);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                getClaimTrail(claimAppModel.getCaHeadId());
                getClaimProofList(claimAppModel.getCaHeadId());

                Log.e("STATUS", "---------*********************------------------1111--------------------- " + claimAppModel.getClaimStatus());

                Log.e("STATUS", "---------*********************-------------------22222-------------------- " + claimAppModel.getClaimStatus());
                if (claimAppModel.getClaimStatus() == 1) {
                    tvStatus.setText("Initial Pending");
                    tvStatus.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
                } else if (claimAppModel.getClaimStatus() == 2) {
                    Log.e("222222222", "------------------------------------------------");
                    tvStatus.setText("Final Pending");
                    tvStatus.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
                } else if (claimAppModel.getClaimStatus() == 3) {
                    tvStatus.setText("Final Approved");
                    tvStatus.setTextColor(getContext().getResources().getColor(R.color.colorApproved));
                } else if (claimAppModel.getClaimStatus() == 8) {
                    tvStatus.setText("Initial Rejected");
                    tvStatus.setTextColor(getContext().getResources().getColor(R.color.colorRejected));
                } else if (claimAppModel.getClaimStatus() == 9) {
                    tvStatus.setText("Final Rejected");
                    tvStatus.setTextColor(getContext().getResources().getColor(R.color.colorRejected));
                } else if (claimAppModel.getClaimStatus() == 7) {
                    tvStatus.setText("Leave Cancelled");
                    tvStatus.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
                }


                if (claimAppModel.getCaFinAuthEmpId() == loginUser.getEmpId()) {
                    llAction.setVisibility(View.VISIBLE);

                } else {
                    llAction.setVisibility(View.GONE);

                }


            } else {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new ClaimApprovalPendingFragment(), "HomeFragment");
                ft.commit();

            }
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnApprove) {

            final String remark = edRemark.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final String currDate = sdf.format(System.currentTimeMillis());

            if (claimAppModel != null && loginUser != null) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                builder.setTitle("Confirmation");
                builder.setMessage("Do you want to APPROVE the claim of " + claimAppModel.getEmpName() + " for Rs. " + claimAppModel.getClaimAmount() + "/-");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (claimAppModel.getCaFinAuthEmpId() == loginUser.getEmpId()) {
                            llAction.setVisibility(View.VISIBLE);
                            SaveClaimTrail saveClaimTrail = new SaveClaimTrail(0, claimAppModel.getCaHeadId(), claimAppModel.getEmpId(), remark, 3, loginUser.getUserId(), currDate);


                            if(claimPayroll.getValue().equals("1"))
                            {
                                String dt = tv_click_me.getText().toString();

                                String temp[] = dt.split("-");
                                updateClaimStatus(claimAppModel.getCaHeadId(), 3, saveClaimTrail,Integer.parseInt(temp[0]),Integer.parseInt(temp[1]));

                            }else {
                                updateClaimStatus(claimAppModel.getCaHeadId(), 3, saveClaimTrail,0,0);

                            }

                        } else if (claimAppModel.getCaIniAuthEmpId() == loginUser.getEmpId()) {

                            llAction.setVisibility(View.GONE);

//                            SaveClaimTrail saveClaimTrail = new SaveClaimTrail(0, claimModel.getCaHeadId(), claimModel.getEmpId(), remark, 2, loginUser.getUserId(), currDate);
//                            updateClaimStatus(claimModel.getCaHeadId(), 2, saveClaimTrail);

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


            if (claimAppModel != null && loginUser != null) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                builder.setTitle("Confirmation");
                builder.setMessage("Do you want to REJECT the claim of " + claimAppModel.getEmpName() + " for Rs. " + claimAppModel.getClaimAmount() + "/-");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (claimAppModel.getCaFinAuthEmpId() == loginUser.getEmpId()) {
                            llAction.setVisibility(View.VISIBLE);
                            SaveClaimTrail saveClaimTrail = new SaveClaimTrail(0, claimAppModel.getCaHeadId(), claimAppModel.getEmpId(), remark, 9, loginUser.getUserId(), currDate);
                            updateClaimStatus(claimAppModel.getCaHeadId(), 9, saveClaimTrail,0,0);

                        } else if (claimAppModel.getCaIniAuthEmpId() == loginUser.getEmpId()) {

                            llAction.setVisibility(View.GONE);

//                            SaveClaimTrail saveClaimTrail = new SaveClaimTrail(0, claimModel.getCaHeadId(), claimModel.getEmpId(), remark, 8, loginUser.getUserId(), currDate);
//                            updateClaimStatus(claimModel.getCaHeadId(), 8, saveClaimTrail);

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

        }else if(v.getId()==R.id.tv_click_me)
        {
            MonthYearPickerDialog pickerDialog = new MonthYearPickerDialog();
            pickerDialog.setListener(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int i2) {
                    monthYearStr = year + "-" + (month + 1) + "-" + i2;
                    String datemy=formatMonthYear(monthYearStr);
                    tv_click_me.setText(datemy);
                    // tvDate.setText("" + (month + 1));
                    // tvYear.setText("" + year);
                    //   Log.e("Text Date", "--------------------------------" + tvDate.getText().toString());
                    Log.e("Text Month", "--------------------------------" + (month + 1));
                    //    click_me.setText((monthYearStr));
                }
            });
            pickerDialog.show(getFragmentManager(), "MonthYearPickerDialog");
        }
    }

    String formatMonthYear(String str) {
        Log.e("String","--------------------------------"+str);
        Date date = null;
        try {
            date = input.parse(str);
            Log.e("Date","--------------------------------"+date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(date);
    }

    private void getClaimTrail(final Integer claimId) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<ClaimHeader>> listCall = Constants.myInterface.getEmpClaimInfoListByTrailEmpId(authHeader, claimId);
            listCall.enqueue(new Callback<ArrayList<ClaimHeader>>() {
                @Override
                public void onResponse(Call<ArrayList<ClaimHeader>> call, Response<ArrayList<ClaimHeader>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("CLAIM TRAIL DATA : ", " - " + response.body());

                            ClaimTrailAdapter adapter = new ClaimTrailAdapter(response.body(), getContext());
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
                public void onFailure(Call<ArrayList<ClaimHeader>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getClaimProofList(final Integer claimId) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<ClaimProofList>> listCall = Constants.myInterface.getClaimProofList(authHeader, claimId);
            listCall.enqueue(new Callback<ArrayList<ClaimProofList>>() {
                @Override
                public void onResponse(Call<ArrayList<ClaimProofList>> call, Response<ArrayList<ClaimProofList>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("CLAIM PROOF DATA : ", " - " + response.body());

                            ClaimAttachmentAdapter adapter = new ClaimAttachmentAdapter(response.body(), getContext());
//                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ClaimHistoryActivity.this);
//                            recyclerView.setLayoutManager(mLayoutManager);
//                            recyclerView.setItemAnimator(new DefaultItemAnimator());
//                            recyclerView.setAdapter(adapter);
                            rvAttachment.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
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
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }


    private void updateClaimStatus(final Integer claimId, int status, final SaveClaimTrail saveClaimTrail,int month,int year) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.updateClaimStatus(authHeader, claimId, status,month,year);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("UPDATE CLAIM : ", " - " + response.body());

                            if (!response.body().getError()) {
                                saveClaimTrail(claimId, saveClaimTrail);
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

    private void saveClaimTrail(final Integer claimId, SaveClaimTrail saveClaimTrail) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<SaveClaimTrail> listCall = Constants.myInterface.saveClaimTrail(authHeader, saveClaimTrail);
            listCall.enqueue(new Callback<SaveClaimTrail>() {
                @Override
                public void onResponse(Call<SaveClaimTrail> call, Response<SaveClaimTrail> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("UPDATE CLAIM : ", " - " + response.body());

                            if (response.body().getClaimTrailPkey() > 0) {
                                updateClaimTrailId(claimId, response.body().getClaimTrailPkey());
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
                public void onFailure(Call<SaveClaimTrail> call, Throwable t) {
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

    private void updateClaimTrailId(final Integer claimId, int trailId) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.updateClaimTrailId(authHeader, claimId, trailId);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("UPDATE CLAIM TRAID ID: ", " - " + response.body());

                            if (!response.body().getError()) {

                                // Toast.makeText(getContext(), "SUCCESS", Toast.LENGTH_SHORT).show();

                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                                builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                                builder.setMessage("Success");

                                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

//                                        if (claimModelList.size() > 0) {
//
//                                            int pos = 0;
//                                            for (int i = 0; i < claimModelList.size(); i++) {
//
//                                                if (claimAppModel.getCaHeadId() == claimModelList.get(i).getCaHeadId()) {
//                                                    pos = i;
//                                                }
//                                            }
//                                            claimModelList.remove(pos);
//
//                                            setData();
//                                            edRemark.setText("");
//                                        }
                                        dialog.dismiss();
                                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                        ft.replace(R.id.content_frame, new ClaimApprovalPendingFragment(), "HomeFragment");
                                        ft.commit();
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

    //***************************************************************

    private void getProof(Integer caHeadId) {
        Log.e("PARAMETER","               CLAIM ID         "+caHeadId);
        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getActivity())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
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
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getStructure(Integer empId) {
        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getActivity())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<StructureData>> listCall = Constants.myInterface.getClaimStructureDetailByEmpId(authHeader, empId);
            listCall.enqueue(new Callback<ArrayList<StructureData>>() {
                @Override
                public void onResponse(Call<ArrayList<StructureData>> call, final Response<ArrayList<StructureData>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("CLAIM STRUCTURE DATA : ", " - " + response.body());
                            getClaimDetail(claimAppModel.getCaHeadId(),response.body());


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
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }


    private void getClaimDetail(Integer caHeadId, final ArrayList<StructureData> structureDataList) {
        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getActivity())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<ClaimDetail>> listCall = Constants.myInterface.getClaimDetailListByEmpId(authHeader, caHeadId);
            listCall.enqueue(new Callback<ArrayList<ClaimDetail>>() {
                @Override
                public void onResponse(Call<ArrayList<ClaimDetail>> call, final Response<ArrayList<ClaimDetail>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("CLAIM DETAIL DATA : ", " - " + response.body());

                            ClaimDetailAdapter adapter = new ClaimDetailAdapter(response.body(), getActivity(),structureDataList,claimProofsList);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
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
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getClaimHeaderAndTrail(Integer caHeadId) {
        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getActivity())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<ClaimHeader>> listCall = Constants.myInterface.getEmpClaimInfoListByTrailEmpId(authHeader, caHeadId);
            listCall.enqueue(new Callback<ArrayList<ClaimHeader>>() {
                @Override
                public void onResponse(Call<ArrayList<ClaimHeader>> call, final Response<ArrayList<ClaimHeader>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("CLAIM TRAIL DATA : ", " - " + response.body());

                            tvEmpolyeeName.setText(response.body().get(0).getEmpFname()+" "+response.body().get(0).getEmpSname());
                            tvEmpName.setText(response.body().get(0).getEmpFname()+" "+response.body().get(0).getEmpSname());
                            tvEmpCode.setText(response.body().get(0).getEmpCode());
                            tvAmount.setText(response.body().get(0).getClaimAmount());
                            tvTitle.setText(response.body().get(0).getClaimTitle());
                            tvDate.setText(response.body().get(0).getCaFromDt()+" to "+response.body().get(0).getCaToDt());
                            // tvRemark.setText("" + claimModel.getClaimRemarks());

                            String imageUri = String.valueOf(claimAppModel.getEmpPhoto());
                            try {
                                Picasso.with(getContext()).load(Constants.IMAGE_URL + "" + imageUri).placeholder(getActivity().getResources().getDrawable(R.drawable.profile)).into(ivPhoto);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            if (claimAppModel.getClaimStatus() == 1) {
                                tvStatus.setText("Initial & Final Approve Pending");
                                tvStatus.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
                            } else if (claimAppModel.getClaimStatus() == 2) {
                                tvStatus.setText("Final Approve Pending");
                                tvStatus.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
                            } else if (claimAppModel.getClaimStatus() == 3) {
                                tvStatus.setText("Final Approved");
                                tvStatus.setTextColor(getContext().getResources().getColor(R.color.colorApproved));
                            } else if (claimAppModel.getClaimStatus() == 8) {
                                tvStatus.setText("Initial Rejected");
                                tvStatus.setTextColor(getContext().getResources().getColor(R.color.colorRejected));
                            } else if (claimAppModel.getClaimStatus() == 9) {
                                tvStatus.setText("Final Rejected");
                                tvStatus.setTextColor(getContext().getResources().getColor(R.color.colorRejected));
                            } else if (claimAppModel.getClaimStatus() == 7) {
                                tvStatus.setText("Cancelled");
                                tvStatus.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
                            }

                            if (response.body().get(0).getVisibleStatus() == 1) {
                                llItems.setVisibility(View.VISIBLE);
                                imageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_up));
                            } else {
                                llItems.setVisibility(View.GONE);
                                imageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_down));
                            }

                            tvDetail.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (response.body().get(0).getVisibleStatus() == 0) {
                                        response.body().get(0).setVisibleStatus(1);
                                        llItems.setVisibility(View.VISIBLE);
                                        imageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_up));
                                    } else if (response.body().get(0).getVisibleStatus() == 1) {
                                        response.body().get(0).setVisibleStatus(0);
                                        llItems.setVisibility(View.GONE);
                                        imageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_down));
                                    }
                                }
                            });

                            ClaimTrailAdapter adapter = new ClaimTrailAdapter(response.body(), getActivity());
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
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
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

}
