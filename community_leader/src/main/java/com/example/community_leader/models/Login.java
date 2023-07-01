package com.example.community_leader.models;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.community_leader.Login_Activity;
import com.example.community_leader.User_Dashboard_Activity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class Login {
    public static void login(String fname, String password, Context context) {
        new LoginTask(fname, password, context).execute();
    }

    private static class LoginTask extends AsyncTask<Void, Void, Boolean> {
        private String fname;
        private String password;
        private Context context;
        private ProgressDialog progressDialog;

        public LoginTask(String fname, String password, Context context) {
            this.fname = fname;
            this.password = password;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Logging in...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }


        @Override
        protected Boolean doInBackground(Void... params) {
            String apiURL = "http://10.0.2.2:8000/api/v1/User/community-leader/";

            try {
                JSONObject loginData = new JSONObject();
                loginData.put("fname", fname);
                loginData.put("password", password);

                URL url = new URL(apiURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                // Send the JSON request
                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                writer.write(loginData.toString());
                writer.flush();

                int statusCode = conn.getResponseCode();
                Log.d("Login", "Response code: " + statusCode);
                if (statusCode == HttpURLConnection.HTTP_OK) {
                    // Login successful
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    // Process the response data here
                    JSONObject jsonResponse = new JSONObject(response.toString());
                    JSONObject userData = jsonResponse.getJSONObject("user_data");

                    int uid = userData.getInt("uid");
                    String fname = userData.getString("fname");
                    String lname = userData.getString("lname");
                    String pnumber = userData.getString("pnumber");
                    String email = userData.getString("email");

                    // Store user session data using SharedPreferences
                    SharedPreferences preferences = context.getSharedPreferences("UserSession", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("uid", uid);
                    editor.putString("fname", fname);
                    editor.putString("lname", lname);
                    editor.putString("email", email);
                    editor.putString("pnumber", pnumber);
                    editor.apply();

                    Log.d("Login", "Login successful! uid: " + uid);
                    Log.d("Login", "Lastname: " + lname);
                    Log.d("Login", "Email: " + email);
                    Log.d("Login", "Phone: " + pnumber);

                    return true;

                } else {
                    // Login failed
                    Log.d("Login", "Login failed. Response code: " + statusCode);
                    // Handle login failure
                    Toast.makeText(context, "Login Failed!", Toast.LENGTH_SHORT).show();
                    return false;

                }
//                conn.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
                // Handle exception here
                return false;
            }
//            return null;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            progressDialog.dismiss();

            if (success) {
                // Redirect to User_Dashboard_Activity
                Intent intent = new Intent(context, User_Dashboard_Activity.class);
                context.startActivity(intent);
            } else {
                // Show a toast message for failed login
                Toast.makeText(context, "Login Failed!", Toast.LENGTH_SHORT).show();
            }
        }


    }

}
