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

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    DatabaseReference databaseReference;
    LinearLayout containerLast3Applications,containerLast3ApplicationsTouchable,
            containerLast3ApplicationsStatus,containerLast3Connections,containerLast3ConnectionsTouchable,containerLast3ConnectionsLocation;

    Context context;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_home, container, false);

        containerLast3Applications = view.findViewById(R.id.containerLast3Applications);
        containerLast3ApplicationsTouchable=view.findViewById(R.id.containerLast3ApplicationsTouchable);
        containerLast3ApplicationsStatus=view.findViewById(R.id.containerLast3ApplicationsStatus);
        containerLast3Connections=view.findViewById(R.id.containerLast3Connections);
        containerLast3ConnectionsTouchable=view.findViewById(R.id.containerLast3ConnectionsTouchable);
        containerLast3ConnectionsLocation=view.findViewById(R.id.containerLast3ConnectionsLocation);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("applications");

        fetchAndDisplayLast3Applications();
        fetchAndDisplayLast3Connections();

        containerLast3ApplicationsTouchable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm=getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.main,new ApplicationsFragment()).addToBackStack(null).commit();

            }
        });

        containerLast3ConnectionsTouchable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm=getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.main,new ConnectionsFragment()).addToBackStack(null).commit();
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

    public  void  fetchAndDisplayLast3Connections()
    {
        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
        String currentUserUid=currentUser.getUid();
        DatabaseReference userReference=FirebaseDatabase.getInstance().getReference().child("applications").child(currentUserUid);
        List<String> userLocations=new ArrayList<>();

        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    String jobLocation=dataSnapshot.child("jobLocationn").getValue(String.class);
                    userLocations.add(jobLocation);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                for(DataSnapshot userSnapshot:snapshot.getChildren())
                {

                    String otherUserID=userSnapshot.getKey();

                    for(DataSnapshot applicationSnapshot:userSnapshot.getChildren())
                    {
                        if(!otherUserID.equals(currentUserUid))
                        {
                            String otherUserLocation=applicationSnapshot.child("jobLocationn").getValue(String.class);
                            String otherUserEmail=applicationSnapshot.child("userGmail").getValue(String.class);


                            if(userLocations.contains(otherUserLocation))
                            {
                                TextView textView=new TextView(getContext());
                                textView.setText(otherUserEmail);
                                textView.setPadding(10,10,10,10);
                                textView.setTextSize(20);
                                textView.setTextColor(Color.WHITE);
                                containerLast3Connections.addView(textView);

                                TextView textView2=new TextView(getContext());
                                textView2.setText(otherUserLocation);
                                textView2.setPadding(50,10,10,10);
                                textView2.setTextSize(20);
                                textView2.setTextColor(Color.BLACK);
                                containerLast3ConnectionsLocation.addView(textView2);
                            }


                        }



                    }



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}

