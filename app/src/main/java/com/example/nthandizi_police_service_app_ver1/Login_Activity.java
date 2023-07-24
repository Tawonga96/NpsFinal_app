package com.example.nthandizi_police_service_app_ver1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nthandizi_police_service_app_ver1.models.Citizen;

import org.json.JSONException;
import org.json.JSONObject;

public class Login_Activity extends AppCompatActivity {
    EditText usernameET, passwordET;
    AppCompatButton signInBtn;
    TextView signUpBtn;
    ImageView passwordIcon;
    private boolean passwordShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        passwordIcon = findViewById(R.id.passwordIcon);
        signInBtn = findViewById(R.id.signInBtn);
        signUpBtn = findViewById(R.id.signUpBtn);


        //when already have an account click to return to login page
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login_Activity.this, Register_Activity.class));

            }
        });

        //when sign in button clicked
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = usernameET.getText().toString();
                String password = passwordET.getText().toString();

                Citizen citizen = new Citizen();
                citizen.userLogin(fname, password, Login_Activity.this, new Citizen.LoginCallback() {
                    @Override
                    public void onSuccess(JSONObject userData) {
                        // Handle successful login
                        // Extract user data from the JSONObject
                        try {
                            String uid = userData.getString("uid");
                            String fname = userData.getString("fname");
                            String lname = userData.getString("lname");
                            String pnumber = userData.getString("pnumber");
                            String email = userData.getString("email");

                            // Save user data in SharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("uid", uid);
                            editor.putString("fname", fname);
                            editor.putString("lname", lname);
                            editor.putString("pnumber", pnumber);
                            editor.putString("email", email);
                            editor.apply();
                        } catch (JSONException e) {
                            String errorMessage = "Failed to parse login response.";
                            Toast.makeText(Login_Activity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Login_Activity.this, "Login Successful", Toast.LENGTH_SHORT).show();

//                                Intent intent = new Intent(Login_Activity.this, User_Dashboard_Activity.class);
//                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        // Handle login failure
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Login_Activity.this, errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
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


    }
}