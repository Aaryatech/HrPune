package com.ats.hrpune.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ats.hrpune.R;
import com.ats.hrpune.activity.HomeActivity;
import com.ats.hrpune.constant.Constants;
import com.ats.hrpune.fragment.UpdateClaimInfoFragment;
import com.ats.hrpune.fragment.UpdateClaimStatusFragment;
import com.ats.hrpune.model.ClaimApp;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClaimApprovalPendingAdapter extends RecyclerView.Adapter<ClaimApprovalPendingAdapter.MyViewHolder> {

    private ArrayList<ClaimApp> claimList;
    private Context context;
    private String type;

    public ClaimApprovalPendingAdapter(ArrayList<ClaimApp> claimList, Context context, String type) {
        this.claimList = claimList;
        this.context = context;
        this.type = type;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView ivPhoto;
        public TextView tvEmpName, tvEmpDesg, tvDate, tvClaimType, tvProject, tvAmount, tvStatus;
        public LinearLayout linearLayout;

        public MyViewHolder(View view) {
            super(view);
            ivPhoto = view.findViewById(R.id.ivPhoto);
            tvEmpName = view.findViewById(R.id.tvEmpName);
            tvEmpDesg = view.findViewById(R.id.tvEmpDesg);
            tvDate = view.findViewById(R.id.tvDate);
            tvClaimType = view.findViewById(R.id.tvClaimType);
            tvProject = view.findViewById(R.id.tvProject);
            tvAmount = view.findViewById(R.id.tvAmount);
            tvStatus = view.findViewById(R.id.tvStatus);
            linearLayout = view.findViewById(R.id.linearLayout);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_claim_approval_pending, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ClaimApp model = claimList.get(position);

        holder.tvEmpName.setText(model.getEmpName());
        //holder.tvEmpDesg.setText(model.getName());
        holder.tvClaimType.setText(model.getClaimTitle());
        holder.tvProject.setText(model.getProjectTitle());
        holder.tvAmount.setText("" + model.getClaimAmount() + "/-");

        String imageUri = String.valueOf(model.getEmpPhoto());
        try {
            Picasso.with(context).load(Constants.IMAGE_URL + "" + imageUri).placeholder(context.getResources().getDrawable(R.drawable.profile)).into(holder.ivPhoto);

        } catch (Exception e) {

        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");

        try {
            Date dt = sdf.parse(model.getCaFromDt());
            String date = sdf1.format(dt.getTime());
            holder.tvDate.setText("" + date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (type.equalsIgnoreCase("pending")) {

            if (model.getClaimStatus() == 1) {
                holder.tvStatus.setText("Initial & Final Approve Pending");
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            } else if (model.getClaimStatus() == 2) {
                holder.tvStatus.setText("Final Approve Pending");
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            } else if (model.getClaimStatus() == 3) {
                holder.tvStatus.setText("Final Approved");
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorApproved));
            } else if (model.getClaimStatus() == 8) {
                holder.tvStatus.setText("Initial Rejected");
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorRejected));
            } else if (model.getClaimStatus() == 9) {
                holder.tvStatus.setText("Final Rejected");
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorRejected));
            } else if (model.getClaimStatus() == 7) {
                holder.tvStatus.setText("Cancelled");
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            }

        } else if (type.equalsIgnoreCase("info")) {

            if (model.getClaimStatus() == 1) {
                holder.tvStatus.setText("Initial Pending & Final Pending");
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            } else if (model.getClaimStatus() == 2) {
                holder.tvStatus.setText("Final Pending");
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            } else if (model.getClaimStatus() == 3) {
                holder.tvStatus.setText("Final Approved");
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorApproved));
            } else if (model.getClaimStatus() == 8) {
                holder.tvStatus.setText("Initial Rejected");
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorRejected));
            } else if (model.getClaimStatus() == 9) {
                holder.tvStatus.setText("Final Rejected");
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorRejected));
            } else if (model.getClaimStatus() == 7) {
                holder.tvStatus.setText("Leave Cancelled");
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            }

        }


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Gson gson = new Gson();
                String json = gson.toJson(model);

                Gson gson1 = new Gson();
                String json1 = gson1.toJson(claimList);

                HomeActivity activity = (HomeActivity) context;

                if (type.equalsIgnoreCase("pending")) {
                    Fragment adf = new UpdateClaimStatusFragment();
                    Bundle args = new Bundle();
                    args.putString("model", json);
                    args.putString("modelList", json1);
                    adf.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "ClaimApprovalPendingFragment").commit();

                } else if (type.equalsIgnoreCase("info")) {
                    Fragment adf = new UpdateClaimInfoFragment();
                    Bundle args = new Bundle();
                    args.putString("model", json);
                    args.putString("modelList", json1);
                    adf.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "ClaimApprovalPendingFragment").commit();

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return claimList.size();
    }
}
