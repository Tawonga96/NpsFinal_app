package com.example.community_leader.models;

import java.util.Set;

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