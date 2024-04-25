package com.example.androidapp.ui;
import android.util.Log;


import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidapp.R;
import org.json.JSONException;
import org.json.JSONObject;
import android.text.TextUtils;
import com.example.androidapp.ToastManager;

import java.nio.charset.StandardCharsets;

public class SignUpActivity extends AppCompatActivity {

    EditText firstNameEditText, lastNameEditText, dateOfBirthEditText, genderEditText, weightEditText, heightEditText, emailEditText, passwordEditText;
    Button buttonSignUp;

    private ToastManager toastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // Initialize ToastManager
        toastManager = new ToastManager();
        // Initialize views
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        dateOfBirthEditText = findViewById(R.id.dateOfBirthEditText);
        genderEditText = findViewById(R.id.genderEditText);
        weightEditText = findViewById(R.id.weightEditText);
        heightEditText = findViewById(R.id.heightEditText);
        emailEditText = findViewById(R.id.SignUPEmail);
        passwordEditText = findViewById(R.id.SignUPPassword);
        buttonSignUp = findViewById(R.id.buttonSignUp);

        // Set up listeners
        buttonSignUp.setOnClickListener(v -> attemptSignUp());
    }

    private void attemptSignUp() {
        // Check for empty fields
        if (TextUtils.isEmpty(firstNameEditText.getText()) || TextUtils.isEmpty(lastNameEditText.getText()) ||
                TextUtils.isEmpty(dateOfBirthEditText.getText()) || TextUtils.isEmpty(genderEditText.getText()) ||
                TextUtils.isEmpty(weightEditText.getText()) || TextUtils.isEmpty(heightEditText.getText()) ||
                TextUtils.isEmpty(emailEditText.getText()) || TextUtils.isEmpty(passwordEditText.getText())) {
            toastManager.showToast(this, "Please fill in all fields");
            return;
        }


        JSONObject params = new JSONObject();
        try {
            params.put("Firstname", firstNameEditText.getText().toString().trim());
            params.put("Lastname", lastNameEditText.getText().toString().trim());
            params.put("dateOfBirth", dateOfBirthEditText.getText().toString().trim());
            params.put("Gender", genderEditText.getText().toString().trim());
            params.put("weight", Integer.parseInt(weightEditText.getText().toString().trim()));
            params.put("height", Integer.parseInt(heightEditText.getText().toString().trim()));
            params.put("Email", emailEditText.getText().toString().trim());
            params.put("Password", passwordEditText.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
            return;
        }
        // Log the JSON payload
        Log.d("SignUpActivity", "JSON Payload: " + params.toString());

        // Send the request
        String url = "https://gitfit.azurewebsites.net/api/Fit/signup";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, params,
                response -> {
                    try {
                        // Assuming the server sends a status in the JSON response
                        boolean isSuccess = response.optBoolean("success", false);  // Make sure server sends this or change according to actual response
                        String message = response.optString("message", "Sign up successful!");  // Same here, adjust according to server response
                        if (isSuccess) {
                            Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
                            // Maybe navigate to login screen or main activity
                        } else {
                            Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(SignUpActivity.this, "Response parsing error", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                        String errorMsg = "Sign up failed! Please try again.";
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            String responseData = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                            try {
                                JSONObject jsonObject = new JSONObject(responseData);
                                String serverError = jsonObject.optString("error", "No specific error message provided.");
                                errorMsg += "\nServer Response: " + serverError;
                            } catch (JSONException e) {
                                errorMsg += "\nServer Response: " + responseData;  // Fallback if not JSON
                            }
                        }
                        toastManager.showToast(SignUpActivity.this, errorMsg);
                    });

        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
}
