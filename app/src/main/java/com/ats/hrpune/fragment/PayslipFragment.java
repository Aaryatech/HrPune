package com.ats.hrpune.fragment;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.hrpune.R;
import com.ats.hrpune.activity.LoginActivity;
import com.ats.hrpune.constant.Constants;
import com.ats.hrpune.model.Info;
import com.ats.hrpune.model.Login;
import com.ats.hrpune.utils.CommonDialog;
import com.ats.hrpune.utils.CustomSharedPreference;
import com.ats.hrpune.utils.MonthYearPickerDialog;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PayslipFragment extends Fragment implements View.OnClickListener {
    TextView click_me,tvDate,tvYear;
    TextView tvSubmit,tvPdf;
    String monthYearStr;
    Login loginUser;

    SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
    SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payslip, container, false);
        getActivity().setTitle("Download Payslip");
        click_me =view.findViewById(R.id.click_me);
        tvDate =view.findViewById(R.id.tvDate);
        tvYear =view.findViewById(R.id.tvYear);
        tvSubmit =view.findViewById(R.id.tvSubmit);
        tvPdf =view.findViewById(R.id.tvPdf);

        String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_USER);
        Gson gson = new Gson();
        loginUser = gson.fromJson(userStr, Login.class);
        Log.e("HOME_ACTIVITY : ", "--------USER-------" + loginUser);

        tvPdf.setVisibility(View.GONE);

        click_me.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        tvPdf.setOnClickListener(this);

        return view;
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.click_me) {
            MonthYearPickerDialog pickerDialog = new MonthYearPickerDialog();
            pickerDialog.setListener(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int i2) {
                    monthYearStr = year + "-" + (month + 1) + "-" + i2;
                    String datemy=formatMonthYear(monthYearStr);
                    click_me.setText(datemy);
                    tvDate.setText("" + (month + 1));
                    tvYear.setText("" + year);
                    Log.e("Text Date", "--------------------------------" + tvDate.getText().toString());
                    Log.e("Text Month", "--------------------------------" + (month + 1));
                    //    click_me.setText((monthYearStr));
                }
            });
            pickerDialog.show(getFragmentManager(), "MonthYearPickerDialog");


        }else if(v.getId()==R.id.tvSubmit)
        {
            if (click_me.getText().toString().isEmpty()) {
                click_me.setError("Select Month and Year");
                click_me.requestFocus();

            } else {
                click_me.setError(null);

//                String strMonth = tvDate.getText().toString();
//                String strYear = tvYear.getText().toString();
                String strDate = click_me.getText().toString();

                String[] namesList = strDate.split("-");
                String strMonth = namesList [0];
                String strYear = namesList [1];

                int month= Integer.parseInt(strMonth);
                int year= Integer.parseInt(strYear);

                Log.e("STR Month", "--------------------------------" + month);
                Log.e("STR Month", "--------------------------------" + year);

                tvPdf.setVisibility(View.GONE);

                getPaylist(month,year,loginUser.getEmpId());

            }
        }else if(v.getId()==R.id.tvPdf){

            String strDate = click_me.getText().toString();

            String[] namesList = strDate.split("-");
            String strMonth = namesList [0];
            String strYear = namesList [1];

            int month= Integer.parseInt(strMonth);
            int year= Integer.parseInt(strYear);

            Log.e("URL","             ----------                 "+Constants.PDF_URL +loginUser.getEmpId()+"/"+month+"-"+year);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(Constants.PDF_URL +loginUser.getEmpId()+"/"+month+"-"+year));
            startActivity(intent);
        }
    }

    private void getPaylist(int month, int year, int empId) {
        Log.e("PARAMETERS : ", "      MONTH    : " + month + "      YEAR    : " + year+"      EMP ID    : " + empId);
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            String base = Constants.userName + ":" + Constants.password;
            String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

            Call<Info> listCall = Constants.myInterface.checkPayslipisgenerated(authHeader, month ,year,empId);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {
                            commonDialog.dismiss();
                            Log.e("Data : ", "------------" + response.body());
                            Info info=response.body();
                            if (!info.getError()){

                                Log.e("ERROR","-------------------------------------"+info.getError());
                                tvPdf.setVisibility(View.VISIBLE);


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
}
