package com.example.wtjobs;

public class MyConnections {

    private String jobLocationn;

    private String userGmail;

    public MyConnections( String userGmail,String jobLocationn) {
        this.userGmail = userGmail;
        this.jobLocationn = jobLocationn;
    }

    public String getJobLocationn() {
        return jobLocationn;
    }

    public void setJobLocationn(String jobLocationn) {
        this.jobLocationn = jobLocationn;
    }

    public String getUserGmail() {
        return userGmail;
    }

    public void setUserGmail(String userGmail) {
        this.userGmail = userGmail;
    }
}
