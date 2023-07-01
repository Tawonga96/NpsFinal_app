package com.example.community_leader.models;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.app.Dialog;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;


import com.example.community_leader.Fragment_Add_Community_Member;

public class Registration {
    public static void register(String fname, String lname, String pnumber, String email, String password, Context context) {
        new RegistrationTask(fname, lname, pnumber, email, password, context).execute();
    }

    private static class RegistrationTask extends AsyncTask<Void, Void, Void> {
        private String fname;
        private String lname;
        private String pnumber;
        private String email;
        private String password;
        private Context context;

        private Dialog progressDialog;


        public RegistrationTask(String fname, String lname, String pnumber, String email, String password, Context context) {
            this.fname = fname;
            this.lname = lname;
            this.pnumber = pnumber;
            this.email = email;
            this.password = password;
            this.context = context;
        }

        private ProgressDialog createProgressDialog() {
            ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Registering...");
            progressDialog.setCancelable(false);
            return progressDialog;
        }

        @Override
        protected Void doInBackground(Void... params) {
            String apiURL = "http://10.0.2.2:8000/api/v1/User/register/";

            try {
                // Generate OTP
                String otp = generateOTP();

                URL url = new URL(apiURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");

                JSONObject requestBody = new JSONObject();
                requestBody.put("fname", fname);
                requestBody.put("lname", lname);
                requestBody.put("pnumber", pnumber);
                requestBody.put("email", email);
                requestBody.put("password", password);
                requestBody.put("otp", otp);
                requestBody.put("is_active", 1);

                // Send the JSON request
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(requestBody.toString());
                wr.flush();
                wr.close();

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_CREATED) {
                    // Registration successful
                    Log.d("Registration", "Registration successful!" + responseCode);

                    // Redirect to the login activity
                    Intent intent = new Intent(context, Fragment_Add_Community_Member.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                } else {
                    // Registration failed
                    Log.d("Registration", "Registration failed. Response code: " + responseCode);
                    // Handle registration failure here
                    Intent intent = new Intent(context, Fragment_Add_Community_Member.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                }

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                // Handle exception here
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = createProgressDialog();
            progressDialog.show();
        }

        private String generateOTP() {
            Random random = new Random();
            int otpDigits = 4; // Number of OTP digits

            StringBuilder otp = new StringBuilder();
            for (int i = 0; i < otpDigits; i++) {
                int digit = random.nextInt(10); // Generate a random digit (0-9)
                otp.append(digit);
            }

            return otp.toString();
        }
    }
}
