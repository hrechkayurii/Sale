package com.ua.yuriihrechka.sale;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddNewProductActivity extends AppCompatActivity {

    private String categoryName, pDescription, pPrice, pName, saveCurrentDate, saveCurrentTime;
    private Button addNewProductButton;
    private EditText inputProductName;
    private EditText inputProductDescription;
    private EditText inputProductPrice;
    private ImageView inputProductImage;

    private static final int GALLERY_PICK = 1;
    private Uri imageUri;

    private String productRandomKey, downloadImageUrl;

    private StorageReference productImagesRef;
    private DatabaseReference productRef;

    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);

        categoryName = getIntent().getExtras().get("category").toString();
        productImagesRef = FirebaseStorage.getInstance().getReference().child("ProductImages");
        productRef = FirebaseDatabase.getInstance().getReference().child("Products");

        init();

        inputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        addNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateProductData();
            }
        });


    }

    private void validateProductData() {

        pDescription = inputProductDescription.getText().toString();
        pName = inputProductName.getText().toString();
        pPrice = inputProductPrice.getText().toString();

        if (imageUri == null) {
            Toast.makeText(this, "image not added", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(pDescription)) {
            Toast.makeText(this, "Enter description", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(pName)) {
            Toast.makeText(this, "Enter name", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(pPrice)) {
            Toast.makeText(this, "Enter price", Toast.LENGTH_LONG).show();
        }else {

            storeImageInformation();

        }


    }

    private void storeImageInformation() {

        mProgressDialog.setTitle("Add new product");
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = productImagesRef.child(imageUri.getLastPathSegment()+productRandomKey+".jpg");
        final UploadTask uploadTask = filePath.putFile(imageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(AdminAddNewProductActivity.this, "Error: "+message, Toast.LENGTH_LONG).show();
                mProgressDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminAddNewProductActivity.this, "image uploaded sacces.", Toast.LENGTH_LONG).show();

                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if (!task.isSuccessful()){
                            throw task.getException();


                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if (task.isSuccessful()){

                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(AdminAddNewProductActivity.this, "Image save to DB", Toast.LENGTH_LONG).show();

                            saveProductInfoToDB();
                        }

                    }
                });
            }
        });



    }

    private void saveProductInfoToDB() {

        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid",productRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time",saveCurrentTime);
        productMap.put("description",pDescription);
        productMap.put("image",downloadImageUrl);
        productMap.put("name",pName);
        productMap.put("price",pPrice);

        productRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){

                            Intent intent = new Intent(AdminAddNewProductActivity.this, AdminCategoryActivity.class);
                            startActivity(intent);

                            mProgressDialog.dismiss();
                            Toast.makeText(AdminAddNewProductActivity.this, "Product save to DB", Toast.LENGTH_LONG).show();

                        }else {

                            mProgressDialog.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AdminAddNewProductActivity.this, "Error: "+message, Toast.LENGTH_LONG).show();
                        }

                    }
                });


    }

    private void openGallery() {

        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GALLERY_PICK);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK && data != null){

            imageUri = data.getData();
            inputProductImage.setImageURI(imageUri);


        }
    }

    private void init() {

        addNewProductButton = (Button)findViewById(R.id.add_new_product);
        inputProductName = (EditText)findViewById(R.id.product_name);
        inputProductDescription = (EditText)findViewById(R.id.product_description);
        inputProductPrice = (EditText)findViewById(R.id.product_price);
        inputProductImage = (ImageView)findViewById(R.id.select_product_image);

        mProgressDialog = new ProgressDialog(this);
    }
}
