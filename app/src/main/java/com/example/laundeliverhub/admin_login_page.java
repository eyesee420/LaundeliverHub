package com.example.laundeliverhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import admin_bottom_nav.admin_home;
import admin_bottom_nav.admin_myShop;
import users_bottom_nav.users_home;

public class admin_login_page extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    EditText email, password;
    Button login_btn;

    TextView admin_login, txt_create_user, forgot_password ,login_shopOwner;
    CheckBox remembermber_me;
    private static final String TAG = "login admin page";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        email = findViewById(R.id.user_email_login);
        password = findViewById(R.id.user_password_login);
        login_shopOwner = findViewById(R.id.login_shopOwner);
        login_btn = findViewById(R.id.user_login_btn);
        admin_login = findViewById(R.id.login_admin);
        txt_create_user = findViewById(R.id.create_account);
        forgot_password= findViewById(R.id.forgot_password);
        remembermber_me= findViewById(R.id.remember_me);

        ProgressBar progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);

        SharedPreferences preferences = getSharedPreferences("admin_login" ,MODE_PRIVATE);
        String checkbox = preferences.getString("remembers", "");
        if(checkbox.equals("true")){
            Intent intent = new Intent(admin_login_page.this, admin_myShop.class);
            startActivity(intent);
            finish();
        }
        else if(checkbox.equals("false")){
            //Toast.makeText(this, "Please sign in", Toast.LENGTH_SHORT).show();
        }
        remembermber_me.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("admin_login" ,MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remembers","true");
                    editor.apply();
                    Toast.makeText(admin_login_page.this, "Remember me", Toast.LENGTH_SHORT).show();
                }else if (!compoundButton.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("admin_login" ,MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remembers","false");
                    editor.apply();
                    Toast.makeText(admin_login_page.this, ".......", Toast.LENGTH_SHORT).show();
                }
            }
        });



        login_shopOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(admin_login_page.this
                        ,shopOwners_login_page.class);
                startActivity(intent);

            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailaddress = email.getText().toString().trim();
                String userpassword = password.getText().toString().trim();

                if (!Patterns.EMAIL_ADDRESS.matcher(emailaddress).matches()) {
                    email.setError("Please Provide Valid Email!");
                    email.requestFocus();
                    return;
                }
                if (userpassword.isEmpty()) {
                    password.setError("Password is Required!");
                    password.requestFocus();
                    return;
                }
                if (userpassword.length() < 6) {
                    password.setError("Min Password Length should be 6 characters!");
                    password.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(emailaddress, userpassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                if(mAuth.getCurrentUser().isEmailVerified()){
                                    checkAccess(authResult.getUser().getUid());
                                    progressBar.setVisibility(View.INVISIBLE);
                                }else{
                                    Toast.makeText(getApplicationContext(), "Please Verify your Email Address", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.INVISIBLE);
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(admin_login_page.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });



            }
        });



        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_login_page.this, forgot_password_page.class);
                Toast.makeText(admin_login_page.this, "reset password", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });


    }

    private void checkAccess(String uid) {

        DocumentReference df = db.collection("users").document(uid);

        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d(TAG, "on success" + documentSnapshot.getData());

                if(documentSnapshot.getBoolean("is_admin")!=false){

                    Toast.makeText(getApplicationContext(), "Welcome to Laundeliver", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(admin_login_page.this, admin_myShop.class));
//                    Intent i = new Intent();
//                    i.setClassName("com.example.laundeliverhub", "admin_bottom_nav.admin_myShop");
//                    startActivity(i);
                    Log.d(TAG, "on success" + documentSnapshot.getData());
                    finish();
                }
//
//                if(documentSnapshot.getBoolean("is_user")!=false){
//
//                    Toast.makeText(getApplicationContext(), "Sorry your not allowed", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(admin_login_page.this, MainActivity.class));
//                    Log.d(TAG, "on success" + documentSnapshot.getData());
//                    finish();
//                }


            }
        });

    }
}