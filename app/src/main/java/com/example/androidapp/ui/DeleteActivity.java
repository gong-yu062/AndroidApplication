package com.example.androidapp.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.androidapp.R;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.android.volley.RequestQueue;



public class DeleteActivity extends AppCompatActivity {
    EditText editTextDeletionId;
    Button buttonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        editTextDeletionId = findViewById(R.id.editTextDeletionId);
        buttonDelete = findViewById(R.id.buttonDelete);

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idToDelete = editTextDeletionId.getText().toString();
                if (!idToDelete.isEmpty()) {
                    deleteUser(idToDelete);
                } else {
                    Toast.makeText(DeleteActivity.this, "Please enter an ID", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void deleteUser(String id) {

        // URL to your backend API, replace with your actual URL and append the id to delete
        String url = "https://gitfit.azurewebsites.net/api/Fit/" + id;

// Initialize a new request queue instance
        RequestQueue requestQueue = Volley.newRequestQueue(this);

// Create a new string request
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the successful response here
                        Toast.makeText(DeleteActivity.this, "User deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error response here
                        Toast.makeText(DeleteActivity.this, "Error deleting user: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                // Add the request to the Volley request queue
                        requestQueue.add(stringRequest);

    }
}