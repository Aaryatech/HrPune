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
import com.ats.hrpune.model.LeaveEmployeeModel;

import java.util.ArrayList;

public class InitalApproveAdapter extends RecyclerView.Adapter<InitalApproveAdapter.MyViewHolder> {

    private ArrayList<InitalApprove> initalApprovList;
    private Context context;

    public InitalApproveAdapter(ArrayList<InitalApprove> initalApprovList, Context context) {
        this.initalApprovList = initalApprovList;
        this.context = context;
    }

    @NonNull
    @Override
    public InitalApproveAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inital_layout_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InitalApproveAdapter.MyViewHolder holder, int position) {
        final InitalApprove model = initalApprovList.get(position);
        final int pos = position;
        holder.tvDate.setText(model.getAttDate());
        holder.tvStatus.setText(model.getAttStatus());
        holder.tvEmpName.setText(model.getEmpName());
        holder.tvWrHrs.setText(model.getWorkingHrs());
        holder.tvShift.setText(model.getCurrentShiftname());
        holder.edOTHrs.setText(model.getOtHr());

        holder.checkBox.setChecked(initalApprovList.get(position).getChecked());

        holder.checkBox.setTag(initalApprovList.get(position));

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                InitalApprove materialDetail = (InitalApprove) cb.getTag();

                materialDetail.setChecked(cb.isChecked());
                initalApprovList.get(pos).setChecked(cb.isChecked());

            }
        });
    }

    @Override
    public int getItemCount() {
        return initalApprovList.size();
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
