package com.example.androidapp.ui.BMI;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.example.androidapp.R;
import java.util.Locale;

public class BMIFragment extends Fragment {

    private EditText editTextHeight;
    private EditText editTextWeight;
    private TextView textViewBMIResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bmi, container, false);

        // Initialize your EditTexts and TextView here
        editTextHeight = view.findViewById(R.id.editTextHeight);
        editTextWeight = view.findViewById(R.id.editTextWeight);
        textViewBMIResult = view.findViewById(R.id.textViewBMIResult);

        // Set up the button click listener if not using android:onClick in XML
        view.findViewById(R.id.buttonCalculateBMI).setOnClickListener(v -> {
            onCalculateBMIClicked();
        });

        return view;
    }

    public void onCalculateBMIClicked() {
        // Use editTextHeight, editTextWeight, and textViewBMIScore here to calculate BMI
        String heightStr = editTextHeight.getText().toString();
        String weightStr = editTextWeight.getText().toString();

        if (!heightStr.isEmpty() && !weightStr.isEmpty()) {
            float heightValue = Float.parseFloat(heightStr) / 100; // convert cm to meters
            float weightValue = Float.parseFloat(weightStr);

            float bmi = weightValue / (heightValue * heightValue);

            textViewBMIResult.setText(String.format(Locale.US, "%.2f", bmi));
        }
    }
}