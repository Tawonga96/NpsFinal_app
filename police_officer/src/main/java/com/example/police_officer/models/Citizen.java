package com.example.police_officer.models;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Set;

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


    // Method to register a new user
    public void registerUser(String fname, String lname, String pnumber, String email, String password, final Context context, final RegistrationCallback callback) {
        OkHttpClient client = new OkHttpClient();

        // Create a JSON object with the user's registration data
        JSONObject registrationData = new JSONObject();
        try {
            registrationData.put("fname", fname);
            registrationData.put("lname", lname);
            registrationData.put("pnumber", pnumber);
            registrationData.put("email", email);
            registrationData.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onFailure("Failed to create registration data.");
            return;
        }

        // Create the request body
        RequestBody registrationRequestBody = RequestBody.create(MediaType.parse("application/json"), registrationData.toString());

        // Prepare the HTTP POST request for user registration
        Request userRegistrationRequest = new Request.Builder()
                .url(UserRegisterApiUrl)
                .header("Content-Type", "application/json")
                .post(registrationRequestBody)
                .build();

        // Execute the request asynchronously for user registration
        client.newCall(userRegistrationRequest).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                // Handle the failure case on the main/UI thread
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String errorMessage = "Failed to register user. Please check your internet connection.";
                        callback.onFailure(errorMessage);
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String responseBody = response.body().string();

                // Handle the response based on the HTTP status code
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject responseObject = new JSONObject(responseBody);

                            if (response.isSuccessful()) {
                                // Successful registration
                                callback.onSuccess(responseObject);

                            } else if (response.code() == 400) {
                                // Bad Request - Validation error or other issue with data
                                String errorMessage = "Please provide values for all required fields.";
                                callback.onFailure(errorMessage);

                            } else if (response.code() == 401) {
                                // Unauthorized - Invalid data format or password format
                                String errorMessage = "Invalid data format or password format.";
                                callback.onFailure(errorMessage);

                            } else if (response.code() == 403) {
                                // Forbidden - Invalid email format
                                String errorMessage = "Invalid email format.";
                                callback.onFailure(errorMessage);

                            } else if (response.code() == 409) {
                                // Conflict - Email already exists
                                String errorMessage = "Email already exists. Please use a different email.";
                                callback.onFailure(errorMessage);

                            } else if (response.code() == 411) {
                                // Length Required - Invalid phone number format
                                String errorMessage = "Phone number must have 13 digits and start with +265.";
                                callback.onFailure(errorMessage);

                            } else if (response.code() == 422) {
                                // Unprocessable Entity - Invalid first name format
                                String errorMessage = "First name must start with an uppercase letter.";
                                callback.onFailure(errorMessage);

                            } else {
                                // Other error
                                String errorMessage = "Failed to register user.";
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


//    //REGISTER CITIZEN
//    public void registerCitizen(String citizenID, String occupation, final Context context, final CitizenRegistrationCallback callback) {
//        OkHttpClient client = new OkHttpClient();
//
//        // Create a JSON object with the citizen's registration data
//        JSONObject citizenRequestData = new JSONObject();
//        try {
//            citizenRequestData.put("cid", citizenID);
//            citizenRequestData.put("occupation", occupation);
//        } catch (JSONException e) {
//            e.printStackTrace();
//            callback.onFailure("Failed to create Citizen registration data.");
//            return;
//        }
//
//        // Create the request body
//        RequestBody citizenRequestBody = RequestBody.create(MediaType.parse("application/json"), citizenRequestData.toString());
//
//        // Prepare the HTTP POST request for Citizen registration
//        Request citizenRegisterRequest = new Request.Builder()
//                .url(CitizenRegisterApiUrl)
//                .header("Content-Type", "application/json")
//                .post(citizenRequestBody)
//                .build();
//
//        // Execute the request asynchronously for Citizen registration
//        client.newCall(citizenRegisterRequest).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                e.printStackTrace();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        String errorMessage = "Failed to register Citizen.";
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
//                if (response.isSuccessful()) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            callback.onSuccess();
//                            Log.d("RegistrationResponse", responseBody);
//                        }
//                    });
//                } else {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            String errorMessage = "Failed to register";
//                            try {
//                                JSONObject errorObject = new JSONObject(responseBody);
//                                errorMessage = errorObject.getString("error");
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
//                            callback.onFailure(errorMessage);
//                        }
//                    });
//                }
//            }
//        });
//    }

//    // USER LOGIN
//    public void userLogin(String fname, String password, final Context context, final LoginCallback callback) {
//        OkHttpClient client = new OkHttpClient();
//
//        // Create a JSON object with the user's login data
//        JSONObject loginRequestData = new JSONObject();
//        try {
//            loginRequestData.put("fname", fname);
//            loginRequestData.put("password", password);
//        } catch (JSONException e) {
//            e.printStackTrace();
//            callback.onFailure("Failed to create login data.");
//            return;
//        }
//
//        // Create the request body
//        RequestBody loginRequestBody = RequestBody.create(MediaType.parse("application/json"), loginRequestData.toString());
//
//        // Prepare the HTTP POST request
//        Request loginRequest = new Request.Builder()
//                .url(UserLoginApiUrl)
//                .header("Content-Type", "application/json")
//                .post(loginRequestBody)
//                .build();
//
//        // Execute the request asynchronously
//        client.newCall(loginRequest).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                e.printStackTrace();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        String errorMessage = "Failed to perform login. Please check your internet connection.";
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
//                if (response.isSuccessful()) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                JSONObject jsonObject = new JSONObject(responseBody);
//                                JSONObject userData = jsonObject.getJSONObject("user_data");
//                                // Handle successful login and pass the user data to the callback
//                                callback.onSuccess(userData);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                                String errorMessage = "Failed to parse login response.";
//                                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
//                                callback.onFailure(errorMessage);
//                            }
//                        }
//                    });
//                } else {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            String errorMessage = "Provide both firstname and password";
//                            try {
//                                JSONObject errorObject = new JSONObject(responseBody);
//                                errorMessage = errorObject.getString("error");
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
//                            callback.onFailure(errorMessage);
//                        }
//                    });
//                }
//            }
//        });
//    }

    public interface RegistrationCallback {
        void onSuccess(JSONObject response);
        void onFailure(String errorMessage);
    }

    public interface CitizenRegistrationCallback {
        void onSuccess();
        void onFailure(String errorMessage);
    }

    public interface LoginCallback {
        void onSuccess(JSONObject userdata);
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
