package com.example.nthandizi_police_service_app_ver1.models;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class alert_multimedia extends Alert {

    public alert_multimedia() {
        super();
    }

    public int alert_id;

    public String path;

    public String ext;

    public void sendMultimediaReport(String selectedOptionColorCode, String memberId, String alert_type, String videoUpload,final Context context, final VideoReportCallback callback) {
        OkHttpClient client = new OkHttpClient();
        File videoFile = new File(videoUpload);


        // Check if any of the required fields are empty
        if (TextUtils.isEmpty(selectedOptionColorCode) || TextUtils.isEmpty(memberId) || TextUtils.isEmpty(videoUpload) || TextUtils.isEmpty(alert_type)) {
            String errorMessage = "Please provide values for all required fields.";
            callback.onFailure(errorMessage);
            return;
        }

        if(videoFile.exists()){
            // Assuming you have a method to convert the video file to a byte array
            byte[] videoByteArray = convertVideoFileToByteArray(videoFile);
            // Create a JSON object with the text report data
            JSONObject VideoRequestData = new JSONObject();
            try {
                VideoRequestData.put("code", selectedOptionColorCode);
                VideoRequestData.put("author", memberId);
                VideoRequestData.put("a_type", alert_type);
                VideoRequestData.put("path", videoUpload);
                VideoRequestData.put("video", videoByteArray); // Add the video byte array to the JSON object

            } catch (JSONException e) {
                e.printStackTrace();
                callback.onFailure("Failed to create video report data.");
                return;
            }
            // Create the request body
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),VideoRequestData .toString());
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
        }else{
            callback.onFailure("Video file does not exist.");
            return;
        }
    }

    private byte[] convertVideoFileToByteArray(File videoFile) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            FileInputStream inputStream = new FileInputStream(videoFile);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    public interface VideoReportCallback {
        void onSuccess(JSONObject responseBody);
        void onFailure(String errorMessage);
    }


    private void runOnUiThread(Runnable runnable) {
        // Implement the method to run the runnable on the main (UI) thread
        new Handler(Looper.getMainLooper()).post(runnable);
    }

}