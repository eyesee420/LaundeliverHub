package com.example.laundeliverhub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.protobuf.StringValue;

import org.checkerframework.checker.units.qual.C;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import admin_bottom_nav.admin_addProducts;
import pl.droidsonroids.gif.GifImageView;
import users_bottom_nav.users_home;
import users_bottom_nav.users_order_details;
import users_bottom_nav.users_view_orders;

public class confirm_orders extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    GifImageView btn_info;
    StorageReference reference = FirebaseStorage.getInstance().getReference();
    ProgressBar progressBar;
    ImagePicker imagePicker;
    Uri uri;
    ImageView shop_imageview;
    TextView shop_name_txt, shop_location;
    TextView laundry_price, detergent_txt, softener_txt, bleach_txt, iron_fold, delivery_charge, total_amount, discription_txt, date_time;
    TextView fullname_txt, phonenumber_txt, homeaddress_txt;
    TextView addwash, bedshetsC_txt,blankCurt_txt;
    addOns_Prices addOnsPrices;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    RadioGroup radio_group_Delivery_method;
    RadioButton radio_pickup, radio_delivery;
    view_orders_model viewOrdersModel;
    Create_Users_Model users_model;
    uploadShop_model uploadShopModel;
    String phone,currentAdd;
    public String pp ="";

    private static final String TAG = "confirm_orders";

    Button btn_cash, btn_gcash, btn_back;
    int checkedgroup;
    TextView admin_phonenumber, admin_gcashName;


    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            fullname_txt = findViewById(R.id.fullname_txt);
            fullname_txt.setText(personName);

        }

        FirebaseFirestore.getInstance().collection("users").document("8CfwH7MOsXNJgBptaRHSZWifPAF3")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        Create_Users_Model create = value.toObject(Create_Users_Model.class);
                        if (create != null) {
                            admin_phonenumber = findViewById(R.id.admin_phonenumber);
                            admin_gcashName = findViewById(R.id.admin_gcashName);

                            admin_phonenumber.setText(create.getPhonenumber());
                            admin_gcashName.setText(create.getFullname());
                        }
                    }
                });


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_orders);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btn_back = findViewById(R.id.btn_back);
        btn_info = findViewById(R.id.btn_info);
        progressBar = (ProgressBar) findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);

        addOnsPrices = (addOns_Prices) getIntent().getSerializableExtra("addOnsPrices");

        shop_imageview = findViewById(R.id.shop_imageview);
        btn_gcash = findViewById(R.id.btn_gcash);
        btn_cash = findViewById(R.id.btn_cash);
        radio_pickup = findViewById(R.id.radio_pickup);
        radio_delivery = findViewById(R.id.radio_delivery);
        radio_group_Delivery_method = findViewById(R.id.radio_group_Delivery_method);

        shop_name_txt = findViewById(R.id.shop_name_txt);
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
        admin_phonenumber = findViewById(R.id.admin_phonenumber);
        admin_gcashName = findViewById(R.id.admin_gcashName);

        addwash = findViewById(R.id.addwash);
        bedshetsC_txt = findViewById(R.id.bedshetsC_txt);
        blankCurt_txt = findViewById(R.id.blankCurt_txt);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd/LLL/yyyy");
        String date = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm:ss a");
        String time = currentTime.format(calendar.getTime());
        String mytime = date + " " + time;


        //display from addonprices object model class serializable
        shop_name_txt.setText(addOnsPrices.getShop_name());
        shop_location.setText(addOnsPrices.getLocation_address());
        laundry_price.setText("₱ "+addOnsPrices.getKilo_price());
        detergent_txt.setText("₱ "+addOnsPrices.getDetergent_quantity());
        softener_txt.setText("₱ "+addOnsPrices.getSoftener_quantity());
        bleach_txt.setText("₱ "+addOnsPrices.getBleach_quantity());
        iron_fold.setText("₱ "+addOnsPrices.getIron_on());
        addwash.setText("₱ "+addOnsPrices.getAddwash());
        bedshetsC_txt.setText("₱ "+addOnsPrices.getBedsheets_Comforters());
        blankCurt_txt.setText("₱ "+addOnsPrices.getBlankets_curtains());
        total_amount.setText("₱ "+addOnsPrices.getTotal_price());
        discription_txt.setText(addOnsPrices.getDiscriptions());
        date_time.setText(mytime + " ");


        Log.d(TAG, "onCreate: ");

        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

        new AlertDialog.Builder(confirm_orders.this)
                        .setTitle("Information")
                        .setMessage("Note: Please update your profile and your exact location for detailed Delivery thank you.")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        }).create().show();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(confirm_orders.this, users_order_details.class);
                startActivity(intent);
                finish();
            }
        });



        /// fetch data from user id
        FirebaseUser userid = mAuth.getCurrentUser();
        DocumentReference docRef = db.collection("users").document(userid.getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                users_model = documentSnapshot.toObject(Create_Users_Model.class);
                if(users_model == null){
                    Toast.makeText(confirm_orders.this, "please update profile", Toast.LENGTH_SHORT).show();
                   startActivity(new Intent(confirm_orders.this, profile_page.class));
                    finish();
                }
                if (users_model != null) {
                    fullname_txt = findViewById(R.id.fullname_txt);
                    phonenumber_txt = findViewById(R.id.phonenumber_txt);
                    homeaddress_txt = findViewById(R.id.homeaddress_txt);

                    phone = users_model.getPhonenumber().trim();
                    currentAdd = users_model.getCurrentaddress();

                    fullname_txt.setText(users_model.getFullname());
                    phonenumber_txt.setText(users_model.getPhonenumber().trim());
                    homeaddress_txt.setText(users_model.getCurrentaddress().trim());


                    if(phone.isEmpty()){
                        Toast.makeText(confirm_orders.this, "Please edit your profile!", Toast.LENGTH_SHORT).show();
                        phonenumber_txt.setError("Needed");;
                        return;
                    }
                    if(currentAdd.isEmpty()){
                        Toast.makeText(confirm_orders.this, "Please edit your profile!", Toast.LENGTH_SHORT).show();
                        homeaddress_txt.setError("Needed");
                        return;
                    }
                    if(currentAdd.equals(pp)&& phone.equals(pp) ){
                        Toast.makeText(confirm_orders.this, "Please edit your profile!", Toast.LENGTH_SHORT).show();
                        homeaddress_txt.setError("Needed");
                        phonenumber_txt.setError("Needed");
                        return;
                    }
                    if(currentAdd.equals(pp)){
                        Toast.makeText(confirm_orders.this, "Please edit your profile!", Toast.LENGTH_SHORT).show();
                        homeaddress_txt.setError("Needed");
                        return;
                    }
                    if(phone.equals(pp)){
                        Toast.makeText(confirm_orders.this, "Please edit your profile!", Toast.LENGTH_SHORT).show();
                        phonenumber_txt.setError("Needed");
                        return;
                    }

                }
            }
        });

        viewOrdersModel = new view_orders_model();
        radio_group_Delivery_method.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                checkedgroup = checkedId;
                switch (checkedId) {
                    case R.id.radio_pickup:
                        if (radio_pickup.isEnabled())
                            viewOrdersModel.setDelivery_fee(50);
                        viewOrdersModel.setPick_up_delivery("pick up");
                        break;

                    case R.id.radio_delivery:
                        if (radio_delivery.isChecked())
                            viewOrdersModel.setDelivery_fee(100);
                        viewOrdersModel.setPick_up_delivery("pick up and delivery");
                        break;


                }


                delivery_charge.setText("₱ " + viewOrdersModel.getDelivery_fee());
               // total_amount.setText("₱ " + addOnsPrices.getTotal_price() + viewOrdersModel.getDelivery_fee());
                total_amount.setText("₱ "+calculate_total());
            }
        });


        viewOrdersModel.setMtimestamp(mytime);

        uploadShopModel = new uploadShop_model();
        btn_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(phone.isEmpty()){
                    Toast.makeText(confirm_orders.this, "Please edit your profile!", Toast.LENGTH_SHORT).show();
                    phonenumber_txt.setError("Needed");;
                    return;
                }
                if(currentAdd.isEmpty()){
                    Toast.makeText(confirm_orders.this, "Please edit your profile!", Toast.LENGTH_SHORT).show();
                    homeaddress_txt.setError("Needed");
                    return;
                }
                if(currentAdd.equals(pp)&& phone.equals(pp) ){
                    Toast.makeText(confirm_orders.this, "Please edit your profile!", Toast.LENGTH_SHORT).show();
                    homeaddress_txt.setError("Needed");
                    phonenumber_txt.setError("Needed");
                    return;
                }
                if(currentAdd.equals(pp)){
                    Toast.makeText(confirm_orders.this, "Please edit your profile!", Toast.LENGTH_SHORT).show();
                    homeaddress_txt.setError("Needed");
                    return;
                }
                if(phone.equals(pp)){
                    Toast.makeText(confirm_orders.this, "Please edit your profile!", Toast.LENGTH_SHORT).show();
                    phonenumber_txt.setError("Needed");
                    return;
                }

                if (checkedgroup <= 0) {
                    Toast.makeText(confirm_orders.this, "Please select delivery method", Toast.LENGTH_SHORT).show();

                    delivery_charge.requestFocus();
                    return;
                }
                viewOrdersModel.setPayments("cash on delivery");
                viewOrdersModel.setStatus("PENDING");
                viewOrdersModel.setStatus1("");
                viewOrdersModel.setStatus2("");
                viewOrdersModel.setStatus3("");
                viewOrdersModel.setStatus4("");
                viewOrdersModel.setStatus5("");


                String shop_id =db.collection("my orders").document().getId();

                viewOrdersModel.setShop_name(addOnsPrices.getShop_name());
                viewOrdersModel.setLocation_address(addOnsPrices.getLocation_address());
                viewOrdersModel.setFullname(users_model.getFullname());
                viewOrdersModel.setPhonenumber(users_model.getPhonenumber());
                viewOrdersModel.setCurrentaddress(users_model.getCurrentaddress());
                viewOrdersModel.setDisriptions(addOnsPrices.getDiscriptions());
                viewOrdersModel.setMtimestamp(viewOrdersModel.getMtimestamp());
                viewOrdersModel.setKilo_price(addOnsPrices.getKilo_price());
                viewOrdersModel.setDetergent_quantity(addOnsPrices.getDetergent_quantity());
                viewOrdersModel.setSoftener_quantity(addOnsPrices.getSoftener_quantity());
                viewOrdersModel.setBleach_quantity(addOnsPrices.getBleach_quantity());
                viewOrdersModel.setIron_on(addOnsPrices.getIron_on());
                viewOrdersModel.setDelivery_fee(viewOrdersModel.getDelivery_fee());
                viewOrdersModel.setTotal_price(addOnsPrices.getTotal_price() + viewOrdersModel.getDelivery_fee());
                viewOrdersModel.setIdd(shop_id);
                viewOrdersModel.setUser_idd(userid.getUid());
                viewOrdersModel.setShop_id(addOnsPrices.getShop_id());
                viewOrdersModel.setDel_time(time);
                viewOrdersModel.setAddwash(addOnsPrices.getAddwash());
                viewOrdersModel.setBedsheets_Comforters(addOnsPrices.getBedsheets_Comforters());
                viewOrdersModel.setBlankets_curtains(addOnsPrices.getBlankets_curtains());
                viewOrdersModel.setOwners_phonenumber(addOnsPrices.getOwners_phonenumber());

                viewOrdersModel.setCurrent_time1(time);
                viewOrdersModel.setCurrent_time2(time);
                viewOrdersModel.setCurrent_time3(time);
                viewOrdersModel.setCurrent_time4(time);
                viewOrdersModel.setCurrent_time5(time);

                viewOrdersModel = new view_orders_model(viewOrdersModel.getPayments(), viewOrdersModel.getPick_up_delivery(), viewOrdersModel.getStatus(), viewOrdersModel.getShop_name(), viewOrdersModel.getLocation_address()
                        , viewOrdersModel.getFullname(), viewOrdersModel.getPhonenumber(), viewOrdersModel.getCurrentaddress(), viewOrdersModel.getDisriptions(),
                        viewOrdersModel.getMtimestamp(), viewOrdersModel.getKilo_price(), viewOrdersModel.getDetergent_quantity(), viewOrdersModel.getSoftener_quantity(), viewOrdersModel.getBleach_quantity(),
                        viewOrdersModel.getIron_on(), viewOrdersModel.getDelivery_fee(), viewOrdersModel.getTotal_price(),viewOrdersModel.getIdd(),viewOrdersModel.getUser_idd(),
                        viewOrdersModel.getShop_id(),viewOrdersModel.getDel_time(),viewOrdersModel.getAddwash(),viewOrdersModel.getBedsheets_Comforters()
                        ,viewOrdersModel.getBlankets_curtains(),viewOrdersModel.getOwners_phonenumber(),viewOrdersModel.getStatus1(),viewOrdersModel.getStatus2()
                        ,viewOrdersModel.getStatus3(),viewOrdersModel.getStatus4(),viewOrdersModel.getStatus5(),viewOrdersModel.getCurrent_time1()
                        ,viewOrdersModel.getCurrent_time2(),viewOrdersModel.getCurrent_time3(),viewOrdersModel.getCurrent_time4(),viewOrdersModel.getCurrent_time5());

                Bundle viewItemParams = new Bundle();
                viewItemParams.putString(FirebaseAnalytics.Param.ITEM_NAME,viewOrdersModel.getShop_name());
                viewItemParams.putString(FirebaseAnalytics.Param.LOCATION, viewOrdersModel.getLocation_address());
                viewItemParams.putString(FirebaseAnalytics.Param.LEVEL_NAME,viewOrdersModel.getFullname());
                viewItemParams.putString(FirebaseAnalytics.Param.LOCATION,viewOrdersModel.getCurrentaddress());
                viewItemParams.putString(FirebaseAnalytics.Param.SHIPPING,viewOrdersModel.getPick_up_delivery());
                viewItemParams.putString(FirebaseAnalytics.Param.ITEM_ID, addOnsPrices.getShop_id());
                viewItemParams.putInt("Total Prices",addOnsPrices.getTotal_price());

                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, viewItemParams);

                FirebaseUser userid = mAuth.getCurrentUser();
                db.collection("orders").document(userid.getUid()).collection("my orders").document(shop_id)

                        //  db.collection(userid.getUid())
                        .set(viewOrdersModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                notification();
                                Toast.makeText(getApplicationContext(), "thank you for your orders", Toast.LENGTH_SHORT).show();

//                                Intent i = new Intent();
//                                i.setClassName("com.example.laundeliverhub", "users_bottom_nav.users_view_orders");
//                                startActivity(i);
                                Intent intent = new Intent(confirm_orders.this , users_view_orders.class);
                                startActivity(intent);
                                finish();
                            }
                        });

            }
        });


        shop_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePicker.Companion.with(confirm_orders.this)
                        //.crop()                 //Crop image(Optional), Check Customization for more option
                        // .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        //   .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();


            }
        });


        btn_gcash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkedgroup <= 0) {
                    Toast.makeText(getApplicationContext(), "Please select delivery method", Toast.LENGTH_SHORT).show();
                    delivery_charge.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;

                }
                if(phone.isEmpty()){
                    Toast.makeText(confirm_orders.this, "Please edit your profile!", Toast.LENGTH_SHORT).show();
                    phonenumber_txt.setError("Needed");
                    return;
                }if(currentAdd.isEmpty()){
                    Toast.makeText(confirm_orders.this, "Please edit your profile!", Toast.LENGTH_SHORT).show();
                    homeaddress_txt.setError("Needed");
                    return;
                }
                if (uri != null) {
                    progressBar.setVisibility(View.VISIBLE);
                    uploadtofirebase(uri);

                } else {
                    Toast.makeText(confirm_orders.this, "Please select image", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


    private void notification() {

        //Creating a notification channel

        NotificationChannel channel = new NotificationChannel("channel1",
                "hello",
                NotificationManager.IMPORTANCE_HIGH);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //Creating the notification object
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "channel1");
        //notification.setAutoCancel(true);
        notification.setSound(uri);
        notification.setContentTitle("Notification");
        notification.setContentText("thank you for Confirming your orders please wait for Us to Confirm your Order");
        notification.setSmallIcon(R.drawable.ic_baseline_notifications_24);
        notification.setPriority(NotificationCompat.PRIORITY_HIGH);


        //make the notification manager to issue a notification on the notification's channel
        manager.notify(121, notification.build());
    }


    private void uploadtofirebase(Uri uri) {

        StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm:ss a");
                        String time = currentTime.format(calendar.getTime());

                        FirebaseUser userid = mAuth.getCurrentUser();
                        //uploadShop_model uploadShopModel = new uploadShop_model(uri.toString());
                        String shop_id =db.collection("my orders").document().getId();

                        viewOrdersModel.setPayments("Gcash");
                        viewOrdersModel.setStatus("PENDING");

                        viewOrdersModel.setShop_name(addOnsPrices.getShop_name());
                        viewOrdersModel.setLocation_address(addOnsPrices.getLocation_address());
                        viewOrdersModel.setFullname(users_model.getFullname());
                        viewOrdersModel.setPhonenumber(users_model.getPhonenumber());
                        viewOrdersModel.setCurrentaddress(users_model.getCurrentaddress());
                        viewOrdersModel.setDisriptions(addOnsPrices.getDiscriptions());
                        viewOrdersModel.setMtimestamp(viewOrdersModel.getMtimestamp());
                        viewOrdersModel.setKilo_price(addOnsPrices.getKilo_price());
                        viewOrdersModel.setDetergent_quantity(addOnsPrices.getDetergent_quantity());
                        viewOrdersModel.setSoftener_quantity(addOnsPrices.getSoftener_quantity());
                        viewOrdersModel.setBleach_quantity(addOnsPrices.getBleach_quantity());
                        viewOrdersModel.setIron_on(addOnsPrices.getIron_on());
                        viewOrdersModel.setDelivery_fee(viewOrdersModel.getDelivery_fee());
                        viewOrdersModel.setTotal_price(addOnsPrices.getTotal_price() + viewOrdersModel.getDelivery_fee());
                        viewOrdersModel.setIdd(shop_id);
                        viewOrdersModel.setUser_idd(userid.getUid());
                        viewOrdersModel.setShop_id(addOnsPrices.getShop_id());
                        viewOrdersModel.setDel_time(time);

                        viewOrdersModel = new view_orders_model(uri.toString(), viewOrdersModel.getPayments(), viewOrdersModel.getPick_up_delivery(), viewOrdersModel.getStatus(), viewOrdersModel.getShop_name(), viewOrdersModel.getLocation_address()
                                , viewOrdersModel.getFullname(), viewOrdersModel.getPhonenumber(), viewOrdersModel.getCurrentaddress(), viewOrdersModel.getDisriptions(),
                                viewOrdersModel.getMtimestamp(), viewOrdersModel.getKilo_price(), viewOrdersModel.getDetergent_quantity(), viewOrdersModel.getSoftener_quantity(), viewOrdersModel.getBleach_quantity(),
                                viewOrdersModel.getIron_on(), viewOrdersModel.getDelivery_fee(), viewOrdersModel.getTotal_price(),viewOrdersModel.getIdd(),viewOrdersModel.getUser_idd(),viewOrdersModel.getShop_id()
                                ,viewOrdersModel.getDel_time());

                       // FirebaseUser userid = mAuth.getCurrentUser();


                        db.collection("orders").document(userid.getUid()).collection("my orders").document(shop_id)

                                //  db.collection(userid.getUid())
                                .set(viewOrdersModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getApplicationContext(), "thank you for your orders", Toast.LENGTH_SHORT).show();
                                        notification();
                                        Intent i = new Intent();
                                        i.setClassName("com.example.laundeliverhub", "users_bottom_nav.users_view_orders");
                                        startActivity(i);
                                        finish();
                                    }
                                });
                        progressBar.setVisibility(View.INVISIBLE);

                    }
                });
            }
        });


    }

    public int calculate_total() {

        int total_price = addOnsPrices.getTotal_price()+viewOrdersModel.getDelivery_fee();

        return total_price;
    }

    private String getFileExtension(Uri muri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(muri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        uri = data.getData();
        shop_imageview.setImageURI(uri);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, users_order_details.class);
        startActivity(intent);
        finish();
    }

}