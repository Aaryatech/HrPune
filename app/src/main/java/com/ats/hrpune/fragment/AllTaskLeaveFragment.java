package com.ats.hrpune.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ats.hrpune.R;
import com.ats.hrpune.adapter.LeaveApprovalPendingAdapter;
import com.ats.hrpune.inerfaces.AllTaskLeaveInterface;

import static com.ats.hrpune.fragment.LeaveApprovalPendingFragment.staticInfoLeave;


public class AllTaskLeaveFragment extends Fragment implements AllTaskLeaveInterface {

    private RecyclerView recyclerView;
    LeaveApprovalPendingAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_task_leave, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);



        return view;
    }

    @Override
    public void fragmentBecameVisible() {
        adapter = new LeaveApprovalPendingAdapter(staticInfoLeave, getContext(),"info");
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);



    }
}
