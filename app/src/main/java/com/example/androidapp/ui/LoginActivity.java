package com.example.androidapp.ui;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.text.TextUtils;
import android.util.Log;
import com.example.androidapp.R;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.text.TextUtils;
import android.util.Log;
import android.net.Uri;  // Correct import for Uri
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;
import org.json.JSONException;
import com.android.volley.toolbox.JsonObjectRequest;

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
        if (TextUtils.isEmpty(emailEditText.getText()) || TextUtils.isEmpty(passwordEditText.getText())) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        String url = "https://gitfit.azurewebsites.net/api/Fit/login";

        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("email", email);
            jsonObj.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObj,
                response -> {
                    // Check response as per your backend response
                    try {
                        if ("Login Succesfull".equalsIgnoreCase(response.getString("message").trim())) {
                            Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Login failed! Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(LoginActivity.this, "Error parsing response!", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    String errorMsg = "Login failed! Please try again.";
                    // Check if there is a network response
                    if (error.networkResponse != null) {
                        // Log status code to see if there's a HTTP error
                        Log.e("LoginActivity", "Error status code: " + error.networkResponse.statusCode);

                        // Attempt to retrieve the body of the error response
                        String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                        Log.e("LoginActivity", "Error body: " + responseBody);

                        // Append the detailed error message to the toast message
                        errorMsg += "\nError Code: " + error.networkResponse.statusCode + "\nError Body: " + responseBody;
                    } else {
                        // If there's no network response (e.g., timeout or no internet), log the volley error
                        Log.e("LoginActivity", "Volley error: " + error.toString());
                    }
                    Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                });

        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

}
