package com.example.nthandizi_police_service_app_ver1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Login_Activity extends AppCompatActivity {
    private boolean passwordShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText usernameET = findViewById(R.id.usernameET);
        final EditText passwordET = findViewById(R.id.passwordET);
        final ImageView passwordIcon = findViewById(R.id.passwordIcon);
        final TextView signUpBtn = findViewById(R.id.signUpBtn);
        final AppCompatButton signInBtn = findViewById(R.id.signInBtn);

        //when sign in button clicked
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login_Activity.this, User_Dashboard_Activity.class));
            }
        });

        //when password icon clicked
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

        //when signup button clicked
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login_Activity.this, Register_Activity.class));
            }
        });
    }
}