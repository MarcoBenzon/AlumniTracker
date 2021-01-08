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

public class AdminListGraduates extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseFirestore agraduatesFstore;
    private AdminGraduatesAdapter adapter;
    private List<GraduatesModel> list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list_graduates);

        recyclerView = findViewById(R.id.aGraduatesRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        agraduatesFstore = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        adapter = new AdminGraduatesAdapter(this, list);
        recyclerView.setAdapter(adapter);

        graduateShowData();
    }
    private void graduateShowData(){

        agraduatesFstore.collection("Users").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        for (DocumentSnapshot snapshot : task.getResult()){
                            GraduatesModel model = new GraduatesModel(snapshot.getString("Fullname"),
                                    snapshot.getString("Email"),
                                    snapshot.getString("Phone"),
                                    snapshot.getString("isUser"));
                            list.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminListGraduates.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}