package com.example.androidapp.ui;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.AuthFailureError;

import org.json.JSONObject;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;
import java.nio.charset.StandardCharsets;
import android.text.TextUtils;
import android.util.Log;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.Network;

import com.example.androidapp.R;
import com.example.androidapp.ToastManager;
import com.android.volley.DefaultRetryPolicy;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button buttonLogin;
    private RequestQueue requestQueue;

    private ToastManager toastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toastManager = new ToastManager(); // Ensure ToastManager is correctly initialized in your project

        requestQueue = Volley.newRequestQueue(this);
        emailEditText = findViewById(R.id.editTextUsername);
        passwordEditText = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        if (!isNetworkAvailable()) {
            toastManager.showToast(LoginActivity.this, "No internet connection");
            return;
        }

        final String email = emailEditText.getText().toString().trim();
        final String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            toastManager.showToast(LoginActivity.this, "Please fill in all fields");
            return;
        }

        String url = "https://gitfit.azurewebsites.net/api/Fit/login";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle success
                        handleSuccess(new JSONObject()); // Assuming the response is just a success message, not JSON
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        handleError(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Set your post request params in the form of key-value pairs here
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000, // 20 seconds timeout
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);
    }

    private void handleSuccess(JSONObject response) {
        String message = "Login successful!";
        toastManager.showToast(LoginActivity.this, message);
    }

    private void handleError(VolleyError error) {
        String message = "Network error";
        if (error.networkResponse != null) {
            if (error.networkResponse.statusCode == 404) {
                message = "Server endpoint not found";
            } else {
                String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                Log.e("LoginActivity", "Server Response: " + responseBody);
                message = parseErrorMessage(responseBody);
            }
        }
        toastManager.showToast(LoginActivity.this, message);
    }

    private String parseErrorMessage(String responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody);
            return jsonObject.optString("message", "Error processing the request");
        } catch (JSONException e) {
            Log.e("LoginActivity", "Error parsing server response", e);
            return "Error parsing server response";
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network = connectivityManager.getActiveNetwork();
        NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
        return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
    }
}
