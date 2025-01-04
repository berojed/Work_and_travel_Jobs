package com.example.wtjobs.Activities;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wtjobs.R;
import com.example.wtjobs.fragments.JobsFragment;
import com.example.wtjobs.models.Job;
import com.squareup.picasso.Picasso;

import java.util.List;


public class JobsAdapter extends RecyclerView.Adapter<JobsViewHolder> {

    private final Fragment fragment;
    private  List<Job> jobList;

    public JobsAdapter(Fragment fragment, List<Job> jobList) {   //Kontruktor za referencu na fragment i listu poslova
        this.fragment = fragment;
        this.jobList = jobList;
    }

    public void setJobs(List<Job> jobList) {
        this.jobList = jobList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public JobsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //Stvaranje view holdera za predstavljanje pojedinačnih stavka u RecyclerView-u
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.job_item,parent,false); //Dodavanje pojedinačne stavke u roditeljski viewgroup(RecyclerView u ovom slucaju)
        return new JobsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobsViewHolder holder, int position) {

            //Dohvaćanje odgovarajućih podataka za pojedinačnu stavku i postavljanje u odgovarajući textView
            holder.jobDescription.setText("Description: "+jobList.get(position).getJobDescription());

            holder.jobLocation.setText(jobList.get(position).getJobLocation());

            //Provjera za poziciju(server,busser,bartender i foodrunner dobivaju tipse) i postavljanje odgovarajuće satnice
            if(jobList.get(position).getJobTitle().equals("Server") || jobList.get(position).getJobTitle().equals("Busser") || jobList.get(position).getJobTitle().equals("Bartender") || jobList.get(position).getJobTitle().equals("Food Runner"))
                 holder.jobWage.setText("Hourly wage: " + jobList.get(position).getJobWage() + " $/h + tips");
            else
                holder.jobWage.setText("Hourly wage: " + jobList.get(position).getJobWage() + " $/h");

            holder.jobTitle.setText("Job title: " + jobList.get(position).getJobTitle());
            //Korištenje picasso biblioteke za učitavanje slika posla sa URL-om iz jobList i postavljanje u ImageView jobImage, dok se učitava slika prikazuje se loading_icon
            Picasso.get().load(jobList.get(position).getJobImage()).into(holder.jobImage);

            holder.btnApplyJob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(fragment instanceof JobsFragment)
                    {
                        int position= holder.getBindingAdapterPosition();
                        String jobTitle=jobList.get(position).getJobTitle();
                        String jobLocation=jobList.get(position).getJobLocation();
                        String jobApplicationStatus="pending";


                        new AlertDialog.Builder(v.getContext()).setTitle("Confirm application").setMessage("Are you sure that you want to apply for "+jobTitle+" in "+jobLocation+"?")
                                .setPositiveButton("Yes", (dialog, which) -> {
                                    ((JobsFragment) fragment).applyForJob(jobTitle, jobLocation,jobApplicationStatus);
                                }
                                ).setNegativeButton("No", null).show();

                    }
                }
            });

    }





    @Override
    public int getItemCount() {
        return jobList.size();
    }

}

class JobsViewHolder extends RecyclerView.ViewHolder {

   //deklariranje svih view objekata
    public TextView jobTitle, jobLocation, jobWage,jobDescription,jobCompany;

     public ImageView jobImage;

     public Button btnApplyJob;

    public JobsViewHolder(@NonNull View itemView) {
        super(itemView);
        //Čuvanje  referenci svih viewova u jednom view holderu RecyclerView-a pomoću metode findViewById()
        jobDescription=itemView.findViewById(R.id.textViewJobDescription);
        jobLocation=itemView.findViewById(R.id.textViewJobLocation);
        jobWage=itemView.findViewById(R.id.textViewJobWage);
        jobTitle=itemView.findViewById(R.id.textViewJobTitle);
        jobImage=itemView.findViewById(R.id.job_picture);
        btnApplyJob=itemView.findViewById(R.id.btnApplytoJob);
    }

}
