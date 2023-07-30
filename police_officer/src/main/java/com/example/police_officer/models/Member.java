package com.example.police_officer.models;

import java.util.Set;

/**
 * 
 */
public class Member {

    /**
     * Default constructor
     */
    public Member() {
    }

    /**
     * 
     */
    public int mid;

    /**
     * 
     */
    public int cid;

    public int community_id;

    /**
     * 
     */
    public String date_joined;

    /**
     * 
     */
    public String left_on;

    /**
     * 
     */
    public String citizen_typ;

    /**
     * 
     */
    public Citizen citizen;


    /**
     * 
     */
    public Set<Alert> alerts;

    /**
     * 
     */
    public Set<HouseMember> households;

    /**
     * 
     */
    public Set<Community_intervention> interventions;

    /**
     * 
     */
    public void intervene() {
        // TODO implement here
    }

}