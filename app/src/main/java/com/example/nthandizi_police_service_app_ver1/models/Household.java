package com.example.nthandizi_police_service_app_ver1.models;

import java.util.*;

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