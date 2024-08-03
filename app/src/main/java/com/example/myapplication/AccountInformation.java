package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication.Resources.User;
import com.example.myapplication.Resources.UserManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AccountInformation extends AppCompatActivity {
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account_information);
        final ImageView backbutton = findViewById(R.id.backbutton);
        final TextView name = findViewById(R.id.name);
        final TextView email = findViewById(R.id.email);
        final EditText firstname = findViewById(R.id.firstname_input);
        final EditText lastname = findViewById(R.id.last_name_input);
        final Button save_changes = findViewById(R.id.submit_button);

        //Overlay
        final RelativeLayout overlayPopup = findViewById(R.id.overlay_popup);
        final TextView cancel = findViewById(R.id.cancel);
        final TextView submit = findViewById(R.id.submit);



        mAuth = FirebaseAuth.getInstance();

        User currentUser = UserManager.getInstance().getUser();
        String username = currentUser.getName();
        email.setText(currentUser.getEmail());
        name.setText(username);

        String[] names = username.split(" ");
        firstname.setText(names[0]);
        lastname.setText(names[1]);


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        save_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overlayPopup.setVisibility(View.VISIBLE);

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Ensure that mAuth and currentUser are properly initialized before this block of code
                FirebaseUser cUser = mAuth.getCurrentUser();
                if (cUser == null) {
                    Toast.makeText(AccountInformation.this, "User not authenticated.", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                DocumentReference docRef = firestore.collection("users").document(cUser.getUid());
                String firstNameText = firstname.getText().toString().trim();
                String lastNameText = lastname.getText().toString().trim();

                if (firstNameText.isEmpty() || lastNameText.isEmpty()) {
                    Toast.makeText(AccountInformation.this, "First name or last name cannot be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, Object> updateMap = new HashMap<>();
                updateMap.put("firstname", firstNameText);
                updateMap.put("lastname", lastNameText);

                docRef.update(updateMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            currentUser.setName(firstNameText + " " + lastNameText);
                            Toast.makeText(AccountInformation.this, "Updated user information.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AccountInformation.this, "Failed to update verification details.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                overlayPopup.setVisibility(View.GONE);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overlayPopup.setVisibility(View.GONE);
            }
        });

    }
}