package com.example.androidapp.ui;
import android.util.Log;

import com.android.volley.RequestQueue;
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
import com.android.volley.DefaultRetryPolicy;

import java.nio.charset.StandardCharsets;

import com.android.volley.AuthFailureError;
import java.util.Map;
import java.util.HashMap;


public class SignUpActivity extends AppCompatActivity {

    EditText firstNameEditText, lastNameEditText, dateOfBirthEditText, genderEditText, weightEditText, heightEditText, emailEditText, passwordEditText;
    Button buttonSignUp;

    private ToastManager toastManager;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // Initialize ToastManager
        toastManager = new ToastManager();

        requestQueue = Volley.newRequestQueue(this);
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
            params.put("Gender", genderEditText.getText().toString().trim());
            params.put("weight", Integer.parseInt(weightEditText.getText().toString().trim()));
            params.put("height", Integer.parseInt(heightEditText.getText().toString().trim()));
            params.put("Email", emailEditText.getText().toString().trim());
            params.put("Password", passwordEditText.getText().toString().trim());
            params.put("DateOfBirth", dateOfBirthEditText.getText().toString().trim()); // Directly use the input string
        } catch (JSONException e) {
            Log.e("SignUpActivity", "JSON Exception", e);
            toastManager.showToast(this, "An error occurred with data formatting. Please try again.");
            return;
        }

        // Log the JSON payload
        Log.d("SignUpActivity", "JSON Payload: " + params.toString());

        // Send the request
        String url = "https://gitfit.azurewebsites.net/api/Fit/signup";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, params,
                response -> {
                    try {
                        boolean isSuccess = response.optBoolean("success", false);
                        String message = response.optString("message", "Sign up successful!");
                        Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(SignUpActivity.this, "Response parsing error", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e("SignUpActivity", "Error: " + error.toString());
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        String responseData = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                        Log.e("SignUpActivity", "Server Response: " + responseData);
                        try {
                            JSONObject jsonObject = new JSONObject(responseData);
                            String serverMessage = jsonObject.optString("message", "No message provided");
                            toastManager.showToast(this, "Error: " + serverMessage);
                        } catch (JSONException e) {
                            toastManager.showToast(this, "Error parsing server response");
                        }
                    } else {
                        toastManager.showToast(this, "Client error with no server response");
                    }


                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000, // 30 seconds timeout
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonObjectRequest);
    }
}