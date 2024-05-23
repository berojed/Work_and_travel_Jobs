package com.example.wtjobs;

public class Job {
    private  String jobDescription;
    private  String jobLocation;
    private  double jobWage;
    private  String jobCompany;
    private  String jobTitle;
    private  String jobImage;


    public  Job() {}

    public Job(String jobDescription, String jobLocation, double jobWage, String jobCompany, String jobTitle,String jobImage) {
        this.jobDescription = jobDescription;
        this.jobLocation = jobLocation;
        this.jobWage = jobWage;
        this.jobCompany = jobCompany;
        this.jobTitle = jobTitle;
        this.jobImage = jobImage;
    }



    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public double getJobWage() {
        return jobWage;
    }

    public void setJobWage(double jobWage) {
        this.jobWage = jobWage;
    }

    public String getJobCompany() {
        return jobCompany;
    }

    public void setJobCompany(String jobCompany) {
        this.jobCompany = jobCompany;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

     public String getJobImage() {
       return jobImage;
         }
}
