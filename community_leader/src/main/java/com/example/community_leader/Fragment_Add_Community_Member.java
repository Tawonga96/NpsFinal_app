package com.example.community_leader;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.community_leader.models.Registration;


public class Fragment_Add_Community_Member extends Fragment {
    EditText fnameEditText, lnameEditText, pnumberEditText, emailEditText,passwordET;
    ImageView passwordIcon;
    private boolean passwordShowing = false;

    AppCompatButton registerAccount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_community_member, container, false);

        fnameEditText = rootView.findViewById(R.id.FnameET);
        lnameEditText = rootView.findViewById(R.id.LnameET);
        pnumberEditText = rootView.findViewById(R.id.phoneET);
        emailEditText = rootView.findViewById(R.id.emailET);
        passwordET = rootView.findViewById(R.id.passwordET);

        passwordIcon = rootView.findViewById(R.id.passwordIcon);
        registerAccount = rootView.findViewById(R.id.RegisterAccount);


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

        registerAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = fnameEditText.getText().toString();
                String lname = lnameEditText.getText().toString();
                String pnumber = pnumberEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordET.getText().toString();

                Registration.register(fname, lname, pnumber, email, password, requireContext());
                fnameEditText.setText("");
                lnameEditText.setText("");
                pnumberEditText.setText("");
                emailEditText.setText("");
                passwordET.setText("");

//                Intent intent = new Intent(requireContext(), User_Dashboard_Activity.class);
//                startActivity(intent);
            }
        });

        return rootView;
    }
}