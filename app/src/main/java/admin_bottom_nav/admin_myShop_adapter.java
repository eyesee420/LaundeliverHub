package admin_bottom_nav;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.laundeliverhub.R;
import com.example.laundeliverhub.admin_myShop_update;
import com.example.laundeliverhub.my_orders_final_admin;
import com.example.laundeliverhub.uploadShop_model;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class admin_myShop_adapter extends
        FirestoreRecyclerAdapter<uploadShop_model, admin_myShop_adapter.shop_sholder> {



    public admin_myShop_adapter(@NonNull FirestoreRecyclerOptions<uploadShop_model> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull admin_myShop_adapter.shop_sholder shop_sholder, int i, @NonNull uploadShop_model model) {


        shop_sholder.bind(model);
    }

    @NonNull
    @Override
    public admin_myShop_adapter.shop_sholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shop_row_admin, parent, false);
        return new admin_myShop_adapter.shop_sholder(view);
    }

    public void deleteitems(int adapterPosition) {
        getSnapshots().getSnapshot(adapterPosition).getReference().delete();
    }

    public class shop_sholder extends RecyclerView.ViewHolder {
        uploadShop_model uploadShopModels;
        Button btn_delete, btn_update;
        ImageView images;
        TextView shop_name, location_address ,ratenum;
        RatingBar StarRatings;
        Context context;
;

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

            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position =  getAdapterPosition();
                    getSnapshots().getSnapshot(position).getReference().delete();
                }
            });

            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), admin_myShop_update.class);
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

            shop_name.setText(uploadShopModel.getShop_name());
            location_address.setText(uploadShopModel.getLocation_address());
            float average = uploadShopModel.getMyrate()/uploadShopModel.getNumrate() ;
            StarRatings.setRating(average);
            ratenum.setText("("+uploadShopModel.getNumrate()+")");
            uploadShopModels = uploadShopModel;

        }

    }


}
