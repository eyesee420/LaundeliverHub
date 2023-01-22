package com.example.laundeliverhub;

import static android.content.Context.NOTIFICATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class view_orders_adapter extends FirestoreRecyclerAdapter<view_orders_model, view_orders_adapter.ordersholder>{


    public view_orders_adapter(@NonNull FirestoreRecyclerOptions<view_orders_model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull view_orders_adapter.ordersholder holder , int i, view_orders_model models) {
        holder.bind(models);

    }

    @NonNull
    @Override
    public view_orders_adapter.ordersholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_my_orders3, parent, false);
        return new view_orders_adapter.ordersholder(view);

    }


    public void deleteitems(int adapterPosition) {
        getSnapshots().getSnapshot(adapterPosition).getReference().delete();
    }


    public class ordersholder extends RecyclerView.ViewHolder {
        view_orders_model viewOrdersModels;
        RelativeLayout relala1, relala2,relala3,relala4,relala5,relala6;
        CardView ctat1 ,ctat2 ,ctat3 ,ctat4 ,ctat5 ;
        Context context;
        TextView shop_name, shop_location ,payment_method,total_amount, date_time,
                delivery_method ,order_status ,current_time;

        TextView order_status1,order_status2,order_status3,order_status4,order_status5;
        TextView current_time1,current_time2,current_time3,current_time4,current_time5;
        Button btn_view;

        public ordersholder(@NonNull View itemView) {
            super(itemView);

            context =itemView.getContext();
            date_time = itemView.findViewById(R.id.date_time);
            shop_name = itemView.findViewById(R.id.shop_name);
            shop_location = itemView.findViewById(R.id.shop_location);
            delivery_method = itemView.findViewById(R.id.delivery_method);
            payment_method = itemView.findViewById(R.id.payment_method);
            total_amount = itemView.findViewById(R.id.total_amount);
            order_status = itemView.findViewById(R.id.order_status);
            btn_view = itemView.findViewById(R.id.btn_view);
            current_time = itemView.findViewById(R.id.current_time);

            order_status1 = itemView.findViewById(R.id.order_status1);
            order_status2 = itemView.findViewById(R.id.order_status2);
            order_status3 = itemView.findViewById(R.id.order_status3);
            order_status4 = itemView.findViewById(R.id.order_status4);
            order_status5 = itemView.findViewById(R.id.order_status5);

            current_time1 = itemView.findViewById(R.id.current_time1);
            current_time2 = itemView.findViewById(R.id.current_time2);
            current_time3 = itemView.findViewById(R.id.current_time3);
            current_time4 = itemView.findViewById(R.id.current_time4);
            current_time5 = itemView.findViewById(R.id.current_time5);


            relala2 = itemView.findViewById(R.id.relala2);
            relala3 = itemView.findViewById(R.id.relala3);
            relala4 = itemView.findViewById(R.id.relala4);
            relala5 = itemView.findViewById(R.id.relala5);
            relala6 = itemView.findViewById(R.id.relala6);

            ctat1 = itemView.findViewById(R.id.ctat1);
            ctat2 = itemView.findViewById(R.id.ctat2);
            ctat3 = itemView.findViewById(R.id.ctat3);
            ctat4 = itemView.findViewById(R.id.ctat4);
            ctat5 = itemView.findViewById(R.id.ctat5);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Intent intent = new Intent(itemView.getContext(), my_orders_final.class);
                        intent.putExtra("viewOrdersModels",viewOrdersModels);
                        itemView.getContext().startActivity(intent);
                         ((Activity) context).finish();
                }
            });
            btn_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), my_orders_final.class);
                    intent.putExtra("viewOrdersModels",viewOrdersModels);

                    ((Activity) context).finish();
                    itemView.getContext().startActivity(intent);
                }
            });

        }

        public void bind(view_orders_model viewOrdersModel) {

            date_time.setText(viewOrdersModel.getMtimestamp());
            shop_name.setText(viewOrdersModel.getShop_name());
            shop_location.setText(viewOrdersModel.getLocation_address());
            payment_method.setText(viewOrdersModel.getPayments());
            delivery_method.setText(viewOrdersModel.getPick_up_delivery());
            total_amount.setText("₱ "+viewOrdersModel.getTotal_price());

           // order_status.setText(viewOrdersModel.getStatus());
            current_time.setText(viewOrdersModel.getDel_time());
            viewOrdersModels = viewOrdersModel;

            order_status1.setText(viewOrdersModel.getStatus1());
            order_status2.setText(viewOrdersModel.getStatus2());
            order_status3.setText(viewOrdersModel.getStatus3());
            order_status4.setText(viewOrdersModel.getStatus4());
            order_status5.setText(viewOrdersModel.getStatus5());

            current_time1.setText(viewOrdersModel.getCurrent_time1());
            current_time2.setText(viewOrdersModel.getCurrent_time2());
            current_time3.setText(viewOrdersModel.getCurrent_time3());
            current_time4.setText(viewOrdersModel.getCurrent_time4());
            current_time5.setText(viewOrdersModel.getCurrent_time5());



            String s1 = "RIDER ON THE WAY";
            String s2 = "SHOP RECIVED YOUR ORDERS";
            String s3 = "LAUNDRY COMPLETE";
            String s4 = "ITEMS ON DELIVERY";
            String s5 = "ORDERS COMPLETE";


            if(s1.trim().equals(viewOrdersModel.getStatus1())){
                order_status1.setVisibility(View.VISIBLE);
                order_status1.setTextColor(context.getColor(R.color.white));
                relala2.setVisibility(View.VISIBLE);
                ctat1.setVisibility(View.VISIBLE);

            }
            if(s2.equals(viewOrdersModel.getStatus2())){
                order_status2.setVisibility(View.VISIBLE);
                order_status2.setTextColor(context.getColor(R.color.white));
                relala3.setVisibility(View.VISIBLE);
                ctat2.setVisibility(View.VISIBLE);

            }
            if(s3.equals(viewOrdersModel.getStatus3())){
                order_status3.setVisibility(View.VISIBLE);
                order_status3.setTextColor(context.getColor(R.color.white));
                relala4.setVisibility(View.VISIBLE);
                ctat3.setVisibility(View.VISIBLE);

            }
            if(s4.equals(viewOrdersModel.getStatus4())){
                order_status4.setVisibility(View.VISIBLE);
                relala5.setVisibility(View.VISIBLE);
                ctat4.setVisibility(View.VISIBLE);

            }
            if(s5.equals(viewOrdersModel.getStatus5())){
                order_status5.setVisibility(View.VISIBLE);
                relala6.setVisibility(View.VISIBLE);
                ctat5.setVisibility(View.VISIBLE);
            }



        }
    }




}
