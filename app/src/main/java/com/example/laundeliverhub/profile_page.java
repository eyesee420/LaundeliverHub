package com.example.laundeliverhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import users_bottom_nav.users_home;

public class profile_page extends AppCompatActivity {

    FirebaseFirestore db;
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClients;
    ToggleButton btn_edt;
    ImageView image_view;
    FirebaseAuth mAuth;
    Button update_btn;
    EditText txt_full_name, txt_phone_number, txt_current_address, txt_email_address;
    Create_Users_Model create_users;
    Button log_out_btn;
    boolean is_user = true;
    boolean is_admin = false;
    ProgressBar progressBar;
    String email;
    private static final String TAG = "profile_page";

    @Override
    protected void onStart() {
        super.onStart();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClients = GoogleSignIn.getClient(profile_page.this, gso);

        FirebaseUser userid = mAuth.getCurrentUser();
        DocumentReference docRef = db.collection("users").document(userid.getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Create_Users_Model create = documentSnapshot.toObject(Create_Users_Model.class);
                if (create != null) {
                    txt_full_name = findViewById(R.id.txt_full_name);
                    txt_phone_number = findViewById(R.id.txt_phone_number);
                    txt_current_address = findViewById(R.id.txt_current_address);
                    txt_email_address = findViewById(R.id.txt_email_address);

                    txt_full_name.setText(create.getFullname());
                    txt_phone_number.setText(create.getPhonenumber());
                    txt_current_address.setText(create.getCurrentaddress());
                   // email = create.getEmailaddress();
                    txt_email_address.setText(create.getEmailaddress());

                }
            }
        });

        image_view = findViewById(R.id.image_view);
        txt_current_address = findViewById(R.id.txt_current_address);
        txt_phone_number = findViewById(R.id.txt_phone_number);
        txt_full_name = findViewById(R.id.txt_full_name);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
              email = acct.getEmail();

              String displayName = acct.getDisplayName();

            String emailaddress =acct.getEmail();
            txt_email_address.setText(emailaddress);
            txt_full_name.setText(personName);
            //   txt_current_address.setText(personEmail);


            Glide.with(getApplicationContext())
                    .load(acct.getPhotoUrl())
                    .into(image_view);
        }



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        update_btn = findViewById(R.id.update_btn);
        log_out_btn = findViewById(R.id.log_out_btn);
        btn_edt = findViewById(R.id.btn_edt);

        image_view = findViewById(R.id.image_view);
        txt_current_address = findViewById(R.id.txt_current_address);
        txt_phone_number = findViewById(R.id.txt_phone_number);
        txt_full_name = findViewById(R.id.txt_full_name);
        txt_email_address = findViewById(R.id.txt_email_address);

        progressBar = (ProgressBar) findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);

        create_users = new Create_Users_Model();





        btn_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn_edt.isChecked()){
                 txt_current_address.setEnabled(true);
                 txt_phone_number.setEnabled(true);
                 txt_full_name.setEnabled(true);
                }
                if(btn_edt.isChecked() == false){
                    txt_current_address.setEnabled(false);
                    txt_phone_number.setEnabled(false);
                    txt_full_name.setEnabled(false);
                }
            }
        });


        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create update method
                update_profile();


            }
        });

        log_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create logout method
                logout();
            }
        });

    }



    private void logout() {
        //mAuth.getInstance().signOut();
        SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));


        finish();
        mGoogleSignInClients.signOut();

    }


    private void update_profile() {
        progressBar.setVisibility(View.VISIBLE);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            email = acct.getEmail();

        }
        String fullname = txt_full_name.getText().toString().trim();
        String phonenumber = txt_phone_number.getText().toString().trim();
        String currentaddress = txt_current_address.getText().toString().trim();
        String emailaddress = txt_email_address.getText().toString().trim();

        create_users.setFullname(fullname);
        create_users.setPhonenumber(phonenumber);
        create_users.setCurrentaddress(currentaddress);
        create_users.setIs_user(is_user);
        create_users.setIs_admin(is_admin);
        create_users.setEmailaddress(emailaddress);


        if (phonenumber.length() < 11 || phonenumber.length() > 13) {
            txt_phone_number.setError("Phone Number is invalid!");
            txt_phone_number.requestFocus();
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }
        if (currentaddress.length() < 20 ) {
            txt_current_address.setError("Please enter valid address!");
            txt_current_address.requestFocus();
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }
        if (!Patterns.PHONE.matcher(phonenumber).matches()) {
            txt_phone_number.setError("Phone Number is invalid!");
            txt_phone_number.requestFocus();
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }

        FirebaseUser userid = mAuth.getCurrentUser();
        //  FirebaseUser userid = FirebaseAuth.getInstance().getCurrentUser();
         create_users  = new Create_Users_Model(create_users.getFullname(), create_users.getPhonenumber(), create_users.getCurrentaddress()
        ,create_users.emailaddress,create_users.is_user,create_users.is_admin ,create_users.is_shopowner);
        db.collection("users").document(userid.getUid())
                .set(create_users).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Profile Updated ", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        txt_current_address.setEnabled(false);
                        txt_phone_number.setEnabled(false);
                        txt_full_name.setEnabled(false);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "failed ", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });



        }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,users_home.class));
        finish();
    }
}