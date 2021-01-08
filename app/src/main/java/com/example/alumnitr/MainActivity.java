package com.example.alumnitr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public CardView dashAnnouncements, dashEvents, dashJobs;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dashAnnouncements = (CardView) findViewById(R.id.dashboardAnnouncement);
        dashEvents = (CardView) findViewById(R.id.dashboardEvents);
        dashJobs = (CardView) findViewById(R.id.dashboardJobs);

        dashAnnouncements.setOnClickListener(this);
        dashEvents.setOnClickListener(this);
        dashJobs.setOnClickListener(this);


//        Button logout = findViewById(R.id.logoutBtn);
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(getApplicationContext(), Login.class));
//                finish();
//            }
//        });

        setUpToolbar();
        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case  R.id.nav_dashboard:

                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_profile:

                        Intent intentprofile = new Intent(MainActivity.this, Profile.class);
                        startActivity(intentprofile);
                        break;

                    case R.id.nav_aboutus:

                        Intent intentaboutus = new Intent(MainActivity.this, Aboutus.class);
                        startActivity(intentaboutus);
                        break;

//                    case R.id.nav_graduates:
//
//                        Intent intentgraduates = new Intent(MainActivity.this, ListGraduates.class);
//                        startActivity(intentgraduates);
//                        break;

                    case R.id.nav_logout:
                        Intent intentlogout = new Intent(MainActivity.this, Login.class);
                        FirebaseAuth.getInstance().signOut();
                        startActivity(intentlogout);
                        finish();
                        break;

//                    case R.id.nav_graduates:
//                        Intent intentgraduates = new Intent(MainActivity.this, Announcements.class);
//                        startActivity(intentgraduates);
//                        break;

                    case  R.id.nav_share:{

                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody =  "http://play.google.com/store/apps/detail?id=" + getPackageName();
                        String shareSub = "Try now";
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(sharingIntent, "Share using"));

                    }
                    break;
                }
                return false;
            }
        });
    }
    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()){
            case R.id.dashboardAnnouncement:
                i = new Intent(this, AnnouncementActivity.class);
                startActivity(i);
                break;

            case R.id.dashboardEvents:
                i = new Intent(this, EventsActivity.class);
                startActivity(i);
                break;

            case R.id.dashboardJobs:
                i = new Intent(this, JobsActivity.class);
                startActivity(i);
                break;
        }
    }
}