
package com.example.alumnitr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
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

public class AdminEventRecords extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseFirestore fStore;
    private AdminEventAdapter adapter;
    private List<EventsModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_event_records);

        recyclerView = findViewById(R.id.aEventRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fStore = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        adapter = new AdminEventAdapter(this, list);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new AdminEventTouchHelper(adapter));
        touchHelper.attachToRecyclerView(recyclerView);

        showEventsData();
    }

    public void showEventsData() {
        fStore.collection("Events").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        for (DocumentSnapshot snapshot: task.getResult()){
                            EventsModel model = new EventsModel(snapshot.getString("id"),
                                    snapshot.getString("EventName"),
                                    snapshot.getString("EventDate"),
                                    snapshot.getString("EventContent"));

                            list.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminEventRecords.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}