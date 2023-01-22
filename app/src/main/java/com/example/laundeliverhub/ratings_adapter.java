package com.example.laundeliverhub;

import android.content.Context;
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
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ratings_adapter extends FirestoreRecyclerAdapter<rating_model, ratings_adapter.holder> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ratings_adapter(@NonNull FirestoreRecyclerOptions<rating_model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ratings_adapter.holder holder, int i, @NonNull rating_model models) {
        holder.bind(models);
    }

    @NonNull
    @Override
    public ratings_adapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shop_rate, parent, false);
        return new ratings_adapter.holder(view);
    }

    public class holder extends RecyclerView.ViewHolder {
        rating_model ratingModel;
        Context context;
        ImageView image_view, image_view_null;
        RatingBar StarRatings;
        TextView display_name, num_rate , date_time ,text_reviews ;


        public holder(@NonNull View itemView) {
            super(itemView);

            context =itemView.getContext();
            image_view_null = itemView.findViewById(R.id.image_view_null);
            image_view = itemView.findViewById(R.id.image_view);
            StarRatings = itemView.findViewById(R.id.StarRatings);
            display_name = itemView.findViewById(R.id.display_name);
            date_time = itemView.findViewById(R.id.date_time);
            num_rate = itemView.findViewById(R.id.num_rate);
            text_reviews = itemView.findViewById(R.id.text_reviews);

        }

        public void bind(rating_model ratingModel) {

            StarRatings.setRating(ratingModel.getMyrate());
            display_name.setText(ratingModel.getDisplay_name());
            date_time.setText(ratingModel.getDatetime());
            num_rate.setText( "("+ratingModel.getMyrate() + ")");
            text_reviews.setText(ratingModel.getDiscription());
            if(ratingModel.getMy_image() == null){
                image_view_null.setVisibility(View.VISIBLE);
            }

            Glide.with(context.getApplicationContext())
                    .load(ratingModel.getMy_image())
                    .into(image_view);

            ratingModel = ratingModel;


        }
    }
}
