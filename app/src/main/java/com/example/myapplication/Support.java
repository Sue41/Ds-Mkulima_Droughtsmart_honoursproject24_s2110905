package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Resources.ReusableInforRepository;


public class Support extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_support);

        final LinearLayout back = findViewById(R.id.backbutton);
        final LinearLayout about = findViewById(R.id.about_us_button);
        final LinearLayout terms = findViewById(R.id.terms_and_condition_button);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReusableInforRepository.ReusableInfor reusable = ReusableInforRepository.ReusableInforMap.get("about");
                Intent intent = new Intent(Support.this,ReusableCoponent.class);
                intent.putExtra("title",reusable.getTitle());
                intent.putExtra("icon",reusable.getIcon());
                intent.putExtra("text",reusable.getText());
                startActivity(intent);
            }
        });

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReusableInforRepository.ReusableInfor reusable = ReusableInforRepository.ReusableInforMap.get("terms_and_conditions");
                Intent intent = new Intent(Support.this,ReusableCoponent.class);
                intent.putExtra("title",reusable.getTitle());
                intent.putExtra("icon",reusable.getIcon());
                intent.putExtra("text",reusable.getText());
                startActivity(intent);
            }
        });
    }
}