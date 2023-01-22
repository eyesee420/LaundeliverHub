package com.example.laundeliverhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import admin_bottom_nav.admin_myShop;

public class admin_myShop_update extends AppCompatActivity {
    Uri uri;
    ImageView shop_imageview;
    EditText shop_name_edittxt, laundry_location_edittxt ,laundry_phoneNumber;
    EditText washdry_kilo1, washdry_kilo2, washdry_kilo3, washdry_kilo4, washdry_kilo5;
    EditText iron_fold , addwash,bedshetsC_txt,blankCurt_txt;
    Button btn_update;
    FirebaseFirestore db;
     FirebaseAuth mAuth;
    uploadShop_model uploadShopModel;
    ProgressBar progressBar;

    private static final String TAG = "admin_myShop_update";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_my_shop_update);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        shop_imageview = findViewById(R.id.shop_imageview);
        shop_name_edittxt = findViewById(R.id.shop_name_edittxt);
        laundry_location_edittxt = findViewById(R.id.laundry_location_edittxt);
        laundry_phoneNumber = findViewById(R.id.laundry_phoneNumber);
        washdry_kilo1 = findViewById(R.id.washdry_kilo1);
        washdry_kilo2 = findViewById(R.id.washdry_kilo2);
        washdry_kilo3 = findViewById(R.id.washdry_kilo3);
        washdry_kilo4 = findViewById(R.id.washdry_kilo4);
        washdry_kilo5 = findViewById(R.id.washdry_kilo5);
        iron_fold = findViewById(R.id.iron_fold);
        addwash = findViewById(R.id.addwash);
        bedshetsC_txt = findViewById(R.id.bedshetsC_txt);
        blankCurt_txt = findViewById(R.id.blankCurt_txt);

        btn_update = findViewById(R.id.btn_update);
        uploadShopModel = (uploadShop_model) getIntent().getSerializableExtra("uploadShopModel");


        //declair and initialize progressbar
        progressBar = (ProgressBar) findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);


        //display from addonprices object model class serializable

        Glide.with(getApplicationContext())
                .load(uploadShopModel.getUri())
                .into(shop_imageview);


        shop_name_edittxt.setText(uploadShopModel.getShop_name());
        laundry_location_edittxt.setText(uploadShopModel.getLocation_address());
        laundry_phoneNumber.setText(uploadShopModel.getPhoneNumber());
        washdry_kilo1.setText(String.valueOf(uploadShopModel.getWashdry1()));
        washdry_kilo2.setText(String.valueOf(uploadShopModel.getWashdry2()));
        washdry_kilo3.setText(String.valueOf(uploadShopModel.getWashdry3()));
        washdry_kilo4.setText(String.valueOf(uploadShopModel.getWashdry4()));
        washdry_kilo5.setText(String.valueOf(uploadShopModel.getWashdry5()));
        iron_fold.setText(String.valueOf(uploadShopModel.getIron_fold()));
        addwash.setText(String.valueOf(uploadShopModel.getAddwash()));
        bedshetsC_txt.setText(String.valueOf(uploadShopModel.getBedsheets_Comforters()));
        blankCurt_txt.setText(String.valueOf(uploadShopModel.getBlankets_curtains()));



        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pics = uploadShopModel.getUri();
                String shopname = shop_name_edittxt.getText().toString().trim();
                String shoploc = laundry_location_edittxt.getText().toString().trim();
                String phoneNumber = laundry_phoneNumber.getText().toString().trim();
                String activestats = uploadShopModel.getActive_status();
                int w1 = Integer.parseInt(washdry_kilo1.getText().toString());
                int w2 = Integer.parseInt(washdry_kilo2.getText().toString());
                int w3 = Integer.parseInt(washdry_kilo3.getText().toString());
                int w4 = Integer.parseInt(washdry_kilo4.getText().toString());
                int w5 = Integer.parseInt(washdry_kilo5.getText().toString());
                int ironfold = Integer.parseInt(iron_fold.getText().toString());
                int addwashs = Integer.parseInt(addwash.getText().toString());
                int bedshetsC_txts = Integer.parseInt(bedshetsC_txt.getText().toString());
                int blankCurt_txts = Integer.parseInt(blankCurt_txt.getText().toString());


                String shop_id = uploadShopModel.getIdd();
                float star_rate =uploadShopModel.getMyrate();
                int num_rate = uploadShopModel.getNumrate();


                uploadShop_model uploadShopModel = new uploadShop_model(pics ,shopname,shoploc,phoneNumber,activestats,
                        w1,w2,w3,w4,w5,shop_id,star_rate,num_rate,ironfold,addwashs,bedshetsC_txts,blankCurt_txts);


                FirebaseUser userid = mAuth.getCurrentUser();
                db.collection("shop and products").document(userid.getUid()).collection("my shops")
                        .document(shop_id)
                        .set(uploadShopModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    shop_name_edittxt.setText("");
                                    laundry_location_edittxt.setText("");
                                    laundry_phoneNumber.setText("");
                                    washdry_kilo1.setText("");
                                    washdry_kilo2.setText("");
                                    washdry_kilo3.setText("");
                                    washdry_kilo4.setText("");
                                    washdry_kilo5.setText("");

//
//                                    Map<String, String> state = new HashMap<>();
//                                    state.put("title", shoploc);
//
//                                    String title = state.get("title").toLowerCase();
//                                    // Create an array list to store the substrings
//                                    ArrayList<String> array = new ArrayList<>();
//
//                                    for (int i = 1; i < title.length() + 1; i++) {
//                                        // Add the current substring to the array
//                                        array.add(title.substring(0, i));
//                                    }
//
//                                    Map<String, Object> data = new HashMap<>();
//                                //    data.put("title", state.get("title"));
//                                    data.put("keye", array);
//
//                                    FirebaseUser userid = mAuth.getCurrentUser();
//                                    db.collection("shop and products")
//                                            .document(userid.getUid()).collection("my shops")
//                                            .document(shop_id)
//                                            .update(data)
//                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                @Override
//                                                public void onSuccess(Void aVoid) {
//                                                    // Document updated successfully
//                                                    Log.d(TAG, "Document updated successfully");
//                                                }
//                                            })
//                                            .addOnFailureListener(new OnFailureListener() {
//                                                @Override
//                                                public void onFailure(@NonNull Exception e) {
//                                                    // Error updating document
//                                                    Log.w(TAG, "Error updating document", e);
//                                                }
//                                            });


                                    Toast.makeText(admin_myShop_update.this, "shop updated", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), admin_myShop.class));
                                    finish();
                                }else {
                                    Toast.makeText(admin_myShop_update.this, "shop updated faild", Toast.LENGTH_SHORT).show();
                                }

                            }

                        });


            }
        });


    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(admin_myShop_update.this, admin_myShop.class));
        finish();
    }


}