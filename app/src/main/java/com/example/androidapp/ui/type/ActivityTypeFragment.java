package com.example.androidapp.ui.type;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.androidapp.R;



public class ActivityTypeFragment extends Fragment {

        private Spinner spinnerActivityTypes;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_type, container, false);
            spinnerActivityTypes = rootView.findViewById(R.id.spinnerActivityTypes);
            Button buttonConfirmActivity = rootView.findViewById(R.id.buttonConfirmActivity);

            // Setup the Spinner with predefined activity types
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.activity_types_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerActivityTypes.setAdapter(adapter);

            buttonConfirmActivity.setOnClickListener(view -> {
                String selectedActivity = spinnerActivityTypes.getSelectedItem().toString();
                Toast.makeText(getContext(), "Activity Selected: " + selectedActivity, Toast.LENGTH_SHORT).show();
            });

            return rootView;
        }


}
