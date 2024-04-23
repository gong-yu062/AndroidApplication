package com.example.androidapp.ui.calories;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.androidapp.R;
public class CaloriesFragment extends Fragment {

    private TextView caloriesBurnedText;

    public CaloriesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calories, container, false);
        caloriesBurnedText = view.findViewById(R.id.calories_burned_text);
        return view;
    }

    public void updateCalories(int calories) {
        caloriesBurnedText.setText(String.format("Calories Burned: %d", calories));
    }
}
