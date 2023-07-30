package com.example.community_leader.models;


/**
 * 
 */
public abstract class User {

    public static String UserRegisterApiUrl = "http://10.0.2.2:8000/api/v1/User/register/";
    public static String CitizenRegisterApiUrl = "http://10.0.2.2:8000/api/v1/Community/CitizenRegister/";
    public static  String UserLoginApiUrl = "http://10.0.2.2:8000/api/v1/User/login/";
    public static String LeaderLoginApiUrl="http://10.0.2.2:8000/api/v1/User/community_leader_login/";
    public  static String AccountActivationApiUrl="http://10.0.2.2:8000/api/v1/User/activate-account/";
    public User() {

    }

    public String uid;

    public String fname;

    public String lname;

    public String pnumber;

    public String email;

    public String password;

    public int otp;

    public boolean is_active;

    // Rest of the class remains the same...
//
//    public void register(String fname, String lname, String pnumber, String email, String password, String occupation, final Context context, final RegistrationCallback callback) {
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
//                } else {
//                    // User registration successful, proceed with Citizen registration
//                    // Get the User ID from the response and use it for Citizen registration
//                    String userId;
//                    try {
//                        JSONObject userResponseData = new JSONObject(responseBody);
//                        userId = userResponseData.optString("uid");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        String errorMessage = "Failed to parse User registration response.";
//                        callback.onFailure(errorMessage);
//                        return;
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
//                    // Prepare the HTTP POST request for Citizen registration
//                    Request citizenRegisterRequest = new Request.Builder()
//                            .url(CitizenRegisterApiUrl)
//                            .header("Content-Type", "application/json")
//                            .post(citizenRequestBody)
//                            .build();
//
//                    // Execute the request asynchronously for Citizen registration
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
//                }
//            }
//        });
//    }
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


}