package com.example.community_leader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


public class FragmentBottom_text_report extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_bottom_text_report, container, false);
        return view;

    }

    public void OnRadioBtnClicked(View view) {
        RadioGroup radioGroup = view.findViewById(R.id.radioGroup_Id);
        RadioButton radioButton = view.findViewById(radioGroup.getCheckedRadioButtonId());
        Toast.makeText(getActivity(), radioButton.getText()+" is selected ", Toast.LENGTH_SHORT).show();
    }


}