package com.example.police_officer;

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

import com.example.police_officer.models.Citizen;
import com.example.police_officer.models.PoliceOfficer;

import org.json.JSONException;
import org.json.JSONObject;

public class registerActivity extends AppCompatActivity {
    private boolean passwordShowing = false;
    private boolean conPasswordShowing = false;
    EditText Phone, Email,Lastname,Firstname, Password;
    AppCompatButton getIDBtn;
    ImageView passwordIcon;
    TextView signInBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Phone = findViewById(R.id.phoneET);
        Email= findViewById(R.id.emailET);
        Firstname = findViewById(R.id.fnameET);
        Lastname = findViewById(R.id.lnameET);
        Password = findViewById(R.id.passwordET);
        getIDBtn = findViewById(R.id.getIDBtn);

        passwordIcon = findViewById(R.id.passwordIcon);
        signInBtn = findViewById(R.id.signInBtn);

        //when register or create button is clicked
        getIDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerOfficer();
            }
        });

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

        //when already have an account click to return to login page
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(registerActivity.this, loginActivity.class));
            }
        });


    }

    private void registerOfficer() {
        String fname =Firstname .getText().toString();
        String lname = Lastname.getText().toString();
        String email = Email.getText().toString();
        String pnumber = Phone.getText().toString();
        String password = Password.getText().toString();

        // Perform user and citizen registration
        Citizen citizen = new Citizen();
        citizen.registerUser(fname, lname, pnumber, email, password, registerActivity.this, new Citizen.RegistrationCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                // Handle successful login
                // Extract user data from the JSONObject

                try {
                    // TODO: Display Toast message and  Navigate to Citizen and Member registration activity after successful user registration.
                    String email = response.getString("email");

                    // Show a toast message or navigate to the next activity after successful registration
                    Toast.makeText(registerActivity.this, "UserID sent to:" + email, Toast.LENGTH_SHORT).show();
                    // Clear the EditText fields after successful registration
                    Firstname.getText().clear();
                    Lastname.getText().clear();
                    Email.getText().clear();
                    Phone.getText().clear();
                    Password.getText().clear();

                    Intent intent = new Intent(registerActivity.this, registerPoliceOfficer.class);
                    startActivity(intent);



                } catch (JSONException e){
                    String errorMessage = "Failed to parse Registration request.";
                    Toast.makeText(registerActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(String errorMessage) {
                // Handle registration failure with server response code and error message
                Toast.makeText(registerActivity.this, errorMessage, Toast.LENGTH_SHORT).show();

            }
        });
    }
}
