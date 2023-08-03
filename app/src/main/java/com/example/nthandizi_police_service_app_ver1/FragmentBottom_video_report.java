package com.example.nthandizi_police_service_app_ver1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.VideoView;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.nthandizi_police_service_app_ver1.models.alert_multimedia;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class FragmentBottom_video_report extends Fragment {

    private VideoView videoPreview;
    private Button recordButton;

    private AppCompatButton videoReportBtn;

    RadioGroup radioGroup;
    private Button uploadButton;
    private static final int VIDEO_CAPTURE_REQUEST_CODE = 1;
    private static final int VIDEO_PICK_REQUEST_CODE = 2;
    private static final int READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 101;

    SharedPreferences sharedPreferences;
    private Map<Integer, Pair<String, String>> radioButtonMap = new HashMap<>();

    private String selectedOptionColorCode = null;
    private String selectedOptionValue = null;

    private Uri videoUri; // Store the recorded video URI

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_video_report, container, false);

        videoPreview = view.findViewById(R.id.videoPreview);
        recordButton = view.findViewById(R.id.recordButton);
        uploadButton = view.findViewById(R.id.uploadButton);
        videoReportBtn = view.findViewById(R.id.sendVideoReport);

        // Populate the map with RadioButton IDs, values, and color codes
        radioButtonMap.put(R.id.radio_OptA_Id, new Pair<>("Option A", "#FF0000")); // Red color code
        radioButtonMap.put(R.id.radio_OptB_Id, new Pair<>("Option B", "#FFA500")); // Amber color code
        radioButtonMap.put(R.id.radio_OptC_Id, new Pair<>("Option C", "#FFFF00")); // Yellow color code
        radioButtonMap.put(R.id.radio_OptD_Id, new Pair<>("Option D", "#008000")); // Green color code

        RadioGroup radioGroupVideo = view.findViewById(R.id.radioGroup_Id);
        radioGroupVideo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                Pair<String, String> selectedOption = radioButtonMap.get(checkedId);
                if (selectedOption != null) {
                    selectedOptionColorCode = selectedOption.second;
                    selectedOptionValue = selectedOption.first;
                } else {
                    selectedOptionColorCode = null;
                    selectedOptionValue = null;
                }
            }
        });

        //When videoReport clicked
        videoReportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle upload button click
                if (videoUri != null) {
                    uploadVideo(videoUri);
                } else {
                    Toast.makeText(requireActivity(), "Please record a video first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set click listeners for the buttons
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle record button click
                //startRecording();
                checkCameraPermission();

            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle upload button click
                openGallery();

            }
        });

        return view;
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, so request it
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            // Permission is already granted, start recording
            checkReadExternalStoragePermission();
        }
    }

    private void checkReadExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, so request it
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
        } else {
            // Both permissions are granted, start recording
            startRecording();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start recording
                // CAMERA permission granted, check for READ_EXTERNAL_STORAGE permission
                checkReadExternalStoragePermission();
            } else {
                // Permission denied, show a message or take appropriate action
                Toast.makeText(requireActivity(), "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // READ_EXTERNAL_STORAGE permission granted, start recording
                startRecording();
            } else {
                // Permission denied, show a message or take appropriate action
                Toast.makeText(requireActivity(), "Read external storage permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startRecording() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, VIDEO_CAPTURE_REQUEST_CODE);
        } else {
            Toast.makeText(getContext(), "No camera app found", Toast.LENGTH_SHORT).show();
        }
    }


    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, VIDEO_PICK_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VIDEO_CAPTURE_REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {
                // Video recorded successfully
                Toast.makeText(getContext(), "Video recorded", Toast.LENGTH_SHORT).show();
                // Get the recorded video URI
                Uri videoUri = data.getData();
                // Display the recorded video in the VideoView
                videoPreview.setVideoURI(videoUri);
                videoPreview.start();
                // upload the recorded video to the server
                uploadVideo(videoUri);
            } else if (resultCode == getActivity().RESULT_CANCELED) {
                // Video recording cancelled
                Toast.makeText(getContext(), "Video recording cancelled", Toast.LENGTH_SHORT).show();
            } else {
                // Video recording failed
                Toast.makeText(getContext(), "Video recording failed", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == VIDEO_PICK_REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {
                // Video picked from gallery successfully
                Uri videoUri = data.getData();
                // Display the picked video in the VideoView
                videoPreview.setVideoURI(videoUri);
                videoPreview.start();
            } else if (resultCode == getActivity().RESULT_CANCELED) {
                // Video picking cancelled
                Toast.makeText(getContext(), "Video picking cancelled", Toast.LENGTH_SHORT).show();
            } else {
                // Video picking failed
                Toast.makeText(getContext(), "Video picking failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadVideo(Uri videoUri) {
        sharedPreferences= getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);

        String alert_type= "Alert_multimedia";
        String memberId = sharedPreferences.getString("mid", "");
        String videoPath = getRealPathFromUri(videoUri);


        if(selectedOptionColorCode != null && selectedOptionValue !=null) {
            alert_multimedia videoReport = new alert_multimedia();
            videoReport.path = videoPath; // Set the 'path' field in the alert_multimedia object
            videoReport.ext = "video"; // Assuming the extension is 'video', you can change it accordingly

            // Convert the video file to a byte array
            byte[] videoBytes = getBytesFromUri(videoUri);
            videoReport.sendMultimediaReport(selectedOptionColorCode, memberId, alert_type, videoPath, requireContext(), new alert_multimedia.VideoReportCallback() {

                @Override
                public void onSuccess(JSONObject responseBody) {
                    Toast.makeText(requireContext(), "Alert Sent successful", Toast.LENGTH_SHORT).show();

                    //messageEditText.getText().clear();
                    if (radioGroup != null) {
                        radioGroup.clearCheck();
                    }
                }

                @Override
                public void onFailure(String errorMessage) {
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }else{
            Toast.makeText(requireContext(), "Please select an option before sending the report", Toast.LENGTH_SHORT).show();
        }
    }

    private byte[] getBytesFromUri(Uri videoUri) {
        try {
            InputStream inputStream = requireActivity().getContentResolver().openInputStream(videoUri);
            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
            return byteBuffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getRealPathFromUri(Uri contentUri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, projection, null, null, null);
        if (cursor == null) return null;
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(columnIndex);
        cursor.close();
        return path;
    }
}
