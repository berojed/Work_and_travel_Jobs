package com.example.wtjobs;

public class JobApplications {

    private String userGmail;
    private String jobTitlee;
    private String jobLocationn;

    public JobApplications(String userGmail, String jobTitlee, String jobLocationn) {
        this.userGmail = userGmail;
        this.jobTitlee = jobTitlee;
        this.jobLocationn = jobLocationn;
    }

    public String getUserGmail() {
        return userGmail;
    }

    public String getJobTitlee() {return jobTitlee;}
    public String getJobLocationn() {
        return jobLocationn;
    }


}


