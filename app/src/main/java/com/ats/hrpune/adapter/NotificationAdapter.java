package com.ats.hrpune.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.ats.hrpune.R;
import com.ats.hrpune.model.NotificationTemp;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private ArrayList<NotificationTemp> msgList;
    private Context context;

    public NotificationAdapter(ArrayList<NotificationTemp> msgList, Context context) {
        this.msgList = msgList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvMsg, tvDate;

        public MyViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvMsg = view.findViewById(R.id.tvMsg);
            tvDate = view.findViewById(R.id.tvDate);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_notification, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final NotificationTemp model = msgList.get(position);

        holder.tvTitle.setText(""+model.getTitle());
        holder.tvMsg.setText(""+model.getMessage());
        holder.tvDate.setText(""+model.getDate());


    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

}
