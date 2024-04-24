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
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class LoginActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        emailEditText = findViewById(R.id.editTextUsername);
        passwordEditText = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        // Set up listeners
        buttonLogin.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        // Check for empty fields
        if (TextUtils.isEmpty(emailEditText.getText()) || TextUtils.isEmpty(passwordEditText.getText())) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        String url = "https://gitfit.azurewebsites.net/api/Fit/login?email=" + email + "&password=" + password;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    // Check if login was successful based on the response
                    if (response != null && response.equals("Login Succesfull")) {
                        // Login successful
                        Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Login failed
                        Toast.makeText(LoginActivity.this, "Login failed! Please try again.", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    // General error message
                    String errorMsg = "Login failed! Please try again.";
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        errorMsg += "\nServer Response: " + new String(error.networkResponse.data);
                    }
                    Log.e("LoginActivity", errorMsg, error);
                    Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }
}