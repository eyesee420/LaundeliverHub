package com.example.laundeliverhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;


public class splash_screen extends AppCompatActivity {


    LottieAnimationView watersplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        watersplash = findViewById(R.id.watersplash);

     new Handler().postDelayed(new Runnable() {
         @Override
         public void run() {
             Intent intent = new Intent(splash_screen.this , MainActivity.class);
             startActivity(intent);
             finish();
         }
     },2000);

//
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                Intent intent = new Intent(splash_screen.this , MainActivity.class);
//                startActivity(intent);
//            }
//        }, 5000);
    }
}