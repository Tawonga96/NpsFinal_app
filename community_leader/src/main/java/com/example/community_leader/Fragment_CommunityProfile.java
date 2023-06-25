package com.example.community_leader;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import com.example.community_leader.R;

import androidx.fragment.app.Fragment;


public class Fragment_CommunityProfile extends Fragment {

    private boolean passwordShowing = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment__community_profile, container, false);

        EditText passwordET = rootView.findViewById(R.id.passwordET);
        EditText conpasswordET = rootView.findViewById(R.id.conPasswordET);
        ImageView passwordIcon = rootView.findViewById(R.id.passwordIcon);
        ImageView conpasswordIcon = rootView.findViewById(R.id.conpasswordIcon);

        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //checking showing and hiding of password

                if(passwordShowing){
                    passwordShowing = false;

                    passwordET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.show);
                }
                else{
                    passwordShowing = true;

                    passwordET.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.hide);
                }
                //move the cursor at the last of the text
                passwordET.setSelection(passwordET.length());
            }
        });

        conpasswordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //checking showing and hiding of password

                if(passwordShowing){
                    passwordShowing = false;

                    conpasswordET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    conpasswordIcon.setImageResource(R.drawable.show);
                }
                else{
                    passwordShowing = true;

                    conpasswordET.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    conpasswordIcon.setImageResource(R.drawable.hide);
                }
                //move the cursor at the last of the text
                conpasswordET.setSelection(conpasswordET.length());
            }
        });


        return rootView;
    }
}