package users_bottom_nav;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import android.util.Log;
import android.util.TypedValue;

import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import android.widget.Button;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.example.laundeliverhub.R;

import com.example.laundeliverhub.uploadShop_model;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class users_stores extends AppCompatActivity {
    RecyclerView recycler_view;
    BottomNavigationView bottom_navigation;
    FirebaseFirestore db;
    ToggleButton search_bar;
    androidx.appcompat.widget.SearchView searchView;
    stores_adapter adapter;
   //FirestoreRecyclerAdapter adapter;
    private FirebaseAuth mAuth;
    Button btn_info ;
    private static final String TAG = "users_stores";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_stores);

        searchView = findViewById(R.id.searchView);
        search_bar = findViewById(R.id.search_bar);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        bottom_navigation = findViewById(R.id.bottom_navigation_users);
        bottom_navigation.setSelectedItemId(R.id.nav_users_Stores);
        btn_info = findViewById(R.id.btn_info);
        recycler_view = findViewById(R.id.recycler_view);


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

        setUpRecyclerView();


        search_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(search_bar.isChecked()){
                    searchView.setVisibility(View.VISIBLE);
                    searchView.setIconified(false);
                    searchView.clearFocus();
                }else{
                    searchView.setVisibility(View.GONE);
                }
            }
        });

        cart_itemCount();
        final_order_item();
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(users_stores.this)
                        .setTitle("Information")
                        .setMessage("Choose your desired laundry shop from the list")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        }).create().show();
            }
        });



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
             proccess(newText);
                process(newText);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               proccess(newText);
                process(newText);
                return false;
            }

        });searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchView.setVisibility(View.GONE);
                search_bar.setChecked(false);
                return false;
            }
        });
    }

    private void process(String newText) {
        Query querys ;

        if (newText.isEmpty()) {
            querys = db.collection("shops and products")
                    .getFirestore().collectionGroup("my shops")
                    .orderBy("myrate", Query.Direction.DESCENDING);

            FirestoreRecyclerOptions<uploadShop_model> options =
                    new FirestoreRecyclerOptions.Builder<uploadShop_model>()
                            .setQuery(querys, uploadShop_model.class)
                            .build();

//
//            adapter.updateOptions(options);
//            adapter.notifyDataSetChanged();
//            adapter = new stores_adapter(options);
//            adapter.startListening();
//            recycler_view.setAdapter(adapter);

        }else{
        querys = db.collection("shops and products")
                    .getFirestore().collectionGroup("my shops")
                    .whereArrayContains("keye",newText);

            FirestoreRecyclerOptions<uploadShop_model> options =
                    new FirestoreRecyclerOptions.Builder<uploadShop_model>()
                            .setQuery(querys, uploadShop_model.class)
                            .build();


            adapter.updateOptions(options);
            adapter.notifyDataSetChanged();
            adapter = new stores_adapter(options);
            adapter.startListening();
            recycler_view.setAdapter(adapter);


        }

        FirestoreRecyclerOptions<uploadShop_model> options =
                new FirestoreRecyclerOptions.Builder<uploadShop_model>()
                        .setQuery(querys, uploadShop_model.class)
                        .build();


        adapter.updateOptions(options);
        adapter.notifyDataSetChanged();
        adapter = new stores_adapter(options);
        adapter.startListening();
        recycler_view.setAdapter(adapter);




    }

    private void proccess(String newText) {
        Query querys;

        if (newText.isEmpty()) {
            querys = db.collection("shops and products")
                    .getFirestore().collectionGroup("my shops")
                    .orderBy("myrate", Query.Direction.DESCENDING);

            FirestoreRecyclerOptions<uploadShop_model> options =
                    new FirestoreRecyclerOptions.Builder<uploadShop_model>()
                            .setQuery(querys, uploadShop_model.class)
                            .build();


            adapter.updateOptions(options);
            adapter.notifyDataSetChanged();
            adapter = new stores_adapter(options);
            adapter.startListening();
            recycler_view.setAdapter(adapter);

        }
        else{

            String sample1 = "Palar";
            Pattern regex = Pattern.compile("[A-Za-z]+");
            Matcher matcher = regex.matcher(sample1);
            while (matcher.find()){

            }

            querys = db.collection("shops and products")
                    .getFirestore().collectionGroup("my shops")
                    .orderBy("location_address")
                    .startAt(newText).endAt(newText+"\uf8ff")
                    ;


            FirestoreRecyclerOptions<uploadShop_model> options =
                    new FirestoreRecyclerOptions.Builder<uploadShop_model>()
                            .setQuery(querys, uploadShop_model.class)
                            .build();

            adapter.updateOptions(options);
            adapter.notifyDataSetChanged();
            adapter = new stores_adapter(options);
            adapter.startListening();
            recycler_view.setAdapter(adapter);

        }
        Log.d(TAG, "proccess: " +newText);
    }




    private void setUpRecyclerView() {


        FirebaseUser userid = mAuth.getCurrentUser();
//        Query query = db.collection("shop and products").
//                document("8CfwH7MOsXNJgBptaRHSZWifPAF3").collection("my shops");

        Query query =db.collection("shops and products")
                .getFirestore().collectionGroup("my shops");
//                .orderBy("myrate", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<uploadShop_model> options = new FirestoreRecyclerOptions.Builder<uploadShop_model>()
                .setQuery(query, uploadShop_model.class).build();



        adapter = new stores_adapter(options);
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycler_view.setAdapter(adapter);

    }

    private void cart_itemCount() {

        FirebaseUser userid = mAuth.getCurrentUser();
        db.collection("cart").document(userid.getUid()).collection("my cart")
                .orderBy("mTimestamp", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        int counter = value.size();
                        //badge notif for certain button nav
                        BadgeDrawable badgetrack = bottom_navigation.getOrCreateBadge(R.id.nav_order_details);
                        badgetrack.setVisible(true);
                        badgetrack.setVerticalOffset(dptopx(users_stores.this,3));
                        badgetrack.setBadgeTextColor(getResources().getColor(R.color.white));
                        badgetrack.setBackgroundColor(getColor(R.color.red_500));
                        badgetrack.setNumber(counter);
                        if (counter == 0) {
                            badgetrack.setVisible(false);
                        }
                        Log.d(TAG, "onEvent:  " + counter);
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
                        badgeDrawable.setVerticalOffset(dptopx(users_stores.this,3));
                        badgeDrawable.setBackgroundColor(getColor(R.color.red_accent));


                        if (counter == 0) {
                            badgeDrawable.setVisible(false);
                        }
                        Log.d(TAG, "onEvent:  " + counter);
                    }
                });

    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
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