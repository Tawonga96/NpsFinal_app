package com.example.community_leader.models;

import java.util.Set;

/**
 * 
 */
public abstract class Intervention {

    /**
     * Default constructor
     */
    public Intervention() {
    }

    /**
     * 
     */
    public int intervention_id;

    /**
     * 
     */
    public String time_initiated;

    /**
     * 
     */
    public String alert_id;


    /**
     * 
     */
    public Set<Status> statuses;

}