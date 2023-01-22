package admin_bottom_nav;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.laundeliverhub.R;
import com.example.laundeliverhub.uploadShop_model;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class admin_addProducts extends AppCompatActivity {
    Uri uri;
    ImageView shop_imageview;

    ImagePicker imagePicker;
    Button add_shop_services_btn ,btn_back;
    EditText shopname, shoplocation ,laundry_phoneNumber;
    EditText wd1;
    EditText wd2;
    EditText wd3;
    EditText wd4;
    EditText wd5 ,iron_fold , addwash,bedshetsC_txt,blankCurt_txt;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference reference = FirebaseStorage.getInstance().getReference();
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_products);
        mAuth = FirebaseAuth.getInstance();

        add_shop_services_btn = findViewById(R.id.add_shop_services_btn);
        add_shop_services_btn = findViewById(R.id.add_shop_services_btn);

        shop_imageview = findViewById(R.id.shop_imageview);
        shopname = findViewById(R.id.shop_name_edittxt);
        shoplocation = findViewById(R.id.laundry_location_edittxt);
        laundry_phoneNumber = findViewById(R.id.laundry_phoneNumber);
        wd1 = findViewById(R.id.washdry_kilo1);
        wd2 = findViewById(R.id.washdry_kilo2);
        wd3 = findViewById(R.id.washdry_kilo3);
        wd4 = findViewById(R.id.washdry_kilo4);
        wd5 = findViewById(R.id.washdry_kilo5);
        iron_fold = findViewById(R.id.iron_fold);
        addwash = findViewById(R.id.addwash);
        bedshetsC_txt = findViewById(R.id.bedshetsC_txt);
        blankCurt_txt = findViewById(R.id.blankCurt_txt);

        btn_back = findViewById(R.id.btn_back);
        //declair and initialize progressbar
         progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        shop_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePicker.Companion.with(admin_addProducts.this)
                        // .crop()                    //Crop image(Optional), Check Customization for more option
                        // .compress(1024)            //Final image size will be less than 1 MB(Optional)
                      //   .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();

            }
        });

        add_shop_services_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uri != null) {
                    progressBar.setVisibility(View.VISIBLE);
                    uploadtofirebase(uri);

                } else {
                    Toast.makeText(admin_addProducts.this, "Please select image", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }


    private void uploadtofirebase(Uri uri) {
        StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        String shop_name = shopname.getText().toString().trim();
                        String location_address = shoplocation.getText().toString().trim();
                        String phoneNumber = laundry_phoneNumber.getText().toString().trim();
                        String activestats = " ";
                        int washdry1 = Integer.parseInt(wd1.getText().toString());
                        int washdry2 = Integer.parseInt(wd2.getText().toString());
                        int washdry3 = Integer.parseInt(wd3.getText().toString());
                        int washdry4 = Integer.parseInt(wd4.getText().toString());
                        int washdry5 = Integer.parseInt(wd5.getText().toString());
                        int ironfold = Integer.parseInt(iron_fold.getText().toString());
                        int addwashs = Integer.parseInt(addwash.getText().toString());
                        int bedshetsC_txts = Integer.parseInt(bedshetsC_txt.getText().toString());
                        int blankCurt_txts = Integer.parseInt(blankCurt_txt.getText().toString());

                        String shop_id = db.collection("my shops").document().getId();


                        uploadShop_model uploadShopModel = new uploadShop_model(uri.toString(),shop_name,location_address,phoneNumber,
                                activestats,washdry1,washdry2,washdry3,washdry4,washdry5,shop_id,ironfold,addwashs,bedshetsC_txts,blankCurt_txts);


                        FirebaseUser userid = mAuth.getCurrentUser();
                       // String idd = db.collection("shop and products").document(userid.getUid()).collection("my shops").getId();
                        db.collection("shop and products").document(userid.getUid()).collection("my shops").document(shop_id)
                                .set(uploadShopModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        shop_imageview.setImageDrawable(null);
                                        shopname.setText("");
                                        shoplocation.setText("");
                                        wd1.setText("");
                                        wd2.setText("");
                                        wd3.setText("");
                                        wd4.setText("");
                                        wd5.setText("");
                                        iron_fold.setText("");
                                        addwash.setText("");
                                        bedshetsC_txt.setText("");
                                        blankCurt_txt.setText("");
                                    }
                                });


                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(admin_addProducts.this, "uploaded", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }


    private String getFileExtension(Uri muri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(muri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        uri = data.getData();
        shop_imageview.setImageURI(uri);



    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(admin_addProducts.this , admin_myShop.class));
        finish();
    }

}