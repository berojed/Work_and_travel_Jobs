package com.example.wtjobs;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.example.wtjobs.fragments.ConnectionsFragment;
import com.example.wtjobs.fragments.ApplicationsFragment;
import com.example.wtjobs.fragments.HomeFragment;
import com.example.wtjobs.fragments.JobsFragment;
import com.example.wtjobs.fragments.MyAccountFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    FirebaseAuth auth;
    FirebaseUser user;

    BottomNavigationView bottomNavigationView;
    ApplicationsFragment applicationsFragment=new ApplicationsFragment();
    JobsFragment jobsFragment=new JobsFragment();
    ConnectionsFragment connectionsFragment =new ConnectionsFragment();

    HomeFragment homeFragment=new HomeFragment();

    MyAccountFragment myAccountFragment=new MyAccountFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            return insets;
        });

        FirebaseApp.initializeApp(this);

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();


        if(user==null)
        {
            Intent intent=new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
        else {

            bottomNavigationView = findViewById(R.id.bottom_navigation);
            getSupportFragmentManager().beginTransaction().replace(R.id.main,homeFragment).commit();
            bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    if (menuItem.getItemId() == R.id.navigation_home) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main,homeFragment).commit();
                        return true;
                    }

                    else if (menuItem.getItemId() == R.id.navigation_jobs) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main,jobsFragment).commit();
                        return true;
                    }
                    else if (menuItem.getItemId() == R.id.navigation_my_account) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main,myAccountFragment).commit();
                        return true;
                    }

                    return false;
                }
            });
        }









    }
}

