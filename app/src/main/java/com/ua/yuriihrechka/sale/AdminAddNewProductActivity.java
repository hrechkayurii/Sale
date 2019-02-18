package com.ua.yuriihrechka.sale;

import android.content.Intent;
import android.net.Uri;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AdminAddNewProductActivity extends AppCompatActivity {

    private String categoryName, pDescription, pPrice, pName, saveCurrendDate, saveCurrentTime;
    private Button addNewProductButton;
    private EditText inputProductName;
    private EditText inputProductDescription;
    private EditText inputProductPrice;
    private ImageView inputProductImage;

    private static final int GALLERY_PICK = 1;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);

        categoryName = getIntent().getExtras().get("category").toString();

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

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrendDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

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

        if (requestCode == GALLERY_PICK && requestCode == RESULT_OK && data != null){

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
    }
}
