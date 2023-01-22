package com.example.laundeliverhub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import admin_bottom_nav.admin_completed_orders;
import admin_bottom_nav.admin_customersOrders;

public class my_orders_final_admin extends AppCompatActivity {
    TextView shop_name_txt, shop_location;
    TextView laundry_price, detergent_txt, softener_txt, bleach_txt, iron_fold, delivery_charge, total_amount, discription_txt, date_time ,payment_method,delivery_method;
    TextView fullname_txt, phonenumber_txt, homeaddress_txt,order_status ,txtG;
    ImageView image_view;
    view_orders_model viewOrdersModels;
    Button btn_order_complete, btn_order_delete;
    private static final String TAG = "my_orders_final_admin";
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders_final_admin);

        btn_order_delete = findViewById(R.id.btn_order_delete);
        btn_order_complete = findViewById(R.id.btn_order_complete);
        viewOrdersModels = (view_orders_model) getIntent().getSerializableExtra("viewOrdersModels1");
        image_view = findViewById(R.id.image_view);
        shop_name_txt = findViewById(R.id.shop_name_txt);


        db = FirebaseFirestore.getInstance();
        fullname_txt = findViewById(R.id.fullname_txt);
        phonenumber_txt = findViewById(R.id.phonenumber_txt);
        homeaddress_txt = findViewById(R.id.homeaddress_txt);

        shop_location = findViewById(R.id.shop_location);
        laundry_price = findViewById(R.id.laundry_price);
        detergent_txt = findViewById(R.id.detergent_txt);
        softener_txt = findViewById(R.id.softener_txt);
        bleach_txt = findViewById(R.id.bleach_txt);
        iron_fold = findViewById(R.id.iron_fold);
        delivery_charge = findViewById(R.id.delivery_charge);
        total_amount = findViewById(R.id.total_amount);
        discription_txt = findViewById(R.id.discription_txt);
        date_time = findViewById(R.id.date_time);
        payment_method = findViewById(R.id.payment_method);
        delivery_method = findViewById(R.id.delivery_method);
        order_status = findViewById(R.id.order_status);
        txtG = findViewById(R.id.txtG);


        //display from viewOrdersModel object model class serializable

        Glide.with(getApplicationContext())
                .load(viewOrdersModels.getUri())
                .into(image_view);


        shop_name_txt.setText(viewOrdersModels.getShop_name());
        shop_location.setText(viewOrdersModels.getLocation_address());
        laundry_price.setText(viewOrdersModels.getKilo_price() + " ₱");
        detergent_txt.setText(viewOrdersModels.getDetergent_quantity() + " ₱");
        softener_txt.setText(viewOrdersModels.getSoftener_quantity() + " ₱");
        bleach_txt.setText(viewOrdersModels.getBleach_quantity() + " ₱");
        iron_fold.setText(viewOrdersModels.getIron_on() + " ₱");
        total_amount.setText(viewOrdersModels.getTotal_price() + " ₱");

        discription_txt.setText(viewOrdersModels.getDisriptions() +"");

        date_time.setText(viewOrdersModels.getMtimestamp() + " ");
        delivery_charge.setText(viewOrdersModels.getDelivery_fee() + " ₱");
        fullname_txt.setText(viewOrdersModels.getFullname());
        phonenumber_txt.setText(viewOrdersModels.getPhonenumber());
        homeaddress_txt.setText(viewOrdersModels.getCurrentaddress());


        payment_method.setText(viewOrdersModels.getPayments());
        delivery_method.setText(viewOrdersModels.getPick_up_delivery());
        order_status.setText(viewOrdersModels.getStatus());

        String orders_status1 = viewOrdersModels.getStatus1();
        String orders_status2 = viewOrdersModels.getStatus2();
        String orders_status3 = viewOrdersModels.getStatus3();
        String orders_status4 = viewOrdersModels.getStatus4();
        String orders_status5 = viewOrdersModels.getStatus5();





        String stats ="Gcash";
        if(stats.equals(viewOrdersModels.getPayments())){

            image_view.setVisibility(View.VISIBLE);
            txtG.setVisibility(View.VISIBLE);
        }


        btn_order_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewOrdersModels = new view_orders_model(viewOrdersModels.getPayments(), viewOrdersModels.getPick_up_delivery(), viewOrdersModels.getStatus(),
                        viewOrdersModels.getShop_name(), viewOrdersModels.getLocation_address(), viewOrdersModels.getFullname(), viewOrdersModels.getPhonenumber(),
                        viewOrdersModels.getCurrentaddress(), viewOrdersModels.getDisriptions(), viewOrdersModels.getMtimestamp(), viewOrdersModels.getKilo_price(),
                        viewOrdersModels.getDetergent_quantity(), viewOrdersModels.getSoftener_quantity(), viewOrdersModels.getBleach_quantity(),
                        viewOrdersModels.getIron_on(), viewOrdersModels.getDelivery_fee(), viewOrdersModels.getTotal_price(),viewOrdersModels.getIdd()
                        ,viewOrdersModels.getUser_idd(),viewOrdersModels.getShop_id(),viewOrdersModels.getDel_time()
                        ,viewOrdersModels.getAddwash(),viewOrdersModels.getBedsheets_Comforters()
                        ,viewOrdersModels.getBlankets_curtains(),viewOrdersModels.getOwners_phonenumber(), viewOrdersModels.getStatus1(),viewOrdersModels.getStatus2()
                ,viewOrdersModels.getStatus3(),viewOrdersModels.getStatus4(),viewOrdersModels.getStatus5(),viewOrdersModels.getCurrent_time1(),viewOrdersModels.getCurrent_time2()
                ,viewOrdersModels.getCurrent_time3(),viewOrdersModels.getCurrent_time4(),viewOrdersModels.getCurrent_time5());


                db.collection("completed orders").document(viewOrdersModels.getIdd())


                        //  db.collection(userid.getUid())
                        .set(viewOrdersModels).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Order Complete", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(my_orders_final_admin.this , admin_completed_orders.class));
                                finish();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "failed ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
        btn_order_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("orders").document(viewOrdersModels.getUser_idd())
                        .collection("my orders").document(viewOrdersModels.getIdd()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(my_orders_final_admin.this, "Deleted", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onComplete: "+task);
                        startActivity(new Intent(my_orders_final_admin.this , admin_completed_orders.class));
                        finish();
                    }
                });

            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(my_orders_final_admin.this , admin_customersOrders.class));
        finish();
    }

}