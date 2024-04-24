package com.example.androidapp.ui;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidapp.R;
import com.android.volley.AuthFailureError;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUsername;
    EditText editTextPassword;
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        String url = "https://gitfit.azurewebsites.net/api/Fit/login";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    // Handle the response from the server here.
                    Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    // Handle error more comprehensively
                    String errorMsg = "Login failed!";
                    if (error.networkResponse != null) {
                        errorMsg += " Error: " + error.networkResponse.statusCode;
                        errorMsg += " " + new String(error.networkResponse.data);
                    }
                    Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", editTextUsername.getText().toString().trim());
                params.put("password", editTextPassword.getText().toString().trim());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }
}

