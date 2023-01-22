package com.example.laundeliverhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgot_password_page extends AppCompatActivity {
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_page);

        EditText email_forgot_pass = findViewById(R.id.email_forgot_pass);
        Button reset_btn  =findViewById(R.id.reset_btn);

        auth = FirebaseAuth.getInstance();
        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetpassword();
            }

            private void resetpassword() {
                String email = email_forgot_pass.getText().toString().trim();

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    email_forgot_pass.setError("Please Provide Valid Email!");
                    email_forgot_pass.requestFocus();
                    return;
                }
                if (email.isEmpty()) {
                    email_forgot_pass.setError("Email is Required!");
                    email_forgot_pass.requestFocus();
                    return;
                }


                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(forgot_password_page.this, "check your email", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(forgot_password_page.this, MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(forgot_password_page.this, "try again! Something Happened", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}