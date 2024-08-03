package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class PredictionOutcomes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_prediction_outcomes);


        final TextView title = findViewById(R.id.about_title);
        final TextView description = findViewById(R.id.description);
        final TextView nutrition = findViewById(R.id.nutrition);
        final TextView planttime = findViewById(R.id.planttime);
        final TextView text = findViewById(R.id.text);
        final ImageView back = findViewById(R.id.backbutton);


        Intent intent = getIntent();
        String intentDescription = intent.getStringExtra("description");
        String intentTitle = intent.getStringExtra("crop");
        String intentPlantTime = intent.getStringExtra("plantingTime");
        String intentNutrition = intent.getStringExtra("nutrition");

        title.setText("About "+intentTitle);
        text.setText(intentDescription);

        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setText(intentDescription);
                description.setTextColor(Color.parseColor("#4BA26A"));
                nutrition.setTextColor(Color.parseColor("#A19C9C"));
                planttime.setTextColor(Color.parseColor("#A19C9C"));
            }
        });

        nutrition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setText(intentNutrition);
                nutrition.setTextColor(Color.parseColor("#4BA26A"));
                description.setTextColor(Color.parseColor("#A19C9C"));
                planttime.setTextColor(Color.parseColor("#A19C9C"));
            }
        });
        planttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setText(intentPlantTime);
                planttime.setTextColor(Color.parseColor("#4BA26A"));
                description.setTextColor(Color.parseColor("#A19C9C"));
                nutrition.setTextColor(Color.parseColor("#A19C9C"));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            PredictionOutcomes.super.onBackPressed();
            }
        });
    }
}