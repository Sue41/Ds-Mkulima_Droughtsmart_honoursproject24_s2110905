package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication.Resources.ReusableInforRepository;
import com.example.myapplication.Resources.User;
import com.example.myapplication.Resources.UserManager;
import com.google.firebase.auth.FirebaseAuth;

public class Account extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account);

        final ImageView backbutton = findViewById(R.id.backbutton);
        final LinearLayout logoutbutton = findViewById(R.id.about_us_button);
        final LinearLayout myaccount = findViewById(R.id.myaccount);
        final View logoutOverLay = findViewById(R.id.logout_overlay);
        final LinearLayout privacy = findViewById(R.id.privacy);
        final LinearLayout help = findViewById(R.id.help);
        final RelativeLayout logoutOvePop = findViewById(R.id.logout_popup);
        final TextView name = findViewById(R.id.name);
        final TextView email = findViewById(R.id.email);
        final TextView cancel = findViewById(R.id.logout_cancel);
        final TextView logout = findViewById(R.id.logout);

        User currentUser = UserManager.getInstance().getUser();
        String username = currentUser.getName();
        email.setText(currentUser.getEmail());
        name.setText(username);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account.super.onBackPressed();
                finish();
            }
        });
        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutOvePop.setVisibility(View.VISIBLE);
                //handleLogout();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  logoutOvePop.setVisibility(View.GONE);
                handleLogout();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutOvePop.setVisibility(View.GONE);
                //handleLogout();
            }
        });
        myaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Account.this, AccountInformation.class));
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Account.this, Support.class));
            }
        });
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReusableInforRepository.ReusableInfor reusable = ReusableInforRepository.ReusableInforMap.get("privacy_policy");
                Intent intent = new Intent(Account.this,ReusableCoponent.class);
                intent.putExtra("title",reusable.getTitle());
                intent.putExtra("icon",reusable.getIcon());
                intent.putExtra("text",reusable.getText());
                startActivity(intent);
            }
        });

    }

    private void handleLogout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(Account.this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


}