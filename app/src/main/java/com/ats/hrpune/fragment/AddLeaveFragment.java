package com.ats.hrpune.fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ats.hrpune.BuildConfig;
import com.ats.hrpune.R;
import com.ats.hrpune.adapter.BalanceLeaveAdapter;
import com.ats.hrpune.constant.Constants;
import com.ats.hrpune.inerfaces.AddLeaveInterface;
import com.ats.hrpune.model.AuthorityIds;
import com.ats.hrpune.model.BalanceLeaveModel;
import com.ats.hrpune.model.BalanceLeaveTemp;
import com.ats.hrpune.model.ClaimProof;
import com.ats.hrpune.model.CompOffLeave;
import com.ats.hrpune.model.CurrentYearModel;
import com.ats.hrpune.model.Info;
import com.ats.hrpune.model.LeaveApply;
import com.ats.hrpune.model.LeaveInfo;
import com.ats.hrpune.model.LeaveWeeklyOffCount;
import com.ats.hrpune.model.Login;
import com.ats.hrpune.model.SaveLeaveTrail;
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
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.ats.hrpune.fragment.LeaveFragment.staticEmpModel;
//import static com.ats.hrpune.activity.LeaveActivity.staticEmpModel;


public class AddLeaveFragment extends Fragment implements View.OnClickListener, AddLeaveInterface {

    public Spinner spType;
    private EditText edFromDate, edToDate, edDays, edRemark, edWeeklyOff;
    private TextView tvFromDate, tvToDate, tvViewBalnceLeave, tvEmpName, tvEmpDesg;
    private CircleImageView ivPhoto;
    private LinearLayout linearLayoutDocUpload;
    private Button btn_apply;
    private TextView tvBrowse, tvFileName;
    long fromDateMillis, toDateMillis;
    int yyyy, mm, dd;
    private RadioButton rbFullDay, rbHalfDay;
    private RadioGroup rgDayes;
    //int spinnerPosition;
    String selectedtext;
    CurrentYearModel currentYearModel;
    Login loginUser;

    LeaveWeeklyOffCount leaveWeeklyOffCount;

    ArrayList<BalanceLeaveModel> balanceLeaveList = new ArrayList<>();
    ArrayList<BalanceLeaveModel> balanceLeaveCompOffList = new ArrayList<>();
    ArrayList<String> leaveTypeNameArray = new ArrayList<>();
    ArrayList<String> leaveTypeShortNameArray = new ArrayList<>();
    ArrayList<Integer> leaveTypeBalArray = new ArrayList<>();
    ArrayList<Integer> leaveTypeIdArray = new ArrayList<>();
    ArrayList<Integer> leaveFileArray = new ArrayList<>();
    LeaveInfo info;

    ArrayList<String> typeNameArray = new ArrayList<>();

    CommonDialog commonDialog,commonDialog1;

    int fileType;

    //Image
    File folder = new File(Environment.getExternalStorageDirectory() + File.separator, "HREasy_Attachment");
    File f;

    Bitmap myBitmap = null;

    private final int RESULT_OK = -1;
    public static String path, imagePath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_leave, container, false);
        spType = (Spinner) view.findViewById(R.id.spType);
        edFromDate = view.findViewById(R.id.edFromDate);
        edToDate = view.findViewById(R.id.edToDate);
        tvFromDate = view.findViewById(R.id.tvFromDate);
        tvToDate = view.findViewById(R.id.tvToDate);
        tvViewBalnceLeave = view.findViewById(R.id.tv_balanceLeave);
        edDays = view.findViewById(R.id.edTotalDays);
        edRemark = view.findViewById(R.id.edRemark);
        edWeeklyOff = view.findViewById(R.id.edWeeklyOff);

        tvBrowse = view.findViewById(R.id.tvBrowse);
        tvFileName = view.findViewById(R.id.tvFileName);
        linearLayoutDocUpload = (LinearLayout) view.findViewById(R.id.linearLayoutDocUpload);

        btn_apply = view.findViewById(R.id.btn_apply);

        tvEmpName = view.findViewById(R.id.tvEmpName);
        tvEmpDesg = view.findViewById(R.id.tvEmpDesg);
        ivPhoto = view.findViewById(R.id.ivPhoto);

        rbFullDay = view.findViewById(R.id.rbFullday);
        rbHalfDay = view.findViewById(R.id.rbHalfDay);
        rgDayes = view.findViewById(R.id.rg);

        rbFullDay.setChecked(true);

        edFromDate.setOnClickListener(this);
        edToDate.setOnClickListener(this);
        btn_apply.setOnClickListener(this);
        tvViewBalnceLeave.setOnClickListener(this);

        String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_USER);
        Gson gson = new Gson();
        loginUser = gson.fromJson(userStr, Login.class);
        // Log.e("HOME_ACTIVITY : ", "--------USER-------" + loginUser);


        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = formatter.format(todayDate);

        fromDateMillis = todayDate.getTime();
        toDateMillis = todayDate.getTime();

        //  Log.e("Mytag", "todayString" + currentDate);
        edFromDate.setText(currentDate);

        String toDate = edFromDate.getText().toString();
        edToDate.setText(toDate);

        String from = formatter1.format(todayDate);
        String to = formatter1.format(todayDate);

        getLeaveCountByEmp(staticEmpModel.getEmpId(), from, to);

        //getDays(edFromDate.getText().toString().trim(), edToDate.getText().toString().trim());

        try {
            if (staticEmpModel != null) {

                tvEmpName.setText("" + staticEmpModel.getFirstName() + " " + staticEmpModel.getMiddleName() + " " + staticEmpModel.getSurname());
                tvEmpDesg.setText("" + staticEmpModel.getCmpCode());

                String imageUri = String.valueOf(staticEmpModel.getShiftname());
                try {
                    Picasso.with(getContext()).load(Constants.IMAGE_URL + "" + imageUri).placeholder(getActivity().getResources().getDrawable(R.drawable.profile)).into(ivPhoto);

                } catch (Exception e) {
                }

                getCurrentYear(staticEmpModel.getEmpId());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        rbHalfDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

                    try {
                        Date d1 = sdf1.parse(edFromDate.getText().toString().trim());
                        Date d2 = sdf1.parse(edToDate.getText().toString().trim());

                        String from = sdf2.format(d1.getTime());
                        String to = sdf2.format(d2.getTime());

                        getLeaveCountByEmp(staticEmpModel.getEmpId(), from, to);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        rbFullDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

                    try {
                        Date d1 = sdf1.parse(edFromDate.getText().toString().trim());
                        Date d2 = sdf1.parse(edToDate.getText().toString().trim());

                        String from = sdf2.format(d1.getTime());
                        String to = sdf2.format(d2.getTime());

                        getLeaveCountByEmp(staticEmpModel.getEmpId(), from, to);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                 fileType = leaveFileArray.get(position);
                Log.e("File type","-----------------------------------"+fileType);
                Log.e("File Array","-----------------------------------"+leaveFileArray);

                if(fileType==-1){
                    linearLayoutDocUpload.setVisibility(View.GONE);
                    imagePath=null;
                    myBitmap=null;
                    tvFileName.setText("");
                    tvFileName.setText("");
                    //imageViewImage.setImageBitmap(myBitmap1);
                }else if(fileType==0){
                    linearLayoutDocUpload.setVisibility(View.GONE);
                    imagePath=null;
                    myBitmap=null;
                    tvFileName.setText("");
                   // imageViewImage.setImageBitmap(myBitmap1);
                }else if(fileType==1){
                    linearLayoutDocUpload.setVisibility(View.VISIBLE);
                }

                //******************************************************

                SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    Date d1 = sdf1.parse(edFromDate.getText().toString().trim());
                    Date d2 = sdf1.parse(edToDate.getText().toString().trim());

                    String from = sdf2.format(d1.getTime());
                    String to = sdf2.format(d2.getTime());

                    getLeaveCountByEmp(staticEmpModel.getEmpId(), from, to);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tvBrowse.setOnClickListener(this);

        if (PermissionsUtil.checkAndRequestPermissions(getActivity())) {
        }

        createFolder();

        return view;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edFromDate) {

            int yr, mn, dy;

            Calendar purchaseCal;

            long minDate = 0;

            purchaseCal = Calendar.getInstance();
            purchaseCal.add(Calendar.DAY_OF_MONTH, -7);
            minDate = purchaseCal.getTime().getTime();
            purchaseCal.setTimeInMillis(fromDateMillis);

            yr = purchaseCal.get(Calendar.YEAR);
            mn = purchaseCal.get(Calendar.MONTH);
            dy = purchaseCal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(getContext(), fromDateListener, yr, mn, dy);
            dialog.getDatePicker().setMinDate(minDate);
            dialog.show();

        } else if (v.getId() == R.id.edToDate) {

            int yr, mn, dy;

            long minValue = 0;

            Calendar purchaseCal;

            SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
            String fromDate = edFromDate.getText().toString().trim();
            Date fromdate = null;

            try {
                fromdate = formatter1.parse(fromDate);//catch exception
            } catch (ParseException e) {
                e.printStackTrace();
            }

            purchaseCal = Calendar.getInstance();
            purchaseCal.add(Calendar.DAY_OF_MONTH, -7);
            minValue = purchaseCal.getTime().getTime();
            purchaseCal.setTimeInMillis(toDateMillis);
            yr = purchaseCal.get(Calendar.YEAR);
            mn = purchaseCal.get(Calendar.MONTH);
            dy = purchaseCal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(getContext(), toDateListener, yr, mn, dy);
            dialog.getDatePicker().setMinDate(fromdate.getTime());
            //dialog.getDatePicker().setMinDate(purchaseCal.getTimeInMillis());
            dialog.show();

        } else if (v.getId() == R.id.tv_balanceLeave) {
            new LeaveTypeDialog(getContext()).show();

        }else if (v.getId() == R.id.tvBrowse) {

            Log.e("PHOTO 1", "--------------------------***************************");

           // showCameraDialog("Photo1");
            showFileChooser();

        }
        else if (v.getId() == R.id.btn_apply) {

            final String strFromDate, strTodate, strTotalDayes, strRemark;

            strFromDate = edFromDate.getText().toString();
            strTodate = edToDate.getText().toString();
            strTotalDayes = edDays.getText().toString();
            strRemark = edRemark.getText().toString().trim();

            final float days = Float.parseFloat(strTotalDayes);

            SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");

            Date ToDate = null;
            try {
                ToDate = formatter1.parse(strTodate);//catch exception
            } catch (ParseException e) {
                e.printStackTrace();
            }

            final String DateTo = formatter3.format(ToDate);

            Date FromDate = null;
            try {
                FromDate = formatter1.parse(strFromDate);//catch exception
            } catch (ParseException e) {
                e.printStackTrace();
            }

            final String DateFrom = formatter3.format(FromDate);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final String currDate = sdf.format(System.currentTimeMillis());

            //  Log.e("fromDate", "-------------" + strFromDate);
            //  Log.e("fromTo", "-------------" + strTodate);
            //  Log.e("Dayes", "-------------" + strTotalDayes);
            //  Log.e("Remark", "-------------" + strRemark);
            //Log.e("Model","-------------"+staticEmpModel);

            String dayType = "1";
            if (rbFullDay.isChecked()) {
                dayType = "1";
            } else if (rbHalfDay.isChecked()) {
                dayType = "2";
            }

            int noDayes = 0;
            String stringId = null;
            try {
                 noDayes = (int) days;
                ArrayList<Integer> compOffIdsList = new ArrayList<>();
                for (int i = 0; i < noDayes; i++) {
                    compOffIdsList.add(info.getDailyrecordlistforcompoff().get(i).getId());
                }

                String empIds=compOffIdsList.toString().trim();
                Log.e("COMPOFF ID","---------------------------------"+empIds);

                 stringId = ""+empIds.substring(1, empIds.length()-1).replace("][", ",")+"";

                Log.e("COMPOFF  ID STRING","---------------------------------"+stringId);


            }catch (Exception e)
            {
                e.printStackTrace();
                Log.e("Exception","----------------"+e);
            }
            Log.e("No of Dayes","--------------------------------"+noDayes);

            final int leaveType = leaveTypeIdArray.get(spType.getSelectedItemPosition());
            String leaveShortName = leaveTypeShortNameArray.get(spType.getSelectedItemPosition());

            Log.e("Short name","-----------------------------------"+leaveShortName);

            if (leaveType == 0) {
                TextView viewType = (TextView) spType.getSelectedView();
                viewType.setError("required");

            }else if(info.getError())
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                builder.setTitle("Caution");
                builder.setMessage(info.getMsg());
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }else if(leaveShortName.equals("LWP"))
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                builder.setTitle("Confirmation");
                builder.setMessage("Applied for Leave from  " + strFromDate + " to " + strTodate + " for " + days + " days.");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        final LeaveApply leaveApply = new LeaveApply(0, currentYearModel.getCalYrId(), staticEmpModel.getEmpId(), leaveType, "1", DateFrom, DateTo, days, strRemark, 1, "", 1, 1, loginUser.getUserId(), currDate, 1, 0, 0, "", "0", "-");
                        getAuthIdByEmpId(staticEmpModel.getEmpId(), leaveApply);

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
            }else if((leaveShortName.equals("Compoff")) && (info.getDailyrecordlistforcompoff().size()<=0))
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                builder.setTitle("Caution");
                builder.setMessage("Your balance leaves are insufficient. Available balance leaves for " + leaveTypeNameArray.get(spType.getSelectedItemPosition()) + " is " + info.getDailyrecordlistforcompoff().size());
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else if (!(leaveShortName.equals("Compoff")) && (leaveTypeBalArray.get(spType.getSelectedItemPosition()) < (int) days)) {
                TextView viewType = (TextView) spType.getSelectedView();
                viewType.setError(null);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                builder.setTitle("Caution");
                builder.setMessage("Your balance leaves are insufficient. Available balance leaves for " + leaveTypeNameArray.get(spType.getSelectedItemPosition()) + " is " + leaveTypeBalArray.get(spType.getSelectedItemPosition()));
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
//            else if(fileType==1)
//            {
//                Log.e("LOG","---------------------File----------------");
//                if(imagePath1==null)
//                {
//                    Toast.makeText(getActivity(), "Please Select Photo", Toast.LENGTH_SHORT).show();
//                }
//            }
            else {

               // final LeaveApply leaveApply = new LeaveApply(0, currentYearModel.getCalYrId(), staticEmpModel.getEmpId(), leaveType, dayType, DateFrom, DateTo, days, strRemark, 1, "", 1, 1, loginUser.getUserId(), currDate, 1, 0, 0, "", "", "");

                final LeaveApply leaveApply = new LeaveApply(0, currentYearModel.getCalYrId(), staticEmpModel.getEmpId(), leaveType, "1", DateFrom, DateTo, days, strRemark, 1, "", 1, 1, loginUser.getUserId(), currDate, 1, 0, 0, "", "", "-");

                if(leaveType==1)
                {
                    leaveApply.setExVar2(stringId);
                    Log.e("LEAVE BIN","--------------------------------------------"+leaveApply);
                }else{
                    leaveApply.setExVar2("0");
                    Log.e("LEAVE OUT BIN","--------------------------------------------"+leaveApply);
                }

                if(fileType==1)
                {
                    if(imagePath!=null)
                    {
                        Log.e("Doc upload","----------------------------------------");

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                        builder.setTitle("Confirmation");
                        builder.setMessage("Applied for Leave from  " + strFromDate + " to " + strTodate + " for " + days + " days.");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ArrayList<String> pathArray = new ArrayList<>();
                                ArrayList<String> fileNameArray = new ArrayList<>();

                                String photo1 = "";

                                // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss");
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                if (imagePath != null) {

                                    pathArray.add(imagePath);

                                    File imgFile1 = new File(imagePath);
                                    int pos = imgFile1.getName().lastIndexOf(".");
                                    String ext = imgFile1.getName().substring(pos + 1);
                                    photo1 = sdf.format(Calendar.getInstance().getTimeInMillis()) + "_p1." + ext;
                                    fileNameArray.add(photo1);
                                }

                                leaveApply.setExVar3(photo1);
                                sendImage(pathArray, fileNameArray, leaveApply);

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

                    }else{
                        Toast.makeText(getActivity(), "Please Select Photo", Toast.LENGTH_SHORT).show();
                    }
                }else{

                    Log.e("Doc not upload","----------------------------------------");

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Applied for Leave from  " + strFromDate + " to " + strTodate + " for " + days + " days.");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            getAuthIdByEmpId(staticEmpModel.getEmpId(), leaveApply);

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

    private void sendImage(final ArrayList<String> filePath, final ArrayList<String> fileName, final LeaveApply leaveApply) {

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
        RequestBody imgType = RequestBody.create(MediaType.parse("text/plain"), "3");

        Call<JSONObject> call = Constants.myInterface.imageUpload(authHeader, uploadImagesParts, fileName, imgType);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                commonDialog.dismiss();
                imagePath = null;

                Log.e("Response : ", "--" + response.body());

                getAuthIdByEmpId(staticEmpModel.getEmpId(), leaveApply);

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

    private void getAuthIdByEmpId(Integer empId, final LeaveApply leaveApply) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<AuthorityIds> listCall = Constants.myInterface.getAuthIdByEmpId(authHeader, empId);
            listCall.enqueue(new Callback<AuthorityIds>() {
                @Override
                public void onResponse(Call<AuthorityIds> call, Response<AuthorityIds> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("AUTHORITY MODEL : ", " - " + response.body());
                            AuthorityIds authorityIds = response.body();


                            if (loginUser.getEmpId() == authorityIds.getFinAuthEmpId()) {
                                leaveApply.setFinalStatus(3);
                                leaveApply.setExInt1(3);

                                getApplyLeave(leaveApply);

                            } else if (loginUser.getEmpId() == authorityIds.getIniAuthEmpId()) {
                                leaveApply.setFinalStatus(2);
                                leaveApply.setExInt1(2);
                                getApplyLeave(leaveApply);

                            } else {
                                getApplyLeave(leaveApply);
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

    private void getChangeInDaily(String strFromDate, String strTodate, Integer empId, int userId, final LeaveApply leaveApply) {

        Log.e("PARAMETERS : ", "      FROM DATE    : " + strFromDate + "      TO DATE    : " + strTodate+"      EMP ID    : " + empId+"      USER ID    : " + userId);
        if (Constants.isOnline(getContext())) {
          //  final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
          //  commonDialog.show();

            String base = Constants.userName + ":" + Constants.password;
            String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

            Call<Info> listCall = Constants.myInterface.changeInDailyDailyAfterLeaveTransactionByApp(authHeader, strFromDate ,strTodate,empId,userId);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {
                            commonDialog.dismiss();
                            Log.e("Data Daily Daily: ", "------------" + response.body());
                            Info info=response.body();



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

    private void getApplyLeave(final LeaveApply leaveApply) {
        Log.e("PARAMETERS : ", "      ------------------------------------- LEAVE MODEL :------------------- " + leaveApply);
        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            //final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            //commonDialog.show();

            Call<LeaveApply> listCall = Constants.myInterface.saveLeaveApply(authHeader, leaveApply);
            listCall.enqueue(new Callback<LeaveApply>() {
                @Override
                public void onResponse(Call<LeaveApply> call, Response<LeaveApply> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("APPLY LEAVE : ", " ---------------------APPLY LEAVE---------------------- " + response.body());
                            LeaveApply model = response.body();

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            String currDate = sdf.format(System.currentTimeMillis());

                            SaveLeaveTrail saveLeaveTrail = new SaveLeaveTrail(0, model.getLeaveId(), staticEmpModel.getEmpId(), "", model.getExInt1(), loginUser.getUserId(), "" + currDate);
                            saveLeaveTrail(model.getLeaveId(), saveLeaveTrail,leaveApply);

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
                public void onFailure(Call<LeaveApply> call, Throwable t) {
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


    private void saveLeaveTrail(final Integer leaveId, SaveLeaveTrail saveLeaveTrail, final LeaveApply leaveApply) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            //final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            // commonDialog.show();

            Call<SaveLeaveTrail> listCall = Constants.myInterface.saveLeaveTrail(authHeader, saveLeaveTrail);
            listCall.enqueue(new Callback<SaveLeaveTrail>() {
                @Override
                public void onResponse(Call<SaveLeaveTrail> call, Response<SaveLeaveTrail> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("SAVE TRAIL : ", " ------------------------------SAVE TRAIL------------------------- " + response.body());

                            if (response.body().getTrailPkey() > 0) {

                                String strFromDate = edFromDate.getText().toString();
                                String strTodate = edToDate.getText().toString();

                                if(leaveApply.getFinalStatus()==3) {
                                    getChangeInDaily(strFromDate, strTodate, staticEmpModel.getEmpId(), loginUser.getEmpId(), leaveApply);
                                }

                                if(leaveApply.getLvTypeId()==1)
                                {
                                    updateWeeklyOff(1,leaveApply.getExVar2());
                                }

                                updateLeaveTrailId(leaveId, response.body().getTrailPkey());
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
                public void onFailure(Call<SaveLeaveTrail> call, Throwable t) {
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

    private void updateWeeklyOff(int status, String compOff) {
        Log.e("PARAMETERS : ", "      STATUS    : " + status + "      COMP OFF   : " + compOff);
        if (Constants.isOnline(getContext())) {
            //  final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            //  commonDialog.show();

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
                            Info info=response.body();



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

    private void updateLeaveTrailId(final Integer leaveId, int trailId){
        Log.e("PARAMETER","                   LEAVR ID  :      "+leaveId+"        TRAIL ID  :    "+trailId);

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            // final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            // commonDialog.show();

            Call<Info> listCall = Constants.myInterface.updateLeaveTrailId(authHeader, leaveId, trailId);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("UPDATE LEAVE : ", " - " + response.body());

                            if (!response.body().getError()) {

                                //Toast.makeText(getContext(), "SUCCESS", Toast.LENGTH_SHORT).show();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                                    builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                                    builder.setMessage("Leave applied successfully");

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

    DatePickerDialog.OnDateSetListener fromDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            yyyy = year;
            mm = month + 1;
            dd = dayOfMonth;
            edFromDate.setText(dd + "-" + mm + "-" + yyyy);
            edToDate.setText(dd + "-" + mm + "-" + yyyy);
            tvFromDate.setText(yyyy + "-" + mm + "-" + dd);
            tvToDate.setText(yyyy + "-" + mm + "-" + dd);

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -7);
            calendar.set(yyyy, mm - 1, dd);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR, 0);
            fromDateMillis = calendar.getTimeInMillis();


            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date d1 = sdf1.parse(edFromDate.getText().toString().trim());
                Date d2 = sdf1.parse(edToDate.getText().toString().trim());

                String from = sdf2.format(d1.getTime());
                String to = sdf2.format(d2.getTime());

                getLeaveCountByEmp(staticEmpModel.getEmpId(), from, to);

            } catch (ParseException e) {
                e.printStackTrace();
            }


            //getDays(edFromDate.getText().toString().trim(), edToDate.getText().toString().trim());


        }
    };

    DatePickerDialog.OnDateSetListener toDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            yyyy = year;
            mm = month + 1;
            dd = dayOfMonth;
            edToDate.setText(dd + "-" + mm + "-" + yyyy);
            tvToDate.setText(yyyy + "-" + mm + "-" + dd);

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -7);
            calendar.set(yyyy, mm - 1, dd);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR, 0);
            toDateMillis = calendar.getTimeInMillis();

            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date d1 = sdf1.parse(edFromDate.getText().toString().trim());
                Date d2 = sdf1.parse(edToDate.getText().toString().trim());

                String from = sdf2.format(d1.getTime());
                String to = sdf2.format(d2.getTime());

                getLeaveCountByEmp(staticEmpModel.getEmpId(), from, to);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            // getDays(edFromDate.getText().toString().trim(), edToDate.getText().toString().trim());
        }
    };

    @Override
    public void fragmentBecameVisible() {

    }

    private class LeaveTypeDialog extends Dialog {

        public Button btnCancel;
        public RecyclerView recyclerView;
        private BalanceLeaveAdapter mAdapter;
        private ArrayList<BalanceLeaveTemp> balanceList = new ArrayList<>();

        public LeaveTypeDialog(@NonNull Context context) {
            super(context);
        }


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setTitle("Filter");
            setContentView(R.layout.dialog_layout_balance_leave);
            setCancelable(false);

            Window window = getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.TOP | Gravity.RIGHT;
            wlp.x = 10;
            wlp.y = 10;
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(wlp);

            btnCancel = (Button) findViewById(R.id.btnCancel);
            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

            mAdapter = new BalanceLeaveAdapter(balanceLeaveCompOffList, getActivity());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);

        }

    }


    public float getDays(String dt1, String dt2) {

        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");

        float result = 0;

        try {
            Date date1 = myFormat.parse(dt1);
            Date date2 = myFormat.parse(dt2);
            long diff = date2.getTime() - date1.getTime();
            System.out.println("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
            Log.e("DAYS----------------", "***************------------ " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
            result = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            edDays.setText("" + ((int) result + 1));
        } catch (ParseException e) {
            e.printStackTrace();
            result = 0;
            edDays.setText("" + (int) result);
        }
        return result;
    }

    private void getCurrentYear(final Integer empId) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
              commonDialog1 = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog1.show();

            Call<CurrentYearModel> listCall = Constants.myInterface.getCurrentYear(authHeader);
            listCall.enqueue(new Callback<CurrentYearModel>() {
                @Override
                public void onResponse(Call<CurrentYearModel> call, Response<CurrentYearModel> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("CURRENT YEAR : ", " - " + response.body());
                            currentYearModel = response.body();

                            getEmpTypeByEmpId(empId,response.body().getCalYrId());

                            //getBalanceLeave(empId, response.body().getCalYrId());

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
                public void onFailure(Call<CurrentYearModel> call, Throwable t) {
                    commonDialog1.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getEmpTypeByEmpId(final Integer empId, final Integer calYrId) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
           // final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            //commonDialog.show();

            Call<CompOffLeave> listCall = Constants.myInterface.getEmpTypeByempId(authHeader,empId);
            listCall.enqueue(new Callback<CompOffLeave>() {
                @Override
                public void onResponse(Call<CompOffLeave> call, Response<CompOffLeave> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("EMP TYPE ID : ", " - " + response.body());
                            CompOffLeave compOffLeave = response.body();

                          //  getEmpTypeByEmpId(empId,response.body().getCalYrId());

                            getBalanceLeave(empId, calYrId,compOffLeave);

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
                public void onFailure(Call<CompOffLeave> call, Throwable t) {
                    commonDialog1.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }


    private void getBalanceLeave(Integer empId, int curYrId, final CompOffLeave compOffLeave) {
        Log.e("PARAMETERS : ", "        EMP ID : " + empId + "           CURR_YEAR_ID : " + curYrId);

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
           // final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
           // commonDialog.show();

            Call<ArrayList<BalanceLeaveModel>> listCall = Constants.myInterface.getBalanceLeave(authHeader, empId, curYrId);
            listCall.enqueue(new Callback<ArrayList<BalanceLeaveModel>>() {
                @Override
                public void onResponse(Call<ArrayList<BalanceLeaveModel>> call, Response<ArrayList<BalanceLeaveModel>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("BALANCE LIST : ", " - " + response.body());
                            balanceLeaveList.clear();
                            balanceLeaveCompOffList.clear();
                            leaveTypeNameArray.clear();
                            leaveTypeIdArray.clear();
                            leaveTypeBalArray.clear();
                            leaveFileArray.clear();

                            leaveTypeNameArray.add("Select Leave Type");
                            leaveTypeShortNameArray.add("Select Leave Type");
                            leaveTypeBalArray.add(0);
                            leaveTypeIdArray.add(0);
                            leaveFileArray.add(-1);

                            balanceLeaveList = response.body();
                            for (int i = 0; i < balanceLeaveList.size(); i++) {

                                if(balanceLeaveList.get(i).getLvTypeId()==1)
                                {
                                    if(compOffLeave.getWhWork().equals("Compoff")) {

                                        leaveTypeNameArray.add(balanceLeaveList.get(i).getLvTitle());
                                        leaveTypeShortNameArray.add(balanceLeaveList.get(i).getLvTitleShort());

                                        int balLeave = (int) ((balanceLeaveList.get(i).getBalLeave() + balanceLeaveList.get(i).getLvsAllotedLeaves()) - balanceLeaveList.get(i).getSactionLeave());

                                        leaveTypeBalArray.add(balLeave);
                                        leaveTypeIdArray.add(balanceLeaveList.get(i).getLvTypeId());
                                        leaveFileArray.add(balanceLeaveList.get(i).getIsFile());

                                        BalanceLeaveModel balanceLeaveModel=new BalanceLeaveModel(balanceLeaveList.get(i).getLvTypeId(),balanceLeaveList.get(i).getLvsId(),balanceLeaveList.get(i).getLvTitleShort(),balanceLeaveList.get(i).getLvTitle(),balanceLeaveList.get(i).getLvsAllotedLeaves(),balanceLeaveList.get(i).getBalLeave(),balanceLeaveList.get(i).getAplliedLeaeve(),balanceLeaveList.get(i).getSactionLeave(),balanceLeaveList.get(i).getIsFile(),balanceLeaveList.get(i).getMaxAccumulateCarryforward(),balanceLeaveList.get(i).getIsCarryforward(),balanceLeaveList.get(i).getMaxCarryforward(),balanceLeaveList.get(i).getLvsName(),balanceLeaveList.get(i).getIsInCash());
                                        balanceLeaveCompOffList.add(balanceLeaveModel);
                                    }

                                }else {

                                    leaveTypeNameArray.add(balanceLeaveList.get(i).getLvTitle());
                                    leaveTypeShortNameArray.add(balanceLeaveList.get(i).getLvTitleShort());

                                    int balLeave = (int) ((balanceLeaveList.get(i).getBalLeave() + balanceLeaveList.get(i).getLvsAllotedLeaves()) - balanceLeaveList.get(i).getSactionLeave());

                                    leaveTypeBalArray.add(balLeave);
                                    leaveTypeIdArray.add(balanceLeaveList.get(i).getLvTypeId());
                                    leaveFileArray.add(balanceLeaveList.get(i).getIsFile());

                                    BalanceLeaveModel balanceLeaveModel=new BalanceLeaveModel(balanceLeaveList.get(i).getLvTypeId(),balanceLeaveList.get(i).getLvsId(),balanceLeaveList.get(i).getLvTitleShort(),balanceLeaveList.get(i).getLvTitle(),balanceLeaveList.get(i).getLvsAllotedLeaves(),balanceLeaveList.get(i).getBalLeave(),balanceLeaveList.get(i).getAplliedLeaeve(),balanceLeaveList.get(i).getSactionLeave(),balanceLeaveList.get(i).getIsFile(),balanceLeaveList.get(i).getMaxAccumulateCarryforward(),balanceLeaveList.get(i).getIsCarryforward(),balanceLeaveList.get(i).getMaxCarryforward(),balanceLeaveList.get(i).getLvsName(),balanceLeaveList.get(i).getIsInCash());
                                    balanceLeaveCompOffList.add(balanceLeaveModel);
                                }
                            }

                            final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, leaveTypeNameArray);
                            spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
                            spType.setAdapter(spinnerAdapter);



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
                public void onFailure(Call<ArrayList<BalanceLeaveModel>> call, Throwable t) {
                    commonDialog1.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }


    private void getLeaveCountByEmp(final Integer empId, final String fromDate, final String toDate) {
        Log.e("PARAMETERS : ", "        EMP ID : " + empId + "           FROM DATE : " + fromDate + "             TO DATE : " + toDate);

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<LeaveWeeklyOffCount> listCall = Constants.myInterface.getLeaveCountByEmp(authHeader, empId, fromDate, toDate);
            listCall.enqueue(new Callback<LeaveWeeklyOffCount>() {
                @Override
                public void onResponse(Call<LeaveWeeklyOffCount> call, Response<LeaveWeeklyOffCount> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("LEAVE COUNT LIST : ", " - " + response.body());

                            leaveWeeklyOffCount = response.body();

                            float leaveCount = leaveWeeklyOffCount.getLeavecount();
                            float holidayCount = leaveWeeklyOffCount.getHolidaycount();

                            if (rbHalfDay.isChecked()) {
                                leaveCount = leaveCount / 2;
                            }

//                            edDays.setText("" + leaveWeeklyOffCount.getLeavecount());
//                            edWeeklyOff.setText("" + leaveWeeklyOffCount.getHolidaycount());

                            edDays.setText("" + leaveCount);
                            edWeeklyOff.setText("" + holidayCount);

                            int leaveType = leaveTypeIdArray.get(spType.getSelectedItemPosition());
                            String leaveTypeShortName = leaveTypeShortNameArray.get(spType.getSelectedItemPosition());
                            int noOfDayes= (int) leaveCount;

                            CheckLeaveValidation(fromDate,toDate,empId,leaveType,leaveTypeShortName,noOfDayes);

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
                public void onFailure(Call<LeaveWeeklyOffCount> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void CheckLeaveValidation(String fromDate, String toDate, Integer empId, int leaveType, final String leaveTypeShortName, int leaveCount) {

        Log.e("PARAMETERS : ", "      FROM DATE    : " + fromDate + "      TO DATE    : " + toDate+"      EMP ID    : " + empId+"      LEAVE TYPE    : " + leaveType+"      LEAVE SHORT NAME    : " + leaveTypeShortName+"      NO OF DAYES    : " + leaveCount);
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            String base = Constants.userName + ":" + Constants.password;
            String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

            Call<LeaveInfo> listCall = Constants.myInterface.checkDateForRepetedLeaveValidation(authHeader, fromDate ,toDate,empId,leaveType,leaveTypeShortName,leaveCount);
            listCall.enqueue(new Callback<LeaveInfo>() {
                @Override
                public void onResponse(Call<LeaveInfo> call, Response<LeaveInfo> response) {
                    try {
                        if (response.body() != null) {
                            commonDialog.dismiss();
                            Log.e("Data : ", "------------" + response.body());
                             info=response.body();
                            Log.e("Data Info : ", "------------" + !info.getError());
                            if (!info.getError()){

                                if(leaveTypeShortName.equals("Compoff")) {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                                    builder.setTitle(getResources().getString(R.string.app_name));
                                    builder.setMessage("Your Compoff Pending "+info.getDailyrecordlistforcompoff().size());
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();

                                        }
                                    });
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                }


                            }else
                            {
                                if(leaveTypeShortName.equals("Compoff")) {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                                    builder.setTitle(getResources().getString(R.string.app_name));
                                    builder.setMessage("Your Compoff Pending "+info.getDailyrecordlistforcompoff().size());
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();

                                        }
                                    });
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                }
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
                public void onFailure(Call<LeaveInfo> call, Throwable t) {
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

    private void showFileChooser() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        builder.setTitle("Choose");
        builder.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent pictureActionIntent = null;
                pictureActionIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pictureActionIntent, 101);
            }
        });
        builder.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        f = new File(folder + File.separator, "Camera.jpg");

                        String authorities = BuildConfig.APPLICATION_ID + ".provider";
                        Uri imageUri = FileProvider.getUriForFile(getContext(), authorities, f);

                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, 102);

                    } else {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        f = new File(folder + File.separator, "Camera.jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, 102);

                    }
                } catch (Exception e) {
                    ////Log.e("select camera : ", " Exception : " + e.getMessage());
                }
            }
        });
        builder.show();

    }


    //----NEW-----
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
                    myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    //imageView.setImageBitmap(myBitmap);

                    myBitmap = shrinkBitmap(imgFile.getAbsolutePath(), 720, 720);

                    try {
                        FileOutputStream out = new FileOutputStream(path);
                        myBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                        out.flush();
                        out.close();
                        //Log.e("Image Saved  ", "---------------");

                    } catch (Exception e) {
                        //Log.e("Exception : ", "--------" + e.getMessage());
                        //  e.printStackTrace();
                    }
                }
                imagePath = f.getAbsolutePath();
                tvFileName.setText("" + f.getName());
            } catch (Exception e) {
                // e.printStackTrace();
            }

        } else if (resultCode == getActivity().RESULT_OK && requestCode == 101) {
            try {
                realPath = RealPathUtil.getRealPathFromURI_API19(getContext(), data.getData());
                Uri uriFromPath = Uri.fromFile(new File(realPath));
                myBitmap = getBitmapFromCameraData(data, getContext());

                //imageView.setImageBitmap(myBitmap);
                imagePath = uriFromPath.getPath();
                tvFileName.setText("" + uriFromPath.getPath());

                try {

                    FileOutputStream out = new FileOutputStream(uriFromPath.getPath());
                    myBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
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



    public void createFolder() {
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

}
