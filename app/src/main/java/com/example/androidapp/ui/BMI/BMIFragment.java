package com.example.androidapp.ui.BMI;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.androidapp.R;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class BMIFragment extends Fragment {

    private EditText editTextWeight;
    private EditText editTextHeight;
    private TextView textViewBMIResult;
    private Button buttonCalculateBMI;

    public BMIFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bmi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        textViewBMIResult = view.findViewById(R.id.textViewBMIResult);
        buttonCalculateBMI = view.findViewById(R.id.buttonCalculateBMI);

        buttonCalculateBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FetchBMIFromAPI().execute("https://gitfit.azurewebsites.net/api/Fit/bmi/12");
            }
        });
    }

    private class FetchBMIFromAPI extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String response = "";
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    response += line;
                }
                br.close();
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                double bmi = jsonObject.getDouble("Bmi");
                textViewBMIResult.setText(String.format("Your BMI: %.2f", bmi));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}