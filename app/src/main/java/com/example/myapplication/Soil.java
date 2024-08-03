package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class Soil extends AppCompatActivity {
    private BarChart barChart;
    private LinearLayout backbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_soil);

        barChart = findViewById(R.id.barChart);
        backbutton = findViewById(R.id.backbutton);

        // Data for soils (potassium, phosphorus, nitrogen for w1, w2, w3)
        ArrayList<BarEntry> w1Entries = new ArrayList<>();
        w1Entries.add(new BarEntry(0, 10f)); // w1 potassium
        w1Entries.add(new BarEntry(1, 20f)); // w1 phosphorus
        w1Entries.add(new BarEntry(2, 30f)); // w1 nitrogen

        ArrayList<BarEntry> w2Entries = new ArrayList<>();
        w2Entries.add(new BarEntry(3, 15f)); // w2 potassium
        w2Entries.add(new BarEntry(4, 25f)); // w2 phosphorus
        w2Entries.add(new BarEntry(5, 35f)); // w2 nitrogen

        ArrayList<BarEntry> w3Entries = new ArrayList<>();
        w3Entries.add(new BarEntry(6, 12f)); // w3 potassium
        w3Entries.add(new BarEntry(7, 22f)); // w3 phosphorus
        w3Entries.add(new BarEntry(8, 32f)); // w3 nitrogen

        // Creating BarDataSet for each soil type and nutrient
        BarDataSet w1DataSet = new BarDataSet(w1Entries, "w1 Soil");
        w1DataSet.setColors(getResources().getColor(R.color.colorPotassium), getResources().getColor(R.color.colorPhosphorus), getResources().getColor(R.color.colorNitrogen));

        BarDataSet w2DataSet = new BarDataSet(w2Entries, "w2 Soil");
        w2DataSet.setColors(getResources().getColor(R.color.colorPotassium), getResources().getColor(R.color.colorPhosphorus), getResources().getColor(R.color.colorNitrogen));

        BarDataSet w3DataSet = new BarDataSet(w3Entries, "w3 Soil");
        w3DataSet.setColors(getResources().getColor(R.color.colorPotassium), getResources().getColor(R.color.colorPhosphorus), getResources().getColor(R.color.colorNitrogen));

        // Adding data sets to BarData
        BarData barData = new BarData(w1DataSet, w2DataSet, w3DataSet);

        barChart.setData(barData);
        barChart.groupBars(0f, 0.4f, 0.1f); // Grouping the bars
        barChart.invalidate(); // refresh

        // Customize X-Axis
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"", "w1", "w2", "w3"}));

        // Customize Y-Axis
        YAxis leftAxis = barChart.getAxisLeft();
        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}