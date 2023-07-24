package com.example.nthandizi_police_service_app_ver1.models;

import java.util.*;

/**
 * 
 */
public class PoliceOfficer extends User {


    public PoliceOfficer() {
//        super(User.LoginApiURL);
    }

    public int pid;

    public int fname;

    public int lname;

    public Set<Job_posting> postings;

    public static void register_officer() {
        // TODO implement here
    }

    public static void login() {

    }

}