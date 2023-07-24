package com.example.nthandizi_police_service_app_ver1.models;

import java.util.*;

/**
 * 
 */
public class Community {

    /**
     * Default constructor
     */
    public Community() {
    }

    /**
     * 
     */
    public int community_id;

    /**
     * 
     */
    public String district;

    /**
     * 
     */
    public String comm_name;

    /**
     * 
     */
    public String area;

    /**
     * 
     */
    public String date_added;

    /**
     * 
     */
    public Set<Member> members;


    /**
     * 
     */
    public Set<subscribe> subscriptions;

}