package com.example.laundeliverhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import admin_bottom_nav.admin_home;

public class shopOwner_profile extends AppCompatActivity {
    FirebaseFirestore db;
    String userId;
    FirebaseAuth mAuth;
    Button update_btn;
    EditText txt_full_name, txt_phone_number, txt_current_address;
    Create_Users_Model create_users;
    Button log_out_btn;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser userid = mAuth.getCurrentUser();
        DocumentReference docRef = db.collection("users").document(userid.getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Create_Users_Model create = documentSnapshot.toObject(Create_Users_Model.class);
                if (create != null) {
                    txt_full_name = findViewById(R.id.txt_full_name);
                    txt_phone_number = findViewById(R.id.txt_phone_number);
                    txt_current_address = findViewById(R.id.txt_current_address);

                    txt_full_name.setText(create.getFullname());
                    txt_phone_number.setText(create.getPhonenumber());
                    txt_current_address.setText(create.getCurrentaddress());


                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_owner_profile);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        update_btn = findViewById(R.id.update_btn);
        log_out_btn = findViewById(R.id.log_out_btn);
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create update method
                update_profile();
            }});

        log_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create logout method
                logout();
            }
        });

    }

    private void logout() {
        mAuth.getInstance().signOut();
//        SharedPreferences preferences =getSharedPreferences("admin_login", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.clear();
//        editor.apply();
//        startActivity(new Intent(getApplicationContext(),MainActivity.class));
//        finish();
    }

    private void update_profile() {

        String fullname = txt_full_name.getText().toString().trim();
        String phonenumber = txt_phone_number.getText().toString().trim();
        String currentaddress = txt_current_address.getText().toString().trim();


        Map<String, Object> user = new HashMap<>();
        user.put("currentaddress",currentaddress) ;
        user.put("fullname", fullname);
        user.put("phonenumber", phonenumber);



        //set the user id
        userId =mAuth.getCurrentUser().getUid();
        create_users = new Create_Users_Model(fullname, phonenumber, currentaddress);
        db.collection("users").document(userId)
                .update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Profile Updated ", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "failed ", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(shopOwner_profile.this , shopOwner_home.class));
        finish();
    }
}