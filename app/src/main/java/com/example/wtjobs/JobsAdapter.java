package com.example.wtjobs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class JobsAdapter extends RecyclerView.Adapter<JobsViewHolder> {

    private Fragment fragment;
    private List<Job> jobList;

    public JobsAdapter(Fragment fragment, List<Job> jobList) {   //Kontruktor za referencu na fragment i listu poslova
        this.fragment = fragment;
        this.jobList = jobList;
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
            holder.jobDescription.setText("Opis: "+jobList.get(position).getJobDescription());
            holder.jobLocation.setText(jobList.get(position).getJobLocation());

            //Provjera za poziciju(server,busser,bartender i foodrunner dobivaju tipse) i postavljanje odgovarajuće satnice
            if(jobList.get(position).getJobTitle().equals("Server") || jobList.get(position).getJobTitle().equals("Busser") || jobList.get(position).getJobTitle().equals("Bartender") || jobList.get(position).getJobTitle().equals("Food Runner"))
                 holder.jobWage.setText("Satnica: " + jobList.get(position).getJobWage() + " $/h + tips");
            else
                holder.jobWage.setText("Satnica: " + jobList.get(position).getJobWage() + " $/h");
            holder.jobTitle.setText("Pozicija: " + jobList.get(position).getJobTitle());
            //Korištenje picasso biblioteke za učitavanje slika posla sa URL-om iz jobList i postavljanje u ImageView jobImage, dok se učitava slika prikazuje se loading_icon
            Picasso.get().load(jobList.get(position).getJobImage()).placeholder(R.drawable.loading_icon).into(holder.jobImage);

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

    public JobsViewHolder(@NonNull View itemView) {
        super(itemView);
        //Čuvanje  referenci svih viewova u jednom view holderu RecyclerView-a pomoću metode findViewById()
        jobDescription=itemView.findViewById(R.id.textViewJobDescription);
        jobLocation=itemView.findViewById(R.id.textViewJobLocation);
        jobWage=itemView.findViewById(R.id.textViewJobWage);
        jobTitle=itemView.findViewById(R.id.textViewJobTitle);
        jobImage=itemView.findViewById(R.id.job_picture);
    }

}
