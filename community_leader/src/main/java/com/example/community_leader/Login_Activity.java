package com.example.community_leader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.community_leader.models.Login;

public class Login_Activity extends AppCompatActivity {
    private boolean passwordShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText usernameET = findViewById(R.id.usernameET);
        EditText passwordET = findViewById(R.id.passwordET);
        ImageView passwordIcon = findViewById(R.id.passwordIcon);
        AppCompatButton signInBtn = findViewById(R.id.signInBtn);

        //when sign in button clicked
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = usernameET.getText().toString();
                String password = passwordET.getText().toString();
//                Context context = Login_Activity.this;

                // Check if either username or password fields are empty
                if (TextUtils.isEmpty(fname) || TextUtils.isEmpty(password)) {
                    Toast.makeText(Login_Activity.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                Login.login(fname, password, Login_Activity.this);

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