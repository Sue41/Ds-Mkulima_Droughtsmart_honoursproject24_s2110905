package com.example.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.orbitalsonic.waterwave.WaterWaveView;

public class IrrigationControl extends AppCompatActivity {
    private ObjectAnimator animator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_irrigation_control);

        final LinearLayout back = findViewById(R.id.backbutton);
        final WaterWaveView watermeter = findViewById(R.id.waterWaveView);
        final Switch irrigationswitch = findViewById(R.id.irrigationswitch);
        watermeter.stopAnimation();

        // Initial progress
        final int[] currentProgress = {0};
        watermeter.setProgress(currentProgress[0]);

        final ObjectAnimator[] animator = {ObjectAnimator.ofInt(watermeter, "progress", currentProgress[0], 100)};
        animator[0].setDuration(1000000); // Duration for the animation

        irrigationswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Start animation from the last progress
                    watermeter.startAnimation();
                    animator[0] = ObjectAnimator.ofInt(watermeter, "progress", currentProgress[0], 100);
                    animator[0].setDuration(100000 * (100 - currentProgress[0]) / 100); // Adjust duration
                    animator[0].addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            if (watermeter.getProgress() == 100) {
                                irrigationswitch.setChecked(false);
                            }
                        }
                    });
                    animator[0].start();
                } else {
                    // Store the current progress when the switch is turned off
                    currentProgress[0] = watermeter.getProgress();
                    watermeter.stopAnimation();
                    animator[0].cancel();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
