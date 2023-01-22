package com.example.laundeliverhub;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import admin_bottom_nav.admin_myShop_adapter;
import users_bottom_nav.stores_adapter;
import users_bottom_nav.users_home;


public class shopOwner_home extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    shopOwner_adapter adapter;
    ratings_adapter adapters;
   // stores_adapter adapter;
    RecyclerView recycler_view;
    RecyclerView recycler_view_ratings;
    String userId;
    ImageView profile_btn;


    private static final String TAG = "shopOwner_home";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_owner_home);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        recycler_view = findViewById(R.id.recycler_view);
        recycler_view_ratings = findViewById(R.id.recycler_view_ratings);
        profile_btn = findViewById(R.id.profile_btn);

        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(shopOwner_home.this , shopOwner_profile.class));
            finish();
            }
        });

        userId =mAuth.getCurrentUser().getUid();
        DocumentReference docRef = db.collection("users").document(userId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Create_Users_Model create = documentSnapshot.toObject(Create_Users_Model.class);
             mymethod(create);
             starRate(create);
            }
        });

    }


    private void mymethod(Create_Users_Model create) {
        Log.d(TAG, "setUpRecyclerView: " +create.getPhonenumber());

      Query querys =db.collection("shops and products")
              .getFirestore().collectionGroup("my shops")
              .whereEqualTo("phoneNumber" ,create.getPhonenumber());

        Log.d(TAG, "mymethod: " + create.getPhonenumber());

        FirestoreRecyclerOptions<uploadShop_model> options =
                new FirestoreRecyclerOptions.Builder<uploadShop_model>()
                .setQuery(querys, uploadShop_model.class).build();


        adapter = new shopOwner_adapter(options);
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycler_view.setAdapter(adapter);



        adapter.startListening();

        Log.d(TAG, "mymethod: "+adapter);
    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }






    private void starRate(Create_Users_Model create) {

        Query query =db.collection("ratings")
                .whereEqualTo("phoneNumber",create.getPhonenumber());


        FirestoreRecyclerOptions<rating_model> options =
                new FirestoreRecyclerOptions.Builder<rating_model>()
                        .setQuery(query, rating_model.class).build();


        adapters = new ratings_adapter(options);
        recycler_view_ratings.setHasFixedSize(true);
        recycler_view_ratings.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycler_view_ratings.setAdapter(adapters);

        adapters.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }


    }




    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        shopOwner_home.super.onBackPressed();
                        SharedPreferences preferences =getSharedPreferences("login", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.apply();
                        Intent intent = new Intent(shopOwner_home.this,shopOwners_login_page.class);
                        startActivity(intent);
                        finish();

                    }
                }).create().show();

    }
}