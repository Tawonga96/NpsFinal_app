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
import android.widget.Toast;

import com.example.nthandizi_police_service_app_ver1.models.Citizen;

public class Register_Activity extends AppCompatActivity {

    private boolean passwordShowing = false;
    private boolean conPasswordShowing = false;

    EditText phone, e_mail,lastname,firstname, Password;
    AppCompatButton createBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

         phone = findViewById(R.id.phoneET);
         e_mail= findViewById(R.id.emailET);
         firstname = findViewById(R.id.FnameET);
         lastname = findViewById(R.id.LnameET);
         Password = findViewById(R.id.PasswordET);
         createBtn = findViewById(R.id.createBtn);
        final ImageView passwordIcon = findViewById(R.id.passwordIcon);


        final TextView signInBtn = findViewById(R.id.signInBtn);


        //when register or create button is clicked
         createBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 registerUser();
             }
         });

        //when password icon is clicked
        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //checking showing and hiding of password

                if(passwordShowing){
                    passwordShowing = false;

                    Password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.show);
                }
                else{
                    passwordShowing = true;

                    Password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.hide);
                }
                //move the cursor at the last of the text
                Password.setSelection(Password.length());

            }
        });

        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //checking showing and hiding of password

                if(conPasswordShowing){
                    conPasswordShowing = false;

                    Password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.show);
                }
                else{
                    conPasswordShowing = true;

                    Password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.hide);
                }
                //move the cursor at the last of the text
                Password.setSelection(Password.length());

            }
        });


    //when already have an account click to return to login page
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register_Activity.this, Login_Activity.class));
            }
        });

    }

    private void registerUser() {
        String fname =firstname .getText().toString();
        String lname = lastname.getText().toString();
        String email = e_mail.getText().toString();
        String pnumber = phone.getText().toString();
        String password = Password.getText().toString();

        // Perform user and citizen registration
        Citizen citizen = new Citizen();
        citizen.registerUser(fname, lname, pnumber, email, password,Register_Activity.this, new Citizen.RegistrationCallback() {
            @Override
            public void onSuccess() {
                // Handle successful registration
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Register_Activity.this, "UserID check Email", Toast.LENGTH_SHORT).show();
                        // Start the login activity
                        startActivity(new Intent(Register_Activity.this, RegisterCitizen.class));
                    }
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                // Handle registration failure
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Register_Activity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}