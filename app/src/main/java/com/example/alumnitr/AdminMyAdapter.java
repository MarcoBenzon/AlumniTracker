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

public class AdminMyAdapter extends RecyclerView.Adapter<AdminMyAdapter.AdminMyViewHolder> {

    private AdminAnnouncementRecords activity;
    private List<Model> modelList;
    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    public AdminMyAdapter(AdminAnnouncementRecords activity, List<Model> modelList){
        this.activity = activity;
        this.modelList = modelList;
    }

    public void updateData(int position){
        Model item = modelList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("uId", item.getId());
        bundle.putString("uSubject", item.getSubject());
        bundle.putString("uSender", item.getSender());
        bundle.putString("uContent", item.getContent());
        bundle.putString("uDate", item.getDate());
        Intent intent = new Intent(activity, AdminAnnouncement.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public void deleteData(int position){
        Model item = modelList.get(position);
        fStore.collection("Announcements").document(item.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            notifyRemoved(position);
                            Toast.makeText(activity, "Data Deleted", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(activity, "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void notifyRemoved(int position){
        modelList.remove(position);
        notifyItemRemoved(position);
        activity.showData();
    }

    @NonNull
    @Override
    public AdminMyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item, parent, false);
        return new AdminMyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminMyViewHolder holder, int position) {

        holder.SubjectName.setText(modelList.get(position).getSubject());
        holder.SenderName.setText(modelList.get(position).getSender());
        holder.ContentName.setText(modelList.get(position).getContent());
        holder.DateSent.setText(modelList.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public static class AdminMyViewHolder extends RecyclerView.ViewHolder{

        TextView SubjectName, SenderName, ContentName, DateSent;

        public AdminMyViewHolder(@NonNull View itemView) {
            super(itemView);

            SubjectName = itemView.findViewById(R.id.subjectText);
            SenderName = itemView.findViewById(R.id.senderText);
            ContentName = itemView.findViewById(R.id.contentText);
            DateSent = itemView.findViewById(R.id.dateText);

        }
    }

}
