package com.example.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private EditText emailEditText;
    private Button signUpButton;
    private FrameLayout signUpProgressOverlay;
    FirebaseAuth mAuth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signup), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        firstNameEditText=findViewById(R.id.signupFirstName);
        lastNameEditText=findViewById(R.id.signupLastName);
        passwordEditText=findViewById(R.id.signupPassword);
        confirmPasswordEditText=findViewById(R.id.signupConfirmPassword);
        emailEditText=findViewById(R.id.signupEmail);
        signUpButton=findViewById(R.id.signupButton);
        signUpProgressOverlay=findViewById(R.id.signUpProgressOverlay);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUser(
                        firstNameEditText.getText().toString(),
                        lastNameEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        confirmPasswordEditText.getText().toString(),
                        emailEditText.getText().toString()
                );
            }
        });

    }

    private void saveUser(String firstName, String lastName, String password, String confirmPassword, String email){
        signUpProgressOverlay.setVisibility(View.VISIBLE);

        firstName.trim();
        lastName.trim();
        email.trim();
        password.trim();
        confirmPassword.trim();

        if(!password.equals(confirmPassword)){
            new AlertDialog.Builder(this)
                    .setTitle("Failed")
                    .setMessage("Confirm password do not match !")
                    .setPositiveButton("Ok", (dialog, which) -> {
                        signUpProgressOverlay.setVisibility(View.INVISIBLE);
                    }).show();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userId = mAuth.getCurrentUser().getUid();

                        User user = new User(firstName, lastName);

                        database.getReference("users")
                                .child(userId)
                                .setValue(user)
                                .addOnSuccessListener(aVoid -> {

                                    new AlertDialog.Builder(this)
                                            .setTitle("Success")
                                            .setMessage("you have been successfully signed up !")
                                            .setPositiveButton("Ok", (dialog, which) -> {
                                                signUpProgressOverlay.setVisibility(View.INVISIBLE);
                                                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                            }).show();

                                })
                                .addOnFailureListener(e -> {

                                    new AlertDialog.Builder(this)
                                            .setTitle("Failed")
                                            .setMessage(e.getMessage())
                                            .setPositiveButton("Ok", (dialog, which) -> {
                                                signUpProgressOverlay.setVisibility(View.INVISIBLE);
                                            }).show();
                                });

                    } else {
                        new AlertDialog.Builder(this)
                                .setTitle("Failed")
                                .setMessage(task.getException().getMessage())
                                .setPositiveButton("Ok", (dialog, which) -> {
                                    signUpProgressOverlay.setVisibility(View.INVISIBLE);
                                }).show();
                    }
                });
    }
}