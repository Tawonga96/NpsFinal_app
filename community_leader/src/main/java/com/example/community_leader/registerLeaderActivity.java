package com.example.community_leader;

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

import com.example.community_leader.models.Citizen;

import org.json.JSONException;
import org.json.JSONObject;

public class registerLeaderActivity extends AppCompatActivity {
    private boolean passwordShowing = false;

    private boolean conPasswordShowing = false;

    EditText phone, e_mail, lastname, firstname, Password;
    TextView signInBtn;
    AppCompatButton createBtn;

    ImageView passwordIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_leader);

        phone = findViewById(R.id.ETPhone);
        e_mail = findViewById(R.id.ETEemail);
        firstname = findViewById(R.id.ETFname);
        lastname = findViewById(R.id.ETLname);
        Password = findViewById(R.id.ETPassword);
        createBtn = findViewById(R.id.getIDBtn);
        passwordIcon = findViewById(R.id.passwordIcon);
        signInBtn = findViewById(R.id.AlreadyExitstBtn);

        //when password icon is clicked
        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //checking showing and hiding of password

                if (passwordShowing) {
                    passwordShowing = false;

                    Password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.show);
                } else {
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

                if (conPasswordShowing) {
                    conPasswordShowing = false;

                    Password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.show);
                } else {
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
                startActivity(new Intent(registerLeaderActivity.this, loginActivity.class));
            }
        });



        //when register or create button is clicked
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String fname = firstname.getText().toString();
        String lname = lastname.getText().toString();
        String email = e_mail.getText().toString();
        String pnumber = phone.getText().toString();
        String password = Password.getText().toString();

        // Perform user and citizen registration
        Citizen citizen = new Citizen();
        citizen.registerUser(fname, lname, pnumber, email, password, registerLeaderActivity.this, new Citizen.RegistrationCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                // Handle successful login
                // Extract user data from the JSONObject

                try {
                    // TODO: Display Toast message and  Navigate to Citizen and Member registration activity after successful user registration.
                    String email = response.getString("email");

                    // Show a toast message or navigate to the next activity after successful registration
                    Toast.makeText(registerLeaderActivity.this, "UserID sent to:" + email, Toast.LENGTH_SHORT).show();
                    // Clear the EditText fields after successful registration
                    firstname.getText().clear();
                    lastname.getText().clear();
                    e_mail.getText().clear();
                    phone.getText().clear();
                    Password.getText().clear();
                    Intent intent = new Intent(registerLeaderActivity.this, RegisterCitizen.class);
                    startActivity(intent);



                } catch (JSONException e){
                    String errorMessage = "Failed to parse Registration request.";
                    Toast.makeText(registerLeaderActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(String errorMessage) {
                // Handle registration failure with server response code and error message
                Toast.makeText(registerLeaderActivity.this, errorMessage, Toast.LENGTH_SHORT).show();

            }
        });
    }
}