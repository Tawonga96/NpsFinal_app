package com.example.police_officer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.police_officer.models.PoliceOfficer;


public class registerPoliceOfficer extends AppCompatActivity {
    EditText officerID, position,policeName;
    AppCompatButton citizenCreateBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_police_officer);


        officerID = findViewById(R.id.citizenET);
        position = findViewById(R.id.occupationET);
        policeName= findViewById(R.id.policenameET);
        citizenCreateBtn = findViewById(R.id.CitizenCreateBtn);

        citizenCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerOfficer();
            }
        });
    }

    private void registerOfficer() {
        String Officer_ID = officerID.getText().toString();
        String Position = position.getText().toString();
        String PoliceName=policeName.getText().toString();

        // Create an instance of the Citizen class
        PoliceOfficer policeOfficer = new PoliceOfficer();

        // Call the login method to register the citizen
        policeOfficer.createPoliceOfficer(Officer_ID, Position, PoliceName, registerPoliceOfficer.this, new PoliceOfficer.OfficerCreateCallback() {
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(registerPoliceOfficer.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(registerPoliceOfficer.this, loginActivity.class));

                    }
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(registerPoliceOfficer.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}