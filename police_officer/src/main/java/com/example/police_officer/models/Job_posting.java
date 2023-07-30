package com.example.police_officer.models;

import java.util.Set;

/**
 * 
 */
public class Job_posting {

    /**
     * Default constructor
     */
    public Job_posting() {
    }

    /**
     * 
     */
    public int posting_id;

    /**
     * 
     */
    public int pid;

    /**
     * 
     */
    public int psid;

    /**
     * 
     */
    public String assigned_on;

    /**
     * 
     */
    public boolean is_active;


    /**
     * 
     */
    public Set<Police_intervention> interventions;


}