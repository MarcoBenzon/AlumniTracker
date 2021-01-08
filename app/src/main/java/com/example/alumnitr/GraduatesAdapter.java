package com.example.alumnitr;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GraduatesAdapter extends RecyclerView.Adapter<GraduatesAdapter.GraduatesViewHolder> {
    private ListGraduates activity;
    private List<GraduatesModel> gmList;

    public GraduatesAdapter(ListGraduates activity, List<GraduatesModel> gmList){
        this.activity = activity;
        this.gmList = gmList;
    }

    @NonNull
    @Override
    public GraduatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.itemgraduates, parent, false);
        return new GraduatesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GraduatesViewHolder holder, int position) {

        holder.Fullname.setText(gmList.get(position).getFullname());
        holder.Email.setText(gmList.get(position).getEmail());
        holder.PhoneNumber.setText(gmList.get(position).getPhoneNumber());
        holder.isUserr.setText(gmList.get(position).getIsUser());
    }

    @Override
    public int getItemCount() {
        return gmList.size();
    }

    public static class GraduatesViewHolder extends RecyclerView.ViewHolder{

        TextView Fullname, Email, PhoneNumber, isUserr;

        public GraduatesViewHolder(@NonNull View itemView) {
            super(itemView);

            Fullname = itemView.findViewById(R.id.alumniName);
            Email = itemView.findViewById(R.id.alumniEmail);
            PhoneNumber = itemView.findViewById(R.id.alumniPhone);
            isUserr = itemView.findViewById(R.id.isUser);
        }
    }
}
