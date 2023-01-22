package users_bottom_nav;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.laundeliverhub.R;
import com.example.laundeliverhub.add_cart;
import com.example.laundeliverhub.uploadShop_model;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.analytics.FirebaseAnalytics;

import admin_bottom_nav.admin_myShop_adapter;


public class stores_adapter extends
        FirestoreRecyclerAdapter<uploadShop_model, stores_adapter.shop_sholder> {

    public stores_adapter(@NonNull FirestoreRecyclerOptions<uploadShop_model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull shop_sholder shop_sholder, int i, @NonNull uploadShop_model model) {
        shop_sholder.bind(model);
    }

    @NonNull
    @Override
    public stores_adapter.shop_sholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shop_row, parent, false);
        return new stores_adapter.shop_sholder(view);
    }

    public class shop_sholder extends RecyclerView.ViewHolder {
        uploadShop_model uploadShopModels;
        Button btn_delete, btn_update;
        RelativeLayout parent;
        ImageView images;
        TextView shop_name, location_address ,ratenum , active_stats;
        RatingBar StarRatings;
        Context context;



        public shop_sholder(@NonNull View itemView) {
            super(itemView);
            context =itemView.getContext();
            parent = itemView.findViewById(R.id.parent);
            StarRatings = itemView.findViewById(R.id.StarRatings);
            active_stats = itemView.findViewById(R.id.active_stats);
            ratenum = itemView.findViewById(R.id.ratenum);
            images = itemView.findViewById(R.id.shop_imageview);
            shop_name = itemView.findViewById(R.id.shop_name_txt);
            location_address = itemView.findViewById(R.id.laundry_location_txt);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            btn_update = itemView.findViewById(R.id.btn_update);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), add_cart.class);
                    intent.putExtra("uploadShopModel",uploadShopModels);
                    itemView.getContext().startActivity(intent);
                    ((Activity) context).finish();
                }
            });



        }

        public void bind(uploadShop_model uploadShopModel) {

            Glide.with(context.getApplicationContext())
                    .load(uploadShopModel.getUri())
                    .into(images);
            active_stats.setText(uploadShopModel.getActive_status());
            shop_name.setText(uploadShopModel.getShop_name());
            location_address.setText(uploadShopModel.getLocation_address());
            float average = uploadShopModel.getMyrate()/uploadShopModel.getNumrate() ;
            StarRatings.setRating(average);
            ratenum.setText("("+uploadShopModel.getNumrate()+")");

            String active_status = "Closed";
            if (active_status.equals(uploadShopModel.getActive_status())){
                itemView.setEnabled(false);
                active_stats.setTextColor(context.getColor(R.color.red_accent));
               // parent.setBackgroundColor(context.getColor(R.color.my_grey));

            }else{
                parent.setEnabled(true);
            }
            uploadShopModels = uploadShopModel;
        }
    }
}
