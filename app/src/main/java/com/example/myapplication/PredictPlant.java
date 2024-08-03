package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication.Resources.PlantInfoRepository;

import java.io.IOException;

public class PredictPlant extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_predict_plant);


        final EditText nitrogen = findViewById(R.id.nitrogen_input);
        final EditText phosphorus = findViewById(R.id.phosphorus_input);
        final EditText potassium = findViewById(R.id.potassium_input);
        final EditText temparature = findViewById(R.id.temparature_input);
        final EditText humidity = findViewById(R.id.humidity_input);
        final EditText phValue = findViewById(R.id.phvalue_input);
        final EditText rainfall = findViewById(R.id.rainfall_input);
        final Button submit = findViewById(R.id.submit_button);
        final ImageView back = findViewById(R.id.backbutton);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(nitrogen.getText().toString().isEmpty() || phosphorus.getText().toString().isEmpty()  || potassium.getText().toString().isEmpty()  || temparature.getText().toString().isEmpty() || humidity.getText().toString().isEmpty()  || phValue.getText().toString().isEmpty() || rainfall.getText().toString().isEmpty() ){
                    Toast.makeText(PredictPlant.this, "Please fill all fields", Toast.LENGTH_SHORT).show();

                }else{

                    float[] inputData = new float[]{Integer.parseInt(nitrogen.getText().toString()), Integer.parseInt(phosphorus.getText().toString()), Integer.parseInt(potassium.getText().toString()),Float.parseFloat(temparature.getText().toString()), Float.parseFloat(humidity.getText().toString()), Float.parseFloat(phValue.getText().toString()), Float.parseFloat(rainfall.getText().toString())};

                    CropRecommendation cropRecommendation = null;
                    try {
                        cropRecommendation = new CropRecommendation(PredictPlant.this);
                    } catch (IOException e) {
                        Toast.makeText(PredictPlant.this, "Error processing data try again later", Toast.LENGTH_SHORT).show();
                    }
                    String recommendedCrop = cropRecommendation.recommendCrop(inputData);

                    if(recommendedCrop.isEmpty()){
                        Toast.makeText(PredictPlant.this, "Plant not found" + recommendedCrop, Toast.LENGTH_SHORT).show();
                    }else{
                        PlantInfoRepository.PlantInfo plantInfo = PlantInfoRepository.plantInfoMap.get(recommendedCrop);
                        if (plantInfo != null) {
                            String description = plantInfo.getDescription();
                            String nutrition = plantInfo.getNutrition();
                            String plantingTime = plantInfo.getPlantingTime();

                            Intent intent = new Intent(PredictPlant.this , PredictionOutcomes.class);
                            intent.putExtra("description",description);
                            intent.putExtra("crop",recommendedCrop);
                            intent.putExtra("nutrition",nutrition);
                            intent.putExtra("plantingTime",plantingTime);
                            startActivity(intent);

                        }
                    }

                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PredictPlant.super.onBackPressed();
                finish();
            }
        });



    }
}