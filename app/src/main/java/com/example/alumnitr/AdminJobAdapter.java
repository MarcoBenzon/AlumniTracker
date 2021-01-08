package com.example.alumnitr;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AdminJobAdapter extends RecyclerView.Adapter<AdminJobAdapter.AdminJobViewHolder> {
    private AdminJobsRecords activity;
    private List<JobsModel> jmList;
    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    public AdminJobAdapter(AdminJobsRecords activity, List<JobsModel> jmList){
        this.activity = activity;
        this.jmList = jmList;
    }

    public void updateData(int position){
        JobsModel item = jmList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("uId", item.getId());
        bundle.putString("uJobName", item.getJobName());
        bundle.putString("uJobContent", item.getContent());
        bundle.putString("uStatusName", item.getStatus());
        bundle.putString("uJobDate", item.getJobsDate());
        Intent intent = new Intent(activity, AdminJobs.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public void deleteData(int position){
        JobsModel item = jmList.get(position);
        fStore.collection("Jobs").document(item.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            notifyRemoved(position);
                            Toast.makeText(activity, "Data deleted", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(activity, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void notifyRemoved (int position){
        jmList.remove(position);
        notifyItemRemoved(position);
        activity.jobShowData();
    }

    @NonNull
    @Override
    public AdminJobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.itemjobs, parent, false);
        return new AdminJobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminJobViewHolder holder, int position) {
        holder.jobNameA.setText(jmList.get(position).getJobName());
        holder.jobStatusA.setText(jmList.get(position).getStatus());
        holder.jobDateA.setText(jmList.get(position).getJobsDate());
        holder.jobContentA.setText(jmList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return jmList.size();
    }

    public static class AdminJobViewHolder extends RecyclerView.ViewHolder{

        TextView jobNameA, jobStatusA, jobDateA, jobContentA;

        public AdminJobViewHolder(@NonNull View itemView) {
            super(itemView);

            jobNameA = itemView.findViewById(R.id.jobNameA);
            jobStatusA = itemView.findViewById(R.id.jobStatusA);
            jobDateA = itemView.findViewById(R.id.jobDateA);
            jobContentA = itemView.findViewById(R.id.jobContentA);
        }
    }
}
