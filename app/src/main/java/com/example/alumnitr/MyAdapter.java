package com.example.alumnitr;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private AnnouncementActivity activity;
    private List<Model> modelList;

    public MyAdapter(AnnouncementActivity activity, List<Model> modelList){
        this.activity = activity;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.SubjectName.setText(modelList.get(position).getSubject());
        holder.SenderName.setText(modelList.get(position).getSender());
        holder.ContentName.setText(modelList.get(position).getContent());
        holder.DateSent.setText(modelList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView SubjectName, SenderName, ContentName, DateSent;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            SubjectName = itemView.findViewById(R.id.subjectText);
            SenderName = itemView.findViewById(R.id.senderText);
            ContentName = itemView.findViewById(R.id.contentText);
            DateSent = itemView.findViewById(R.id.dateText);

        }
    }
}
