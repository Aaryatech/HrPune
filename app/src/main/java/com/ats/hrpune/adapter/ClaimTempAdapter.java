package com.ats.hrpune.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ats.hrpune.R;
import com.ats.hrpune.activity.ImageZoomActivity;
import com.ats.hrpune.constant.Constants;
import com.ats.hrpune.fragment.AddClaimFragment;
import com.ats.hrpune.model.ClaimApply;
import com.ats.hrpune.model.ClaimHistoryModel;

import java.util.ArrayList;

public class ClaimTempAdapter extends RecyclerView.Adapter<ClaimTempAdapter.MyViewHolder> {

    private ArrayList<ClaimApply> ClaimTempList;
    private Context context;

    public ClaimTempAdapter(ArrayList<ClaimApply> claimTempList, Context context) {
        ClaimTempList = claimTempList;
        this.context = context;
    }

    @NonNull
    @Override
    public ClaimTempAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_claim_temp, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClaimTempAdapter.MyViewHolder holder, final int position) {
        final ClaimApply model=ClaimTempList.get(position);
        holder.tvClaimType.setText(""+model.getExVar1());
        holder.tvAmt.setText(""+model.getClaimAmount());
        holder.tvRemark.setText(""+model.getClaimRemarks());

        holder.ivAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClaimTempList.remove(position);
                new AddClaimFragment().onClickData(position);
                //notifyItemRemoved(i);
                notifyDataSetChanged();
            }
        });

        holder.tvFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(model.getExVar3()== null || model.getExVar3().isEmpty())
                {
                   Log.e("NULL","-------------------------");
                }else{

                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(Constants.IMAGE_URL +model.getExVar3()));
                    context.startActivity(i);

//                    Intent intent = new Intent(context, ImageZoomActivity.class);
//                    intent.putExtra("image", Constants.IMAGE_URL +model.getExVar3());
//                    context.startActivity(intent);
                }

            }
        });

        Log.e("Image","-------------------------------------"+model.getExVar3());
        if(model.getExVar3()== null || model.getExVar3().isEmpty())
        {
            holder.tvFile.setImageResource(R.drawable.ic_line);
        }else{
            holder.tvFile.setImageResource(R.drawable.ic_file);
        }

    }

    @Override
    public int getItemCount() {
        return ClaimTempList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvClaimType,tvAmt,tvRemark;
        public ImageView tvFile;
        public ImageView ivAction;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvClaimType=itemView.findViewById(R.id.tvClaimType);
            tvAmt=itemView.findViewById(R.id.tvAmt);
            tvRemark=itemView.findViewById(R.id.tvRemark);
            tvFile=itemView.findViewById(R.id.tvFile);
            ivAction=itemView.findViewById(R.id.ivAction);
        }
    }
}
