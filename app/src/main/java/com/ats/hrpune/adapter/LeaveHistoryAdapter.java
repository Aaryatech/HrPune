package com.ats.hrpune.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ats.hrpune.R;
import com.ats.hrpune.activity.LeaveDetailActivity;
import com.ats.hrpune.model.LeaveHistory;
import com.ats.hrpune.model.MyLeaveData;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LeaveHistoryAdapter extends RecyclerView.Adapter<LeaveHistoryAdapter.MyViewHolder> {
    private ArrayList<LeaveHistory> historyList;
    private Context context;

    public LeaveHistoryAdapter(ArrayList<LeaveHistory> historyList, Context context) {
        this.historyList = historyList;
        this.context = context;
    }

    @NonNull
    @Override
    public LeaveHistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_leave_history, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaveHistoryAdapter.MyViewHolder myViewHolder, int i) {
        final LeaveHistory model = historyList.get(i);

        myViewHolder.tvType.setText(model.getLvTitle());
        myViewHolder.tvDay.setText("" + model.getLeaveNumDays() + " days");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");

        try {
            Date fromDate = sdf.parse(model.getLeaveFromdt());
            Date toDate = sdf.parse(model.getLeaveTodt());

            String fromDt = sdf1.format(fromDate.getTime());
            String toDt = sdf1.format(toDate.getTime());

            myViewHolder.tvDate.setText("" + fromDt + " to " + toDt);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (model.getExInt1() == 1) {
            myViewHolder.tvStatus.setText("Initial & Final Approve Pending");
            //myViewHolder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        } else if (model.getExInt1() == 2) {
            myViewHolder.tvStatus.setText("Final Approve Pending");
           // myViewHolder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        } else if (model.getExInt1() == 3) {
            myViewHolder.tvStatus.setText("Final Approved");
           // myViewHolder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorApproved));
        } else if (model.getExInt1() == 8) {
            myViewHolder.tvStatus.setText("Initial Rejected");
            //myViewHolder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorRejected));
        } else if (model.getExInt1() == 9) {
            myViewHolder.tvStatus.setText("Final Rejected");
            //myViewHolder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorRejected));
        } else if (model.getExInt1() == 7) {
            myViewHolder.tvStatus.setText("Leave Cancelled");
            //myViewHolder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        }


        if (model.getLeaveDuration().equals("1")) {
            myViewHolder.tvDayType.setText("Full Day");
        } else {
            myViewHolder.tvDayType.setText("Half Day");
        }


        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String json = gson.toJson(model);

                Intent intent = new Intent(context, LeaveDetailActivity.class);
                Bundle args = new Bundle();
                args.putString("model", json);
                intent.putExtra("model", json);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDate, tvType, tvDayType, tvDay, tvStatus;
        public CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvType = itemView.findViewById(R.id.tvLeaveType);
            tvDayType = itemView.findViewById(R.id.tvDayType);
            tvDay = itemView.findViewById(R.id.tvDays);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
