package com.example.nthandizi_police_service_app_ver1.models;

import java.util.*;

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