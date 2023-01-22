package com.example.laundeliverhub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeDrawable;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.WriteResult;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import admin_bottom_nav.admin_addProducts;
import pl.droidsonroids.gif.GifImageView;
import users_bottom_nav.users_order_details;
import users_bottom_nav.users_stores;

public class add_cart extends AppCompatActivity {

    int checkedgroup;
    GifImageView dialog_button;
    private FirebaseAuth mAuth;
    uploadShop_model uploadShopModel;
    addOns_Prices addOnsPrices;
    Button add_cart , btn_back;
    CheckBox radio_iron ,radio_addwash,radio_besheets_comfort,radio_blankets_curtains;
    ImageView shop_imageview;
    TextView shop_name, washdry1, washdry2, washdry3, washdry4, washdry5;
    TextView laundry_price, detergent_txt, softener_txt, bleach_txt, iron_fold_txt, total_amount;
    EditText discription_edt_txt;
    RadioGroup radioGroup_kilos;
    TextView addwash, bedshetsC_txt,blankCurt_txt;
    RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5;
    RatingBar StarRatings;
    TextView text_average_rate ;
    RecyclerView recycler_view;
    private ratings_adapter adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String idd;
    private static final String TAG = "add_cart";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        recycler_view = findViewById(R.id.recycler_view);


        btn_back = findViewById(R.id.btn_back);

        mAuth = FirebaseAuth.getInstance();
        dialog_button = findViewById(R.id.dialog_button);
        uploadShopModel = (uploadShop_model) getIntent().getSerializableExtra("uploadShopModel");
        shop_name = findViewById(R.id.shopname);
        washdry1 = findViewById(R.id.washdry_kilo1);
        washdry2 = findViewById(R.id.washdry_kilo2);
        washdry3 = findViewById(R.id.washdry_kilo3);
        washdry4 = findViewById(R.id.washdry_kilo4);
        washdry5 = findViewById(R.id.washdry_kilo5);
        shop_imageview = findViewById(R.id.shop_imageview);

        radioButton1 = findViewById(R.id.radio_11);

        radioButton2 = findViewById(R.id.radio_22);
        radioButton3 = findViewById(R.id.radio_3);
        radioButton4 = findViewById(R.id.radio_4);
        radioButton5 = findViewById(R.id.radio_5);
        radioGroup_kilos = findViewById(R.id.radio_group_kilos);


        radio_blankets_curtains = findViewById(R.id.radio_blankets_curtains);
        radio_besheets_comfort = findViewById(R.id.radio_besheets_comfort);
        radio_addwash = findViewById(R.id.radio_addwash);
        radio_iron = findViewById(R.id.radio_iron);
        detergent_txt = findViewById(R.id.detergent_txt);
        softener_txt = findViewById(R.id.softener_txt);
        bleach_txt = findViewById(R.id.bleach_txt);
        laundry_price = findViewById(R.id.laundry_price);
        iron_fold_txt = findViewById(R.id.iron_fold_txt);
        total_amount = findViewById(R.id.total_amount);
        add_cart = findViewById(R.id.add_cart);
        discription_edt_txt = findViewById(R.id.discription_edt_txt);

        addwash = findViewById(R.id.addwash);
        bedshetsC_txt = findViewById(R.id.bedshetsC_txt);
        blankCurt_txt = findViewById(R.id.blankCurt_txt);
        //display laundry prices
        shop_name.setText(uploadShopModel.getShop_name());
        //location_address.setText(uploadShopModel.getLocation_address());

        Glide.with(getApplicationContext())
                .load(uploadShopModel.getUri())
                .into(shop_imageview);

        washdry1.setText("₱ "+uploadShopModel.getWashdry1());
        washdry2.setText("₱ "+uploadShopModel.getWashdry2());
        washdry3.setText("₱ "+uploadShopModel.getWashdry3());
        washdry4.setText("₱ "+uploadShopModel.getWashdry4());
        washdry5.setText("₱ "+uploadShopModel.getWashdry5());
        radio_iron.setText("Iron and fold  ₱ "+uploadShopModel.getIron_fold());
        radio_addwash.setText("Add Wash  30mins  ₱ "+uploadShopModel.getAddwash());
        radio_besheets_comfort.setText("Blankets & Curtains  ₱ "+uploadShopModel.getBedsheets_Comforters());
        radio_blankets_curtains.setText("Bedsheets & Comforters  ₱ "+uploadShopModel.getBlankets_curtains());
        TextView textimp =(TextView) findViewById(R.id.textimp);

        String text = "Note: Please make sure to match the weight of your laundry to the Loads(Kilo) that is indicated below.";

        SpannableString s1 =new SpannableString(text);
        StyleSpan bold = new StyleSpan(Typeface.BOLD);
        s1.setSpan(bold,0 ,5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textimp.setText(s1);


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(add_cart.this, users_stores.class);
                startActivity(intent);
                finish();
            }
        });


        DocumentReference docRef = db.collection("shop and products")
                .document("8CfwH7MOsXNJgBptaRHSZWifPAF3").collection("my shops")
                .document(uploadShopModel.getIdd());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                uploadShop_model rates = documentSnapshot.toObject(uploadShop_model.class);
                if (rates != null) {
                    StarRatings = findViewById(R.id.StarRatings);
                    text_average_rate = findViewById(R.id.text_average_rate);
                    //num_rate = findViewById(R.id.num_rate);
                
                    float average = rates.getMyrate()/rates.getNumrate();
                    StarRatings.setRating(average);   
                    
                    Log.d(TAG, "onSuccess: " + average);
                    text_average_rate.setText("( "+rates.getMyrate()+" /"+rates.getNumrate()+" )");
                    //num_rate.setText("/"+rates.getNumrate());

                }
            }
        });

        dialog_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(add_cart.this)
                        .setTitle("Information")
                        .setMessage("Select Services and Prices we also offer addons  enjoy shopping")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        }).create().show();

            }

        });

        radioGroup_kilos.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                checkedgroup = checkedId;

                switch (checkedId) {
                    case R.id.radio_11:
                        if (radioButton1.isEnabled())
                            addOnsPrices.setKilo_price(uploadShopModel.getWashdry1());

                        break;
                    case R.id.radio_22:
                        if (radioButton2.isChecked())
                            addOnsPrices.setKilo_price(uploadShopModel.getWashdry2());

                        break;
                    case R.id.radio_3:
                        if (radioButton3.isChecked())
                            addOnsPrices.setKilo_price(uploadShopModel.getWashdry3());

                        break;
                    case R.id.radio_4:
                        if (radioButton4.isChecked())
                            addOnsPrices.setKilo_price(uploadShopModel.getWashdry4());

                        break;
                    case R.id.radio_5:
                        if (radioButton5.isChecked())
                            addOnsPrices.setKilo_price(uploadShopModel.getWashdry5());

                        break;


                }

                laundry_price.setText("₱ "+addOnsPrices.getKilo_price());
                total_amount.setText("₱ "+calculate_total());

            }
        });
        radio_iron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radio_iron.isChecked()) {
                    addOnsPrices.setIron_on(uploadShopModel.getIron_fold());
                    iron_fold_txt.setText("₱ "+addOnsPrices.getIron_on());
                    total_amount.setText("₱ "+calculate_total());
                } else {
                    addOnsPrices.setIron_on(0);
                    iron_fold_txt.setText("₱ "+addOnsPrices.getIron_on());
                    total_amount.setText("₱ "+calculate_total());
                }
            }
        });

        radio_addwash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radio_addwash.isChecked()) {
                    addOnsPrices.setAddwash(uploadShopModel.getAddwash());
                    addwash.setText("₱ "+addOnsPrices.getAddwash());
                    total_amount.setText("₱ "+calculate_total());
                } else {
                    addOnsPrices.setAddwash(0);
                    addwash.setText("₱ "+addOnsPrices.getAddwash());
                    total_amount.setText("₱ "+calculate_total());
                }
            }
        });

        radio_blankets_curtains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radio_blankets_curtains.isChecked()) {
                    addOnsPrices.setBlankets_curtains(uploadShopModel.getBlankets_curtains());
                    blankCurt_txt.setText("₱ "+addOnsPrices.getBlankets_curtains());
                    total_amount.setText("₱ "+calculate_total());
                } else {
                    addOnsPrices.setBlankets_curtains(0);
                    blankCurt_txt.setText("₱ "+addOnsPrices.getBlankets_curtains());
                    total_amount.setText("₱ "+calculate_total());
                }
            }
        });

        radio_besheets_comfort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radio_besheets_comfort.isChecked()) {
                    addOnsPrices.setBedsheets_Comforters(uploadShopModel.getBedsheets_Comforters());
                    bedshetsC_txt.setText("₱ "+addOnsPrices.getBedsheets_Comforters());
                    total_amount.setText("₱ "+calculate_total());
                } else {
                    addOnsPrices.setBedsheets_Comforters(0);
                    bedshetsC_txt.setText("₱ "+addOnsPrices.getBedsheets_Comforters());
                    total_amount.setText("₱ "+calculate_total());
                }
            }
        });

        addOnsPrices = new addOns_Prices();
        ElegantNumberButton button1 = (ElegantNumberButton) findViewById(R.id.number_btn1);
        button1.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = Integer.parseInt(button1.getNumber());
                if (num <= 10) {
                    addOnsPrices.setDetergent_quantity(num * 20);
                }
                detergent_txt.setText("₱ "+addOnsPrices.getDetergent_quantity());
                total_amount.setText("₱ "+calculate_total());

            }
        });
        ElegantNumberButton button2 = (ElegantNumberButton) findViewById(R.id.number_btn2);
        button2.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = Integer.parseInt(button2.getNumber());
                if (num <= 10) {
                    addOnsPrices.setSoftener_quantity(num * 20);
                }
                softener_txt.setText("₱ "+addOnsPrices.getSoftener_quantity());
                total_amount.setText("₱ "+calculate_total());

            }
        });
        ElegantNumberButton button3 = (ElegantNumberButton) findViewById(R.id.number_btn3);
        button3.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = Integer.parseInt(button3.getNumber());
                if (num <= 10) {
                    addOnsPrices.setBleach_quantity(num * 20);
                }
                bleach_txt.setText("₱ "+addOnsPrices.getBleach_quantity());
                total_amount.setText("₱ "+calculate_total());

            }
        });


        add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkedgroup <= 0) {
                    Toast.makeText(add_cart.this, "Please select a Service", Toast.LENGTH_SHORT).show();
                    laundry_price.requestFocus();
                    return;
                }


                addOnsPrices.setTotal_price(calculate_total());
                String discription_edt_txts = discription_edt_txt.getText().toString().trim();

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat currentDate = new SimpleDateFormat("dd/LLL/yyyy");
                String date = currentDate.format(calendar.getTime());
                SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm:ss a");
                String time = currentTime.format(calendar.getTime());
                String mytime = date + " " + time;

                addOnsPrices.setmTimestamp(mytime);

                String location = uploadShopModel.getLocation_address();
                addOnsPrices.setLocation_address(location);
                String shopname = uploadShopModel.getShop_name();
                addOnsPrices.setShop_name(shopname);

                addOnsPrices.setUri(uploadShopModel.getUri());
                addOnsPrices.setShop_id(uploadShopModel.getIdd());
                addOnsPrices.setOwners_phonenumber(uploadShopModel.getPhoneNumber());


                addOns_Prices orders = new addOns_Prices(addOnsPrices.getUri(), addOnsPrices.getKilo_price(), addOnsPrices.getDetergent_quantity(), addOnsPrices.getSoftener_quantity(),
                        addOnsPrices.getBleach_quantity(), addOnsPrices.getIron_on(), addOnsPrices.getTotal_price(), discription_edt_txts
                        , addOnsPrices.getShop_name(), addOnsPrices.getLocation_address(), addOnsPrices.getmTimestamp(),addOnsPrices.getShop_id()
                ,addOnsPrices.getAddwash(),addOnsPrices.getBlankets_curtains(),addOnsPrices.getBedsheets_Comforters(),addOnsPrices.getOwners_phonenumber());

                //set the user id

                FirebaseUser userid = mAuth.getCurrentUser();
                db.collection("cart").document(userid.getUid()).collection("my cart")
                        .add(orders).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {

                                Toast.makeText(getApplicationContext(), "cart added", Toast.LENGTH_SHORT).show();

                                Intent i = new Intent();
                                i.setClassName("com.example.laundeliverhub", "users_bottom_nav.users_order_details");
                                startActivity(i);
                                finish();

                            }


                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(add_cart.this, "failed to add cart! " + e, Toast.LENGTH_SHORT).show();
                                 finish();
                            }
                        });

            }
        });



        setUpRecyclerView();

        String shopid = uploadShopModel.getIdd();

        db.collection("ratings").whereEqualTo("shopId",shopid)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                int count = value.size();
                if (count == 0) {
                  recycler_view.setVisibility(View.GONE);
                }
                Log.d(TAG, "onEvent:  " + count);
            }
        });

    }



    private void setUpRecyclerView() {
        Query query = db.collection("ratings").whereEqualTo("shopId",uploadShopModel.getIdd());
        FirestoreRecyclerOptions<rating_model> options = new FirestoreRecyclerOptions.Builder<rating_model>()
                .setQuery(query, rating_model.class)
                .build();

        adapter = new ratings_adapter(options);
        recycler_view.setHasFixedSize(false);
        recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycler_view.setAdapter(adapter);

    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    public int Add_ons() {
        int add_ons = addOnsPrices.getDetergent_quantity() + addOnsPrices.getSoftener_quantity()
                + addOnsPrices.getBleach_quantity();
        return add_ons;
    }

    public int calculate_total() {

        int total_price = Add_ons() + addOnsPrices.getIron_on() + addOnsPrices.getAddwash()
                + addOnsPrices.getBlankets_curtains() + addOnsPrices.getBedsheets_Comforters()
                + addOnsPrices.getKilo_price();

        return total_price;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(add_cart.this, users_stores.class);
        startActivity(intent);
        finish();
    }
}