package com.example.police_officer.models;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

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

/**
 * 
 */
public class PoliceOfficer extends User {
    public  static String OfficerLoinApiUrl="http://10.0.2.2:8000/api/v1/User/police_officer_login/";

    public static  String OfficerCreateApiUrl="http://10.0.2.2:8000/api/v1/PoliceStation/register_policeofficer/";
    public PoliceOfficer() {
        super();
    }

    public int pid;

    public int position;

    public Set<Job_posting> postings;

    public void createPoliceOfficer(String Officer_ID, String Position, String PoliceName, final Context context, final OfficerCreateCallback callback) {
        OkHttpClient client = new OkHttpClient();

        // Create a JSON object with the police officer data
        JSONObject policeOfficerData = new JSONObject();
        try {
            policeOfficerData.put("position", Position);
            policeOfficerData.put("pid", Officer_ID);
            policeOfficerData.put("ps_name", PoliceName);

        } catch (JSONException e) {
            e.printStackTrace();
            callback.onFailure("Failed to create police officer data.");
            return;
        }

        // Create the request body
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), policeOfficerData.toString());

        // Prepare the HTTP POST request to create a new Police Officer
        Request request = new Request.Builder()
                .url(OfficerCreateApiUrl)
                .header("Content-Type", "application/json")
                .post(requestBody)
                .build();

        // Execute the request asynchronously
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                // Handle the failure case on the main/UI thread
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String errorMessage = "Failed to Register Police Officer.";
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
                                // Successful login
                                callback.onSuccess(); // Remove policeOfficerData from here

                            } else if (response.code() == 400) {
                                // Bad Request - User does not exist or other validation error
                                String errorMessage = "User with the provided First Name does not exist.";
                                callback.onFailure(errorMessage);

                            } else if (response.code() == 401) {
                                // Unauthorized - Invalid password
                                String errorMessage = "Invalid password.";
                                callback.onFailure(errorMessage);

                            } else if (response.code() == 403) {
                                // Forbidden - Account not activated
                                String errorMessage = "Your account is not yet activated.";
                                callback.onFailure(errorMessage);
                            }else {
                                // Other error
                                String errorMessage = "Failed to login.";
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


    public void policeOfficerLogin(String fname, String password, final Context context, final OfficerLoginCallback callback) {
        OkHttpClient client = new OkHttpClient();

        // Create a JSON object with the police officer's login data
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

        // Prepare the HTTP POST request for police officer login
        Request policeOfficerLoginRequest = new Request.Builder()
                .url(OfficerLoinApiUrl)
                .header("Content-Type", "application/json")
                .post(loginRequestBody)
                .build();

        // Execute the request asynchronously for police officer login
        client.newCall(policeOfficerLoginRequest).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                // Handle the failure case on the main/UI thread
                ((Activity) context).runOnUiThread(new Runnable() {
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

                // Handle the response based on the HTTP status code
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject responseObject = new JSONObject(responseBody);

                            if (response.isSuccessful()) {
                                // Successful login
                                callback.onSuccess(responseObject);

                            } else if (response.code() == 400) {
                                // Bad Request - User does not exist or other validation error
                                String errorMessage = responseObject.optString("error");
                                callback.onFailure(errorMessage);

                            } else if (response.code() == 401) {
                                // Unauthorized - Invalid password
                                String errorMessage = responseObject.optString("error");
                                callback.onFailure(errorMessage);

                            } else if (response.code() == 403) {
                                // Forbidden - Account not activated
                                String errorMessage = responseObject.optString("message");
                                callback.onFailure(errorMessage);

                            } else {
                                // Other error
                                String errorMessage = "Failed to login.";
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

    //OFFICER REGISTRATION
//    public void registerOfficer(String officer_ID, String Position, final Context context, final RegistrationCallback callback) {
//        OkHttpClient client = new OkHttpClient();
//
//        // Create a JSON object with the citizen's registration data
//        JSONObject citizenRequestData = new JSONObject();
//        try {
//            citizenRequestData.put("cid", officer_ID);
//            citizenRequestData.put("occupation", Position);
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
//                .url(PsRegisterApiUrl)
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
//
//    //USER REGISTRATION
//    public  void register(String fname, String lname, String pnumber, String email, String password, final Context context, final OfficerRegistrationCallback callback) {
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
//            callback.onFailure("Failed to create Officer registration data.");
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
//                } else {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            callback.onSuccess();
//                            Log.d("RegistrationResponse", responseBody);
//                        }
//                    });
//                }
//            }
//        });
//    }



    // OFFICER LOGIN
//    public void officerLogin(String fname, String password, final Context context, final OfficerLoginCallback callback) {
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

    public interface OfficerLoginCallback {
        void onSuccess(JSONObject userdata);
        void onFailure(String errorMessage);
    }

    public  interface OfficerCreateCallback{
        void onSuccess();
        void onFailure(String errorMessage);
    }

    public interface RegistrationCallback {
        void onSuccess();
        void onFailure(String errorMessage);
    }

    public interface OfficerRegistrationCallback {
        void onSuccess();
        void onFailure(String errorMessage);
    }
    private static void runOnUiThread(Runnable runnable) {
        // Implement the method to run the runnable on the main (UI) thread
        new Handler(Looper.getMainLooper()).post(runnable);

    }
   // public static void login() {
//
//    }

}