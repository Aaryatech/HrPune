package com.ats.hrpune.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.ColorSpace;
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
import com.ats.hrpune.model.ClaimDetail;
import com.ats.hrpune.model.ClaimProof;
import com.ats.hrpune.model.StructureData;

import java.util.ArrayList;

public class ClaimDetailAdapter  extends RecyclerView.Adapter<ClaimDetailAdapter.MyViewHolder>{
    private ArrayList<ClaimDetail> claimDetailsList;
    private Context context;
    private ArrayList<StructureData> structureDataList;
    private ArrayList<ClaimProof> claimProofsList;
    public static String image = null;

    public ClaimDetailAdapter(ArrayList<ClaimDetail> claimDetailsList, Context context, ArrayList<StructureData> structureDataList, ArrayList<ClaimProof> claimProofsList) {
        this.claimDetailsList = claimDetailsList;
        this.context = context;
        this.structureDataList = structureDataList;
        this.claimProofsList = claimProofsList;
    }

    @NonNull
    @Override
    public ClaimDetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_claim_detail, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClaimDetailAdapter.MyViewHolder holder, final int position) {
        final ClaimDetail model=claimDetailsList.get(position);
        holder.tvClaimType.setText(""+model.getClaimTypeTitle());
        holder.tvAmt.setText(""+model.getClaimAmount());
        holder.tvRemark.setText(""+model.getClaimRemarks());

        for(int i=0;i<structureDataList.size();i++) {
            if (model.getClaimTypeId().equals(structureDataList.get(i).getClmTypeId()))
            {
                holder.tvLimit.setText(""+structureDataList.get(i).getClmAmt());
            }
        }

        for(int i=0;i<claimProofsList.size();i++) {
            if (model.getClaimId() == claimProofsList.get(i).getExInt1())
            {
                Log.e("File","-------------------------"+model.getClaimId());
                Log.e("File","-------------------------"+claimProofsList.get(i).getExInt1());
                image = claimProofsList.get(i).getCpDocPath();
                holder.tvFile.setImageResource(R.drawable.ic_file);

            }else{
                Log.e("Line","-------------------------"+model.getClaimId());
                Log.e("Line","-------------------------"+claimProofsList.get(i).getExInt1());
                holder.tvFile.setImageResource(R.drawable.ic_line);
            }
        }

        Log.e("STRUCTURE","--------------------------------------"+structureDataList);
        Log.e("Model","--------------------------------------"+ model);
        Log.e("PROOF","--------------------------------------"+claimProofsList);

        holder.tvFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0;i<claimProofsList.size();i++) {
                    if (model.getClaimId() == claimProofsList.get(i).getExInt1())
                    {
                      //  if(claimProofsList.get(i).getCpDocPath()!= null || ! claimProofsList.get(i).getCpDocPath().isEmpty() || claimProofsList.get(i).getCpDocPath().equals("NA") ) {
                            Log.e("File", "-------------------------" + model.getClaimId());
                            Log.e("File", "-------------------------" + claimProofsList.get(i).getExInt1());
                            image = claimProofsList.get(i).getCpDocPath();
                            Log.e("Image", "-------------------------" + image);

                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(Constants.IMAGE_URL +image));
                            context.startActivity(intent);
                      //  }


                    }else{
                        Log.e("Image Null","-------------------------"+image);
                    }
                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return claimDetailsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvClaimType,tvAmt,tvRemark,tvLimit;
        public ImageView tvFile;;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvClaimType=itemView.findViewById(R.id.tvClaimType);
            tvAmt=itemView.findViewById(R.id.tvAmt);
            tvLimit=itemView.findViewById(R.id.tvlimit);
            tvRemark=itemView.findViewById(R.id.tvRemark);
            tvFile=itemView.findViewById(R.id.tvFile);

        }
    }
}
