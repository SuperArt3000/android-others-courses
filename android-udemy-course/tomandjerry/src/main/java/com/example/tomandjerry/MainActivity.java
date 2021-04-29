package com.example.tomandjerry;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    boolean isTomVisible = true;
    ImageView tomImageView;
    ImageView jerryImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tomImageView = (ImageView) findViewById(R.id.tomImageView);
        jerryImageView = (ImageView) findViewById(R.id.jerryImageView);
    }

    public void eraseTom(View view) {

        if (isTomVisible) {
            tomImageView.animate().alpha(0).rotation(tomImageView.getRotation() + 3600).scaleY(0).scaleX(0).setDuration(3000);
            jerryImageView.animate().alpha(1).rotation(jerryImageView.getRotation() + 3600).scaleY(1).scaleX(1).setDuration(3000);
            isTomVisible = false;
        } else {
            tomImageView.animate().alpha(1).rotation(tomImageView.getRotation() + 3600).scaleY(1).scaleX(1).setDuration(3000);
            jerryImageView.animate().alpha(0).rotation(jerryImageView.getRotation() + 3600).scaleY(0).scaleX(0).setDuration(3000);
            isTomVisible = true;
        }
    }
}