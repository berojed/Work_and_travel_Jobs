package com.example.wtjobs.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wtjobs.R;
import com.example.wtjobs.models.MyConnections;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ConnectionsFragment extends Fragment {

    LinearLayout connectionsContainer;
    DatabaseReference userReference, applicationsReference;
    Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_connections, container, false);
        context = view.getContext();

        connectionsContainer = view.findViewById(R.id.connectionsContainer);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String currentUserUid = currentUser.getUid();
            userReference = FirebaseDatabase.getInstance().getReference().child("applications").child(currentUserUid);

            userReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    List<String> currentUserJobLocations = new ArrayList<>();
                    for (DataSnapshot applicationSnapshot : snapshot.getChildren()) {
                        String currentUserJobLocation = applicationSnapshot.child("jobLocationn").getValue(String.class);
                        if (currentUserJobLocation != null) {
                            currentUserJobLocations.add(currentUserJobLocation);
                        }
                    }
                    if (!currentUserJobLocations.isEmpty()) {
                        findUsersWithSameLocations(currentUserJobLocations,currentUserUid);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        return view;
    }

    public void findUsersWithSameLocations(List<String> currentUserJobLocations, String currentUserUid) {
        applicationsReference = FirebaseDatabase.getInstance().getReference().child("applications");

        applicationsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                connectionsContainer.removeAllViews();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String userUid = userSnapshot.getKey();
                    if (!userUid.equals(currentUserUid))
                    {
                        for (DataSnapshot applicationSnapshot : userSnapshot.getChildren()) {
                            String userEmail = applicationSnapshot.child("userGmail").getValue(String.class);
                            String userJobLocation = applicationSnapshot.child("jobLocationn").getValue(String.class);


                            if (userJobLocation != null && currentUserJobLocations.contains(userJobLocation) ) {
                                MyConnections connections = new MyConnections(userEmail, userJobLocation);
                                viewConnection(connections);
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

    public void viewConnection(MyConnections connections) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View connectionsView = inflater.inflate(R.layout.myconnections_item, null);

        TextView myConnectionsLocation = connectionsView.findViewById(R.id.myConnectionsLocation);
        TextView myConnectionsGmail = connectionsView.findViewById(R.id.myConnectionsGmail);

        myConnectionsGmail.setText("Email kontakt: " + connections.getUserGmail());
        myConnectionsLocation.setText("Lokacija: " + connections.getJobLocationn());


        connectionsContainer.addView(connectionsView);

    }
}
