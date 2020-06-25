package com.ats.hrpune.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.ats.hrpune.R;
import com.ats.hrpune.model.MyLeaveTrailData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LeaveTrailListAdapter extends RecyclerView.Adapter<LeaveTrailListAdapter.MyViewHolder> {

        private ArrayList<MyLeaveTrailData> msgList;
        private Context context;

        public LeaveTrailListAdapter(ArrayList<MyLeaveTrailData> msgList, Context context) {
            this.msgList = msgList;
            this.context = context;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView tvEmpName, tvRemark, tvDate,tvStatus,tvActionBy;

            public MyViewHolder(View view) {
                super(view);
                tvEmpName = view.findViewById(R.id.tvEmpName);
                tvRemark = view.findViewById(R.id.tvRemark);
                tvStatus = view.findViewById(R.id.tvStatus);
                tvDate = view.findViewById(R.id.tvDate);
                tvActionBy = view.findViewById(R.id.tvActionBy);
            }
        }

        @Override
        public LeaveTrailListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_leave_trail, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(LeaveTrailListAdapter.MyViewHolder holder, int position) {
            final MyLeaveTrailData model = msgList.get(position);

            holder.tvEmpName.setText(model.getUserName());
            holder.tvRemark.setText(model.getEmpRemarks());
            holder.tvActionBy.setText(model.getUserName());

            SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            SimpleDateFormat sdf1=new SimpleDateFormat("dd MMM yyyy");

            try {
                Date date=sdf.parse(model.getMakerEnterDatetime());
                String dt=sdf1.format(date.getTime());
                holder.tvDate.setText(dt);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (model.getLeaveStatus() == 1) {
                holder.tvStatus.setText("Applied");
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.blueColor));
            } else if (model.getLeaveStatus() == 2) {
                holder.tvStatus.setText("Initial Approved");
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.blueColor));
            } else if (model.getLeaveStatus() == 3) {
                holder.tvStatus.setText("Final Approved");
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.greenColor));
            } else if (model.getLeaveStatus() == 8) {
                holder.tvStatus.setText("Initial Rejected");
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.redColor));
            } else if (model.getLeaveStatus() == 9) {
                holder.tvStatus.setText("Final Rejected");
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.redColor));
            } else if (model.getLeaveStatus() == 7) {
                holder.tvStatus.setText("Leave Cancelled");
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.blueColor));
            }
            
        }

        @Override
        public int getItemCount() {
            return msgList.size();
        }


}
