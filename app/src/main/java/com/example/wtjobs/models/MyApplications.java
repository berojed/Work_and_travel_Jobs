package com.example.wtjobs.models;

public class MyApplications {
    private  String jobLocationn;
    private  String jobTitlee;

    private String jobApplicationStatus;

    private String applicationID;


    public  MyApplications() {}

    public MyApplications( String jobTitlee, String jobLocationn, String jobApplicationStatus) {
        this.jobTitlee = jobTitlee;
        this.jobLocationn = jobLocationn;
        this.jobApplicationStatus = jobApplicationStatus;
    }




    public String getJobLocation() {
        return jobLocationn;
    }

    public void setJobLocation(String jobLocationn) {
        this.jobLocationn = jobLocationn;
    }

    public String getJobTitle() {
        return jobTitlee;
    }

    public void setJobTitle(String jobTitlee) {
        this.jobTitlee = jobTitlee;
    }

    public String getJobApplicationStatus() {return jobApplicationStatus;}

    public void setJobApplicationStatus(String jobApplicationStatus) {this.jobApplicationStatus=jobApplicationStatus;}

    public String getApplicationID() {return applicationID;}
    public void setApplicationID(String applicationID) {this.applicationID = applicationID;}
}
