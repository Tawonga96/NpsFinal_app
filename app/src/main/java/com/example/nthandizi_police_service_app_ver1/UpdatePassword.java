package com.example.nthandizi_police_service_app_ver1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nthandizi_police_service_app_ver1.R;

public class UpdatePassword extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        button = findViewById(R.id.update);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform password update logic here

                Toast.makeText(UpdatePassword.this, "Password updated", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
