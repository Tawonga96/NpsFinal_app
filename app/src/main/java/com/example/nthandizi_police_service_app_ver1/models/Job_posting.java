package com.example.nthandizi_police_service_app_ver1.models;

import java.util.*;

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