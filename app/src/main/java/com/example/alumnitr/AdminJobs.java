package com.example.alumnitr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class AdminJobs extends AppCompatActivity {

    private EditText jobName, contentName, statusName, jobsDate;
    private Button insertJob, showJob;
    private ImageView btnBacks;
    private FirebaseFirestore fStore;
    private String uJobName, uJobContent, uStatusName, uJobDate, uId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_jobs);

        jobName = (EditText) findViewById(R.id.jobName);
        contentName = (EditText) findViewById(R.id.jobContent);
        statusName = (EditText) findViewById(R.id.statusName);
        jobsDate = findViewById(R.id.jobDate);

        fStore = FirebaseFirestore.getInstance();

        insertJob = (Button) findViewById(R.id.jobInsert);
        showJob = findViewById(R.id.jobShow);
        btnBacks = findViewById(R.id.btnBack);
        Bundle bundle = getIntent().getExtras();
        if (bundle !=null){
            insertJob.setText("Update");
            uJobName = bundle.getString("uJobName");
            uJobContent = bundle.getString("uJobContent");
            uJobDate = bundle.getString("uJobDate");
            uStatusName = bundle.getString("uStatusName");
            uId = bundle.getString("uId");

            jobName.setText(uJobName);
            contentName.setText(uJobContent);
            statusName.setText(uStatusName);
            jobsDate.setText(uJobDate);

        }else{
            insertJob.setText("Save");
        }

        btnBacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminJobs.this, Admin.class));
                finish();
            }
        });

        showJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminJobs.this, AdminJobsRecords.class));
                finish();
            }
        });

        insertJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String JobName = jobName.getText().toString();
                String ContentName = contentName.getText().toString();
                String StatusName = statusName.getText().toString();
                String JobsDate = jobsDate.getText().toString();

                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 !=null){
                    String id = uId;
                    updateToFirestore(id, JobName, ContentName, StatusName, JobsDate);

                }else{
                    String id = UUID.randomUUID().toString();
                    saveToFireStore(id, JobName, ContentName, StatusName, JobsDate);
                }
            }
        });
    }

    private void updateToFirestore(String id, String jobName, String contentName, String statusName, String jobsDate) {
        fStore.collection("Jobs").document(id).update("JobName", jobName,
                "Content", contentName,
                "Status", statusName,
                "JobsDate", jobsDate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(AdminJobs.this, "Data updated", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AdminJobs.this, AdminJobsRecords.class));
                            finish();
                        }else{
                            Toast.makeText(AdminJobs.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminJobs.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveToFireStore(String id, String JobName, String ContentName, String StatusName, String JobsDate){

        if (!JobName.isEmpty() && !ContentName.isEmpty() && !StatusName.isEmpty() && !JobsDate.isEmpty()){
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("JobName", JobName);
            map.put("Content", ContentName);
            map.put("Status", StatusName);
            map.put("JobsDate", JobsDate);

            fStore.collection("Jobs").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(AdminJobs.this, "Data inserted", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AdminJobs.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });

        }else {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }
}