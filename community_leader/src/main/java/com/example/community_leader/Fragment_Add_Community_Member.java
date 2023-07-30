package com.example.community_leader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.community_leader.models.Citizen;


public class Fragment_Add_Community_Member extends Fragment {
    EditText AccountActivateET;

    AppCompatButton ActivateAccount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_community_member, container, false);

        AccountActivateET = rootView.findViewById(R.id.ETUserID);
        ActivateAccount = rootView.findViewById(R.id.ActivateAccount);

        ActivateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               activateAccount();

            }
        });

        return rootView;
    }

    private void activateAccount() {
        String ActivateUserid = AccountActivateET.getText().toString();
        Fragment_Add_Community_Member fragment = Fragment_Add_Community_Member.this;

        Citizen citizen = new Citizen();
        citizen.activateAccount(ActivateUserid, fragment.getActivity(), new Citizen.AccountActivationCallback() {
            @Override
            public void onSuccess(String successMessage) {
                // Handle successful account activation
                AccountActivateET.getText().clear();
                // Show a toast message or perform any action you want to take after successful activation
                Toast.makeText(getActivity(), successMessage, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String errorMessage) {
                // Handle account activation failure
                AccountActivateET.getText().clear();
                // Show a toast message or perform any action you want to take after activation failure
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}