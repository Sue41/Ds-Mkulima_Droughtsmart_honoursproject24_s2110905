package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ReusableCoponent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reusable_coponent);

        final ImageView icon = findViewById(R.id.icon);
        final TextView title = findViewById(R.id.title);
        final TextView text = findViewById(R.id.text);
        final LinearLayout backbutton = findViewById(R.id.backbutton);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        title.setText(intent.getStringExtra("title"));
        text.setText(Html.fromHtml(intent.getStringExtra("text"), Html.FROM_HTML_MODE_COMPACT));
        int drawableResourceId = getResources().getIdentifier(intent.getStringExtra("icon"), "drawable", getPackageName());

        if (drawableResourceId != 0) {
            icon.setImageResource(drawableResourceId);
        }
    }
}