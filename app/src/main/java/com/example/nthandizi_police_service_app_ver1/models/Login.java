package com.example.nthandizi_police_service_app_ver1.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login {

    public static void login(String fname, String password, Context context) {
        new LoginTask(fname, password, context).execute();
    }

    private static class LoginTask extends AsyncTask<Void, Void, Void> {
        private String fname;
        private String password;
        private Context context;

        public LoginTask(String fname, String password, Context context) {
            this.fname = fname;
            this.password = password;
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... params) {
            String apiURL = "http://10.0.2.2:8000/api/v1/User/login/";

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

                    JSONObject responseJson = new JSONObject(response.toString());
                    String uid = responseJson.getString("uid");
                    String fname = responseJson.getString("fname");
                    String lname = responseJson.getString("lname");
                    String email = responseJson.getString("email");
                    String pnumber = responseJson.getString("pnumber");

                    // Store user session data in shared preferences
                    SharedPreferences preferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("uid", uid);
                    editor.putString("fname", fname);
                    editor.putString("lname", lname);
                    editor.putString("email", email);
                    editor.putString("pnumber", pnumber);
                    editor.apply();
                } else {
                    // Login failed
                    Log.e("Login", "Login failed");
                }
            } catch (Exception e) {
                Log.e("Login", "Error: " + e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show();
        }
    }
}
