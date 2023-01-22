package users_bottom_nav;

import static java.lang.String.format;

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
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laundeliverhub.R;
import com.example.laundeliverhub.addOns_Prices;
import com.example.laundeliverhub.user_order_details_adapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.rpc.context.AttributeContext;

import java.lang.reflect.Type;
import java.util.ArrayList;

import io.grpc.internal.SharedResourceHolder;

public class users_order_details extends AppCompatActivity {
    BottomNavigationView bottom_navigation;
    RecyclerView recycler_view;
    private FirebaseAuth mAuth;
    String userId;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private user_order_details_adapter adapter;
    Button btn_info;
    ImageView image;
    TextView  txt1,txt2;
    private static final String TAG = "users_order_details";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_orders);
        btn_info = findViewById(R.id.btn_info);
        bottom_navigation = findViewById(R.id.bottom_navigation_users);
        bottom_navigation.setSelectedItemId(R.id.nav_order_details);

        txt1 =findViewById(R.id.txt1);
        txt2 =findViewById(R.id.txt2);
        image =findViewById(R.id.image);
        txt1.setVisibility(View.INVISIBLE);
        txt2.setVisibility(View.INVISIBLE);
        image.setVisibility(View.INVISIBLE);
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



        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(users_order_details.this)
                        .setTitle("Information")
                        .setMessage("If you have not updated your profile Please update your profile in the Home page to proceed cart thank you ")
                        .setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            dialog.cancel();
                            return true;
                        }
                        return false;
                    }
                         })
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        }).create().show();
            }
        });

        recycler_view = findViewById(R.id.recycler_view);
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        //method for recyclerview
        setUpRecyclerView();

        //method for cart and orders notification
        cart_itemCount();
        final_order_item();

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
                        badgetrack.setVerticalOffset(dptopx(users_order_details.this,3));
                        badgetrack.setBadgeTextColor(getResources().getColor(R.color.white));
                        badgetrack.setBackgroundColor(getColor(R.color.red_500));
                        badgetrack.setNumber(counter);
                        if (counter == 0) {
                            badgetrack.setVisible(false);
                            txt1.setVisibility(View.VISIBLE);
                            txt2.setVisibility(View.VISIBLE);

                            //delay image for 5 seconds
                            ImageView yourImage = findViewById(R.id.image);
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    yourImage.setVisibility(View.VISIBLE);
                                }
                            }, 500);

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
                        badgeDrawable.setVerticalOffset(dptopx(users_order_details.this,3));
                        badgeDrawable.setBackgroundColor(getColor(R.color.red_accent));

                        if (counter == 0) {
                            badgeDrawable.setVisible(false);
                        }
                        Log.d(TAG, "onEvent:  " + counter);
                    }
                });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setUpRecyclerView() {

        FirebaseUser userid = mAuth.getCurrentUser();
        Query query = db.collection("cart").document(userid.getUid()).collection("my cart")
                .orderBy("mTimestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<addOns_Prices> options = new FirestoreRecyclerOptions.Builder<addOns_Prices>()
                .setQuery(query, addOns_Prices.class)
                .build();





        adapter = new user_order_details_adapter(options);
        recycler_view.setHasFixedSize(false);
        recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycler_view.setAdapter(adapter);

        int count = adapter.getItemCount();
        Log.d(TAG, "debugging count: "+count);


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

    public static int dptopx(Context context,int dp){
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