package users_bottom_nav;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laundeliverhub.R;
import com.example.laundeliverhub.addOns_Prices;
import com.example.laundeliverhub.my_orders_final;
import com.example.laundeliverhub.user_order_details_adapter;
import com.example.laundeliverhub.view_orders_adapter;
import com.example.laundeliverhub.view_orders_model;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class users_view_orders extends AppCompatActivity {
    BottomNavigationView bottom_navigation;
    RecyclerView recycler_view;
    Button btn_info;
    ImageView image;
    TextView txt1,txt2;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private view_orders_adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_track_orders);

        recycler_view = findViewById(R.id.recycler_view);
        mAuth = FirebaseAuth.getInstance();

        txt1 =findViewById(R.id.txt1);
        txt2 =findViewById(R.id.txt2);
        image =findViewById(R.id.image);
        txt1.setVisibility(View.INVISIBLE);
        txt2.setVisibility(View.INVISIBLE);
        image.setVisibility(View.INVISIBLE);

        btn_info = findViewById(R.id.btn_info);
        bottom_navigation = findViewById(R.id.bottom_navigation_users);
        bottom_navigation.setSelectedItemId(R.id.nav_track_order);

        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_users_home:
                        startActivity(new Intent(getApplicationContext(), users_home.class));
                        overridePendingTransition(0, 0);
                        finish();
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

                        return true;
                }
                return false;
            }
        });



        //count cart
        cart_itemCount();
        final_order_item();
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(users_view_orders.this)
                        .setTitle("Information")
                        .setMessage("Track your orders by checking the status thank you for your orders enjoy using LaundryHub")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        }).create().show();
            }
        });



        //make a method for RecyclerView
        setUpRecyclerView();
    }



    private void cart_itemCount() {
        FirebaseUser userid = mAuth.getCurrentUser();
        db.collection("cart").document(userid.getUid()).collection("my cart")
                .orderBy("mTimestamp", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        int counter = value.size();

                        BadgeDrawable badgetrack = bottom_navigation.getOrCreateBadge(R.id.nav_order_details);
                        badgetrack.setVisible(true);
                        badgetrack.setVerticalOffset(dptopx(users_view_orders.this,3));
                        badgetrack.setBadgeTextColor(getResources().getColor(R.color.white));
                        badgetrack.setBackgroundColor(getColor(R.color.red_500));
                        badgetrack.setNumber(counter);
                         if (counter == 0) {
                            badgetrack.setVisible(false);

                        }

                    }
                });
    }
    private void final_order_item() {
        FirebaseUser userid = mAuth.getCurrentUser();
        db.collection("orders").document(userid.getUid()).collection("my orders")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        int counter = value.size();

                        BadgeDrawable badgeDrawable = bottom_navigation.getOrCreateBadge(R.id.nav_track_order);
                        badgeDrawable.setVisible(true);
                        badgeDrawable.setVerticalOffset(dptopx(users_view_orders.this,3));
                        badgeDrawable.setBackgroundColor(getColor(R.color.red_accent));


                        if (counter == 0) {
                            image.setVisibility(View.VISIBLE);
                            txt1.setVisibility(View.VISIBLE);
                            txt2.setVisibility(View.VISIBLE);
                            badgeDrawable.setVisible(false);
                        }
                    }
                });

    }

    private void setUpRecyclerView() {


        FirebaseUser userid = mAuth.getCurrentUser();
        Query query = db.collection("orders").document(userid.getUid()).collection("my orders").orderBy("mtimestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<view_orders_model> options = new FirestoreRecyclerOptions.Builder<view_orders_model>()
                .setQuery(query, view_orders_model.class)
                .build();

        adapter = new view_orders_adapter(options);
        recycler_view.setHasFixedSize(true);
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

    public static int dptopx(Context context, int dp){
        Resources resources =  context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,resources.getDisplayMetrics()));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        bottom_navigation.setSelectedItemId(R.id.nav_users_home);
        finish();
    }
}