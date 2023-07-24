package com.example.nthandizi_police_service_app_ver1;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

public class Fragment_PersonProfile extends Fragment {

    private TextView usernameTextView;
    private TextView phoneNumberTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__person_profile, container, false);

        usernameTextView = view.findViewById(R.id.username_id);
        phoneNumberTextView = view.findViewById(R.id.profile_num_id);

        AppCompatButton changePassButton = view.findViewById(R.id.changePass);
        changePassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getActivity().getSharedPreferences("UserSession", MODE_PRIVATE);
                String fname = preferences.getString("fname", "");
                String password = preferences.getString("password", "");

                // Call the login method with the retrieved credentials
//                Login.login(fname, password, getActivity());
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        displayUserInfo();
    }

    private void displayUserInfo() {
        SharedPreferences preferences = getActivity().getSharedPreferences("UserSession", MODE_PRIVATE);
        String fname = preferences.getString("fname", "");
        String pnumber = preferences.getString("pnumber", "");

        usernameTextView.setText(fname);
        phoneNumberTextView.setText(pnumber);
    }
}
