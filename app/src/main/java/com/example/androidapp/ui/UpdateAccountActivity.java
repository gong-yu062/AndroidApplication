package com.example.androidapp.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.androidapp.R;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateAccountActivity extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, dateOfBirthEditText,
            genderEditText, phoneNumberEditText, weightEditText, heightEditText, emailEditText;
    private Button buttonSaveChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);

        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        dateOfBirthEditText = findViewById(R.id.dateOfBirthEditText);
        genderEditText = findViewById(R.id.genderEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        weightEditText = findViewById(R.id.weightEditText);
        heightEditText = findViewById(R.id.heightEditText);
        emailEditText = findViewById(R.id.emailEditText);
        buttonSaveChanges = findViewById(R.id.buttonSaveChanges);

        buttonSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAccount();
            }
        });
    }

    private void updateAccount() {
        // Get the text from the EditTexts and trim whitespace
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        // ... (repeat for each field you need to update)

        String email = emailEditText.getText().toString().trim();

        // Perform input validation or any other checks here

        // Assuming you have a method to make network requests
        // updateUserOnServer with all the details you collected
        updateUserOnServer(firstName, lastName, email); // and other fields
    }

    private void updateUserOnServer(String firstName, String lastName, String email) {
        // This method should make an HTTP request to your backend server.
        // You can use Retrofit, Volley, or any other HTTP library.

        // For demonstration, we'll show a Toast
        Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();
    }
}

