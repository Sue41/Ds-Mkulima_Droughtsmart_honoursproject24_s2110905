package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication.utils.EmailSender;
import com.example.myapplication.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.List;

public class Reset_passwordEnterEmailScreen extends AppCompatActivity {

FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_password_enter_email_screen);



        final EditText emailEditText = findViewById(R.id.email);
        final Button send_otp = findViewById(R.id.forgot_password_button);
        final TextView back_to_login = findViewById(R.id.back_to_login);

        back_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Reset_passwordEnterEmailScreen.this, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

       send_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString().trim();
                if (!email.isEmpty()) {
                    if (Utils.isValidEmail(email)) {
                        checkIfEmailExists(email);
                    } else {
                        Toast.makeText(Reset_passwordEnterEmailScreen.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(Reset_passwordEnterEmailScreen.this, "Please enter an email", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void checkIfEmailExists(String email) {
        firestore = FirebaseFirestore.getInstance();
        CollectionReference usersRef = firestore.collection("users");
        usersRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    boolean mailFound = false;
                    List<DocumentSnapshot> documents = task.getResult().getDocuments();
                    for (DocumentSnapshot document : documents) {
                        // Process each document
                        String email_results = document.getString("email");
                        if(email.equals(email_results)){
                            mailFound = true;
                            break;
                        }
                    }
                    if(mailFound){
                        String code = "";
                        code = Utils.generateCode();
                        String finalCode = code;
                        EmailSender.sendEmail(email, "Verification Code", "Reset password", code, "10 min", "droughtsmart", new EmailSender.EmailCallback() {
                            @Override
                            public void onSuccess() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Reset_passwordEnterEmailScreen.this, "Verification email sent.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Reset_passwordEnterEmailScreen.this, Verify_Reset_OTP.class);
                                        intent.putExtra("code", finalCode);
                                        intent.putExtra("email", email);
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
                                        Toast.makeText(Reset_passwordEnterEmailScreen.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });

                    }else{
                        Toast.makeText(Reset_passwordEnterEmailScreen.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });

    }
}