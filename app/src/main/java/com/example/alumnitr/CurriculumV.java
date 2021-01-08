package com.example.alumnitr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class CurriculumV extends AppCompatActivity {

    private Button cancelCv, insertCv;
    private TextView NameView, AddressView, EmailView, PhoneView,
            InterestView, EducationView, WorkView, TrainingView,
            ThesisView, ResearchView, TeachingView, PublicationsView,
            PresentationView, AwardsView, LanguagesView, AffiliationsView;

    private FirebaseFirestore fStore;
    private String userID;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculum_v);

        NameView = findViewById(R.id.cvName);
        AddressView = findViewById(R.id.cvAddress);
        EmailView = findViewById(R.id.cvEmail);
        PhoneView = findViewById(R.id.cvPhone);
        InterestView = findViewById(R.id.cvInterest);
        EducationView = findViewById(R.id.cvEducation);
        WorkView = findViewById(R.id.cvWork1);
        TrainingView = findViewById(R.id.cvTraining);
        ThesisView = findViewById(R.id.cvThesis);
        ResearchView = findViewById(R.id.cvResearch);
        TeachingView = findViewById(R.id.cvTeaching);
        PublicationsView = findViewById(R.id.cvPublications);
        PresentationView = findViewById(R.id.cvPresentation);
        AwardsView = findViewById(R.id.cvAwards);
        LanguagesView = findViewById(R.id.cvLanguages);
        AffiliationsView = findViewById(R.id.cvAffilations);

        cancelCv = findViewById(R.id.cancelCV);
        insertCv = findViewById(R.id.insertCV);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        insertCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NameCV = NameView.getText().toString();
                String AddressCV = AddressView.getText().toString();
                String EmailCV = EmailView.getText().toString();
                String PhoneCV = PhoneView.getText().toString();
                String InterestCV = InterestView.getText().toString();
                String EducationCV = EducationView.getText().toString();
                String WorkCV = WorkView.getText().toString();
                String TrainingCV = TrainingView.getText().toString();
                String ThesisCV = ThesisView.getText().toString();
                String ResearchCV = ResearchView.getText().toString();
                String TeachingCV = TeachingView.getText().toString();
                String PublicationsCV = PublicationsView.getText().toString();
                String PresentationCV = PresentationView.getText().toString();
                String AwardsCV = AwardsView.getText().toString();
                String LanguagesCV = LanguagesView.getText().toString();
                String AffiliationsCV = AffiliationsView.getText().toString();

                saveToFireStore(NameCV, AddressCV, EmailCV, PhoneCV, InterestCV,
                        EducationCV, WorkCV, TrainingCV, ThesisCV, ResearchCV,
                        TeachingCV, PublicationsCV, PresentationCV, AwardsCV,
                        LanguagesCV, AffiliationsCV);
            }
        });

        cancelCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CurriculumV.this, Profile.class));
                finish();
            }
        });
    }

    private void saveToFireStore(String nameCV, String addressCV, String emailCV,
                                 String phoneCV, String interestCV, String educationCV,
                                 String workCV, String trainingCV, String thesisCV,
                                 String researchCV, String teachingCV, String publicationsCV,
                                 String presentationCV, String awardsCV, String languagesCV,
                                 String affiliationsCV) {

        if (!nameCV.isEmpty() && !addressCV.isEmpty() && !emailCV.isEmpty() &&
                !phoneCV.isEmpty() && !interestCV.isEmpty() && educationCV.isEmpty() &&
                !workCV.isEmpty() && !trainingCV.isEmpty() && !thesisCV.isEmpty() &&
                !researchCV.isEmpty() && teachingCV.isEmpty() && !publicationsCV.isEmpty() &&
                !presentationCV.isEmpty() && !awardsCV.isEmpty() && !languagesCV.isEmpty() &&
                !affiliationsCV.isEmpty())
        {
            HashMap<String, Object> map = new HashMap<>();
            map.put("FullName", nameCV);
            map.put("Address", addressCV);
            map.put("Email", emailCV);
            map.put("PhoneNumber", phoneCV);
            map.put("Interest", interestCV);
            map.put("Education", educationCV);
            map.put("Work", workCV);
            map.put("Training", trainingCV);
            map.put("Thesis", thesisCV);
            map.put("Research", researchCV);
            map.put("Teaching", teachingCV);
            map.put("Publication", publicationsCV);
            map.put("Presentation", presentationCV);
            map.put("Awards", awardsCV);
            map.put("Language", languagesCV);
            map.put("Affiliations", affiliationsCV);

            fStore.collection("UsersCV").document(userID).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(CurriculumV.this, "Details Saved", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(CurriculumV.this, CV_View.class));
                                finish();
                            }else {
                                Toast.makeText(CurriculumV.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
//        else {
//            Toast.makeText(this, "Empty Fields not allowed", Toast.LENGTH_SHORT).show();
//        }

    }
}