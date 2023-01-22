package admin_bottom_nav;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.laundeliverhub.R;
import com.example.laundeliverhub.add_cart;
import com.example.laundeliverhub.admin_login_page;
import com.example.laundeliverhub.admin_myShop_update;
import com.example.laundeliverhub.uploadShop_model;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class admin_myShop extends AppCompatActivity {

    FirebaseAuth FirebaseAuth ;


    RecyclerView recycler_view;
    BottomNavigationView bottom_navigation;
    FirebaseFirestore db;
    Button btn_addshop;
    private FirebaseAuth mAuth;
    private admin_myShop_adapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_my_shop);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        recycler_view = findViewById(R.id.recycler_view);

        btn_addshop = findViewById(R.id.btn_addshop);
        bottom_navigation= findViewById(R.id.bottom_navigation);
        bottom_navigation.setSelectedItemId(R.id.nav_myShop);
        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(),admin_home.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.nav_completed_orders:
                        startActivity(new Intent(getApplicationContext(),admin_completed_orders.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.nav_customer_orders:
                        startActivity(new Intent(getApplicationContext(),admin_customersOrders.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.nav_myShop:
                        return true;
                }
                return false;
            }
        });

        btn_addshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_myShop.this ,admin_addProducts.class);
                startActivity(intent);
            }
        });

        final_order_count();
        completed_orders_count();
        users_count();
        myShop_count();

        setUpRecyclerView();


        }

    private void myShop_count() {
        FirebaseUser userid = mAuth.getCurrentUser();
        db.collection("shop and products").document(userid.getUid()).collection("my shops")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        int counter = value.size();

                        BadgeDrawable badgeDrawable = bottom_navigation.getOrCreateBadge(R.id.nav_myShop);
                        badgeDrawable.setVisible(true);
                        badgeDrawable.setBackgroundColor(getColor(R.color.red_accent));


                        if (counter == 0) {
                            badgeDrawable.setVisible(false);
                        }
                    }
                });
    }

    private void users_count() {
        db.collection("users")

                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        int counter = value.size();

                        BadgeDrawable badgetrack = bottom_navigation.getOrCreateBadge(R.id.nav_home);
                        badgetrack.setVisible(true);
                        badgetrack.setVerticalOffset(dptopx(getApplicationContext(),3));
                        badgetrack.setBadgeTextColor(getResources().getColor(R.color.white));
                        badgetrack.setBackgroundColor(getColor(R.color.red_accent));
                        badgetrack.setNumber(counter);
                        if (counter == 0) {
                            badgetrack.setVisible(false);
                        }

                    }
                });

    }

    private void completed_orders_count() {
        db.collection("completed orders")

                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        int counter = value.size();

                        BadgeDrawable badgetrack = bottom_navigation.getOrCreateBadge(R.id.nav_completed_orders);
                        badgetrack.setVisible(true);
                         badgetrack.setVerticalOffset(dptopx(getApplicationContext(),3));
                        badgetrack.setBadgeTextColor(getResources().getColor(R.color.white));
                        badgetrack.setBackgroundColor(getColor(R.color.red_accent));
                        badgetrack.setNumber(counter);
                        if (counter == 0) {
                            badgetrack.setVisible(false);
                        }

                    }
                });

    }



    private void final_order_count() {
        FirebaseUser userid = mAuth.getCurrentUser();
        db.collection("orders").getFirestore().collectionGroup("my orders")

                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        int counter = value.size();

                        BadgeDrawable badgetrack = bottom_navigation.getOrCreateBadge(R.id.nav_customer_orders);
                        badgetrack.setVisible(true);
                        badgetrack.setVerticalOffset(dptopx(getApplicationContext(),3));
                        badgetrack.setBadgeTextColor(getResources().getColor(R.color.white));
                        badgetrack.setBackgroundColor(getColor(R.color.red_accent));
                        badgetrack.setNumber(counter);
                        if (counter == 0) {
                            badgetrack.setVisible(false);
                        }

                    }
                });

    }

    private void setUpRecyclerView() {
        FirebaseUser userid = mAuth.getCurrentUser();
        Query query = db.collection("shop and products")
                .document(userid.getUid()).collection("my shops");

        //  Query query1 =db.collection("shops and products").getFirestore().collectionGroup("my shops");

        FirestoreRecyclerOptions<uploadShop_model> options = new FirestoreRecyclerOptions.Builder<uploadShop_model>()
                .setQuery(query, uploadShop_model.class).build();



        adapter = new admin_myShop_adapter(options);
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycler_view.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        admin_myShop.super.onBackPressed();

                        SharedPreferences preferences =getSharedPreferences("admin_login", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.apply();
                        Intent intent = new Intent(admin_myShop.this, admin_login_page.class);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();

    }
        public static int dptopx(Context context,int dp){
        Resources resources =  context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,resources.getDisplayMetrics()));
    }
}