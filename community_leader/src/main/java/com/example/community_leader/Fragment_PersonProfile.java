package com.example.community_leader;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class Fragment_PersonProfile extends Fragment {
    private TextView phoneNumTV, emailTV, fnameTV, lnameTV, communityTV;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment__person_profile, container, false);
        fnameTV = view.findViewById(R.id.fname_id);
        lnameTV = view.findViewById(R.id.lname_id);
        phoneNumTV =view.findViewById(R.id.profile_num_id);
        emailTV =view.findViewById(R.id.profile_email_id);
        communityTV = view.findViewById(R.id.community_name_id);


        // Retrieve the SharedPreferences values
        SharedPreferences preferences = requireContext().getSharedPreferences("UserSession", MODE_PRIVATE);
        int uid = preferences.getInt("uid", 0);
        String firstName = preferences.getString("fname", "");
        String lastName = preferences.getString("lname", "");
        String email = preferences.getString("email", "");
        String phoneNumber = preferences.getString("pnumber", "");
        String communityName =preferences.getString("community_name", "");

        //Display retrieved values
        fnameTV.setText(firstName);
        lnameTV.setText(lastName);
        emailTV.setText(email);
        phoneNumTV.setText(phoneNumber);
        communityTV.setText(communityName);


        return  view;
    }
}