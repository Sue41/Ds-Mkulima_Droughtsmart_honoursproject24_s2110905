package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Register extends AppCompatActivity {

    private boolean isPasswordShowing = false;
    private boolean isConfirmPasswordShowing = false;
    private EditText firstname;
    private EditText lastname;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private FrameLayout progressOverlay;
    private ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();


        if (currentUser != null) {
            DocumentReference docRef = db.collection("users").document(currentUser.getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Boolean emailVerified = document.getBoolean("verified");

                            if (emailVerified != null && emailVerified) {
                                // Email is verified, navigate to the home screen
                                Intent intent = new Intent(Register.this, Home.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                Intent intent = new Intent(Register.this, SetOtp.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    } else {
                        // Error retrieving user data
                        Toast.makeText(Register.this, "Failed to retrieve user data.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);


        firstname = findViewById(R.id.firstname_text);
        lastname = findViewById(R.id.lastname_text);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password_text);
        confirmPassword = findViewById(R.id.password_c_text);
        final ImageView showPassword = findViewById(R.id.show_password_icon);
        final ImageView showConfirmPassword = findViewById(R.id.show_password_c_icon);
        final Button signUp = findViewById(R.id.sign_up_button);
        final TextView backToLoin = findViewById(R.id.back_to_login_button);

        progressOverlay = findViewById(R.id.progress_overlay);
        progressBar = findViewById(R.id.progress_bar);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress(true);
                final String firstname_text = firstname.getText().toString();
                final String lastname_text = firstname.getText().toString();
                final String email_text = email.getText().toString();
                final String password_text = password.getText().toString();
                final String confirmPassword_text = confirmPassword.getText().toString();

                if (firstname_text.equals("") || lastname_text.isEmpty() || email_text.isEmpty() || password_text.isEmpty() || confirmPassword_text.isEmpty()) {
                    showErrorPopup("Please make sure you fill all fields.");

                } else {
                    if (Utils.isValidEmail(email_text)) {
                        if (password_text.equals(confirmPassword_text)) {
                            if (Utils.isValidPassword(password_text)) {

                                mAuth.createUserWithEmailAndPassword(email_text, password_text)
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    FirebaseUser user = mAuth.getCurrentUser();
                                                    if (user != null) {
                                                        // Create a new user with a username
                                                        String code = "";
                                                        code = Utils.generateCode();
                                                        Map<String, Object> userMap = new HashMap<>();
                                                        userMap.put("firstname", firstname_text);
                                                        userMap.put("lastname", lastname_text);
                                                        userMap.put("email", email_text);
                                                        userMap.put("verified", false);
                                                        userMap.put("reset_code", code);
                                                        userMap.put("reset_code_expire_time", System.currentTimeMillis() + 600000);
                                                        userMap.put("verification_code", code);
                                                        userMap.put("expires_at", System.currentTimeMillis() + 600000); // 10 minutes from now

                                                        // Save user data to Firestore
                                                        FirebaseFirestore firestore;
                                                        firestore = FirebaseFirestore.getInstance();
                                                        FirebaseUser cUser = mAuth.getCurrentUser();
                                                        DocumentReference docRef = firestore.collection("users").document(cUser.getUid());
                                                        docRef.set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Intent intent = new Intent(Register.this, SetOtp.class);
                                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                    startActivity(intent);
                                                                    Toast.makeText(Register.this, "Verification email sent.", Toast.LENGTH_SHORT).show();
                                                                    finish();

                                                                } else {
                                                                    Toast.makeText(Register.this, "Failed to save user data.", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                    }
                                                } else {

                                                    FirebaseFirestore firestore;
                                                    firestore = FirebaseFirestore.getInstance();
                                                    CollectionReference usersRef = firestore.collection("users");
                                                    usersRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                            if (task.isSuccessful()) {
                                                                boolean mailFound = false;
                                                                List<DocumentSnapshot> documents = task.getResult().getDocuments();
                                                                for (DocumentSnapshot document : documents) {
                                                                    String document_id = document.getString("email");
                                                                    if (document_id.equals(currentUser.getEmail())) {
                                                                        mailFound = true;
                                                                        break;
                                                                    }
                                                                    if (!mailFound) {
                                                                      if(!currentUser.getEmail().isEmpty()){
                                                                          String code = "";
                                                                          code = Utils.generateCode();
                                                                          Map<String, Object> userMap = new HashMap<>();
                                                                          userMap.put("firstname", firstname_text);
                                                                          userMap.put("lastname", lastname_text);
                                                                          userMap.put("email", email_text);
                                                                          userMap.put("verified", false);
                                                                          userMap.put("reset_code", code);
                                                                          userMap.put("reset_code_expire_time", System.currentTimeMillis() + 600000);
                                                                          userMap.put("verification_code", code);
                                                                          userMap.put("expires_at", System.currentTimeMillis() + 600000); // 10 minutes from now

                                                                          // Save user data to Firestore


                                                                          FirebaseFirestore firestore;
                                                                          firestore = FirebaseFirestore.getInstance();
                                                                          FirebaseUser cUser = mAuth.getCurrentUser();
                                                                          DocumentReference docRef = firestore.collection("users").document(cUser.getUid());
                                                                          docRef.set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                              @Override
                                                                              public void onComplete(@NonNull Task<Void> task) {
                                                                                  if (task.isSuccessful()) {
                                                                                      Toast.makeText(Register.this, "Verification email sent.", Toast.LENGTH_SHORT).show();
                                                                                      Intent intent = new Intent(Register.this, SetOtp.class);
                                                                                      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                                      startActivity(intent);
                                                                                      finish();

                                                                                  } else {
                                                                                      Toast.makeText(Register.this, "Failed to save user data.", Toast.LENGTH_SHORT).show();
                                                                                  }
                                                                              }
                                                                          });
                                                                      }
                                                                    }
                                                                }


                                                            } else {
                                                                Log.d("TAG", "Error getting documents: ", task.getException());
                                                                showProgress(false);
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                        });
                            } else {
                                showProgress(false);
                                showErrorPopup("Please Enter a strong password\n* At least one digit\n* At least one lower case letter\n* At least one upper case letter\n* At least one special character\n* At least 8 characters\n* No whitespaces. ");
                            }

                        } else {
                            showProgress(false);
                            showErrorPopup("Passwords do not match!!");
                        }
                    } else {
                        showProgress(false);
                        showErrorPopup("Your email is wrong please check and try again.");
                    }
                }
            }
        });
        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPasswordShowing) {
                    isPasswordShowing = false;
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    showPassword.setImageResource(R.drawable.show);
                } else {
                    isPasswordShowing = true;
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    showPassword.setImageResource(R.drawable.hide);
                }
                password.setSelection(password.length());
            }
        });
        showConfirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConfirmPasswordShowing) {
                    isConfirmPasswordShowing = false;
                    confirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    showConfirmPassword.setImageResource(R.drawable.show);
                } else {
                    isConfirmPasswordShowing = true;
                    confirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    showConfirmPassword.setImageResource(R.drawable.hide);
                }
                confirmPassword.setSelection(confirmPassword.length());
            }
        });
        backToLoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress(true);
                Intent intent = new Intent(Register.this, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                showProgress(false);
            }
        });

    }

    private void showErrorPopup(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Close the dialog
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showProgress(boolean show) {
        if (show) {
            progressOverlay.setVisibility(View.VISIBLE);
        } else {
            progressOverlay.setVisibility(View.GONE);
        }
    }
}