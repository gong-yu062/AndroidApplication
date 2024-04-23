package com.example.androidapp.ui.BMI;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Locale; // Import the Locale class
import com.example.androidapp.R;


public class BMIFragment extends Fragment {

    private EditText editTextHeight;
    private EditText editTextWeight;
    private TextView textViewBMIScore;

    // ... Your other methods ...

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bmi, container, false);

        // Initialize your EditTexts and TextView here
        editTextHeight = view.findViewById(R.id.editTextHeight);
        editTextWeight = view.findViewById(R.id.editTextWeight);
        textViewBMIScore = view.findViewById(R.id.textViewBMIScore);

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

        // ... Rest of your BMI calculation logic ...

    }
}

