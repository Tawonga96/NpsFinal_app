package com.example.police_officer.models;

import java.util.Set;

/**
 * 
 */
public abstract class Alert {

    /**
     * Default constructor
     */
    public Alert() {
    }

    /**
     * 
     */
    public int alert_id;

    /**
     * 
     */
    public String a_time;

    /**
     * 
     */
    public String code;

    /**
     * 
     */
    public String author;

    /**
     * 
     */
    public String origin;

    /**
     * 
     */
    public String a_type;

    /**
     * 
     */
    public String false_alarm;

    /**
     * 
     */
    public String voided_by;

    /**
     * 
     */
    public String closed_by;

    /**
     * 
     */
    public String closed_at;


    /**
     * 
     */
    public Set<Intervention> interventions;

}