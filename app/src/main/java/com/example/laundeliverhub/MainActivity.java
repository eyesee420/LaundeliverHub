package com.example.laundeliverhub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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


import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import admin_bottom_nav.admin_home;
import users_bottom_nav.users_home;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    EditText email, password;
    Button login_btn ;
    TextView admin_login, txt_create_user, forgot_password;
    CheckBox remembermber_me;
    private SignInButton sign_in_button;
    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;;
    private int RC_SIGN_IN = 1;
    ProgressBar progressBar;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth and firebase firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);




        email = findViewById(R.id.user_email_login);
        password = findViewById(R.id.user_password_login);
        login_btn = findViewById(R.id.user_login_btn);
        admin_login = findViewById(R.id.login_admin);
        txt_create_user = findViewById(R.id.create_account);
        forgot_password= findViewById(R.id.forgot_password);
        remembermber_me= findViewById(R.id.remember_me);


        sign_in_button = findViewById(R.id.sign_in_button);
        progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);

        SharedPreferences preferences = getSharedPreferences("login" ,MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");
        if(checkbox.equals("true")){
            Intent intent = new Intent(this, users_home.class);
            startActivity(intent);
            finish();
            Log.d(TAG, "checkbox: true ");
        }
        else if(checkbox.equals("false")){
            Log.d(TAG, "checkbox: false ");
        }

        remembermber_me.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("login" ,MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","true");
                    editor.apply();
                    Toast.makeText(MainActivity.this, "Remember me", Toast.LENGTH_SHORT).show();
                }else if (!compoundButton.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("login" ,MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","false");
                    editor.apply();
                }
            }
        });

        txt_create_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, register_page.class);
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
                progressBar.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(emailaddress, userpassword).
                        addOnSuccessListener(new OnSuccessListener<AuthResult>() {
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
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        });

            }
        });


        admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), admin_login_page.class);
                Toast.makeText(getApplicationContext(), "welcome admin", Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), forgot_password_page.class);
                Toast.makeText(getApplicationContext(), "reset password", Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
        });

        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signIn();
            }
        });



    }

    ///  show  gooogle sign in dialog box
    //implicit intent
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    // Check for existing Google Sign In account, if the user is already signed in
    // the GoogleSignInAccount will be non-null.
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try{

            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            Log.d(TAG, "handleSignInResult: " + "Signed In Successfully ");
            FirebaseGoogleAuth(acc);
            progressBar.setVisibility(View.VISIBLE);

        }
        catch (ApiException e){
            Toast.makeText(MainActivity.this,"Failed to sign with Google",Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);
        }

    }

    /// create auhtentication account for google sign in
    private void FirebaseGoogleAuth(GoogleSignInAccount acct) {
        //check if the account is null
        if (acct != null) {
            AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = mAuth.getCurrentUser();

                        updateUI(user);
                    } else {
                        Log.d(TAG, "FirebaseGoogleAuth result: " + "failed");
                        updateUI(null);
                    }
                }
            });
        }
        else{
            Log.d(TAG, "FirebaseGoogleAuth result: " + "failed");
        }
    }

    // if the account is ready can continue to homepage
    private void updateUI(FirebaseUser user) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if(account !=  null){
            progressBar.setVisibility(View.INVISIBLE);
            navigateToSecondActivity();

        }

    }


    void navigateToSecondActivity(){
        Intent i = new Intent();
        i.setClassName("com.example.laundeliverhub", "users_bottom_nav.users_home");
        startActivity(i);
        finish();
    }


    //access level for users only
    private void checkAccess(String uid) {
        DocumentReference df = db.collection("users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if(documentSnapshot.getBoolean("is_user")!=false){

                    Toast.makeText(getApplicationContext(), "Welcome to Laundeliver", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), users_home.class));
                    Log.d(TAG, "on success" + documentSnapshot.getData());
                    finish();

                }
            }
        });
    }

}
