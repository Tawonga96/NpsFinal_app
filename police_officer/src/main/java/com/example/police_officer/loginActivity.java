package com.example.police_officer;

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

import com.example.police_officer.models.PoliceOfficer;

import org.json.JSONException;
import org.json.JSONObject;

public class loginActivity extends AppCompatActivity {
    private boolean passwordShowing = false;

    EditText passwordET, usernameET;
    ImageView passwordIcon;
    AppCompatButton signInBtn;
    TextView signUpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameET = findViewById(R.id.ETUsername);
        passwordIcon = findViewById(R.id.passwordIcon);
        passwordET = findViewById(R.id.ETPassword);
        signInBtn = findViewById(R.id.BtnSignIn);
        signUpBtn = findViewById(R.id.BtnSignUp);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginActivity.this, registerActivity.class);
                startActivity(intent);
            }
        });
        //when sign in button clicked
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = usernameET.getText().toString();
                String password = passwordET.getText().toString();

                PoliceOfficer policeOfficer = new PoliceOfficer();
                policeOfficer.policeOfficerLogin(fname, password, loginActivity.this, new PoliceOfficer.OfficerLoginCallback() {
                    @Override
                    public void onSuccess(JSONObject userdata) {
                        // Handle successful login
                        // Extract user data from the JSONObject
                        try {
                            String uid = userdata.getString("uid");
                            String fname = userdata.getString("fname");
                            String lname = userdata.getString("lname");
                            String pnumber = userdata.getString("pnumber");
                            String email = userdata.getString("email");

                            // Save user data in SharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("uid", uid);
                            editor.putString("fname", fname);
                            editor.putString("lname", lname);
                            editor.putString("pnumber", pnumber);
                            editor.putString("email", email);
                            editor.apply();

                            Toast.makeText(loginActivity.this, "Police officer login successful.", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(loginActivity.this, homeActivity.class);
//                            startActivity(intent);

                        } catch (JSONException e) {
                            String errorMessage = "Failed to parse login response.";
                            Toast.makeText(loginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(loginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

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
                                Toast.makeText(loginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
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