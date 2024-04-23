package com.example.androidapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.androidapp.R;

public class LogoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        Button yesButton = findViewById(R.id.buttonConfirmLogoutYes);
        Button noButton = findViewById(R.id.buttonConfirmLogoutNo);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // User chose to logout
                performLogout();
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // User chose not to logout, so we finish this activity and go back
                finish();
            }
        });
    }

    private void performLogout() {
        // Clear the user session and redirect to LoginActivity
        // Assuming you have a method to clear user's session
        // Then, redirect to login activity and clear the activity stack
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}

