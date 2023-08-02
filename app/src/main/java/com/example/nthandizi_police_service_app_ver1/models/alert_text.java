package com.example.nthandizi_police_service_app_ver1.models;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class alert_text extends Alert {

    public alert_text() {
        super();
    }

    public int alert_id;

    public String message;

    // Method to send the text report to the server
    public void sendTextReport(String selectedOptionColorCode, String memberID, String alert_type, String Message, final Context context, final textReportCallback callback) {
        OkHttpClient client = new OkHttpClient();

        // Check if any of the required fields are empty
        if (TextUtils.isEmpty(selectedOptionColorCode) || TextUtils.isEmpty(memberID) || TextUtils.isEmpty(Message) || TextUtils.isEmpty(alert_type)) {
            String errorMessage = "Please provide values for all required fields.";
            callback.onFailure(errorMessage);
            return;
        }

        // Create a JSON object with the text report data
        JSONObject textReportData = new JSONObject();
        try {
            textReportData.put("code", selectedOptionColorCode);
            textReportData.put("author", memberID);
            textReportData.put("a_type", alert_type);
            textReportData.put("message", Message);
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onFailure("Failed to create text report data.");
            return;
        }

        // Create the request body
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), textReportData.toString());

        // Prepare the HTTP POST request
        Request request = new Request.Builder()
                .url(CreateAlertApiUrl)
                .header("Content-Type", "application/json")
                .post(requestBody)
                .build();

        // Execute the request asynchronously
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                // Handle the failure case on the main/UI thread
                // (Assuming you have a callback method on the UI thread)
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure("Failed to send text report. Please check your internet connection.");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseBody = response.body().string();
                final int responseCode = response.code();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject responseObject = new JSONObject(responseBody);

                            if (response.isSuccessful()) {
                                // Successful registration
                                callback.onSuccess(responseObject);

                            } else if (responseObject.has("error")){
                                String errorMessage = responseObject.optString("error");
                                // Display the error message using a Toast
                                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                                callback.onFailure(errorMessage);
                            }else{
                                // Other error
                                String errorMessage = "Failed to Send Report";
                                callback.onFailure(errorMessage);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            String errorMessage = "Failed to parse server response.";
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                            callback.onFailure(errorMessage);
                        }
                    }
                });


            }
        });
    }




    // Callback interface for handling the response from the server
    public interface textReportCallback {
        void onSuccess(JSONObject responseBody);
        void onFailure(String errorMessage);
    }


    private void runOnUiThread(Runnable runnable) {
        // Implement the method to run the runnable on the main (UI) thread
        new Handler(Looper.getMainLooper()).post(runnable);
    }

}