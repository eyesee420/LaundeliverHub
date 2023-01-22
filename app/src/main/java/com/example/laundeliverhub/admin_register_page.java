package com.example.laundeliverhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class admin_register_page extends AppCompatActivity {

    private EditText full_name, userpasword, admin_phonenumber, current_address, email_address;
    Button register_user_btn;
    private FirebaseAuth mAuth;
    boolean is_user = false;
    boolean is_admin = true;
    boolean is_shopowner;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Create_Users_Model createUsersModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register_page);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //firestore database
        //FirebaseFirestore db = FirebaseFirestore.getInstance();

        full_name = findViewById(R.id.full_name);
        admin_phonenumber = findViewById(R.id.admin_phonenumber);
        current_address = findViewById(R.id.current_address);
        email_address = findViewById(R.id.email_address);
        userpasword = (EditText) findViewById(R.id.password_register);
        register_user_btn = (Button) findViewById(R.id.register_btn);

        ProgressBar progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);

        register_user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = full_name.getText().toString().trim();
                String adminphonenumber = admin_phonenumber.getText().toString().trim();
                String currentaddress = current_address.getText().toString().trim();
                String emailaddress = email_address.getText().toString().trim();
                String password = userpasword.getText().toString().trim();
                boolean is_users = is_user;
                boolean is_admins = is_admin;
                boolean is_shopowners = is_shopowner;



                if (fullname.isEmpty()) {
                    full_name.setError("Fullname is Required!");
                    full_name.requestFocus();
                    return;
                }
                if (adminphonenumber.length() < 11) {
                    admin_phonenumber.setError("Phone Number is invalid!");
                    admin_phonenumber.requestFocus();
                    return;
                }
                if (currentaddress.isEmpty()) {
                    current_address.setError("Current Address is Required!");
                    current_address.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(emailaddress).matches()) {
                    email_address.setError("Please Provide Valid Email!");
                    email_address.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    userpasword.setError("Password is Required!");
                    userpasword.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    userpasword.setError("Min Password Length should be 6 characters!");
                    userpasword.requestFocus();
                    return;
                }
                //end of regex
                progressBar.setVisibility(View.VISIBLE);


                //Custom method for datas
                createUsersModel= new Create_Users_Model(fullname,currentaddress,emailaddress, adminphonenumber,is_users, is_admins,is_shopowners);
                //adding data to firestore
                mAuth.createUserWithEmailAndPassword(emailaddress, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

//                                    //set the user id
                                    // userId =mAuth.getCurrentUser().getUid();
                                    FirebaseUser userid = mAuth.getCurrentUser();
                                    //create collections with generated auth id and add data from custom create user class
                                    DocumentReference documentReference = db.collection("users").document(userid.getUid());
                                    documentReference.set(createUsersModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(admin_register_page.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(admin_register_page.this, admin_login_page.class);
                                            startActivity(intent);
                                            progressBar.setVisibility(View.INVISIBLE);

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(admin_register_page.this, "Failed to register", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.INVISIBLE);
                                        }
                                    });

                                }
                            }
                        });


            }

        });





    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}