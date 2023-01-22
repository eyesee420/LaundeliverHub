package com.example.laundeliverhub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

import users_bottom_nav.users_order_details;

public class user_order_details_adapter extends FirestoreRecyclerAdapter<addOns_Prices, user_order_details_adapter.cartholder> {


    public user_order_details_adapter(@NonNull FirestoreRecyclerOptions<addOns_Prices> options) {
        super(options);
    }



    @Override
    protected void onBindViewHolder(user_order_details_adapter.cartholder holder, int position,  addOns_Prices models) {
        holder.bind(models);

    }

    @NonNull
    @Override
    public cartholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart2, parent, false);
        return new cartholder (view);
    }

    public void deleteitem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();

    }

    public class cartholder extends RecyclerView.ViewHolder {
        addOns_Prices addOnsPrices;

        Button btn_delete;
        ImageView shop_imageview;
        TextView shop_name, location_address , total_amount ,date_time;
        Context context;
        public cartholder(@NonNull View itemView) {
            super(itemView);
            context =itemView.getContext();
            shop_name = itemView.findViewById(R.id.shop_name_txt);
            location_address = itemView.findViewById(R.id.shop_location);
            total_amount = itemView.findViewById(R.id.total_amount);
            date_time = itemView.findViewById(R.id.date_time);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            shop_imageview = itemView.findViewById(R.id.shop_imageview);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), confirm_orders.class);
                    intent.putExtra("addOnsPrices",addOnsPrices);
                    itemView.getContext().startActivity(intent);
                    ((Activity) context).finish();

                }
            });

            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position =  getAdapterPosition();
                    getSnapshots().getSnapshot(position).getReference().delete();
                }
            });

        }


        public void bind(addOns_Prices addOnsPrice) {

            shop_name.setText(addOnsPrice.getShop_name());
            location_address.setText(addOnsPrice.getLocation_address());
            total_amount.setText("₱ "+addOnsPrice.getTotal_price());
            date_time.setText(addOnsPrice.getmTimestamp() + "");
            shop_name.setText(addOnsPrice.getShop_name());

            Glide.with(context.getApplicationContext())
                    .load(addOnsPrice.getUri())
                    .into(shop_imageview);

            addOnsPrices = addOnsPrice;

        }

    }
}
