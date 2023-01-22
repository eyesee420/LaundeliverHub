package admin_bottom_nav;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laundeliverhub.Create_Users_Model;
import com.example.laundeliverhub.R;
import com.example.laundeliverhub.uploadShop_model;
import com.example.laundeliverhub.view_orders_model;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class admin_customersOrders extends AppCompatActivity {
    RecyclerView recycler_view;
    BottomNavigationView bottom_navigation;
    view_orders_model viewOrdersModel;
    private FirebaseAuth mAuth;

    Create_Users_Model users_model;
    FirebaseFirestore db;
    private admin_customers_orders_adapter adapter;


    private static final String TAG = "customers orders";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_customers_orders);

        viewOrdersModel = new view_orders_model();
        users_model = new Create_Users_Model();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        recycler_view = findViewById(R.id.recycler_view);
        bottom_navigation= findViewById(R.id.bottom_navigation);
        bottom_navigation.setSelectedItemId(R.id.nav_customer_orders);

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

                        return true;

                    case R.id.nav_myShop:
                        startActivity(new Intent(getApplicationContext(), admin_myShop.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                }
                return false;
            }
        });

     //   Create_Users_Model users_model = new Create_Users_Model();

        Log.d(TAG, "setUpRecyclerView: " + viewOrdersModel.getPhonenumber());


        final_order_count();
        completed_orders_count();
        users_count();
        myShop_count();
        //make a method for whats happening
        setUpRecyclerView();
    }

    private void myShop_count() {
        FirebaseUser userid = mAuth.getCurrentUser();
        db.collection("shop and products").document(userid.getUid()).collection("my shops")
                .orderBy("mtimestamp", Query.Direction.DESCENDING)
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

        Query query1 =db.collection("orders").getFirestore().collectionGroup("my orders")
                .orderBy("mtimestamp", Query.Direction.DESCENDING);


  // Query query1 =db.collection("orders").getFirestore().collectionGroup("my orders").orderBy("mtimestamp")  ;


        FirestoreRecyclerOptions<view_orders_model> options = new FirestoreRecyclerOptions.Builder<view_orders_model>()
                .setQuery(query1, view_orders_model.class).build();


        adapter = new admin_customers_orders_adapter(options);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        bottom_navigation.setSelectedItemId(R.id.nav_myShop);
        finish();
    }

    public static int dptopx(Context context, int dp){
        Resources resources =  context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,resources.getDisplayMetrics()));
    }

}