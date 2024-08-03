package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication.utils.EmailSender;
import com.example.myapplication.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SetOtp extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String code = "";
        code = Utils.generateCode();
        final String[] email = {""};




        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_set_otp);
        final TextView send_label = findViewById(R.id.send_to);
        final TextView send_button = findViewById(R.id.send_otp);


        if (currentUser != null) {
            DocumentReference docRef = db.collection("users").document(currentUser.getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String email_text = document.getString("email");
                            send_label.setText("Send OTP to " + email_text);
                        }
                    }
                }
            });

        }
        System.out.println("Email is" + email[0]);
        if (email[0].isEmpty()) {
            System.out.println(email[0]);

        } else {
            Intent intent = new Intent(SetOtp.this, Register.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = "";
                code = Utils.generateCode();
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("verification_code", code);
                userMap.put("expires_at", System.currentTimeMillis() + 600000);


                if (currentUser != null) {
                    DocumentReference docRef = db.collection("users").document(currentUser.getUid());
                    docRef.update(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Generate new verification code and expiration time
                                String code = Utils.generateCode();
                                long expiresAt = System.currentTimeMillis() + 600000; // 10 minutes from now

                                // Create a map with the new verification details
                                Map<String, Object> updateMap = new HashMap<>();
                                updateMap.put("verification_code", code);
                                updateMap.put("expires_at", expiresAt);

                                // Update Firestore document with new verification details
                                // Assuming `updateMap` contains the fields you want to update
                                docRef.update(updateMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // Fetch the updated document to get the email address
                                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        DocumentSnapshot document = task.getResult();
                                                        if (document.exists()) {
                                                            // Extract the email address from the document
                                                            String email = document.getString("email");
                                                            if (email != null) {
                                                                // Send the verification email
                                                                EmailSender.sendEmail(email, "Verification Code", "Register", code, "10 min", "droughtsmart", new EmailSender.EmailCallback() {
                                                                    @Override
                                                                    public void onSuccess() {
                                                                        runOnUiThread(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                Toast.makeText(SetOtp.this, "Verification email sent.", Toast.LENGTH_SHORT).show();
                                                                                Intent intent = new Intent(SetOtp.this, VerifyOtp.class);
                                                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                                startActivity(intent);
                                                                                finish();
                                                                            }
                                                                        });
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Exception e) {
                                                                        runOnUiThread(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                Toast.makeText(SetOtp.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                                    }
                                                                });
                                                            } else {
                                                                Toast.makeText(SetOtp.this, "Failed to retrieve email address.", Toast.LENGTH_SHORT).show();
                                                            }
                                                        } else {
                                                            Toast.makeText(SetOtp.this, "Document does not exist.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                        Toast.makeText(SetOtp.this, "Failed to fetch updated document.", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        } else {
                                            Toast.makeText(SetOtp.this, "Failed to update verification details.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } else {
                                Toast.makeText(SetOtp.this, "Failed to save user data.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(SetOtp.this, "Verification email sent.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}