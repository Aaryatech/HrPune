package com.ats.hrpune.fragment;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ats.hrpune.BuildConfig;
import com.ats.hrpune.R;
import com.ats.hrpune.adapter.ClaimTempAdapter;
import com.ats.hrpune.constant.Constants;
import com.ats.hrpune.inerfaces.AddClaimInterface;
import com.ats.hrpune.model.ApplyLeaveDetail;
import com.ats.hrpune.model.AuthorityIds;
import com.ats.hrpune.model.ClaimApply;
import com.ats.hrpune.model.ClaimApplyHeader;
import com.ats.hrpune.model.ClaimPayroll;
import com.ats.hrpune.model.ClaimProof;
import com.ats.hrpune.model.ClaimType;
import com.ats.hrpune.model.Info;
import com.ats.hrpune.model.Login;
import com.ats.hrpune.model.ProjectList;
import com.ats.hrpune.model.SaveClaimTrail;
import com.ats.hrpune.model.TypeClaim;
import com.ats.hrpune.utils.CommonDialog;
import com.ats.hrpune.utils.CustomSharedPreference;
import com.ats.hrpune.utils.PermissionsUtil;
import com.ats.hrpune.utils.RealPathUtil;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.ats.hrpune.fragment.ClaimFragment.staticEmpClaimModel;


public class AddClaimFragment extends Fragment implements AddClaimInterface, View.OnClickListener {
    public Spinner spType, spProject;
    public EditText edDate, edAmount, edRemark,edTitle;
    public TextView tvDate, tvPhoto, tvEmpName, tvEmpDesg;
  //  public TextView tvBrowse,tvFileName;
    private CircleImageView ivPhoto;
    public RecyclerView recyclerView;
    public Button btnApply,btnAdd;
    public ImageView imageViewImage, imageViewPhotoAttach, imageViewImage1, imageViewPhotoAttach1, imageViewImage2, imageViewPhotoAttach2;
    long fromDateMillis, toDateMillis;
    int yyyy, mm, dd;
    private static final int staticEmpClaimModelSELECTED_PIC = 1;
    private static final int RESULT_LOAD_IMAGE = 1;
    CommonDialog commonDialog;
    Login loginUser;
    float tot_amt;
    int pos;
    ClaimPayroll claimPayroll;

    public static ArrayList<ClaimApply> claimTempList = new ArrayList<>();


    ArrayList<String> typeNameArray = new ArrayList<>();
    ArrayList<String> projectArray = new ArrayList<>();

    ArrayList<String> claimTypeNameList = new ArrayList<>();
    ArrayList<Integer> claimTypeIdList = new ArrayList<>();

    ArrayList<String> projectNameList = new ArrayList<>();
    ArrayList<Integer> projectIdList = new ArrayList<>();

   public static ArrayList<ClaimProof> claimProofsList = new ArrayList<>();
    ArrayList<ClaimApply> claimDetailList = new ArrayList<>();


    //Image
    File folder = new File(Environment.getExternalStorageDirectory() + File.separator, "HREasy_Attachment");
    File f;

    Bitmap myBitmap1 = null, myBitmap2 = null, myBitmap3 = null;
    public static String imagePath1 = null, imagePath2 = null, imagePath3 = null;

   // Bitmap myBitmap = null;

    public static String path;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_claim, container, false);

        spType = (Spinner) view.findViewById(R.id.spType);
        spProject = (Spinner) view.findViewById(R.id.spProject);
        edDate = (EditText) view.findViewById(R.id.edDate);
        edAmount = (EditText) view.findViewById(R.id.edAmount);
        edRemark = (EditText) view.findViewById(R.id.edRemark);
        edTitle = (EditText) view.findViewById(R.id.edTitle);
        tvDate = (TextView) view.findViewById(R.id.tvDate);
        tvPhoto = (TextView) view.findViewById(R.id.tvphoto);
        imageViewImage = (ImageView) view.findViewById(R.id.ivImage);
        imageViewPhotoAttach = (ImageView) view.findViewById(R.id.ivPhotoAttach);
        imageViewImage1 = (ImageView) view.findViewById(R.id.ivImage1);
        imageViewPhotoAttach1 = (ImageView) view.findViewById(R.id.ivPhotoAttach1);
        imageViewImage2 = (ImageView) view.findViewById(R.id.ivImage2);
        imageViewPhotoAttach2 = (ImageView) view.findViewById(R.id.ivPhotoAttach2);
        btnApply = (Button) view.findViewById(R.id.btn_apply);
        btnAdd = (Button) view.findViewById(R.id.btnAdd);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

//        tvBrowse = view.findViewById(R.id.tvBrowse);
//        tvFileName = view.findViewById(R.id.tvFileName);

        tvEmpName = view.findViewById(R.id.tvEmpName);
        tvEmpDesg = view.findViewById(R.id.tvEmpDesg);
        ivPhoto = view.findViewById(R.id.ivPhoto);

        String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_USER);
        Gson gson = new Gson();
        loginUser = gson.fromJson(userStr, Login.class);
        Log.e("HOME_ACTIVITY : ", "--------USER-------" + loginUser);


        btnApply.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        edDate.setOnClickListener(this);
       // tvBrowse.setOnClickListener(this);

        imageViewPhotoAttach.setOnClickListener(this);
        imageViewPhotoAttach1.setOnClickListener(this);
        imageViewPhotoAttach2.setOnClickListener(this);

        claimTempList.clear();


        if (PermissionsUtil.checkAndRequestPermissions(getActivity())) {
        }

        createFolder();

        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = formatter.format(todayDate);
        Log.e("Mytag", "todayString" + currentDate);
        edDate.setText(currentDate);

        if (loginUser != null) {
           // getProjectList(loginUser.getCompanyId());
            getProjectList(1);
        }

        try {
            if (staticEmpClaimModel != null) {

                tvEmpName.setText("" + staticEmpClaimModel.getFirstName() + " " + staticEmpClaimModel.getMiddleName() + " " + staticEmpClaimModel.getSurname());
                tvEmpDesg.setText("" + staticEmpClaimModel.getCmpCode());

                String imageUri = String.valueOf(staticEmpClaimModel.getShiftname());
                try {
                    Picasso.with(getContext()).load(Constants.IMAGE_URL+""+imageUri).placeholder(getActivity().getResources().getDrawable(R.drawable.profile)).into(ivPhoto);

                } catch (Exception e) {
                }

                //getCurrentYear(staticEmpModel.getEmpId());
                getClaimType(staticEmpClaimModel.getEmpId());
                getClaimPayroll("payroll_claim_show");
            }

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

    private void getProjectList(Integer companyId) {
        if (Constants.isOnline(getActivity())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            String base = Constants.userName + ":" + Constants.password;
            String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

            Call<ArrayList<ProjectList>> listCall = Constants.myInterface.getProjectsListByCompanyId(authHeader, companyId);
            listCall.enqueue(new Callback<ArrayList<ProjectList>>() {
                @Override
                public void onResponse(Call<ArrayList<ProjectList>> call, Response<ArrayList<ProjectList>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("PROJECT LIST : ", " - " + response.body());

                            projectNameList.clear();
                            projectIdList.clear();

                            projectNameList.add("Select Project");
                            projectIdList.add(0);

                            if (response.body().size() > 0) {
                                for (int i = 0; i < response.body().size(); i++) {
                                    projectIdList.add(response.body().get(i).getProjectId());
                                    projectNameList.add(response.body().get(i).getProjectTitle());
                                }

                                ArrayAdapter<String> projectAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, projectNameList);
                                spProject.setAdapter(projectAdapter);

                            }

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
                public void onFailure(Call<ArrayList<ProjectList>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getClaimType(int empId) {
        Log.e("PARAMETER","           TYPE EMP ID        "+empId);
        if (Constants.isOnline(getActivity())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            String base = Constants.userName + ":" + Constants.password;
            String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

            Call<ArrayList<TypeClaim>> listCall = Constants.myInterface.getEmpClaimStructure(authHeader,empId);
            listCall.enqueue(new Callback<ArrayList<TypeClaim>>() {
                @Override
                public void onResponse(Call<ArrayList<TypeClaim>> call, Response<ArrayList<TypeClaim>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("CLAIM TYPE LIST : ", " - " + response.body());

                            claimTypeNameList.clear();
                            claimTypeIdList.clear();

                            claimTypeNameList.add("Select Claim Type");
                            claimTypeIdList.add(0);

                            if (response.body().size() > 0) {

                                for (int i = 0; i < response.body().size(); i++) {
                                    claimTypeIdList.add(response.body().get(i).getClmStructDetailsId());
                                    claimTypeNameList.add(response.body().get(i).getClaimTypeTitle());
                                }

                                ArrayAdapter<String> projectAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, claimTypeNameList);
                                spType.setAdapter(projectAdapter);

                            }

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
                public void onFailure(Call<ArrayList<TypeClaim>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
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

            DatePickerDialog dialog = new DatePickerDialog(getContext(), dateListener, yr, mn, dy);
            dialog.show();

        } else if (v.getId() == R.id.ivPhotoAttach) {

            Log.e("PHOTO 1", "--------------------------***************************");

            showCameraDialog("Photo1");

        } else if (v.getId() == R.id.ivPhotoAttach1) {

            showCameraDialog("Photo2");

        } else if (v.getId() == R.id.ivPhotoAttach2) {

            showCameraDialog("Photo3");

        }else if(v.getId()==R.id.tvBrowse)
        {
            //showFileChooser();
        }
        else if (v.getId() == R.id.btn_apply) {

            String strDate, strAmount, strTotalDayes, strRemark,strTitle;

            strDate = edDate.getText().toString();
            strAmount = edAmount.getText().toString();
            strRemark = edRemark.getText().toString().trim();
            strTitle = edTitle.getText().toString().trim();

            float amount = 0;

            try {

                amount = Float.parseFloat(strAmount);

            } catch (Exception e) {

                e.printStackTrace();

            }

            SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");

            Date ToDate = null;

            try {

                ToDate = formatter1.parse(strDate);//catch exception

            } catch (ParseException e) {

                e.printStackTrace();

            }

            final String DateTo = formatter3.format(ToDate);
            final String DateToInfo = formatter1.format(ToDate);

            Log.e("Date To","--------------------------------------"+DateTo);


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final String currDate = sdf.format(System.currentTimeMillis());

            if (claimTempList.size() > 0) {

                for (int i = 0; i < claimTempList.size(); i++) {

                    ClaimApply claimApply = new ClaimApply(0, claimTempList.get(i).getEmpId(), claimTempList.get(i).getProjectId(), claimTempList.get(i).getClaimTypeId(), claimTempList.get(i).getClaimDate(), claimTempList.get(i).getClaimAmount(), claimTempList.get(i).getClaimRemarks(), claimTempList.get(i).getClaimFinalStatus(), claimTempList.get(i).getCirculatedTo(), claimTempList.get(i).getDelStatus(), claimTempList.get(i).getIsActive(), claimTempList.get(i).getMakerUserId(), claimTempList.get(i).getMakerEnterDatetime(), claimTempList.get(i).getExInt1(), claimTempList.get(i).getExInt2(), claimTempList.get(i).getExInt3(), claimTempList.get(i).getExVar3(),"","");
                    claimDetailList.add(claimApply);
                    tot_amt = tot_amt + claimTempList.get(i).getClaimAmount();
                    Log.e("Mytag","-------------------CLAIM DETAIL LIST--------------"+claimDetailList);

                }
                    final ClaimApplyHeader header = new ClaimApplyHeader(0,DateTo,DateTo,strTitle,1,staticEmpClaimModel.getEmpId(),0,tot_amt,"1",1,currDate,1,1,0,0,0,"NA","NA","NA",0,0,0,claimDetailList);
                    Log.e("Mytag","-------------------CLAIM HEADER LIST--------------"+header);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                builder.setTitle("Confirmation");
                builder.setMessage("Applied claim for Rs. " + tot_amt + " /- on date " + DateToInfo);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        getAuthIdByEmpId(staticEmpClaimModel.getEmpId(), 1, header);

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
                Toast.makeText(getContext(), "Detail Not found", Toast.LENGTH_SHORT).show();
            }


        }else if(v.getId()==R.id.btnAdd)
        {
            String strDate, strAmount, strTitle, strRemark;

            strDate = edDate.getText().toString();
            strAmount = edAmount.getText().toString();
            strRemark = edRemark.getText().toString().trim();
            strTitle = edTitle.getText().toString().trim();
            float amount = 0;

            try {

                amount = Float.parseFloat(strAmount);

            } catch (Exception e) {

                e.printStackTrace();

            }

            SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");

            Date ToDate = null;

            try {

                ToDate = formatter1.parse(strDate);//catch exception

            } catch (ParseException e) {

                e.printStackTrace();

            }

            final String DateTo = formatter3.format(ToDate);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final String currDate = sdf.format(System.currentTimeMillis());

            int claimType = claimTypeIdList.get(spType.getSelectedItemPosition());
            String claimTypeName = claimTypeNameList.get(spType.getSelectedItemPosition());
            if (claimType == 0) {

                TextView viewType = (TextView) spType.getSelectedView();
                viewType.setError("required");

            } else if (strAmount.isEmpty()) {

                TextView viewType = (TextView) spType.getSelectedView();
                viewType.setError(null);

                edAmount.setError("required");

            }else if(strTitle.isEmpty()){
                edTitle.setError("required");
                edAmount.setError(null);

            }else  if(strRemark.isEmpty())
            {
                edRemark.setError("required");
                edTitle.setError(null);
            }
            else {

                edRemark.setError(null);

              //  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");

                String docFileName = null;
                 ClaimApply claimApply;
                ArrayList<String> pathArray = new ArrayList<>();
                ArrayList<String> fileNameArray = new ArrayList<>();

                String photo1 = "", photo2 = "", photo3 = "";
                    Log.e("Image","------------------------------------"+imagePath1);

                if (imagePath1 != null) {

                    pathArray.add(imagePath1);

                    File imgFile1 = new File(imagePath1);
                    int pos = imgFile1.getName().lastIndexOf(".");
                    String ext = imgFile1.getName().substring(pos + 1);
                    photo1 = sdf1.format(Calendar.getInstance().getTimeInMillis()) + "_p1." + ext;
                    fileNameArray.add(photo1);

                    claimApply = new ClaimApply(0, staticEmpClaimModel.getEmpId(), 0, claimType, DateTo, amount, strRemark, 1, "", 1, 1, loginUser.getUserId(), currDate, 1, 0, 0, claimTypeName, strTitle, photo1);

                    //sendImageTest(pathArray, fileNameArray);

                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    final String currDate1 = sdf2.format(System.currentTimeMillis());

                   ClaimProof claimProof = new ClaimProof(0, 0, "", "NA", 1, 1, loginUser.getUserId(), currDate1,1,0,0,"NA","NA","NA");

                    sendImage(pathArray, fileNameArray, claimProof);

                }else {
                    // final ClaimApply claimApply = new ClaimApply(0, staticEmpClaimModel.getEmpId(), projectList, claimType, DateTo, amount, strRemark, 1, "", 1, 1, loginUser.getUserId(), currDate, 1, 0, 0, "", "", "");
                    claimApply = new ClaimApply(0, staticEmpClaimModel.getEmpId(), 0, claimType, DateTo, amount, strRemark, 1, "", 1, 1, loginUser.getUserId(), currDate, 1, 0, 0, claimTypeName, strTitle, photo1);
                }
                claimTempList.add(claimApply);
                Log.e(" Add Claim ", "-----------------------------------------------" + claimApply);

                ClaimTempAdapter claimTempAdapter = new ClaimTempAdapter(claimTempList, getActivity());
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(claimTempAdapter);

                edAmount.setText("");
                imagePath1 = null;
                myBitmap1 = null;
                imageViewImage.setImageBitmap(null);
                tvPhoto.setText(null);


            }

        }
    }



//    private void showFileChooser() {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
//        builder.setTitle("Choose");
//        builder.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent pictureActionIntent = null;
//                pictureActionIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(pictureActionIntent, 101);
//            }
//        });
//        builder.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                try {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        f = new File(folder + File.separator, "Camera.jpg");
//
//                        String authorities = BuildConfig.APPLICATION_ID + ".provider";
//                        Uri imageUri = FileProvider.getUriForFile(getContext(), authorities, f);
//
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        startActivityForResult(intent, 102);
//
//                    } else {
//                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        f = new File(folder + File.separator, "Camera.jpg");
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
//                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        startActivityForResult(intent, 102);
//
//                    }
//                } catch (Exception e) {
//                    ////Log.e("select camera : ", " Exception : " + e.getMessage());
//                }
//            }
//        });
//        builder.show();
//
//    }


    private void getAuthIdByEmpId(Integer empId, Integer companyId, final ClaimApplyHeader claimApplyHeader) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<AuthorityIds> listCall = Constants.myInterface.getClaimAuthIds(authHeader, empId, companyId);
            listCall.enqueue(new Callback<AuthorityIds>() {
                @Override
                public void onResponse(Call<AuthorityIds> call, Response<AuthorityIds> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("AUTHORITY MODEL : ", " - " + response.body());
                            Log.e("CLAIM HEADER DETAIL : ", " - " + claimApplyHeader);
                            AuthorityIds authorityIds = response.body();

                            String dt = claimApplyHeader.getCafromDt();

                            String temp[] = dt.split("-");

                            if (loginUser.getEmpId() == authorityIds.getFinAuthEmpId()) {

                                if(claimPayroll.getValue().equals("1"))
                                {
                                    claimApplyHeader.setMonth(Integer.parseInt(temp[1]));
                                    claimApplyHeader.setYear(Integer.parseInt(temp[0]));

                                }else{

                                    claimApplyHeader.setMonth(0);
                                    claimApplyHeader.setYear(0);
                                }

                                claimApplyHeader.setClaimStatus(3);
//                                claimApplyHeader.setMonth(Integer.parseInt(temp[1]));
//                                claimApplyHeader.setYear(Integer.parseInt(temp[0]));
                                claimApplyHeader.setIsPaid(0);
                                getApplyClaim(claimApplyHeader);

                            } else if (loginUser.getEmpId() == authorityIds.getIniAuthEmpId()) {
                                claimApplyHeader.setClaimStatus(2);
                                claimApplyHeader.setMonth(0);
                                claimApplyHeader.setYear(0);
                                claimApplyHeader.setIsPaid(0);
                                getApplyClaim(claimApplyHeader);

                            } else {
                                claimApplyHeader.setClaimStatus(1);
                                claimApplyHeader.setMonth(0);
                                claimApplyHeader.setYear(0);
                                claimApplyHeader.setIsPaid(0);
                                getApplyClaim(claimApplyHeader);
                            }

                            //commonDialog.dismiss();

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
                public void onFailure(Call<AuthorityIds> call, Throwable t) {
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

    private void getApplyClaim(final ClaimApplyHeader claimApplyHeader) {

        Log.e("PARAMETERS : ", "      ------------------------------------- CLAIM MODEL :------------------- " + claimApplyHeader);
        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            //final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            //commonDialog.show();

            Call<ClaimApplyHeader> listCall = Constants.myInterface.saveClaimHeaderAndDetail(authHeader, claimApplyHeader);
            listCall.enqueue(new Callback<ClaimApplyHeader>() {
                @Override
                public void onResponse(Call<ClaimApplyHeader> call, Response<ClaimApplyHeader> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("APPLY CLAIM : ", " ---------------------APPLY CLAIM---------------------- " + response.body());
                            ClaimApplyHeader model = response.body();

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            String currDate = sdf.format(System.currentTimeMillis());

                            SaveClaimTrail saveClaimTrail = new SaveClaimTrail(0, model.getCaHeadId(), staticEmpClaimModel.getEmpId(), claimApplyHeader.getClaimTitle(),claimApplyHeader.getClaimStatus(),loginUser.getUserId(),""+currDate,0,0,0,"NA","NA","NA");
                           // SaveClaimTrail saveClaimTrail = new SaveClaimTrail(0, model.getCaHeadId(), staticEmpClaimModel.getEmpId(), claimApplyHeader.getClaimTitle(), model.getExInt1(), loginUser.getUserId(), "" + currDate);
                            saveClaimTrail(model.getCaHeadId(), saveClaimTrail,model.getDetailList());

                            //commonDialog.dismiss();

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
                public void onFailure(Call<ClaimApplyHeader> call, Throwable t) {
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

    private void saveClaimTrail(final int claimId, SaveClaimTrail saveClaimTrail, final List<ClaimApply> claimDetailList) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            //final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            // commonDialog.show();

            Call<SaveClaimTrail> listCall = Constants.myInterface.saveClaimTrail(authHeader, saveClaimTrail);
            listCall.enqueue(new Callback<SaveClaimTrail>() {
                @Override
                public void onResponse(Call<SaveClaimTrail> call, Response<SaveClaimTrail> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("SAVE CLAIM TRAIL : ", " ------------------------------SAVE CLAIM TRAIL------------------------- " + response.body());

                            if (response.body().getClaimTrailPkey() > 0) {
                                updateClaimTrailId(claimId, response.body().getClaimTrailPkey(),claimDetailList);
                            }

                            //commonDialog.dismiss();

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
                public void onFailure(Call<SaveClaimTrail> call, Throwable t) {
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

    private void updateClaimTrailId(final int claimId, int claimTrailPkey, final List<ClaimApply> claimDetailsList) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            // final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            // commonDialog.show();

            Call<Info> listCall = Constants.myInterface.updateClaimTrailId(authHeader, claimId, claimTrailPkey);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("UPDATE CLAIM : ", " - " + response.body());

                            if (!response.body().getError()) {

                                commonDialog.dismiss();
                                Log.e(" PROOF DETAIL","------------------------------------------"+claimDetailsList);

                                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                final String currDate1 = sdf2.format(System.currentTimeMillis());

                                if(claimDetailsList.size()>0) {
                                    for (int j = 0; j < claimDetailsList.size(); j++) {

                                        if(! claimDetailsList.get(j).getExVar1().equals("") && claimDetailsList.get(j).getExVar1()!= null) {

                                            ClaimProof claimProof = new ClaimProof(0, claimId, claimDetailsList.get(j).getExVar1(), "NA", 1, 1, loginUser.getUserId(), currDate1, claimDetailsList.get(j).getClaimId(), 0, 0, "NA", "NA", "NA");
                                            claimProofsList.add(claimProof);
                                        }
                                    }
                                }

                                Log.e(" PROOF LIST","------------------------------------------"+claimProofsList);
                        if (claimProofsList.size()>0) {
                            Log.e(" PROOF LIST","------------FINAL------------------------------"+claimProofsList);
                            saveClaimDocs(claimProofsList, "yes");

                        }else{

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                            builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                            builder.setMessage("Claim applied successfully");

                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                    ft.replace(R.id.content_frame, new HomeFragment(), "Exit");
                                    ft.commit();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }

                            } else {
                                //Toast.makeText(getContext(), "FAILED", Toast.LENGTH_SHORT).show();

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
                            // Toast.makeText(getContext(), "FAILED", Toast.LENGTH_SHORT).show();

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
                        //   Toast.makeText(getContext(), "FAILED", Toast.LENGTH_SHORT).show();

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
                    //  Toast.makeText(getContext(), "FAILED", Toast.LENGTH_SHORT).show();

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

    private void saveClaimDocs(ArrayList<ClaimProof> claimProof, final String lastRec) {

        Log.e("PARAMETERS : ", "      ------------------------------------- CLAIM PROOF MODEL :------------------- " + claimProof);
        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog1 = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog1.show();

            Call<ArrayList<ClaimProof>> listCall = Constants.myInterface.saveClaimProof(authHeader, claimProof);
            listCall.enqueue(new Callback<ArrayList<ClaimProof>>() {
                @Override
                public void onResponse(Call<ArrayList<ClaimProof>> call, Response<ArrayList<ClaimProof>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("APPLY CLAIM : ", " ---------------------APPLY CLAIM---------------------- " + response.body());
                           // ClaimProof model = response.body();

                            commonDialog1.dismiss();

                            if (lastRec.equalsIgnoreCase("yes")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                                builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                                builder.setMessage("Claim applied successfully");

                                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                        ft.replace(R.id.content_frame, new HomeFragment(), "Exit");
                                        ft.commit();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();

                            }


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
                public void onFailure(Call<ArrayList<ClaimProof>> call, Throwable t) {
                    commonDialog1.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }


    private void sendImage(final ArrayList<String> filePath, final ArrayList<String> fileName, final ClaimProof claimProof) {

        Log.e("PARAMETER : ", "   FILE PATH : " + filePath + "            FILE NAME : " + fileName);

        final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
        commonDialog.show();

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);


        File imgFile = null;

        MultipartBody.Part[] uploadImagesParts = new MultipartBody.Part[filePath.size()];

        for (int index = 0; index < filePath.size(); index++) {
            Log.e("ATTACH ACT", "requestUpload:  image " + index + "  " + filePath.get(index));
            imgFile = new File(filePath.get(index));
            RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), imgFile);
            uploadImagesParts[index] = MultipartBody.Part.createFormData("file", "" + fileName.get(index), surveyBody);
        }
        // RequestBody imgName = RequestBody.create(MediaType.parse("text/plain"), "photo1");
        RequestBody imgType = RequestBody.create(MediaType.parse("text/plain"), "1");

        Call<JSONObject> call = Constants.myInterface.imageUpload(authHeader, uploadImagesParts, fileName, imgType);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                commonDialog.dismiss();

                imagePath1 = null;

                Log.e("Response : ", "--" + response.body());

//                if (filePath.size() > 0) {
//                    for (int i = 0; i < filePath.size(); i++) {
//                        claimProof.setCpDocPath(fileName.get(i));
//                        claimProofsList.add(claimProof);
//
////                        if (i == (fileName.size() - 1)) {
////                            saveClaimDocs(claimProof, "yes");
////                        } else {
////                            saveClaimDocs(claimProof, "no");
////                        }
//                   }
//                }
          //  Log.e("CLAIM PROOF","-----------------------------------------------------"+claimProofsList);
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Log.e("Error : ", "--" + t.getMessage());
                commonDialog.dismiss();
                t.printStackTrace();
                Toast.makeText(getContext(), "Unable To Process", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void showCameraDialog(final String type) {

      AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
        builder.setTitle("Choose");
        builder.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (type.equalsIgnoreCase("Photo1")) {
                    Intent pictureActionIntent = null;
                    pictureActionIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pictureActionIntent, 101);
                }
            }
        });
        builder.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        if (type.equalsIgnoreCase("Photo1")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            f = new File(folder + File.separator, "" + Calendar.getInstance().getTimeInMillis() + "_p1.jpg");
                            String authorities = BuildConfig.APPLICATION_ID + ".provider";
                            Uri imageUri = FileProvider.getUriForFile(getContext(), authorities, f);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(intent, 102);
                        } else {

                            if (type.equalsIgnoreCase("Photo1")) {
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                f = new File(folder + File.separator, "" + Calendar.getInstance().getTimeInMillis() + "_p1.jpg");
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                startActivityForResult(intent, 102);
                            }
                        }
                    }

                } catch (Exception e) {
                    ////Log.e("select camera : ", " Exception : " + e.getMessage());
                }
            }
        });
        builder.show();
    }


    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
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


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        String realPath;
//        Bitmap bitmap = null;
//
//        if (resultCode == RESULT_OK && requestCode == 102) {
//            try {
//                String path = f.getAbsolutePath();
//                File imgFile = new File(path);
//                if (imgFile.exists()) {
//                    myBitmap1 = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//                    ivPhoto.setImageBitmap(myBitmap1);
//
//                    myBitmap1 = shrinkBitmap(imgFile.getAbsolutePath(), 720, 720);
//
//                    try {
//                        FileOutputStream out = new FileOutputStream(path);
//                        myBitmap1.compress(Bitmap.CompressFormat.PNG, 100, out);
//                        out.flush();
//                        out.close();
//                        Log.e("Image Saved  ", "---------------");
//
//                    } catch (Exception e) {
//                        Log.e("Exception : ", "--------" + e.getMessage());
//                        e.printStackTrace();
//                    }
//                }
//                imagePath1 = f.getAbsolutePath();
//                tvPhoto.setText("" + f.getName());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }else if (resultCode == RESULT_OK && requestCode == 101) {
//            try {
//                realPath = RealPathUtil.getRealPathFromURI_API19(getActivity(), data.getData());
//                Uri uriFromPath = Uri.fromFile(new File(realPath));
//                myBitmap1 = getBitmapFromCameraData(data, getActivity());
//
//                ivPhoto.setImageBitmap(myBitmap1);
//                imagePath1 = uriFromPath.getPath();
//                tvPhoto.setText("" + uriFromPath.getPath());
//
//                try {
//
//                    FileOutputStream out = new FileOutputStream(uriFromPath.getPath());
//                    myBitmap1.compress(Bitmap.CompressFormat.PNG, 100, out);
//                    out.flush();
//                    out.close();
//                    //Log.e("Image Saved  ", "---------------");
//
//                } catch (Exception e) {
//                    // Log.e("Exception : ", "--------" + e.getMessage());
//                    e.printStackTrace();
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                // Log.e("Image Compress : ", "-----Exception : ------" + e.getMessage());
//            }
//        }
//    }
//    public static Bitmap getBitmapFromCameraData(Intent data, Context context) {
//        Uri selectedImage = data.getData();
//        String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//        Cursor cursor = context.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//        cursor.moveToFirst();
//
//        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//
//        String picturePath = cursor.getString(columnIndex);
//        path = picturePath;
//        cursor.close();
//
//        Bitmap bitm = shrinkBitmap(picturePath, 720, 720);
//        Log.e("Image Size : ---- ", " " + bitm.getByteCount());
//
//        return bitm;
//        // return BitmapFactory.decodeFile(picturePath, options);
//    }
//
//    public static Bitmap shrinkBitmap(String file, int width, int height) {
//        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
//        bmpFactoryOptions.inJustDecodeBounds = true;
//        Bitmap bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
//
//        int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight / (float) height);
//        int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth / (float) width);
//
//        if (heightRatio > 1 || widthRatio > 1) {
//            if (heightRatio > widthRatio) {
//                bmpFactoryOptions.inSampleSize = heightRatio;
//            } else {
//                bmpFactoryOptions.inSampleSize = widthRatio;
//            }
//        }
//
//        bmpFactoryOptions.inJustDecodeBounds = false;
//        bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
//        return bitmap;
//    }
//

    //******************************************************************************************



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String realPath;
        Bitmap bitmap = null;

        if (resultCode == getActivity().RESULT_OK && requestCode == 102) {
            try {
                String path = f.getAbsolutePath();
                File imgFile = new File(path);
                if (imgFile.exists()) {
                    myBitmap1 = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    imageViewImage.setImageBitmap(myBitmap1);

                    myBitmap1 = shrinkBitmap(imgFile.getAbsolutePath(), 720, 720);

                    try {
                        FileOutputStream out = new FileOutputStream(path);
                        myBitmap1.compress(Bitmap.CompressFormat.PNG, 100, out);
                        out.flush();
                        out.close();
                        //Log.e("Image Saved  ", "---------------");

                    } catch (Exception e) {
                        //Log.e("Exception : ", "--------" + e.getMessage());
                        //  e.printStackTrace();
                    }
                }
                imagePath1 = f.getAbsolutePath();
                tvPhoto.setText("" + f.getName());
            } catch (Exception e) {
                // e.printStackTrace();
            }

        } else if (resultCode == getActivity().RESULT_OK && requestCode == 101) {
            try {
                realPath = RealPathUtil.getRealPathFromURI_API19(getContext(), data.getData());
                Uri uriFromPath = Uri.fromFile(new File(realPath));
                myBitmap1 = getBitmapFromCameraData(data, getContext());

                imageViewImage.setImageBitmap(myBitmap1);
                imagePath1 = uriFromPath.getPath();
                tvPhoto.setText("" + uriFromPath.getPath());

                try {

                    FileOutputStream out = new FileOutputStream(uriFromPath.getPath());
                    myBitmap1.compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.flush();
                    out.close();
                    ////Log.e("Image Saved  ", "---------------");

                } catch (Exception e) {
                    // Log.e("Exception : ", "--------" + e.getMessage());
                    //  e.printStackTrace();
                }

            } catch (Exception e) {
                //  e.printStackTrace();
                // Log.e("Image Compress : ", "-----Exception : ------" + e.getMessage());
            }
        }
    }

    public static Bitmap shrinkBitmap(String file, int width, int height) {
        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);

        int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight / (float) height);
        int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth / (float) width);

        if (heightRatio > 1 || widthRatio > 1) {
            if (heightRatio > widthRatio) {
                bmpFactoryOptions.inSampleSize = heightRatio;
            } else {
                bmpFactoryOptions.inSampleSize = widthRatio;
            }
        }

        bmpFactoryOptions.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
        return bitmap;
    }

    public static Bitmap getBitmapFromCameraData(Intent data, Context context) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = context.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

        String picturePath = cursor.getString(columnIndex);
        path = picturePath;
        cursor.close();

        Bitmap bitm = shrinkBitmap(picturePath, 720, 720);
        //Log.e("Image Size : ---- ", " " + bitm.getByteCount());

        return bitm;
        // return BitmapFactory.decodeFile(picturePath, options);
    }

//****************************************************************************************

    public void createFolder() {
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    @Override
    public void fragmentBecameVisible() {

    }

    public void onClickData(int position) {

         pos=position;

         Log.e("Pos","----------------------------------------------"+pos);
    }
}
