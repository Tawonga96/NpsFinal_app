package com.example.nthandizi_police_service_app_ver1.models;

import java.util.*;

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