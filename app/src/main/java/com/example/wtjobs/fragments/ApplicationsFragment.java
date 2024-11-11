package com.example.wtjobs.fragments;

import android.app.AlertDialog;
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

                        MyApplications application = new MyApplications(jobTitle, jobLocation);

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
        Button btnDeleteJobApplication=applicationView.findViewById(R.id.btnDeleteApplication);


        myjobTitle.setText("Odabrani posao: " + application.getJobTitle());
        myjobLocation.setText("Odabrana lokacija: " + application.getJobLocation());

        btnDeleteJobApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Potvrda brisanja prijave za posao").setMessage("Da li ste sigurni da želite  obrisati ovu prijavu za posao?")
                                .setPositiveButton("Da", (dialog, which) -> {
                                            deleteApplication(application.getApplicationID());
                                        }
                                    ).setNegativeButton("Ne", null).show();


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

                        Toast.makeText(context, "Prijava za posao obrisana!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(context, "Greška pri brisanju prijave za posao!", Toast.LENGTH_SHORT).show();
                    }
                }
            });



        }

    }
}
