package com.example.alumnitr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CV_View extends AppCompatActivity {

    private ImageView btnBacks;
    private TextView NameView, AddressView, EmailView, PhoneView,
            InterestView, EducationView, WorkView, TrainingView,
            ThesisView, ResearchView, TeachingView, PublicationsView,
            PresentationView, AwardsView, LanguagesView, AffiliationsView;
    private Button editCv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_v__view);

        btnBacks = findViewById(R.id.btnBack);
        editCv = findViewById(R.id.editcv);

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

        editCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CV_View.this, CurriculumV.class));
                finish();
            }
        });

        btnBacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CV_View.this, Profile.class));
                finish();
            }
        });
    }
}