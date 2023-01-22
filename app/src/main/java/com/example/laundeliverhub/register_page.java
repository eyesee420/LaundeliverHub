package com.example.laundeliverhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Pattern;

public class register_page extends AppCompatActivity {

    private static final String TAG = "register_page";
    //declare regular expressions important!!!
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[_()*!@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$");
    Button register_user_btn;
    boolean is_user = true;
    boolean is_admin = false;
    Create_Users_Model createUsersModel;
    private EditText full_name, phone_number,userpasword, current_address, email_address;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser user;
    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);


        // Initialize Firebase Auth and firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user=FirebaseAuth.getInstance().getCurrentUser();
        createUsersModel = new Create_Users_Model();
        full_name = findViewById(R.id.full_name);
        phone_number = findViewById(R.id.phone_number);
        current_address = findViewById(R.id.current_address);
        email_address = findViewById(R.id.email_address);
        userpasword = findViewById(R.id.password_register);
        register_user_btn = findViewById(R.id.register_btn);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);

        register_user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = full_name.getText().toString().trim();
                String phonenumber = phone_number.getText().toString().trim();
                String currentaddress = current_address.getText().toString().trim();
                String emailaddress = email_address.getText().toString().trim();
                String password = userpasword.getText().toString().trim();
                boolean is_users = is_user;
                boolean is_admins = is_admin;

                if (fullname.isEmpty()) {
                    full_name.setError("Fullname is Required!");
                    full_name.requestFocus();
                    return;
                }
                if (phonenumber.length() < 11) {
                    phone_number.setError("Phone Number is invalid!");
                    phone_number.requestFocus();
                    return;
                }
                if (currentaddress.isEmpty()) {
                    current_address.setError("Current Address is Required!");
                    current_address.requestFocus();
                    return;
                }
                if (!EMAIL_PATTERN.matcher(emailaddress).matches()) {
                    email_address.setError("Please Provide Valid Email!");
                    email_address.requestFocus();
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
                if (!PASSWORD_PATTERN.matcher(password).matches()) {
                    userpasword.setError("At least 1 number, 1 lower case, 1 upper case,1 Symbol");
                    //userpasword.setError("Password is too weak");
                    return;
                }

                if (password.length() < 6) {
                    userpasword.setError("Min Password Length should be 6 characters!");
                    userpasword.requestFocus();
                    return;
                }

                createUsersModel.setFullname(fullname);
                createUsersModel.setCurrentaddress(currentaddress);
                createUsersModel.setEmailaddress(emailaddress);
                createUsersModel.setPhonenumber(phonenumber);
                createUsersModel.setIs_user(is_user);
                createUsersModel.setIs_admin(is_admin);
                //end of regex
                progressBar.setVisibility(View.VISIBLE);

                //adding data to firestore
                  mAuth.createUserWithEmailAndPassword(emailaddress, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                         createUsersModel = new Create_Users_Model(createUsersModel.getFullname(),createUsersModel.getPhonenumber(),createUsersModel.getCurrentaddress()
                                        ,createUsersModel.getEmailaddress(),createUsersModel.is_user,createUsersModel.is_admin ,createUsersModel.is_shopowner);

                                        FirebaseUser userid = mAuth.getCurrentUser();
                                        //create collections with generated auth id and add data from custom create user class
                                        db.collection("users").document(userid.getUid())
                                                .set(createUsersModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        mAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                Toast.makeText(register_page.this, "Registered Succesfully Please Check Your Email", Toast.LENGTH_SHORT).show();
                                                                progressBar.setVisibility(View.INVISIBLE);
                                                                startActivity(new Intent(register_page.this , MainActivity.class));
                                                                finish();
                                                            }
                                                        });
                                                    }
                                                });

                                    }if(!task.isSuccessful()){
                                        Toast.makeText(register_page.this, "Already have an Account please sign in different account", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(register_page.this , MainActivity.class));
                                        finish();
                                    }//end of if
                                }

                            });
                }

        });//button add
    }


    @Override
    public void onBackPressed() {
                    super.onBackPressed();
                    startActivity(new Intent(register_page.this ,shopOwners_login_page.class));
                    finish();
                }
            }