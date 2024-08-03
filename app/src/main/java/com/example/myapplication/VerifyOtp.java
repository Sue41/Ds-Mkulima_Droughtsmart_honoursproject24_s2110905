package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class VerifyOtp extends AppCompatActivity {

    private EditText otpT1, otpT2, otpT3, otpT4;
    private TextView resend;
    private Button submitOtp;
    private TextView timer;
    private TextView sendTo;

    private boolean resendOtp = false;
    private int timeOut = 60;
    private int selectedEtPosition = 0;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verify_otp);

        otpT1 = findViewById(R.id.otp_digit_1);
        otpT2 = findViewById(R.id.otp_digit_2);
        otpT3 = findViewById(R.id.otp_digit_3);
        otpT4 = findViewById(R.id.otp_digit_4);
        resend = findViewById(R.id.resend_otp_btn);
        submitOtp = findViewById(R.id.send_otp);
        timer = findViewById(R.id.sec);
        sendTo = findViewById(R.id.send_to);

        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String code = "";
        code = Utils.generateCode();
        final String[] email_address = {""};
        if (currentUser != null) {
            DocumentReference docRef = db.collection("users").document(currentUser.getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String email_text = document.getString("email");

                            sendTo.setText("Enter 4-digits code sent to you at " + email_text);
                        }
                    }
                }
            });

        }

        otpT1.addTextChangedListener(textWatcher);
        otpT2.addTextChangedListener(textWatcher);
        otpT3.addTextChangedListener(textWatcher);
        otpT4.addTextChangedListener(textWatcher);

        otpT1.setInputType(InputType.TYPE_CLASS_NUMBER);
        otpT2.setInputType(InputType.TYPE_CLASS_NUMBER);
        otpT3.setInputType(InputType.TYPE_CLASS_NUMBER);
        otpT4.setInputType(InputType.TYPE_CLASS_NUMBER);

        otpT1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
        otpT2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
        otpT3.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
        otpT4.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});

        otpT2.setEnabled(false);
        otpT3.setEnabled(false);
        otpT4.setEnabled(false);

        showKeyboard(otpT1);

        // Start count down
        startCountDown();

        resend.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startCountDown();
                    }
                }
        );

        submitOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(otpT1.getText().toString().isEmpty() || otpT1.getText().toString().isEmpty() || otpT2.getText().toString().isEmpty() || otpT3.getText().toString().isEmpty() || otpT4.getText().toString().isEmpty()){
                    Toast.makeText(VerifyOtp.this, "Please Enter Full OTP.", Toast.LENGTH_SHORT).show();
                }else{
                    if (currentUser != null) {
                        DocumentReference docRef = db.collection("users").document(currentUser.getUid());
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        String code_text = document.getString("verification_code");
                                        if(code_text.isEmpty() || code_text.length()!=4){
                                            Toast.makeText(VerifyOtp.this, "Please resend Code", Toast.LENGTH_SHORT).show();
                                        }else{
                                            String otp = otpT1.getText().toString()+otpT2.getText().toString()+otpT3.getText().toString()+otpT4.getText().toString();
                                            if(code_text.equals(otp)){
                                                // OTP is correct, update the 'verified' field
                                                docRef.update("verified", true).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            FirebaseAuth.getInstance().signOut();
                                                            // Successfully updated, navigate to Home screen
                                                            Intent intent = new Intent(VerifyOtp.this, Login.class);
                                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                            startActivity(intent);
                                                            finish();
                                                        } else {
                                                            Toast.makeText(VerifyOtp.this, "Failed to update verification status.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            }else{
                                                Toast.makeText(VerifyOtp.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    }
                                }
                            }
                        });

                    }
                }
            }
        });

    }

    private void showKeyboard(EditText OTP) {
        OTP.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(OTP, InputMethodManager.SHOW_IMPLICIT);
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.length() == 1) {
                switch (selectedEtPosition) {
                    case 0:
                        selectedEtPosition = 1;
                        otpT2.setEnabled(true);
                        otpT2.requestFocus();
                        showKeyboard(otpT2);
                        break;
                    case 1:
                        selectedEtPosition = 2;
                        otpT3.setEnabled(true);
                        otpT3.requestFocus();
                        showKeyboard(otpT3);
                        break;
                    case 2:
                        selectedEtPosition = 3;
                        otpT4.setEnabled(true);
                        otpT4.requestFocus();
                        showKeyboard(otpT4);
                        break;
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public void startCountDown() {
        resendOtp = false;
        resend.setTextColor(Color.parseColor("#99000000"));
        new CountDownTimer(timeOut * 1000, 1000) {

            @Override
            public void onTick(long l) {
                timer.setText("00:" + l / 1000 + " Sec");
            }

            @Override
            public void onFinish() {
                resendOtp = true;
                resend.setTextColor(Color.parseColor("#4BA26A"));
            }
        }.start();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            switch (selectedEtPosition) {
                case 3:
                    if (otpT4.getText().length() == 0) {
                        selectedEtPosition = 2;
                        otpT4.setEnabled(false);
                        showKeyboard(otpT3);
                    } else {
                        otpT4.setText("");
                    }
                    break;
                case 2:
                    if (otpT3.getText().length() == 0) {
                        selectedEtPosition = 1;
                        otpT3.setEnabled(false);
                        showKeyboard(otpT2);
                    } else {
                        otpT3.setText("");
                    }
                    break;
                case 1:
                    if (otpT2.getText().length() == 0) {
                        selectedEtPosition = 0;
                        otpT2.setEnabled(false);
                        showKeyboard(otpT1);
                    } else {
                        otpT2.setText("");
                    }
                    break;
                case 0:
                    otpT1.setText("");
                    break;
            }
            return true;
        } else {
            return super.onKeyUp(keyCode, event);
        }
    }
}
