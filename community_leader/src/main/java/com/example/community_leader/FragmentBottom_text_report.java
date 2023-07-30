package com.example.community_leader;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;


public class FragmentBottom_text_report extends Fragment {
    private RadioGroup radioGroupColor;
    private TextInputEditText textInputMessage;

    private AppCompatButton sendTextReport;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_bottom_text_report, container, false);


        radioGroupColor = view.findViewById(R.id.radioGroup_Id);
        textInputMessage =view.findViewById(R.id.messageEditText_id);
        sendTextReport = view.findViewById(R.id.sendReportText_id);

        sendTextReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageReport = textInputMessage.getText().toString();
                int selectedColor = getColorFromRadioButton();

               //SendReport.sendReport(messageReport, selectedColor);
            }
        });

        return view;
    }

    private int getColorFromRadioButton() {
        int selectedRadioButtonId = radioGroupColor.getCheckedRadioButtonId();

        if (selectedRadioButtonId == R.id.radioBtn_optA_Id) {
            return Color.RED;
        } else if (selectedRadioButtonId == R.id.radioBtn_optB_Id) {
            return Color.GREEN;
        } else if (selectedRadioButtonId == R.id.radioBtn_optC_Id) {
            return Color.YELLOW;
        } else if (selectedRadioButtonId == R.id.radioBtn_optD_Id) {
            return Color.BLUE;
        }

        return Color.BLACK;

    }
}
