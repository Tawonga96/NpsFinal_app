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

        usernameET = findViewById(R.id.UserET);
        passwordET = findViewById(R.id.PsET);
        passwordIcon = findViewById(R.id.passwordIcon);
        signInBtn = findViewById(R.id.signInButton);
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
                     public void onSuccess(JSONObject response) {
                         // Handle successful login
                         // Extract user data from the JSONObject
                         try {
                             String uid = response.getString("uid");
                             String fname = response.getString("fname");
                             String lname = response.getString("lname");
                             String pnumber = response.getString("pnumber");
                             String email = response.getString("email");

                             // Save user data in SharedPreferences
                             SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                             SharedPreferences.Editor editor = sharedPreferences.edit();
                             editor.putString("uid", uid);
                             editor.putString("fname", fname);
                             editor.putString("lname", lname);
                             editor.putString("pnumber", pnumber);
                             editor.putString("email", email);
                             editor.apply();

                             // TODO: Navigate to the dashboard activity or any other screen you want to display after successful login.
                             Intent intent = new Intent(Login_Activity.this, Login_Activity.class);
                             startActivity(intent);
                         } catch (JSONException e) {
                             String errorMessage = "Failed to parse login response.";
                             Toast.makeText(Login_Activity.this, errorMessage, Toast.LENGTH_SHORT).show();
                         }
                     }

                     @Override
                     public void onFailure(String errorMessage) {
                         // Handle login failure
                         runOnUiThread(new Runnable() {
                             @Override
                             public void run() {
                                 if (errorMessage.equals("Your account is not yet activated.")) {
                                     Toast.makeText(Login_Activity.this, "Please wait for activation.", Toast.LENGTH_SHORT).show();
                                 } else {
                                     Toast.makeText(Login_Activity.this, errorMessage, Toast.LENGTH_SHORT).show();
                                 }
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

                 if (passwordShowing) {
                     passwordShowing = false;

                     passwordET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                     passwordIcon.setImageResource(R.drawable.show);
                 } else {
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