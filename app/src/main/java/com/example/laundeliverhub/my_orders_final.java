package com.example.laundeliverhub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialTextInputPicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.firestore.WriteBatch;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import users_bottom_nav.users_view_orders;

public class my_orders_final extends AppCompatActivity {

    RatingBar StarRating;
    private FirebaseAuth mAuth;
    Button btn_rateUs ,btn_delete, btn_cancel;
    EditText text_reviews;
    TextInputLayout editext;
    TextView shop_name_txt, shop_location;
    TextView laundry_price, detergent_txt, softener_txt, bleach_txt, iron_fold, delivery_charge, total_amount, discription_txt, date_time ,payment_method,delivery_method;
    TextView fullname_txt, phonenumber_txt, homeaddress_txt,order_status;
    view_orders_model viewOrdersModels;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView addwash, bedshetsC_txt,blankCurt_txt;
    String shop_id ,doc_Id;
    TextView rate_message;
    uploadShop_model uploadShopModel;
    private static final String TAG = "my_orders_final";
    rating_model ratingModel;
    Uri uri ;
    float numrate = 1;
    float myrating ;
    float avgrate;

    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders_final);
        mAuth = FirebaseAuth.getInstance();
        ratingModel = new rating_model();

        editext = findViewById(R.id.editext);
        text_reviews = findViewById(R.id.text_reviews);
        StarRating = findViewById(R.id.StarRating);
        btn_delete= findViewById(R.id.btn_delete);
        btn_rateUs = findViewById(R.id.btn_rateUs);
        viewOrdersModels = (view_orders_model) getIntent().getSerializableExtra("viewOrdersModels");
        uploadShopModel = new uploadShop_model();
        shop_name_txt = findViewById(R.id.shop_name_txt);
        rate_message = findViewById(R.id.rate_message);

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
        btn_cancel = findViewById(R.id.btn_cancel);

        addwash = findViewById(R.id.addwash);
        bedshetsC_txt = findViewById(R.id.bedshetsC_txt);
        blankCurt_txt = findViewById(R.id.blankCurt_txt);

        //display from viewOrdersModel object model class serializable
        shop_name_txt.setText(viewOrdersModels.getShop_name());
        shop_location.setText(viewOrdersModels.getLocation_address());
        laundry_price.setText("₱ "+viewOrdersModels.getKilo_price());
        detergent_txt.setText("₱ "+viewOrdersModels.getDetergent_quantity());
        softener_txt.setText("₱ "+viewOrdersModels.getSoftener_quantity());
        bleach_txt.setText("₱ "+viewOrdersModels.getBleach_quantity());
        iron_fold.setText("₱ "+viewOrdersModels.getIron_on());
        total_amount.setText("₱ "+viewOrdersModels.getTotal_price());

        discription_txt.setText(viewOrdersModels.getDisriptions() +"");

        date_time.setText(viewOrdersModels.getMtimestamp() + " ");
        delivery_charge.setText("₱ "+viewOrdersModels.getDelivery_fee());
        fullname_txt.setText(viewOrdersModels.getFullname());
        phonenumber_txt.setText(viewOrdersModels.getPhonenumber());
        homeaddress_txt.setText(viewOrdersModels.getCurrentaddress());

        addwash.setText("₱ "+viewOrdersModels.getAddwash());
        bedshetsC_txt.setText("₱ "+viewOrdersModels.getBedsheets_Comforters());
        blankCurt_txt.setText("₱ "+viewOrdersModels.getBlankets_curtains());


        payment_method.setText(viewOrdersModels.getPayments());
        delivery_method.setText(viewOrdersModels.getPick_up_delivery());
        order_status.setText(viewOrdersModels.getStatus());

        shop_id = viewOrdersModels.getShop_id();
        doc_Id = viewOrdersModels.getIdd();

        //instantiate google
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){

           uri = acct.getPhotoUrl();
        }else{

                uri = null;
        }

        // rating bar number and message
        StarRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                int rating =(int) v;
                String message = " ";
                myrating = ratingBar.getRating();
                switch (rating){
                    case 0:
                        message = "Damn don't be to harsh! :( ";
                        break;
                    case 1:
                        message = "Sorry to hear that! :( ";
                        break;
                    case 2:
                        message = "Too bad not Good enough!";
                        break;
                    case 3:
                        message = "Good enough!";
                        break;
                    case 4:
                        message = "Great! Thank you!";
                        break;
                    case 5:
                        message = "Awesome! You are the best!";
                        break;
                }
                rate_message.setText(message);

            }
        });


        //display if the buttons based on users behavior
        String stats_cancel = "CANCELED";
        String stats_gone ="ORDERS COMPLETED";
        String stats ="ORDERS COMPLETE";
        String statss ="PENDING";
        if(statss.equals(viewOrdersModels.getStatus())){
            btn_cancel.setVisibility(View.VISIBLE);
        }
        if(stats_gone.equals(viewOrdersModels.getStatus())){
            StarRating.setVisibility(View.GONE);
            btn_delete.setVisibility(View.VISIBLE);
        }
        if(stats.equals(viewOrdersModels.getStatus())){
            btn_delete.setVisibility(View.VISIBLE);
            btn_rateUs.setVisibility(View.VISIBLE);
            StarRating.setVisibility(View.VISIBLE);
            editext.setVisibility(View.VISIBLE);
        }
        if(stats_cancel.equals(viewOrdersModels.getStatus())){
            btn_delete.setVisibility(View.VISIBLE);
        }
        btn_rateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //rating methods
                transactions();
                myrate();

            }

        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser userid = mAuth.getCurrentUser();
                db.collection("orders").document(userid.getUid()).collection("my orders").document(doc_Id).delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(my_orders_final.this,users_view_orders.class));
                                    finish();
                                }
                            }
                        });
            }

        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> stats = new HashMap<>();
                stats.put("status", "CANCELED");
                FirebaseUser userid = mAuth.getCurrentUser();
                db.collection("orders").document(userid.getUid()).collection("my orders").document(doc_Id).update(stats)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){

                                    Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(my_orders_final.this,users_view_orders.class));
                                    finish();
                                }
                            }
                        });
            }
        });



        // get the averaged rate in database
        DocumentReference docRef = db.collection("shop and products")
                .document("8CfwH7MOsXNJgBptaRHSZWifPAF3")
                .collection("my shops").document(shop_id);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
             uploadShopModel = documentSnapshot.
                     toObject(uploadShop_model.class);
                if (uploadShopModel != null) {

                    avgrate = uploadShopModel.getMyrate();
                }
            }
        });

    }

    // add rating to database using model class rating_model
    private void myrate() {
        String idd = db.collection("ratings").document().getId();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd/LLL/yyyy");
        String date = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm:ss a");
        String time = currentTime.format(calendar.getTime());
        String mytime = date + " " + time;
        //ternary operator exception handler
        final String imageuri = uri != null ? uri.toString() :null;

        String review = text_reviews.getText().toString().trim();
        ratingModel.setMyrate(myrating);
        ratingModel.setNumrate(numrate);
        ratingModel.setDisplay_name(viewOrdersModels.getFullname());
        ratingModel.setDatetime(mytime);
        ratingModel.setShopId(viewOrdersModels.getShop_id());
        ratingModel.setMy_image(imageuri);
        ratingModel.setMeidd(idd);
        ratingModel.setDiscription(review);
        ratingModel.setPhoneNumber(viewOrdersModels.getOwners_phonenumber());

        ratingModel = new rating_model(ratingModel.getMyrate(),ratingModel.getNumrate(),ratingModel.getShopId()
        ,ratingModel.getDatetime(),ratingModel.getDisplay_name(),ratingModel.getMy_image(),ratingModel.getMeidd()
        ,ratingModel.getDiscription(),ratingModel.getPhoneNumber());
        db.collection("ratings")
                .document(idd)
                .set(ratingModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    Log.d(TAG, "my ratings : addedd");
                    //Toast.makeText(my_orders_final.this, "added", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    private void transactions() {
        final DocumentReference DocRef =  db.collection("shop and products")
                .document("8CfwH7MOsXNJgBptaRHSZWifPAF3")
                .collection("my shops").document(shop_id);

        db.runTransaction(new Transaction.Function<Void>() {
                    @Override
                    public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                        DocumentSnapshot snapshot = transaction.get(DocRef);

                        // Note: this could be done without a transaction
                        // incrementing the number of rate
                        float newnum = snapshot.getLong("numrate") + numrate;
                        transaction.update(DocRef, "numrate", newnum);
                        //adding the new rate plus averaged rate
                        float ratings = snapshot.getLong("myrate");
                        transaction.update(DocRef, "myrate", calculate_total());


                        return null;
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override

                    public void onSuccess(Void aVoid) {
                        final DocumentReference DocRefs=  db.collection("orders")
                                .document(viewOrdersModels.getUser_idd())
                                .collection("my orders").document(viewOrdersModels.getIdd());
                        db.runTransaction(new Transaction.Function<Object>() {
                            @Nullable
                            @Override
                            public Object apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                                DocumentSnapshot documentSnapshot  = transaction.get(DocRefs);

                                String stats = documentSnapshot.getString("status" );
                                transaction.update(DocRefs, "status","ORDERS COMPLETED");
                                Log.d(TAG, "apply: stats orders completed");
                                return null;
                            }
                        });


                        Log.d(TAG, "Transaction success!");
                        Toast.makeText(getApplicationContext(), "Thanks for the rating", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(my_orders_final.this,users_view_orders.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Transaction failure.", e);
                    }
                });

    }



    public float calculate_total() {

        //new rate example 5 + existing rate sa database
        float total_rate = myrating + avgrate;
        return total_rate;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,users_view_orders.class));
        finish();
    }



}