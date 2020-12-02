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
import com.ats.hrpune.activity.ClaimHistoryActivity;
import com.ats.hrpune.activity.ClaimHistoryeActivity;
import com.ats.hrpune.model.ClaimHistory;
import com.ats.hrpune.model.ClaimHistoryModel;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ClaimHistoryAdapter extends RecyclerView.Adapter<ClaimHistoryAdapter.MyViewHolder>{
    private ArrayList<ClaimHistory> ClaimHistoryList;
    private Context context;

    public ClaimHistoryAdapter(ArrayList<ClaimHistory> claimHistoryList, Context context) {
        ClaimHistoryList = claimHistoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public ClaimHistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_claim_history, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClaimHistoryAdapter.MyViewHolder myViewHolder, int i) {
        final ClaimHistory model = ClaimHistoryList.get(i);

        myViewHolder.tvType.setText(model.getClaimTitle());
        myViewHolder.tvProjectName.setText(model.getProjectTitle());
        myViewHolder.tvAmount.setText(""+model.getClaimAmount()+"/-");
        myViewHolder.tvDate.setText(model.getClaimFromDate()+" to "+model.getClaimToDate());

        if (model.getClaimFinalStatus() == 1) {
            myViewHolder.tvStatus.setText("Initial & Final Approve Pending");
            myViewHolder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        } else if (model.getClaimFinalStatus() == 2) {
            myViewHolder.tvStatus.setText("Final Approve Pending");
            myViewHolder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        } else if (model.getClaimFinalStatus() == 3) {
            myViewHolder.tvStatus.setText("Final Approved");
            myViewHolder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorApproved));
        } else if (model.getClaimFinalStatus() == 8) {
            myViewHolder.tvStatus.setText("Initial Rejected");
            myViewHolder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorRejected));
        } else if (model.getClaimFinalStatus() == 9) {
            myViewHolder.tvStatus.setText("Final Rejected");
            myViewHolder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorRejected));
        } else if (model.getClaimFinalStatus() == 7) {
            myViewHolder.tvStatus.setText("Cancelled");
            myViewHolder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        }

        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String json = gson.toJson(model);

                Intent intent=new Intent(context, ClaimHistoryeActivity.class);
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
        return ClaimHistoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDate, tvType, tvProjectName, tvAmount,tvStatus;
        public CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvType = itemView.findViewById(R.id.tvClaimType);
            tvProjectName = itemView.findViewById(R.id.tvProjectName);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            cardView=itemView.findViewById(R.id.cardView);
        }
    }
}
