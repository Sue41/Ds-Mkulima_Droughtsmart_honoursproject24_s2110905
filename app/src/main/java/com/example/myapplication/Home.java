package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity implements HomeContent.OnFragmentInteractionListener {
    FirebaseAuth mAuth;
    private FusedLocationProviderClient fusedLocationClient;
    private RelativeLayout home;
    private RelativeLayout scan;
    private RelativeLayout messages;
    private RelativeLayout notification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();

        home = findViewById(R.id.home);
        scan = findViewById(R.id.scan);
        messages = findViewById(R.id.messages);
        notification = findViewById(R.id.notification);

        home.setBackgroundResource(R.drawable.inactive_menu_body);
        scan.setBackgroundResource(R.drawable.inactive_menu_body);
        messages.setBackgroundResource(R.drawable.inactive_menu_body);
        notification.setBackgroundResource(R.drawable.inactive_menu_body);

        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = "Thank you for your interest in DroughtSmart. We're here to assist you with any questions or support you need regarding our products and services. Feel free to reach out to us, and we'll get back to you as soon as possible.";
                Intent intent = new Intent(Home.this,ReusableCoponent.class);
                intent.putExtra("title","Contact us");
                intent.putExtra("icon","help_item_icon");
                intent.putExtra("text",msg);
                startActivity(intent);
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String htmlContent = "<html><body><div>" +
                        "<p><b>Stay Connected with DroughtSmart!</b></p>" +
                        "<p>We're here to assist you with all your irrigation and agricultural needs. Have questions or need support? Reach out to us directly from the app. Our team is ready to help you make the most of our products and services.</p>" +
                        "<p>Feel free to contact us anytime!</p>" +
                        "<p>Best regards,<br>The DroughtSmart Team</p>" +
                        "</div></body></html>";
                Intent intent = new Intent(Home.this,ReusableCoponent.class);
                intent.putExtra("title","Notifications");
                intent.putExtra("icon","help_item_icon");
                intent.putExtra("text", htmlContent);
                startActivity(intent);
            }
        });


        // Set default fragment and backgrounds
        setActiveFragment(new HomeContent(), "home");

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_content, new HomeContent())
                    .commit();
            home.setBackgroundResource(R.drawable.active_menu_body);
        }

        final ImageView profile = findViewById(R.id.account_icon);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, Account.class));
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveFragment(new HomeContent(), "home");
            }
        });

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setActiveFragment(new ScannerContent(), "scan");
            }
        });

    }

    private void setActiveFragment(Fragment fragment, String id) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, fragment, id)
                .commit();
        updateBackground(id);
    }

    private void updateBackground(String id) {
        if (id.equals("home")) {
            home.setBackgroundResource(R.drawable.active_menu_body);
            scan.setBackgroundResource(R.drawable.inactive_menu_body);
            messages.setBackgroundResource(R.drawable.inactive_menu_body);
            notification.setBackgroundResource(R.drawable.inactive_menu_body);
        } else if (id.equals("scan")) {
            home.setBackgroundResource(R.drawable.inactive_menu_body);
            scan.setBackgroundResource(R.drawable.active_menu_body);
            messages.setBackgroundResource(R.drawable.inactive_menu_body);
            notification.setBackgroundResource(R.drawable.inactive_menu_body);
        } else if (id.equals("messages")) {
            home.setBackgroundResource(R.drawable.inactive_menu_body);
            scan.setBackgroundResource(R.drawable.inactive_menu_body);
            messages.setBackgroundResource(R.drawable.active_menu_body);
            notification.setBackgroundResource(R.drawable.inactive_menu_body);
        } else if (id.equals("notification")) {
            home.setBackgroundResource(R.drawable.inactive_menu_body);
            scan.setBackgroundResource(R.drawable.inactive_menu_body);
            messages.setBackgroundResource(R.drawable.inactive_menu_body);
            notification.setBackgroundResource(R.drawable.active_menu_body);
        }
    }

    @Override
    public void onButtonClicked() {
        setActiveFragment(new ScannerContent(), "scan");
    }

    public void onButtonClicked2() {
        startActivity(new Intent(Home.this, Irrigation.class));
    }
}