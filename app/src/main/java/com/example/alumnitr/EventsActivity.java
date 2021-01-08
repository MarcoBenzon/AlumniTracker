package com.example.alumnitr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class EventsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseFirestore fStore;
    private EventAdapter adapter;
    private List<EventsModel> eList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        recyclerView = findViewById(R.id.EventsrecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fStore = FirebaseFirestore.getInstance();
        eList = new ArrayList<>();
        adapter = new EventAdapter(this, eList);
        recyclerView.setAdapter(adapter);
        showData();

    }

    private void showData() {
        fStore.collection("Events").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        eList.clear();
                        for (DocumentSnapshot snapshot : task.getResult()){
                            EventsModel model = new EventsModel(snapshot.getString("id"),
                                    snapshot.getString("EventName"),
                                    snapshot.getString("EventDate"),
                                    snapshot.getString("EventContent"));

                            eList.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EventsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}