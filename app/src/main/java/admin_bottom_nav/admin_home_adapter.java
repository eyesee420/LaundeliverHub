package admin_bottom_nav;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laundeliverhub.Create_Users_Model;
import com.example.laundeliverhub.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class admin_home_adapter  extends FirestoreRecyclerAdapter<Create_Users_Model, admin_home_adapter.home_sholder> {

    public admin_home_adapter(@NonNull FirestoreRecyclerOptions<Create_Users_Model> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull admin_home_adapter.home_sholder home_sholder, int i, @NonNull Create_Users_Model model) {
        home_sholder.bind(model);
    }

    @NonNull
    @Override
    public admin_home_adapter.home_sholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.users_row, parent, false);
        return new admin_home_adapter.home_sholder (view);
    }

    public void deleteitems(int adapterPosition) {
   //     Create_Users_Model createUsersModel;
        getSnapshots().getSnapshot(adapterPosition).getReference().delete();

    }


    public class home_sholder extends RecyclerView.ViewHolder {
        private FirebaseAuth mAuth;
        FirebaseUser firebaseUser;
        Button btn_delete;
        TextView fullname, currentaddress,phonenumber,emailaddress;
        public home_sholder(@NonNull View itemView) {
            super(itemView);

            firebaseUser =FirebaseAuth.getInstance().getCurrentUser();
            fullname = itemView.findViewById(R.id.fullname);
            currentaddress = itemView.findViewById(R.id.currentaddress);
            phonenumber = itemView.findViewById(R.id.phonenumber);
            emailaddress = itemView.findViewById(R.id.emailaddress);
            btn_delete = itemView.findViewById(R.id.btn_delete);

            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position =  getAdapterPosition();
                    getSnapshots().getSnapshot(position).getReference().delete();
                }
            });


        }



        public void bind(Create_Users_Model model) {

            fullname.setText(model.getFullname());
            currentaddress.setText(model.getCurrentaddress());
            phonenumber.setText(model.getPhonenumber());
            emailaddress.setText(model.getEmailaddress());

            model = model;
        }
    }
}
