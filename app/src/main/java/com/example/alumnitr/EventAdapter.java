package com.example.alumnitr;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private EventsActivity activity;
    private List<EventsModel> eList;

    public EventAdapter(EventsActivity activity, List<EventsModel> eList){
        this.activity = activity;
        this.eList = eList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.itemevents, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        holder.eventName.setText(eList.get(position).getEventName());
        holder.eventDate.setText(eList.get(position).getEventDate());
        holder.eventContent.setText(eList.get(position).getEventContent());

    }

    @Override
    public int getItemCount() {
        return eList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder{

        TextView eventName, eventDate, eventContent;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            eventName = itemView.findViewById(R.id.eventName);
            eventDate = itemView.findViewById(R.id.eventDate);
            eventContent = itemView.findViewById(R.id.eventContent);
        }
    }
}
