package com.example.nthandizi_police_service_app_ver1.models;

import java.util.*;
import android.os.Looper;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

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

public class Citizen extends User {

    public Citizen(){
        super();
    }

    public int cid;

    public String occupation;

    public Set<Member> memberships;


    //REGISTER USER
    public void registerUser(String fname, String lname, String pnumber, String email, String password, final Context context, final RegistrationCallback callback) {
        OkHttpClient client = new OkHttpClient();

        // Check if fname and password are provided and not empty
        if (TextUtils.isEmpty(fname) || TextUtils.isEmpty(lname) || TextUtils.isEmpty(pnumber) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            String errorMessage = "Please provide values for all required fields";
            callback.onFailure(errorMessage);
            return;
        }

        // Create a JSON object with the user's registration data
        JSONObject userRequestData = new JSONObject();
        try {
            userRequestData.put("fname", fname);
            userRequestData.put("lname", lname);
            userRequestData.put("pnumber", pnumber);
            userRequestData.put("email", email);
            userRequestData.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onFailure("Failed to create User registration data.");
            return;
        }

        // Create the request body
        RequestBody userRequestBody = RequestBody.create(MediaType.parse("application/json"), userRequestData.toString());

        // Prepare the HTTP POST request
        Request userRegisterRequest = new Request.Builder()
                .url(UserRegisterApiUrl)
                .header("Content-Type", "application/json")
                .post(userRequestBody)
                .build();

        // Execute the request asynchronously
        client.newCall(userRegisterRequest).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                // Handle the failure case on the main/UI thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String errorMessage = "Failed to register. Please check your internet connection.";
                        callback.onFailure(errorMessage);
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String responseBody = response.body().string();
                final int responseCode = response.code();

                // Print the response body and status code for debugging purposes
                Log.d("ResponseDebug", "Response Code: " + responseCode);
                Log.d("ResponseDebug", "Response Body: " + responseBody);

                // Handle the response based on the HTTP status code
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject responseObject = new JSONObject(responseBody);

                            if (response.isSuccessful()) {
                                // Successful registration
                                callback.onSuccess(responseObject);

                            } else {
                                String errorMessage = "Please provide values for all required fields.";
                                if (responseObject.has("message")) {
                                    errorMessage = responseObject.optString("message");
                                } else if (responseObject.has("error")) {
                                    errorMessage = responseObject.optString("error");
                                }

                                // Display the error message using a Toast
                                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
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

    //REGISTER CITIZEN
    public void registerCitizen(String citizenID, String occupation, String Community, final Context context, final CitizenRegistrationCallback callback) {
        OkHttpClient client = new OkHttpClient();

        // Check if fname and password are provided and not empty
        if (TextUtils.isEmpty(citizenID) || TextUtils.isEmpty(occupation) || TextUtils.isEmpty(Community)) {
            String errorMessage = "Please provide values for all required fields.";
            callback.onFailure(errorMessage);
            return;
        }
        // Create a JSON object with the citizen's registration data
        JSONObject citizenRequestData = new JSONObject();
        try {
            citizenRequestData.put("cid", citizenID);
            citizenRequestData.put("occupation", occupation);
            citizenRequestData.put("comm_name", Community);
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onFailure("Failed to create Citizen registration data.");
            return;
        }

        // Create the request body
        RequestBody citizenRequestBody = RequestBody.create(MediaType.parse("application/json"), citizenRequestData.toString());

        // Prepare the HTTP POST request for Citizen registration
        Request citizenRegisterRequest = new Request.Builder()
                .url(CitizenRegisterApiUrl)
                .header("Content-Type", "application/json")
                .post(citizenRequestBody)
                .build();

        // Execute the request asynchronously for Citizen registration
        client.newCall(citizenRegisterRequest).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String errorMessage = "Failed to register Citizen. Please check your internet connection.";
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                        callback.onFailure(errorMessage);
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String responseBody = response.body().string();
                final int responseCode = response.code();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject responseObject = new JSONObject(responseBody);

                            if (responseCode == 201) {
                                // Successful registration
                                callback.onSuccess();
                                Log.d("RegistrationResponse", responseBody);
                            } else if (responseCode == 400) {
                                // Bad Request - Invalid data or missing fields
                                String errorMessage = responseObject.optString("error");
                                callback.onFailure(errorMessage);
                            } else if (responseCode == 409) {
                                // Conflict - User already registered as Citizen
                                String errorMessage = responseObject.optString("error");
                                callback.onFailure(errorMessage);
                            } else if (responseCode == 422) {
                                // Unprocessable Entity - Invalid 'citizen ID' format or 'community name' not provided
                                String errorMessage = responseObject.optString("error");
                                callback.onFailure(errorMessage);
                            } else {
                                // Other error
                                String errorMessage = "Failed to register as Citizen.";
                                callback.onFailure(errorMessage);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            String errorMessage = "Failed to parse server response.";
                            callback.onFailure(errorMessage);
                        }
                    }
                });
            }
        });
    }

    // USER LOGIN
    public void userLogin(String fname, String password,final Context context, final LoginCallback callback) {
        OkHttpClient client = new OkHttpClient();

        // Check if fname and password are provided and not empty
        if (TextUtils.isEmpty(fname) || TextUtils.isEmpty(password)) {
            String errorMessage = "Please provide values for all required fields";
            callback.onFailure(errorMessage);
            return;
        }
        // Create a JSON object with the user's login data
        JSONObject loginRequestData = new JSONObject();
        try {
            loginRequestData.put("fname", fname);
            loginRequestData.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onFailure("Failed to create login data.");
            return;
        }

        // Create the request body
        RequestBody loginRequestBody = RequestBody.create(MediaType.parse("application/json"), loginRequestData.toString());

        // Prepare the HTTP POST request for user login
        Request userLoginRequest = new Request.Builder()
                .url(UserLoginApiUrl )
                .header("Content-Type", "application/json")
                .post(loginRequestBody)
                .build();

        // Execute the request asynchronously for user login
        client.newCall(userLoginRequest).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                // Handle the failure case on the main/UI thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String errorMessage = "Failed to login.";
                        callback.onFailure(errorMessage);
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String responseBody = response.body().string();
                final int responseCode = response.code();


                // Handle the response based on the HTTP status code
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // If response is successful (status code 200)
                            if (responseCode == 200) {
                                JSONObject responseObject = new JSONObject(responseBody);
                                JSONObject userData = responseObject.getJSONObject("user_data");
                                callback.onSuccess(userData);
                            } else if (responseCode == 400) {
                                // Bad Request - User does not exist or other validation error
                                JSONObject responseObject = new JSONObject(responseBody);
                                String errorMessage = responseObject.optString("error");
                                callback.onFailure(errorMessage);
                            } else if (responseCode == 401) {
                                // Unauthorized - Invalid password
                                JSONObject responseObject = new JSONObject(responseBody);
                                String errorMessage = responseObject.optString("error");
                                callback.onFailure(errorMessage);
                            } else if (responseCode == 403) {
                                // Forbidden - Account not activated
                                JSONObject responseObject = new JSONObject(responseBody);
                                String errorMessage = responseObject.optString("message");
                                callback.onFailure(errorMessage);
                            } else {
                                // Other error
                                String errorMessage = "Failed to log in as User.";
                                callback.onFailure(errorMessage);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            String errorMessage = "Failed to parse server response: " + e.getMessage();
                            callback.onFailure(errorMessage);
                        }
                    }
                });
            }
        });
    }

    public interface RegistrationCallback {
        void onSuccess(JSONObject responseBody);
        void onFailure(String errorMessage);
    }

    public interface CitizenRegistrationCallback {
        void onSuccess();
        void onFailure(String errorMessage);
    }

    public interface LoginCallback {
        void onSuccess(JSONObject response);
        void onFailure(String errorMessage);
    }

    private static void runOnUiThread(Runnable runnable) {
        // Implement the method to run the runnable on the main (UI) thread
        new Handler(Looper.getMainLooper()).post(runnable);

    }

}












































//    public static void registerUser(String fname, String lname, String pnumber, String email, String password, String occupation,final Context context, final RegistrationCallback callback) {
//        OkHttpClient client = new OkHttpClient();
//
//        // Create a JSON object with the user's registration data
//        JSONObject userRequestData = new JSONObject();
//        try {
//            userRequestData.put("fname", fname);
//            userRequestData.put("lname", lname);
//            userRequestData.put("pnumber", pnumber);
//            userRequestData.put("email", email);
//            userRequestData.put("password", password);
//        } catch (JSONException e) {
//            e.printStackTrace();
//            callback.onFailure("Failed to create User registration data.");
//            return;
//        }
//
//        // Create the request body
//        RequestBody userRequestBody = RequestBody.create(MediaType.parse("application/json"), userRequestData.toString());
//
//        // Prepare the HTTP POST request
//        Request userRegisterRequest = new Request.Builder()
//                .url(UserRegisterApiUrl)
//                .header("Content-Type", "application/json")
//                .post(userRequestBody)
//                .build();
//
//        // Execute the request asynchronously
//        client.newCall(userRegisterRequest).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        String errorMessage = "Failed to register. Please check your internet connection.";
//                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
//                        callback.onFailure(errorMessage);
//                    }
//                });
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                final String responseBody = response.body().string();
//
//                if (!response.isSuccessful()) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            String errorMessage = "Failed to register";
//                            try {
//                                JSONObject errorObject = new JSONObject(responseBody);
//                                errorMessage = errorObject.getString("message");
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
//                            callback.onFailure(errorMessage);
//                        }
//                    });
//                }else{
//                    // User registration successful, proceed with Citizen registration
//                    // Get the User ID from the response and use it for Citizen registration
//                    String userId="";
//                    try {
//                        JSONObject userResponseData = new JSONObject(response.body().string());
//                        userId = userResponseData.optString("uid");
//
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        String errorMessage = "Failed to parse User registration response.";
//                        callback.onFailure(errorMessage);
//                    }
//
//                    // Create a JSON object with the citizen's registration data
//                    JSONObject citizenRequestData = new JSONObject();
//                    try {
//                        citizenRequestData.put("cid", userId);
//                        citizenRequestData.put("occupation", occupation);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        callback.onFailure("Failed to create Citizen registration data.");
//                        return;
//                    }
//
//                    // Create the request body for Citizen registration
//                    RequestBody citizenRequestBody = RequestBody.create(MediaType.parse("application/json"), citizenRequestData.toString());
//
//                    // Create the POST request for Citizen registration
//                    Request citizenRegisterRequest = new Request.Builder()
//                            .url(CitizenRegisterApiUrl)
//                            .post(citizenRequestBody)
//                            .build();
//
//                    // Execute the Citizen registration request asynchronously
//                    client.newCall(citizenRegisterRequest).enqueue(new Callback() {
//                        @Override
//                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                            e.printStackTrace();
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    String errorMessage = "Failed to register Citizen.";
//                                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
//                                    callback.onFailure(errorMessage);
//                                }
//                            });
//                        }
//
//                        @Override
//                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                            final String responseBody = response.body().string();
//
//                            if (response.isSuccessful()) {
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        callback.onSuccess();
//                                        Log.d("RegistrationResponse", responseBody);
//                                    }
//                                });
//                            } else {
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        String errorMessage = "Failed to register";
//                                        try {
//                                            JSONObject errorObject = new JSONObject(responseBody);
//                                            errorMessage = errorObject.getString("message");
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
//                                        callback.onFailure(errorMessage);
//                                    }
//                                });
//                            }
//                        }
//                    });
//
//                }
//            }
//        });
//    }
//
//    /**
//     *
//     */
//    public static void login() {
//        // TODO implement here
//    }
//
//
//
//    public interface RegistrationCallback {
//        void onSuccess();
//
//        void onFailure(String errorMessage);
//    }
//
//    private static void runOnUiThread(Runnable runnable) {
//        // Implement the method to run the runnable on the main (UI) thread
//        new Handler(Looper.getMainLooper()).post(runnable);
//
//    }

//}
