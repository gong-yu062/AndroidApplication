package com.example.androidapp.ui;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.androidapp.R; // Adjust this import if the package name is different
import com.example.androidapp.MainActivity;

public class DeleteAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        TextView textViewConfirmDeleteAccount = findViewById(R.id.textViewConfirmDeleteAccount);
        Button buttonConfirmDeleteAccountYes = findViewById(R.id.buttonConfirmDeleteAccountYes);
        Button buttonConfirmDeleteAccountNo = findViewById(R.id.buttonConfirmDeleteAccountNo);

        textViewConfirmDeleteAccount.setText(R.string.confirm_delete_account);

        buttonConfirmDeleteAccountYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform the deletion of the account
                deleteUserAccount();
            }
        });

        buttonConfirmDeleteAccountNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // User chose not to delete the account, finish the activity
                finish();
            }
        });
    }

    private void deleteUserAccount() {
        // This method should contain the logic to delete the user's account, like a call to your server.

        // Clear any session data
        SharedPreferences preferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        // Redirect to a safe screen, such as the main screen or login activity, after account deletion
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish(); // Make sure to call this to close the activity
    }
}
