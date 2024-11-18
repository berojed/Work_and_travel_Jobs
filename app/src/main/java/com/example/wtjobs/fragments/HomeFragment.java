package com.example.wtjobs.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wtjobs.R;
import com.example.wtjobs.models.MyApplications;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    DatabaseReference databaseReference;
    LinearLayout containerLast3Applications,containerLast3ApplicationsTouchable,containerLast3ApplicationsStatus;

    Context context;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_home, container, false);

        containerLast3Applications = view.findViewById(R.id.containerLast3Applications);
        containerLast3ApplicationsTouchable=view.findViewById(R.id.containerLast3ApplicationsTouchable);
        containerLast3ApplicationsStatus=view.findViewById(R.id.containerLast3ApplicationsStatus);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("applications");

        fetchAndDisplayLast3Applications();

        containerLast3ApplicationsTouchable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm=getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.main,new ApplicationsFragment()).addToBackStack(null).commit();

            }
        });




        return view;
    }

    public  void  fetchAndDisplayLast3Applications()
    {
        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
        String currentUserUid=currentUser.getUid();

        Query query=databaseReference.child(currentUserUid).orderByKey().limitToLast(3);


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                    for(DataSnapshot dataSnapshot:snapshot.getChildren())
                    {
                        String jobTitle=dataSnapshot.child("jobTitlee").getValue(String.class);

                        TextView textView=new TextView(getContext());
                        textView.setText(jobTitle);
                        textView.setPadding(10,10,10,10);
                        textView.setTextSize(20);
                        textView.setTextColor(Color.WHITE);
                        containerLast3Applications.addView(textView);
                    }

                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    String jobApplicationStatus=dataSnapshot.child("jobApplicationStatus").getValue(String.class);

                    TextView textView=new TextView(getContext());
                    textView.setText(jobApplicationStatus);
                    textView.setPadding(10,10,10,10);
                    textView.setTextSize(20);
                    if(textView.equals("Prihvaćeno"))
                        textView.setTextColor(Color.GREEN);
                    else if(textView.equals("Odbijeno"))
                        textView.setTextColor(Color.RED);
                    else
                        textView.setTextColor(Color.YELLOW);
                    containerLast3ApplicationsStatus.addView(textView);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




}