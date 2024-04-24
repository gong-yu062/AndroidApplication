package com.example.androidapp.ui.goal;



import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidapp.R;

public class GoalFragment extends Fragment {

    private EditText editTextNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_goal, container, false);
        editTextNumber = rootView.findViewById(R.id.editTextNumber);
        Button buttonSetGoal = rootView.findViewById(R.id.buttonSetGoal);

        buttonSetGoal.setOnClickListener(view -> {
            String goal = editTextNumber.getText().toString();
            Toast.makeText(getContext(), "Step Goal Set: " + goal, Toast.LENGTH_SHORT).show();
        });
        return rootView;
    }
}
