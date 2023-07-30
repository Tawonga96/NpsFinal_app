package com.example.community_leader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.community_leader.models.Citizen;

import org.json.JSONException;
import org.json.JSONObject;

public class loginActivity extends AppCompatActivity {
    EditText usernameET, passwordET;
    AppCompatButton signInBtn;

    TextView signUpBtn;
    ImageView passwordIcon;


    private boolean passwordShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameET = findViewById(R.id.ETusername);
        passwordET = findViewById(R.id.ETpassword);
        passwordIcon = findViewById(R.id.passwordIcon);
        signInBtn = findViewById(R.id.AppCompatLogBtn);
        signUpBtn= findViewById(R.id.BtnSignUp);

        //When signUpBtn clicked
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginActivity.this, registerLeaderActivity.class);
                startActivity(intent);
            }
        });

        //when sign in button clicked
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = usernameET.getText().toString();
                String password = passwordET.getText().toString();

                Citizen citizen = new Citizen();
                citizen.communityLeaderLogin(fname, password, loginActivity.this, new Citizen.CommunityLeaderLoginCallback() {
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

                            Toast.makeText(loginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(loginActivity.this, User_Dashboard_Activity.class);
                            startActivity(intent);

                        } catch (JSONException e) {
                            String errorMessage = "Failed to parse login response.";
                            Toast.makeText(loginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        // Handle login failure
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Handle login failure
                                Toast.makeText(loginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
//                                if (errorMessage.equals("Your account is not yet activated.")) {
//                                    Toast.makeText(loginActivity.this, "Please wait for activation.", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(loginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
//                                }
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