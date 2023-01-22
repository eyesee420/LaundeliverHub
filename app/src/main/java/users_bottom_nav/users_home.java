package users_bottom_nav;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleableRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.laundeliverhub.Create_Users_Model;
import com.example.laundeliverhub.MainActivity;
import com.example.laundeliverhub.R;


import com.example.laundeliverhub.profile_page;
import com.example.laundeliverhub.terms_and_conditions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class users_home extends AppCompatActivity {

    TextView full_name ,terms_condi;
    BottomNavigationView bottom_navigation;
    ImageView gmail ,fb ,phn;
    ImageView profile_btn;
    private   FirebaseFirestore db;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseUser userid;
    TextView tx1,tx2,tx3,tx4;
    private static final String TAG = "users_home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_home);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        tx1 = findViewById(R.id.tx1);
        tx2 = findViewById(R.id.tx2);
        tx3 = findViewById(R.id.tx3);
        tx4 = findViewById(R.id.tx4);
        TextView txp = findViewById(R.id.txp);
        TextView txd = findViewById(R.id.txd);
        TextView txc = findViewById(R.id.txc);
        TextView txg = findViewById(R.id.txg);
        TextView txi = findViewById(R.id.txi);

        gmail = findViewById(R.id.gmail);
        fb = findViewById(R.id.fb);
        phn = findViewById(R.id.phn);
        profile_btn = findViewById(R.id.profile_btn);
        terms_condi = findViewById(R.id.terms_condi);
        full_name  = findViewById(R.id.full_name);
        bottom_navigation = findViewById(R.id.bottom_navigation_users);
        bottom_navigation.setSelectedItemId(R.id.nav_users_home);

        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_users_home:
                        return true;

                    case R.id.nav_users_Stores:
                        startActivity(new Intent(getApplicationContext(), users_stores.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                    case R.id.nav_order_details:
                        startActivity(new Intent(getApplicationContext(), users_order_details.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                    case R.id.nav_track_order:
                        startActivity(new Intent(getApplicationContext(), users_view_orders.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                }
                return false;
            }
        });

       cart_itemCount();
       final_order_item();


        ImageSlider imageSlider = findViewById(R.id.image_slider);
        List<SlideModel> slideModels=new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.banner1 , ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.banner2 , ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.banner3 , ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.banner4 , ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.bea_banner , ScaleTypes.FIT));
        imageSlider.setImageList(slideModels,ScaleTypes.FIT);

            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
            String personName = acct.getDisplayName();
            String text2 ="Hello "+acct.getDisplayName();

            Log.d(TAG, "Token: "+acct.getIdToken());

            ForegroundColorSpan red = new ForegroundColorSpan(getResources().getColor(R.color.purple_700));
            SpannableString ss =new SpannableString(text2);
            ss.setSpan(red,6,text2.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            full_name.setText(ss);

            Glide.with(getApplicationContext())
                    .load(acct.getPhotoUrl())
                    .into(profile_btn);
        }



     userid = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference docRef = db.collection("users").document(userid.getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Create_Users_Model get = documentSnapshot.toObject(Create_Users_Model.class);
                if (get != null) {
                    full_name = findViewById(R.id.full_name);

                    String text2 ="Hello "+get.getFullname();

                    ForegroundColorSpan red = new ForegroundColorSpan(getResources().getColor(R.color.purple_700));
                    SpannableString ss =new SpannableString(text2);
                    ss.setSpan(red,6,text2.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                    full_name.setText(ss);

                }
            }
        });



        terms_condi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(users_home.this , terms_and_conditions.class);
                startActivity(intent);
                finish();
            }
        });

        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), profile_page.class));
                finish();
            }
        });

        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("mailto:icecrown420@gmail.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.facebook.com/TourismOperation302");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        phn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = "09266454050";
                Uri uri = Uri.parse("tel:" + number);
                Intent dial = new Intent(Intent.ACTION_DIAL);
                dial.setData(uri);
                startActivity(dial);
            }
        });


        //setting custom styles for the "text"
        String text = "STEP 1: Choose and select your preferred  Laundry shop located at the store module below.";
        String tex2 = "STEP 2: Select your desired service with an option to add a separate or personal note and choose add to cart.";
        String tex3 = "STEP 3: Select view cart and check for your chosen service and choose your payment method located at order confirmation module.";
        String tex4 = "STEP 4: Once confirmation of your desired service is done. You can track and check the status of your chosen service at the Track Order page.";

        String tex5 = "PICKUP - For pickup orders we can get your laundry items to your home and chose to pickup later in laundry shop.";
        String tex6 = "PICKUP AND DELIVERY - We can pickup and deliver your laundry items straight to your doorstep.";
        String tex7 = "CASH ON DELIVERY - You can pay thru cash on delivery when your laundry items arrived to your home.";
        String tex8 = "GCASH PAYMENT - We accept Gcash payment by Scan Qr Code or Send thru our Gcash number.";
        String tex9 = "IMPORTANT NOTE : Payment  via Gcash needs to attach a confirmation by sending a screenshot of proof of payment on the order page menu.";

        SpannableString s1 =new SpannableString(text);
        SpannableString s2 =new SpannableString(tex2);
        SpannableString s3 =new SpannableString(tex3);
        SpannableString s4 =new SpannableString(tex4);

        SpannableString s5 =new SpannableString(tex5);
        SpannableString s6 =new SpannableString(tex6);
        SpannableString s7 =new SpannableString(tex7);
        SpannableString s8 =new SpannableString(tex8);
        SpannableString s9 =new SpannableString(tex9);
        StyleSpan bold = new StyleSpan(Typeface.BOLD);
        StyleSpan italic = new StyleSpan(Typeface.ITALIC);
       // ForegroundColorSpan red = new ForegroundColorSpan(getResources().getColor(R.color.red_500));
        s1.setSpan(bold,0 ,7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        s2.setSpan(bold,0 ,7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        s3.setSpan(bold,0 ,7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        s4.setSpan(bold,0 ,7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        s5.setSpan(bold,0 ,9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        s6.setSpan(bold,0 ,22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        s7.setSpan(bold,0 ,19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        s8.setSpan(bold,0 ,15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        s9.setSpan(bold,0 ,16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        s9.setSpan(italic,0 ,134, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tx1.setText(s1);
        tx2.setText(s2);
        tx3.setText(s3);
        tx4.setText(s4);

        txp.setText(s5);
        txd.setText(s6);
        txc.setText(s7);
        txg.setText(s8);
        txi.setText(s9);
    }

    private void cart_itemCount() {

     // FirebaseUser userid = mAuth.getCurrentUser();
         userid = FirebaseAuth.getInstance().getCurrentUser();
                       db.collection("cart").document(userid.getUid()).collection("my cart")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        int counter = value.size();

                        BadgeDrawable badgetrack = bottom_navigation.getOrCreateBadge(R.id.nav_order_details);
                        badgetrack.setVisible(true);
                        badgetrack.setVerticalOffset(dptopx(users_home.this,3));
                        badgetrack.setBadgeTextColor(getResources().getColor(R.color.white));
                        badgetrack.setBackgroundColor(getColor(R.color.red_500));
                        badgetrack.setNumber(counter);
                        if(counter == 0){
                            badgetrack.setVisible(false);
                        }

                        Log.d(TAG, "onEvent:  "  + counter);
                    }
                });
    }

    //method for badge notfication to display orders notification
    private void final_order_item() {
         userid = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("orders").document(userid.getUid()).collection("my orders")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        int counter = value.size();

                        BadgeDrawable orderCount = bottom_navigation.getOrCreateBadge(R.id.nav_track_order);
                        orderCount.setVisible(true);
                        orderCount.setVerticalOffset(dptopx(users_home.this,3));
                        orderCount.setBackgroundColor(getResources().getColor(R.color.red_accent));


                        if (counter == 0) {
                            orderCount.setVisible(false);
                        }
                        Log.d(TAG, "onEvent:  " + counter);
                    }
                });

    }
    //convert dp to px
    public static int dptopx(Context context,int dp){
        Resources resources =  context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,resources.getDisplayMetrics()));
              }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        users_home.super.onBackPressed();
                        SharedPreferences preferences =getSharedPreferences("login", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.apply();
                        mGoogleSignInClient.signOut();
                        Intent intent = new Intent(users_home.this,MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }).create().show();

    }


}