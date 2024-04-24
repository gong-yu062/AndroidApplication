package com.example.androidapp.ui;

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

public class SignUpActivity extends AppCompatActivity {

    EditText firstNameEditText, lastNameEditText, dateOfBirthEditText, genderEditText, weightEditText, heightEditText, emailEditText, passwordEditText;
    Button buttonSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

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
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "https://gitfit.azurewebsites.net/api/Fit/signup";

        JSONObject params = new JSONObject();
        try {
            params.put("Firstname", firstNameEditText.getText().toString().trim());
            params.put("Lastname", lastNameEditText.getText().toString().trim());
            params.put("DateOfBirth", dateOfBirthEditText.getText().toString().trim());
            params.put("Gender", genderEditText.getText().toString().trim());
            params.put("Weight", weightEditText.getText().toString().trim());
            params.put("Height", heightEditText.getText().toString().trim());
            params.put("Email", emailEditText.getText().toString().trim());
            params.put("Password", passwordEditText.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, params,
                response -> {
                    // Check if signup was successful based on the HTTP status code
                    if (response != null && response.length() > 0) {
                        // Signup successful
                        Toast.makeText(SignUpActivity.this, "Sign up successful!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Signup failed
                        Toast.makeText(SignUpActivity.this, "Sign up failed! Please try again.", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    // General error message
                    String errorMsg = "Sign up failed! Please try again.";
                    if (error.networkResponse != null) {
                        if (error.networkResponse.statusCode == 409) {
                            errorMsg = "This email address is already registered. Please use a different email or reset your password.";
                        } else if (error.networkResponse.data != null) {
                            errorMsg += "\nServer Response: " + new String(error.networkResponse.data);
                        }
                    }
                    Toast.makeText(SignUpActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                });


        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

}
