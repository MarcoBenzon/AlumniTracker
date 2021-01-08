package com.example.alumnitr;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {
    TextView fullname, email, phone;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    ImageView changeProfileImage, btnBacks;
    Button btnEdit, btnCv;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        changeProfileImage = findViewById(R.id.viewImage);
        btnEdit = findViewById(R.id.btn_edit);
        btnBacks = findViewById(R.id.btnBack);
//        btnCv = findViewById(R.id.btn_cv);

        fullname = findViewById(R.id.txtName);
        email = findViewById(R.id.txtEmail);
        phone = findViewById(R.id.txtPhone);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileRef = storageReference.child("Users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(changeProfileImage);
            }
        });

        userId = fAuth.getCurrentUser().getUid();


        DocumentReference documentReference = fStore.collection("Users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot.exists()){
                    phone.setText(documentSnapshot.getString("Phone"));
                    fullname.setText(documentSnapshot.getString("Fullname"));
                    email.setText(documentSnapshot.getString("Email"));
                }else{
                    Log.d("tag", "onEvent: Document do not exist");
                }
            }
        });


        btnBacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

//        btnCv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), CV_View.class));
//                finish();
//            }
//        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(v.getContext(), EditProfile.class);
                i.putExtra("Fullname", fullname.getText().toString());
                i.putExtra("Email", email.getText().toString());
                i.putExtra("Phone", phone.getText().toString());
                startActivity(i);
            }
        });
    }

}