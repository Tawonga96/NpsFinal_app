package com.example.nthandizi_police_service_app_ver1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nthandizi_police_service_app_ver1.models.Citizen;

public class RegisterCitizen extends AppCompatActivity {
    EditText citizenID, occupation, community;
    AppCompatButton citizenCreateBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_citizen);

        citizenID = findViewById(R.id.citizenET);
        occupation = findViewById(R.id.occupationET);
        community = findViewById(R.id.commNameET);
        citizenCreateBtn = findViewById(R.id.CitizenCreateBtn);

        citizenCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerCitizen();
            }
        });

    }

    private void registerCitizen() {
        String citizen_ID = citizenID.getText().toString();
        String Occupation = occupation.getText().toString();
        String Community = community.getText().toString();

        // Create an instance of the Citizen class
        Citizen citizen = new Citizen();

        // Call the login method to register the citizen
        citizen.registerCitizen(citizen_ID, Occupation, Community, this, new Citizen.CitizenRegistrationCallback() {
            @Override
            public void onSuccess() {
                // Handle successful citizen registration
                Toast.makeText(RegisterCitizen.this, "Registration successful and wait for Activation", Toast.LENGTH_SHORT).show();
                // Clear the EditText fields after successful registration
                Intent intent = new Intent(RegisterCitizen.this, Login_Activity.class);
                startActivity(intent);

                citizenID.getText().clear();
                occupation.getText().clear();
                community.getText().clear();
            }

            @Override
            public void onFailure(String errorMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegisterCitizen.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}
