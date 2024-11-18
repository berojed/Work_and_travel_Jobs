package com.example.wtjobs.models;

public class JobApplications {

    private String userGmail;
    private String jobTitlee;
    private String jobLocationn;

    private String jobApplicationStatus;

    public JobApplications(String userGmail, String jobTitlee, String jobLocationn, String jobApplicationStatus) {
        this.userGmail = userGmail;
        this.jobTitlee = jobTitlee;
        this.jobLocationn = jobLocationn;
        this.jobApplicationStatus = jobApplicationStatus;

    }

    public String getUserGmail() {
        return userGmail;
    }

    public String getJobTitlee() {return jobTitlee;}
    public String getJobLocationn() {
        return jobLocationn;
    }

    public String getJobApplicationStatus() {return jobApplicationStatus;}


}


