package com.example.alumnitr;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {

    private JobsActivity activity;
    private List<JobsModel> jmList;

    public JobAdapter(JobsActivity activity, List<JobsModel> jmList){
        this.activity = activity;
        this.jmList = jmList;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.itemjobs, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        holder.jobNameA.setText(jmList.get(position).getJobName());
        holder.jobStatusA.setText(jmList.get(position).getStatus());
        holder.jobDateA.setText(jmList.get(position).getJobsDate());
        holder.jobContentA.setText(jmList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return jmList.size();
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder{

        TextView jobNameA, jobStatusA, jobDateA, jobContentA;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);

            jobNameA = itemView.findViewById(R.id.jobNameA);
            jobStatusA = itemView.findViewById(R.id.jobStatusA);
            jobDateA = itemView.findViewById(R.id.jobDateA);
            jobContentA = itemView.findViewById(R.id.jobContentA);
        }
    }
}
