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
import com.example.laundeliverhub.admin_completed_orders_final;
import com.example.laundeliverhub.my_orders_final_admin;
import com.example.laundeliverhub.view_orders_model;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class admin_compeleted_orders_adapter extends FirestoreRecyclerAdapter<view_orders_model, admin_compeleted_orders_adapter.holder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public admin_compeleted_orders_adapter(@NonNull FirestoreRecyclerOptions<view_orders_model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull admin_compeleted_orders_adapter.holder holder, int i, @NonNull view_orders_model model) {
        holder.bind(model);
    }

    @NonNull
    @Override
    public admin_compeleted_orders_adapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_completed_orders, parent, false);
        return new admin_compeleted_orders_adapter.holder(view);
    }

    public class holder extends RecyclerView.ViewHolder {

        view_orders_model viewOrdersModels;
        Context context;
        TextView shop_name, shop_location ,payment_method,total_amount, date_time, delivery_method ,order_status ,current_time;
        Button btn_view;
        public holder(@NonNull View itemView) {
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

            btn_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), admin_completed_orders_final.class);
                    intent.putExtra("viewOrdersModels1",viewOrdersModels);
                    itemView.getContext().startActivity(intent);
                    ((Activity) context).finish();

                }
            });
        }

        public void bind(view_orders_model model) {
            date_time.setText(model.getMtimestamp());
            shop_name.setText(model.getShop_name());
            shop_location.setText(model.getLocation_address());
            payment_method.setText(model.getPayments());
            delivery_method.setText(model.getPick_up_delivery());
            total_amount.setText(model.getTotal_price()+" ₱");
            order_status.setText(model.getStatus());
            current_time.setText(model.getDel_time());

            viewOrdersModels = model;
        }
    }
}
