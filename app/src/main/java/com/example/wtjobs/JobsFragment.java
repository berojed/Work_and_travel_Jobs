package com.example.wtjobs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class JobsFragment extends Fragment {

    //Definiranje recyclerView-a, reference na bazu i liste objekata tipa Job koja sadrži podatke o poslovima
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    List<Job> jobList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_jobs, container, false);


        recyclerView=view.findViewById(R.id.JobsRecyclerView); //Inicijaliziranje recyclerViewu tražeći ga pomoću id-a
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // Postavljanje linearnog layout manager za recyclerView tako da su stavke postavljene verikalno


        jobList=new ArrayList<>();
        JobsAdapter jobsAdapter=new JobsAdapter(JobsFragment.this,jobList); //Kreiranje nove istance adaptera te mu se prosljeđuje referenca na fragment i listu objekata tipa Job
        recyclerView.setAdapter(jobsAdapter); // Postavljanje adaptera na recyclerView koji će podatke povezati s prikazanim elementima

        databaseReference=FirebaseDatabase.getInstance().getReference("jobs"); //Kreiranje reference na bazu podataka za čvor "jobs"
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                jobList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){  //Iteriranje kroz sve čvorove u bazi podataka, dohvaća trenutni Job iz trenutnog snapshota i dodaje ga u listu
                    Job job=dataSnapshot.getValue(Job.class);
                    jobList.add(job);
                }
                jobsAdapter.notifyDataSetChanged();  //Obavještavanje adaptera da su se podaci promijenili da bi on mogao osvježiti prikaz
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                TextView testTextView = view.findViewById(R.id.samoTesitram); //Ako dode do greske pri dohvaćanju podataka, ispisuje se poruka o grešci
                testTextView.setText("Error: " + error.getMessage());
            }
        });


        return view;
    }

}