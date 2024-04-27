package com.example.androidapp.ui.home;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;


import java.nio.charset.StandardCharsets;
import android.util.Log;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidapp.R;

import org.json.JSONArray;
import org.json.JSONException;

public class HomeFragment extends Fragment {

    private TextView tvActivityInfo;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        tvActivityInfo = view.findViewById(R.id.tvActivityInfo);
        fetchActivities();
        return view;
    }

    private void fetchActivities() {
        String url = "https://gitfit.azurewebsites.net/api/Fit/workouts";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONObject workoutsObj = response.getJSONObject("workouts");
                        JSONArray workoutsArray = workoutsObj.getJSONArray("$values");
                        JSONObject intensitiesObj = response.getJSONObject("intensities");
                        JSONArray intensitiesArray = intensitiesObj.getJSONArray("$values");

                        StringBuilder builder = new StringBuilder();
                        for (int i = 0; i < workoutsArray.length(); i++) {
                            builder.append(workoutsArray.getString(i))
                                    .append(" - ")
                                    .append(intensitiesArray.getString(i)) // Ensure both arrays have the same length.
                                    .append("\n\n");
                        }
                        tvActivityInfo.setText(builder.toString());
                    } catch (JSONException e) {
                        Log.e("HomeFragment", "JSON parsing error: " + e.getMessage());
                        tvActivityInfo.setText(getString(R.string.parsing_error)); // Make sure this string exists in your strings.xml
                    }
                }, error -> {
            if (error.networkResponse != null) {
                String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                Log.e("HomeFragment", "Error response: " + responseBody);
                tvActivityInfo.setText(getString(R.string.fetch_error)); // Make sure this string exists in your strings.xml
            } else {
                Log.e("HomeFragment", "Volley error: " + error.getMessage());
                tvActivityInfo.setText(getString(R.string.fetch_error)); // Make sure this string exists in your strings.xml
            }
        });

        Volley.newRequestQueue(getContext()).add(jsonObjectRequest);
    }


}
