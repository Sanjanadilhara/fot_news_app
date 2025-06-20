package com.example.newsapp;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    private ImageView closeButton;
    private TextView firstName;
    private TextView lastName;
    private TextView email;
    private Button saveButton;
    private Button deleteButton;
    private FrameLayout progressBar;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private String userId;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.editProfile), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firstName=findViewById(R.id.editFirstName);
        lastName=findViewById(R.id.editLastName);
        email=findViewById(R.id.editEmail);
        saveButton=findViewById(R.id.editSaveButton);
        deleteButton=findViewById(R.id.editDeleteButton);
        closeButton=findViewById(R.id.closeButtonEditProfile);
        progressBar=findViewById(R.id.editProgressOverlay);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        userId = mAuth.getCurrentUser().getUid();
        userRef = database.getReference("users").child(userId);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser();
            }
        });

        setFields();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveInfo(firstName.getText().toString(), lastName.getText().toString(), email.getText().toString());
            }
        });
    }
    private void setFields(){
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    firstName.setText(user.firstName);
                    lastName.setText(user.lastName);
                    email.setText(mAuth.getCurrentUser().getEmail());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    private void saveInfo(String firstName, String lastName, String email){
        progressBar.setVisibility(View.VISIBLE);
        if (mAuth.getCurrentUser() != null) {
            EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            ViewGroup.MarginLayoutParams params=new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(20, 0, 20, 0);
            input.setLayoutParams(params);

            new AlertDialog.Builder(this)
                    .setTitle("Re-authenticate")
                    .setMessage("Enter your password to update email")
                    .setView(input)
                    .setPositiveButton("Confirm", (dialog, which) -> {
                        String password = input.getText().toString();
                        mAuth.getCurrentUser().reauthenticate(EmailAuthProvider.getCredential(mAuth.getCurrentUser().getEmail(), password)).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> taskm) {
                                if(taskm.isSuccessful()){
                                    mAuth.getCurrentUser().updateEmail(email)
                                            .addOnCompleteListener(task -> {
                                                if (task.isSuccessful()) {
                                                    saveOtherInfo(firstName, lastName);
                                                } else {
                                                    new AlertDialog.Builder(EditProfile.this)
                                                            .setTitle("Failed")
                                                            .setMessage(task.getException().getMessage().toString())
                                                            .setPositiveButton("Ok", (dialog, which) -> {
                                                                progressBar.setVisibility(View.INVISIBLE);
                                                            }).show();
                                                }
                                            });
                                }
                            }
                        });

                        dialog.dismiss();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .show();
        }

    }
    private void deleteUser(){
        progressBar.setVisibility(View.VISIBLE);
        if (mAuth.getCurrentUser() != null) {
            EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            ViewGroup.MarginLayoutParams params=new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(20, 0, 20, 0);
            input.setLayoutParams(params);

            new AlertDialog.Builder(this)
                    .setTitle("Re-authenticate")
                    .setMessage("Enter your password to update email")
                    .setView(input)
                    .setPositiveButton("Confirm", (dialog, which) -> {
                        String password = input.getText().toString();
                        mAuth.getCurrentUser().reauthenticate(EmailAuthProvider.getCredential(mAuth.getCurrentUser().getEmail(), password)).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> taskm) {
                                if(taskm.isSuccessful()){
                                    mAuth.getCurrentUser().delete()
                                            .addOnCompleteListener(task -> {
                                                if (task.isSuccessful()) {
                                                    deleteUserInfo();
                                                } else {
                                                    new AlertDialog.Builder(EditProfile.this)
                                                            .setTitle("Failed")
                                                            .setMessage(task.getException().getMessage().toString())
                                                            .setPositiveButton("Ok", (dialog, which) -> {
                                                                progressBar.setVisibility(View.INVISIBLE);
                                                            }).show();
                                                }
                                            });
                                }
                            }
                        });

                        dialog.dismiss();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .show();
        }

    }
    private void deleteUserInfo(){
        userRef.removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        new AlertDialog.Builder(EditProfile.this)
                                .setTitle("Success")
                                .setMessage("Successfully deleted the profile")
                                .setPositiveButton("Ok", (dialog, which) -> {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    finish();
                                }).show();
                    } else {
                        new AlertDialog.Builder(EditProfile.this)
                                .setTitle("Failed")
                                .setMessage("Failed to delete user")
                                .setPositiveButton("Ok", (dialog, which) -> {
                                    progressBar.setVisibility(View.INVISIBLE);
                                }).show();
                    }
                });
    }
    private void saveOtherInfo(String firstName, String lastName){

        Map<String, Object> updates = new HashMap<>();
        updates.put("firstName", firstName);
        updates.put("lastName", lastName);
        System.out.println(firstName);

        userRef.updateChildren(updates)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        new AlertDialog.Builder(this)
                                .setTitle("Success")
                                .setMessage("Successfully Updated")
                                .setPositiveButton("Ok", (dialog, which) -> {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    finish();
                                }).show();
                    } else {
                        new AlertDialog.Builder(this)
                                .setTitle("Failed")
                                .setMessage(task.getException().getMessage().toString())
                                .setPositiveButton("Ok", (dialog, which) -> {
                                    progressBar.setVisibility(View.INVISIBLE);
                                }).show();
                    }
                });
    }
}