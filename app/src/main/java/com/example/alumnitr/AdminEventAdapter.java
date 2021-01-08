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

public class AdminEventAdapter extends RecyclerView.Adapter<AdminEventAdapter.AdminEventViewHolder> {
    private AdminEventRecords activity;
    private List<EventsModel> eList;
    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();


    public AdminEventAdapter(AdminEventRecords activity, List<EventsModel> eList){
        this.activity = activity;
        this.eList = eList;
    }

    public void updateData(int position){
        EventsModel item = eList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("uId", item.getId());
        bundle.putString("uEventName", item.getEventName());
        bundle.putString("uEventDate", item.getEventDate());
        bundle.putString("uEventContent", item.getEventContent());
        Intent intent = new Intent(activity, AdminEvents.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public void deleteData(int position){
        EventsModel item = eList.get(position);
        fStore.collection("Events").document(item.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            notifyRemoved(position);
                            Toast.makeText(activity, "Data deleted", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(activity, "Error" +task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void notifyRemoved(int position){
        eList.remove(position);
        notifyItemRemoved(position);
        activity.showEventsData();
    }

    @NonNull
    @Override
    public AdminEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.itemevents, parent, false);
        return new AdminEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminEventViewHolder holder, int position) {
        holder.eventName.setText(eList.get(position).getEventName());
        holder.eventDate.setText(eList.get(position).getEventDate());
        holder.eventContent.setText(eList.get(position).getEventContent());

    }

    @Override
    public int getItemCount() {
        return eList.size();
    }

    public static class AdminEventViewHolder extends RecyclerView.ViewHolder{

        TextView eventName, eventDate, eventContent;

        public AdminEventViewHolder(@NonNull View itemView) {
            super(itemView);

            eventName = itemView.findViewById(R.id.eventName);
            eventDate = itemView.findViewById(R.id.eventDate);
            eventContent = itemView.findViewById(R.id.eventContent);
        }
    }

}
