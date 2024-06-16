package com.example.wtjobs;

public class MyApplications {
    private  String jobLocationn;
    private  String jobTitlee;

    private String applicationID;


    public  MyApplications() {}

    public MyApplications( String jobTitlee, String jobLocationn) {
        this.jobTitlee = jobTitlee;
        this.jobLocationn = jobLocationn;
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


    public String getApplicationID() {return applicationID;}
    public void setApplicationID(String applicationID) {this.applicationID = applicationID;}
}
