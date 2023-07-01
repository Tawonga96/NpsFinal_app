package com.example.community_leader;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;

public class FragmentBottom_video_report extends Fragment {

    private VideoView videoPreview;
    private Button recordButton;
    private Button uploadButton;
    private static final int VIDEO_CAPTURE_REQUEST_CODE = 1;
    private static final int VIDEO_PICK_REQUEST_CODE = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_video_report, container, false);

        videoPreview = view.findViewById(R.id.videoPreview);
        recordButton = view.findViewById(R.id.recordButton);
        uploadButton = view.findViewById(R.id.uploadButton);

        // Set click listeners for the buttons
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle record button click
                startRecording();
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
}
