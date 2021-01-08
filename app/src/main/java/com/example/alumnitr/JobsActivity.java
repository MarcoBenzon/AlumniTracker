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

public class JobsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseFirestore jobFstore;
    private JobAdapter adapter;
    private List<JobsModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);

        recyclerView = findViewById(R.id.jobsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        jobFstore = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        adapter = new JobAdapter(this, list);
        recyclerView.setAdapter(adapter);

        jobShowData();
    }

    private void jobShowData(){
        jobFstore.collection("Jobs").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        for (DocumentSnapshot snapshot : task.getResult()){
                            JobsModel model = new JobsModel(snapshot.getString("id"),
                                    snapshot.getString("JobName"),
                                    snapshot.getString("Status"),
                                    snapshot.getString("JobsDate"),
                                    snapshot.getString("Content"));
                            list.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(JobsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}