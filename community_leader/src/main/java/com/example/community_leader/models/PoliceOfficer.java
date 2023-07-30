package com.example.community_leader.models;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 
 */
public class PoliceOfficer extends User {


    public PoliceOfficer() {
        super();
    }

    public int pid;

    public String position;


    public Set<Job_posting> postings;

    public static void register_officer() {

    }

    public static void login(){

    }


}