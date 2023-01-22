package com.example.laundeliverhub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class shopOwner_adapter extends
        FirestoreRecyclerAdapter<uploadShop_model, shopOwner_adapter.shop_sholder> {

    public shopOwner_adapter(@NonNull FirestoreRecyclerOptions<uploadShop_model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull shopOwner_adapter.shop_sholder holder,
                                    int position, @NonNull uploadShop_model model) {
        holder.bind(model);
    }

    @NonNull
    @Override
    public shopOwner_adapter.shop_sholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shop_row_owners, parent, false);
        return new shopOwner_adapter.shop_sholder(view);
    }

    public class shop_sholder extends RecyclerView.ViewHolder {

        uploadShop_model uploadShopModels;
        Button btn_delete, btn_update;
        ImageView images;
        TextView shop_name, location_address ,ratenum;
        RatingBar StarRatings;
        Switch switch_btn;
        Context context;
        public shop_sholder(@NonNull View itemView) {
            super(itemView);
            context =itemView.getContext();
            StarRatings = itemView.findViewById(R.id.StarRatings);
            ratenum = itemView.findViewById(R.id.ratenum);
            images = itemView.findViewById(R.id.shop_imageview);
            shop_name = itemView.findViewById(R.id.shop_name_txt);
            location_address = itemView.findViewById(R.id.laundry_location_txt);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            btn_update = itemView.findViewById(R.id.btn_update);
            switch_btn = itemView.findViewById(R.id.switch_btn);

            switch_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(switch_btn.isChecked()){
                        itemView.setEnabled(false);
                        int position =  getAdapterPosition();
                        getSnapshots().getSnapshot(position).getReference()
                                .update("active_status","Closed");

                        Toast.makeText(context, "disabled", Toast.LENGTH_SHORT).show();

                    }else {
                        int position =  getAdapterPosition();
                        getSnapshots().getSnapshot(position).getReference()
                                .update("active_status"," ");

                        Toast.makeText(context, "okeyy", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "asdasdasda",
                            Toast.LENGTH_SHORT).show();
                }
            });

        }

        public void bind(uploadShop_model uploadShopModel) {

            Glide.with(context.getApplicationContext())
                    .load(uploadShopModel.getUri())
                    .into(images);

            shop_name.setText(uploadShopModel.getShop_name());
            location_address.setText(uploadShopModel.getLocation_address());
            float average = uploadShopModel.getMyrate()/uploadShopModel.getNumrate() ;
            StarRatings.setRating(average);
            ratenum.setText("("+uploadShopModel.getNumrate()+")");
            uploadShopModels = uploadShopModel;

        }
    }
}
