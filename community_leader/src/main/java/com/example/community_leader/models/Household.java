package com.example.community_leader.models;

import java.util.Set;

/**
 * 
 */
public class Household {

    /**
     * Default constructor
     */
    public Household() {
    }

    /**
     * 
     */
    public int hhid;

    /**
     * 
     */
    public String date_added;

    /**
     * 
     */
    public String hh_name;

    /**
     * 
     */
    public Set<HouseMember> members;

}