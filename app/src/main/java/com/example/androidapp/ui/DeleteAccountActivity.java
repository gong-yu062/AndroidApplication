package com.example.androidapp.ui;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidapp.R;
import org.json.JSONException;
import org.json.JSONObject;

public class DeleteAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        // Get user ID from intent or wherever it's stored
        int userId = 1; // Replace with the actual user ID

        // Call delete user function
        deleteUser(userId);
    }

    private void deleteUser(int userId) {
        String url = "https://gitfit.azurewebsites.net/api/Fit/" + userId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url, null,
                response -> {
                    // Check if delete was successful
                    Toast.makeText(DeleteAccountActivity.this, "User deleted successfully", Toast.LENGTH_SHORT).show();
                    // Finish this activity upon successful deletion
                    finish();
                },
                error -> {
                    // Handle errors
                    String errorMsg = "Delete user failed! Please try again.";
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        errorMsg += "\nServer Response: " + new String(error.networkResponse.data);
                    }
                    Toast.makeText(DeleteAccountActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                });

        // Add the request to the RequestQueue.
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
}
