package com.example.myapplication;

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

import com.example.myapplication.Resources.User;
import com.example.myapplication.Resources.UserManager;
import com.example.myapplication.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class Login extends AppCompatActivity {

    private boolean passwordShowing = false;
    FirebaseAuth mAuth;
    private FrameLayout progressOverlay;
    private ProgressBar progressBar;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(Login.this);
        final EditText email = findViewById(R.id.email);
        final EditText password = findViewById(R.id.password_text);
        final Button login = findViewById(R.id.login_button);
        final ImageView passwordIcon = findViewById(R.id.show_password_icon);
        final TextView forgotPassword = findViewById(R.id.forgot_pass);
        final TextView signup = findViewById(R.id.sign_up_text);

        progressOverlay = findViewById(R.id.progress_overlay);
        progressBar = findViewById(R.id.progress_bar);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    Intent intent = new Intent(Login.this, Home.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passwordShowing) {
                    passwordShowing = false;
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.show);
                } else {
                    passwordShowing = true;
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.hide);
                }
                password.setSelection(password.length());
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress(true);
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
                showProgress(false);
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress(true);
                startActivity(new Intent(Login.this, Reset_passwordEnterEmailScreen.class));
                showProgress(false);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress(true);
                String email_text = email.getText().toString();
                String password_text = password.getText().toString();

                if(email_text.isEmpty() || password_text.isEmpty()){
                    Toast.makeText(Login.this, "All Fields are required!!!", Toast.LENGTH_SHORT).show();
                    showProgress(false);
                }else {

                    if (Utils.isValidEmail(email_text)) {
                        mAuth.signInWithEmailAndPassword(email_text, password_text).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    finish();
                                } else {
                                    showProgress(false);
                                    Toast.makeText(Login.this, "Failed to Login, Please try again!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    } else {
                        showProgress(false);
                        Toast.makeText(Login.this, "Your email is not valid!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });


    }

    private void showProgress(boolean show) {
        if (show) {
            progressOverlay.setVisibility(View.VISIBLE);
        } else {
            progressOverlay.setVisibility(View.GONE);
        }
    }

    private void PopulateUserDetails(){

        if (currentUser != null) {
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

                                Boolean emailVerified = document.getBoolean("verified");
                                if (emailVerified != null && emailVerified) {

                                    User user = new User();
                                    user.setName(document.getString("firstname")+" " + document.getString("lastname"));
                                    user.setEmail(document.getString("email"));
                                    UserManager.getInstance().setUser(user);

                                    // Email is verified, navigate to the home screen
                                    Intent intent = new Intent(Login.this, Home.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                    showProgress(false);
                                } else {
                                    showProgress(false);
                                    Intent intent = new Intent(Login.this, SetOtp.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                                mailFound = true;
                                break;
                            }
                            if (!mailFound) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(Login.this, Login.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                                showProgress(false);
                            }
                        }


                    } else {
                        Log.d("TAG", "Error getting documents: ", task.getException());
                        showProgress(false);
                    }
                }
            });

            if(!currentUser.getEmail().isEmpty()){

            }

        } else {
            showProgress(false);
        }
    }
}