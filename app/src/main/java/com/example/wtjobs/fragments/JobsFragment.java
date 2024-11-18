package com.example.wtjobs.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wtjobs.JobsAdapter;
import com.example.wtjobs.R;
import com.example.wtjobs.models.Job;
import com.example.wtjobs.models.JobApplications;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    List <Job> filteredList;

    JobsAdapter jobsAdapter;

    SearchView searchView;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_jobs, container, false);


        recyclerView=view.findViewById(R.id.JobsRecyclerView); //Inicijaliziranje recyclerViewu tražeći ga pomoću id-a
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // Postavljanje linearnog layout manager za recyclerView tako da su stavke postavljene verikalno


        jobList=new ArrayList<>();
        filteredList=new ArrayList<>();

        jobsAdapter=new JobsAdapter(JobsFragment.this,jobList); //Kreiranje nove istance adaptera te mu se prosljeđuje referenca na fragment i listu objekata tipa Job
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

        searchView=view.findViewById(R.id.searchJobview);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if(searchView.hasFocus())
                    searchJob(query);
                return true;
            }
        });

        return view;
    }



    public void searchJob(String query)
    {
          filteredList.clear();

          for(Job job : jobList)
          {
              if(job.getJobTitle().toLowerCase().contains(query.toLowerCase()))
              {
                  filteredList.add(job);
              }
          }
          if(!filteredList.isEmpty())
              jobsAdapter.setJobs(filteredList);
          else
              Toast.makeText(getContext(),"Nema rezultata vaše pretrage",Toast.LENGTH_SHORT).show();



    }



    public void applyForJob(String jobTitle, String jobLocation,String jobApplicationStatus)
    {
        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser!=null)
        {
            String uid= currentUser.getUid();
            String userGmail= currentUser.getEmail();
            String jobTitlee=jobTitle.toString();
            String jobLocationn=jobLocation.toString();


            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("applications").child(uid);

            String applicationID=databaseReference1.push().getKey();

            JobApplications jobApplications=new JobApplications(userGmail,jobTitlee,jobLocationn,jobApplicationStatus);


                databaseReference1.child(applicationID).setValue(jobApplications)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(getContext(),"Prijava uspješno poslana",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(getContext(),"Prijava nije poslana",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
        }
    }


}