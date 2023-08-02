package com.example.nthandizi_police_service_app_ver1;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.nthandizi_police_service_app_ver1.models.alert_text;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class FragmentBottom_text_report extends Fragment {
    RadioGroup radioGroup;
    TextInputLayout messageTextInputLayout;

    EditText messageEditText;
    AppCompatButton SendReportBtn;

    SharedPreferences sharedPreferences;

    private Map<Integer, Pair<String, String>> radioButtonMap = new HashMap<>();
    private String selectedOptionColorCode = null;
    private String selectedOptionValue = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_bottom_text_report, container, false);

        messageTextInputLayout = view.findViewById(R.id.messageEdittextId);
        SendReportBtn = view.findViewById(R.id.sendReportBtn);
        messageEditText=messageTextInputLayout.getEditText();

        // Populate the map with RadioButton IDs, values, and color codes
        radioButtonMap.put(R.id.optA_Id, new Pair<>("Option A", "#FF0000")); // Red color code
        radioButtonMap.put(R.id.optB_Id, new Pair<>("Option B", "#FFA500")); // Amber color code
        radioButtonMap.put(R.id.optC_Id, new Pair<>("Option C", "#FFFF00")); // Yellow color code
        radioButtonMap.put(R.id.optD_Id, new Pair<>("Option D", "#008000")); // Green color code

        RadioGroup radioGroupText = view.findViewById(R.id.radioGroupText_Id);
        radioGroupText.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
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




        //When Report Button is clicked
        SendReportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendReport();
            }
        });

        return view;

    }
    private void sendReport() {
        sharedPreferences= getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);

        String alert_type= "Alert_text";
        String memberId = sharedPreferences.getString("mid", "");
        String Message =messageEditText.getText().toString();

        if (selectedOptionColorCode != null && selectedOptionValue != null) {
            alert_text alertText = new alert_text();
            alertText.sendTextReport(selectedOptionColorCode, memberId, alert_type, Message,  requireContext(), new alert_text.textReportCallback() {
                @Override
                public void onSuccess(JSONObject responseBody) {
                    Toast.makeText(requireContext(), "Alert Sent successful", Toast.LENGTH_SHORT).show();

                    messageEditText.getText().clear();
                    if (radioGroup != null) {
                        radioGroup.clearCheck();
                    }
                }

                @Override
                public void onFailure(String errorMessage) {
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });

        }else{
            Toast.makeText(requireContext(), "Please select an option before sending the report", Toast.LENGTH_SHORT).show();
        }
    }
    //    String fname = sharedPreferences.getString("fname", "");
//        String lname = sharedPreferences.getString("lname", "");
//        String pnumber = sharedPreferences.getString("pnumber", "");
//        String email = sharedPreferences.getString("email", "");
//        String occupation = sharedPreferences.getString("occupation", "");
//        String district = sharedPreferences.getString("district", "");
//        String communityName = sharedPreferences.getString("comm_name", "");

}