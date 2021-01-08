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

public class AdminAnnouncement extends AppCompatActivity {

    private EditText SubjectName, SenderName, ContentName, DateSent;
    private Button insertAnn, showAnn;
    private ImageView btnBacks;
    private FirebaseFirestore fStore;
    private String uId, uSubject, uSender, uContent, uDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_announcement);

        SubjectName = (EditText) findViewById(R.id.subjectName);
        SenderName = (EditText) findViewById(R.id.senderName);
        ContentName = (EditText) findViewById(R.id.contentName);
        DateSent = findViewById(R.id.dateSent);

        fStore = FirebaseFirestore.getInstance();

        insertAnn = (Button) findViewById(R.id.insertBtn);
        showAnn = findViewById(R.id.showAnnouncements);
        btnBacks = findViewById(R.id.btnBack);
        Bundle bundle = getIntent().getExtras();
        if (bundle !=null){
            insertAnn.setText("Update");
            uSubject = bundle.getString("uSubject");
            uSender = bundle.getString("uSender");
            uContent = bundle.getString("uContent");
            uDate = bundle.getString("uDate");
            uId = bundle.getString("uId");

            SubjectName.setText(uSubject);
            SenderName.setText(uSender);
            ContentName.setText(uContent);
            DateSent.setText(uDate);

        }else{
            insertAnn.setText("Save");
        }

        btnBacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminAnnouncement.this, Admin.class));
                finish();
            }
        });

        showAnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminAnnouncement.this, AdminAnnouncementRecords.class));
                finish();
            }
        });

        insertAnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String subjectName = SubjectName.getText().toString();
                String senderName = SenderName.getText().toString();
                String contentName = ContentName.getText().toString();
                String dateSent = DateSent.getText().toString();

                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 !=null){
                    String id = uId;
                    updateToFirestore(id, subjectName, senderName, contentName, dateSent);

                }else{
                    String id = UUID.randomUUID().toString();
                    saveToFireStore(id, subjectName, senderName, contentName, dateSent);
                }
            }
        });
    }

    private void updateToFirestore(String id, String SubjectName, String SenderName, String ContentName, String DateSent) {
        fStore.collection("Announcements").document(id).update("Subject", SubjectName,
                "Sender", SenderName,
                "Content", ContentName,
                "Date", DateSent)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(AdminAnnouncement.this, "Data updated", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(AdminAnnouncement.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminAnnouncement.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveToFireStore(String id, String subjectName, String senderName, String contentName, String dateSent){

        if (!subjectName.isEmpty() && !senderName.isEmpty() && !contentName.isEmpty() && !dateSent.isEmpty()){
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("Subject", subjectName);
            map.put("Sender", senderName);
            map.put("Content", contentName);
            map.put("Date", dateSent);

            fStore.collection("Announcements").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(AdminAnnouncement.this, "Data inserted", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AdminAnnouncement.this, AdminAnnouncementRecords.class));
                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AdminAnnouncement.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });

        }else {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }
}