package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Resources.User;
import com.example.myapplication.Resources.UserManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class Splash extends AppCompatActivity {
   private FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(Splash.this);

        try{
            authStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    if (firebaseUser != null) {
                       // PopulateUserDetails(firebaseUser);

                        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                        DocumentReference commentRef = firestore.collection("users").document(firebaseUser.getUid());
                        commentRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                                if (e != null) {
                                    return;
                                }

                                if (snapshot != null && snapshot.exists()) {
                                    User user = new User();
                                    user.setName(snapshot.getString("firstname")+" " + snapshot.getString("lastname"));
                                    user.setEmail(snapshot.getString("email"));
                                    UserManager.getInstance().setUser(user);
                                    Intent intent = new Intent(Splash.this, Home.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });

                    }else{
                        Intent intent = new Intent(Splash.this, Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }
            };
        }catch (Exception e){
            Toast.makeText(Splash.this, "Network Error", Toast.LENGTH_SHORT).show();
            finish();
        }

    }
    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    private void PopulateUserDetails(FirebaseUser currentUser){
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
                                    Intent intent = new Intent(Splash.this, Home.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Intent intent = new Intent(Splash.this, SetOtp.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                                mailFound = true;
                                break;
                            }
                            if (!mailFound) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(Splash.this, Login.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }


                    } else {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(Splash.this, Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }
            });


    }

}