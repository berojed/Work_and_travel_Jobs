package com.example.wtjobs;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    FirebaseAuth auth;
    FirebaseUser user;
    Button btnLogout;
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment=new HomeFragment();
    JobsFragment jobsFragment=new JobsFragment();
    ConnectionsFragment connectionsFragment =new ConnectionsFragment();


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
        btnLogout=findViewById(R.id.btnLogout);



        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent= new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

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
                    else if (menuItem.getItemId() == R.id.navigation_jobs_applications) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main, connectionsFragment).commit();
                        return true;
                    }

                    return false;
                }
            });
        }









    }
}

