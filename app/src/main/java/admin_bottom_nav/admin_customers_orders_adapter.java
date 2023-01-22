package admin_bottom_nav;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laundeliverhub.R;
import com.example.laundeliverhub.my_orders_final_admin;
import com.example.laundeliverhub.view_orders_model;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class admin_customers_orders_adapter extends FirestoreRecyclerAdapter<view_orders_model, admin_customers_orders_adapter.ordersholder> {

    public admin_customers_orders_adapter(@NonNull FirestoreRecyclerOptions<view_orders_model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ordersholder ordersholder, int i, @NonNull view_orders_model model) {
        ordersholder.bind(model);
    }

    public void deleteitems(int position) {
      getSnapshots().getSnapshot(position).getReference().delete();

    }
    @NonNull
    @Override
    public ordersholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_my_orders3_admin, parent, false);
        return new ordersholder(view);

    }

    public class ordersholder extends RecyclerView.ViewHolder {

        view_orders_model viewOrdersModels;
        Context context;
        Button btn1 ,btn_view ,btn2,btn3 ,btn4, btn5;
        TextView shop_name, shop_location ,payment_method,total_amount, date_time, delivery_method ,order_status,current_time;

        public ordersholder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            date_time = itemView.findViewById(R.id.date_time);
            shop_name = itemView.findViewById(R.id.shop_name);
            shop_location = itemView.findViewById(R.id.shop_location);
            delivery_method = itemView.findViewById(R.id.delivery_method);
            payment_method = itemView.findViewById(R.id.payment_method);
            total_amount = itemView.findViewById(R.id.total_amount);
            order_status = itemView.findViewById(R.id.order_status);
            current_time = itemView.findViewById(R.id.current_time);
            btn_view = itemView.findViewById(R.id.btn_view);
            btn1 = itemView.findViewById(R.id.btn1);
            btn2 = itemView.findViewById(R.id.btn2);
            btn3 = itemView.findViewById(R.id.btn3);
            btn4 = itemView.findViewById(R.id.btn4);
            btn5 = itemView.findViewById(R.id.btn5);

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm:ss a");
            String time = currentTime.format(calendar.getTime());


            btn_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), my_orders_final_admin.class);
                    intent.putExtra("viewOrdersModels1",viewOrdersModels);
                    itemView.getContext().startActivity(intent);
                    ((Activity) context).finish();
                }
            });

            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm:ss a");
                    String time1 = currentTime.format(calendar.getTime());

                    int position =  getAdapterPosition();
                    getSnapshots().getSnapshot(position).getReference().update("status1","RIDER ON THE WAY");
                    getSnapshots().getSnapshot(position).getReference().update("status","RIDER ON THE WAY");
                    int position1 =  getAdapterPosition();
                    getSnapshots().getSnapshot(position1).getReference().update("del_time",time);
                    getSnapshots().getSnapshot(position1).getReference().update("current_time1",time1);
                }
            });
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm:ss a");
                    String time2 = currentTime.format(calendar.getTime());


                    int position =  getAdapterPosition();
                    getSnapshots().getSnapshot(position).getReference().update("status2","SHOP RECIVED YOUR ORDERS");
                    getSnapshots().getSnapshot(position).getReference().update("status","SHOP RECIVED YOUR ORDERS");
                    int position1 =  getAdapterPosition();
                    getSnapshots().getSnapshot(position1).getReference().update("del_time",time);
                    getSnapshots().getSnapshot(position1).getReference().update("current_time2",time2);

                }
            });
            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm:ss a");
                    String time3 = currentTime.format(calendar.getTime());

                    int position =  getAdapterPosition();
                    getSnapshots().getSnapshot(position).getReference().update("status3","LAUNDRY COMPLETE");
                    getSnapshots().getSnapshot(position).getReference().update("status","LAUNDRY COMPLETE");
                    int position1 =  getAdapterPosition();
                    getSnapshots().getSnapshot(position1).getReference().update("del_time",time);
                    getSnapshots().getSnapshot(position1).getReference().update("current_time3",time3);
                }
            });
            btn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm:ss a");
                    String time4 = currentTime.format(calendar.getTime());

                    int position =  getAdapterPosition();
                    getSnapshots().getSnapshot(position).getReference().update("status4","ITEMS ON DELIVERY");
                    getSnapshots().getSnapshot(position).getReference().update("status","ITEMS ON DELIVERY");
                    int position1 =  getAdapterPosition();
                    getSnapshots().getSnapshot(position1).getReference().update("del_time",time);
                    getSnapshots().getSnapshot(position1).getReference().update("current_time4",time4);
                }
            });
            btn5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm:ss a");
                    String time5 = currentTime.format(calendar.getTime());

                    int position =  getAdapterPosition();
                    getSnapshots().getSnapshot(position).getReference().update("status5","ORDERS COMPLETE");
                    getSnapshots().getSnapshot(position).getReference().update("status","ORDERS COMPLETE");
                    int position1 =  getAdapterPosition();
                    getSnapshots().getSnapshot(position1).getReference().update("del_time",time);
                    getSnapshots().getSnapshot(position1).getReference().update("current_time5",time5);
                }
            });
        }

        public void bind(view_orders_model viewOrdersModel) {
            date_time.setText(viewOrdersModel.getMtimestamp());
            shop_name.setText(viewOrdersModel.getShop_name());
            shop_location.setText(viewOrdersModel.getLocation_address());
            payment_method.setText(viewOrdersModel.getPayments());
            delivery_method.setText(viewOrdersModel.getPick_up_delivery());
            total_amount.setText(viewOrdersModel.getTotal_price()+" ₱");
            order_status.setText(viewOrdersModel.getStatus());
            current_time.setText(viewOrdersModel.getDel_time());


            viewOrdersModels = viewOrdersModel;


        }
    }





}
