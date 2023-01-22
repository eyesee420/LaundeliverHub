package com.example.laundeliverhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import users_bottom_nav.users_home;

public class terms_and_conditions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, users_home.class));
        finish();
    }
}