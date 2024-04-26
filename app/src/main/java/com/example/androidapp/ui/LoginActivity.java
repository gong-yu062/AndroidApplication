package com.example.androidapp.ui;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.AuthFailureError;
import android.text.TextUtils;
import android.util.Log;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.Network;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.example.androidapp.R;
import com.example.androidapp.ToastManager;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;


public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button buttonLogin;
    private RequestQueue requestQueue;
    private ToastManager toastManager; // Make sure to initialize this correctly

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toastManager = new ToastManager(); // This may need to take 'this' as a parameter, depending on your implementation
        requestQueue = Volley.newRequestQueue(this);
        emailEditText = findViewById(R.id.editTextUsername);
        passwordEditText = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        if (!isNetworkAvailable()) {
            toastManager.showToast(this, "No internet connection");
            return;
        }

        final String email = emailEditText.getText().toString().trim();
        final String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            toastManager.showToast(this, "Please fill in all fields");
            return;
        }

        String url = "https://gitfit.azurewebsites.net/api/Fit/login?email=" + email + "&password=" + password;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> handleSuccess(),
                this::handleError
        ) {
            @Override
            protected Map<String, String> getParams() {
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

        requestQueue.add(stringRequest);
    }

    private void handleSuccess() {
        toastManager.showToast(this, "Login successful!");
    }



    private void handleError(VolleyError error) {
        if (error.networkResponse != null) {
            String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
            Log.e("LoginActivity", "Error Response: " + responseBody);
            // Parse error message and display it
            String errorMessage = parseErrorMessage(responseBody);
            runOnUiThread(() -> toastManager.showToast(this, errorMessage));
        } else {
            // General error like timeout or no connection
            runOnUiThread(() -> toastManager.showToast(this, "An error occurred"));
        }
    }


    private String parseErrorMessage(String responseBody) {
        String message = "Error: ";
        try {
            JSONObject jsonObject = new JSONObject(responseBody);
            JSONObject errorsObject = jsonObject.optJSONObject("errors");
            if (errorsObject != null) {
                if (errorsObject.has("email")) {
                    JSONArray emailErrors = errorsObject.getJSONArray("email");
                    message += "Email: " + emailErrors.getString(0) + "\n";
                }
                if (errorsObject.has("password")) {
                    JSONArray passwordErrors = errorsObject.getJSONArray("password");
                    message += "Password: " + passwordErrors.getString(0);
                }
            }
        } catch (JSONException e) {
            Log.e("LoginActivity", "Error parsing server response", e);
            message += "Error parsing server response.";
        }
        return message;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network = connectivityManager.getActiveNetwork();
        NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
        return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
    }
}
