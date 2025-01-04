package com.example.wtjobs.fragments;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.wtjobs.R;
import com.example.wtjobs.models.MyApplications;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.content.Context;

import java.util.Objects;

public class ApplicationsFragment extends Fragment {

    LinearLayout applicationsContainer;
    DatabaseReference reference;
    Context context;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_applications, container, false);
        context = view.getContext();

        applicationsContainer = view.findViewById(R.id.applicationsContainer);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();



        if (currentUser != null) {
            String uid = currentUser.getUid();

            reference = FirebaseDatabase.getInstance().getReference().child("applications").child(uid);

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    applicationsContainer.removeAllViews();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        String applicationID = dataSnapshot.getKey();
                        String jobTitle = dataSnapshot.child("jobTitlee").getValue(String.class);
                        String jobLocation = dataSnapshot.child("jobLocationn").getValue(String.class);
                        String jobApplicationStatus = dataSnapshot.child("jobApplicationStatus").getValue(String.class);

                        MyApplications application = new MyApplications(jobTitle, jobLocation,jobApplicationStatus);

                        application.setApplicationID(applicationID);
                        viewApplication(application);
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

    public void viewApplication(MyApplications application) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View applicationView = inflater.inflate(R.layout.myapplications_item, null);

        TextView myjobTitle = applicationView.findViewById(R.id.myApplicationsJob);
        TextView myjobLocation = applicationView.findViewById(R.id.myApplicationsLocation);
        TextView myjobStatus = applicationView.findViewById(R.id.myApplicationsStatus);
        Button btnDeleteJobApplication=applicationView.findViewById(R.id.btnDeleteApplication);

        if(Objects.equals(application.getJobApplicationStatus(), "accepted"))
        {
            myjobStatus.setTextColor(Color.GREEN);
        }
        else if (Objects.equals(application.getJobApplicationStatus(),"rejected"))
        {
            myjobStatus.setTextColor(Color.RED);
        }
        else
        {
            myjobStatus.setTextColor(Color.YELLOW);
        }

        myjobTitle.setText("Selected position: " + application.getJobTitle());
        myjobLocation.setText("Selected location: " + application.getJobLocation());
        myjobStatus.setText("Application status: " +  application.getJobApplicationStatus());

        btnDeleteJobApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Confirm deleting your application").setMessage("Are you sure that you want to delete this application?")
                                .setPositiveButton("Yes", (dialog, which) -> {
                                            deleteApplication(application.getApplicationID());
                                        }
                                    ).setNegativeButton("No", null).show();


            }
        });

        applicationsContainer.addView(applicationView);
    }


    public void deleteApplication(String applicationID){

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser != null)
        {

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("applications").child(currentUser.getUid()).child(applicationID);

            reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {

                        Toast.makeText(context, "Job application successfully deleted!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(context, "There was an error while deleting your application!", Toast.LENGTH_SHORT).show();
                    }
                }
            });



        }

    }
}
