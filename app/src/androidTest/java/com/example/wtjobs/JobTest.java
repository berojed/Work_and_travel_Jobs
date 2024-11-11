package com.example.wtjobs;

import static org.junit.Assert.assertEquals;

import com.example.wtjobs.models.Job;

import org.junit.Test;

public class JobTest {

    @Test
    public void testGetSetJobDescription() {
        Job job = new Job();
        job.setJobDescription("test job description");
        assertEquals("test job description", job.getJobDescription());
    }

    @Test
    public void testGetSetJobTitle() {
        Job job = new Job();
        job.setJobTitle("test job title");
        assertEquals("test job title", job.getJobTitle());
    }

    @Test
    public void testGetSetJobLocation(){
        Job job=new Job();
        job.setJobLocation("test job location");
        assertEquals("test job location",job.getJobLocation());
    }

}
