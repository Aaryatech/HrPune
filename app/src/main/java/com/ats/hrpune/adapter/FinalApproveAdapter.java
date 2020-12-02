package com.ats.hrpune.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ats.hrpune.R;
import com.ats.hrpune.model.InitalApprove;

import java.util.ArrayList;

public class FinalApproveAdapter extends RecyclerView.Adapter<FinalApproveAdapter.MyViewHolder> {
    private ArrayList<InitalApprove> finalApprovList;
    private Context context;


    public FinalApproveAdapter(ArrayList<InitalApprove> finalApprovList, Context context) {
        this.finalApprovList = finalApprovList;
        this.context = context;
    }

    @NonNull
    @Override
    public FinalApproveAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inital_layout_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FinalApproveAdapter.MyViewHolder holder, int position) {
        final InitalApprove model = finalApprovList.get(position);
        final int pos = position;
        holder.tvDate.setText(model.getAttDate());
        holder.tvStatus.setText(model.getAttStatus());
        holder.tvEmpName.setText(model.getEmpName());
        holder.tvWrHrs.setText(model.getWorkingHrs());
        holder.tvShift.setText(model.getCurrentShiftname());
        holder.edOTHrs.setText(model.getOtHr());

        holder.checkBox.setChecked(finalApprovList.get(position).getChecked());

        holder.checkBox.setTag(finalApprovList.get(position));

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                InitalApprove materialDetail = (InitalApprove) cb.getTag();

                materialDetail.setChecked(cb.isChecked());
                finalApprovList.get(pos).setChecked(cb.isChecked());

            }
        });
    }

    @Override
    public int getItemCount() {
        return finalApprovList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate,tvStatus,tvEmpName,tvWrHrs,tvShift;
        EditText edOTHrs;
        CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvEmpName = itemView.findViewById(R.id.tvEmpName);
            tvWrHrs = itemView.findViewById(R.id.tvWrHrs);
            tvShift = itemView.findViewById(R.id.tvShift);
            edOTHrs = itemView.findViewById(R.id.edOTHrs);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}
