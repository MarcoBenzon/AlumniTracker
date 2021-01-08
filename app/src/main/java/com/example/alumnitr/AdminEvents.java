package com.example.alumnitr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class AdminEvents extends AppCompatActivity {

    private EditText nameEvent, contentEvent, dateEvent;
    private Button insertEvent, showEvent;
    private ImageView imageEvent, btnBacks;
    private FirebaseFirestore fStore;
    private String uEventName, uEventDate, uEventContent, uId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_events);

        nameEvent = findViewById(R.id.eventsName);
        contentEvent = findViewById(R.id.eventsContent);
        dateEvent = findViewById(R.id.eventsDate);
        insertEvent = findViewById(R.id.eventInsert);
        showEvent = findViewById(R.id.eventShow);
        btnBacks = findViewById(R.id.btnBack);

        fStore = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        if (bundle !=null){
            insertEvent.setText("Update");
            uEventName = bundle.getString("uEventName");
            uEventDate = bundle.getString("uEventDate");
            uEventContent = bundle.getString("uEventContent");
            uId = bundle.getString("uId");

            nameEvent.setText(uEventName);
            dateEvent.setText(uEventDate);
            contentEvent.setText(uEventContent);
        }else {
            insertEvent.setText("Save");

        }

        btnBacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminEvents.this, Admin.class));
                finish();
            }
        });

        insertEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eName = nameEvent.getText().toString();
                String eContent = contentEvent.getText().toString();
                String eDate = dateEvent.getText().toString();

                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 !=null){
                    String id = uId;
                    updateToFireStore(id, eName, eContent, eDate);
                }else {
                    String id = UUID.randomUUID().toString();

                    saveToFireStore(id, eName, eContent, eDate);
                }
            }
        });

        showEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminEvents.this, AdminEventRecords.class));
                finish();
            }
        });

    }

    private void updateToFireStore(String id, String eName, String eContent, String eDate) {

        fStore.collection("Events").document(id).update("EventName", eName, "EventDate", eDate, "EventContent", eContent)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(AdminEvents.this, "Data Updated", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AdminEvents.this, AdminEventRecords.class));
                        }else {
                            Toast.makeText(AdminEvents.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminEvents.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveToFireStore(String id, String eName, String eContent, String eDate) {
        if (!eName.isEmpty() && !eContent.isEmpty() && !eDate.isEmpty()){
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("EventName", eName);
            map.put("EventContent", eContent);
            map.put("EventDate", eDate);

            fStore.collection("Events").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(AdminEvents.this, "Data inserted", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AdminEvents.this, AdminEventRecords.class));
                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AdminEvents.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });

        }else {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }
}