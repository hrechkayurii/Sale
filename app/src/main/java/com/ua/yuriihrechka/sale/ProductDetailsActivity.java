package com.ua.yuriihrechka.sale;

import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.squareup.picasso.Picasso;
import com.ua.yuriihrechka.sale.Model.Products;

public class ProductDetailsActivity extends AppCompatActivity {

    //private FloatingActionButton addToCartBtn;
    private ImageView productImage;
    private ElegantNumberButton namberBtn;
    private TextView productPrice;
    private TextView productDescription;
    private TextView productName;

    private String productID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        init();




    }

    private void getProductDetails(String productID) {

        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Products");

        productRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    Products products = dataSnapshot.getValue(Products.class);

                    productName.setText(products.getName());
                    productDescription.setText(products.getDescription());
                    productPrice.setText(products.getPrice());

                    Picasso.get().load(products.getImage()).placeholder(R.drawable.glasses).into(productImage);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void init() {

        productID = getIntent().getStringExtra("pid");

        //addToCartBtn = (FloatingActionButton)findViewById(R.id.add_product_to_card_btn);
        productImage = (ImageView)findViewById(R.id.product_image_details);
        namberBtn = (ElegantNumberButton)findViewById(R.id.number_btn);
        productPrice = (TextView)findViewById(R.id.product_price_details);
        productDescription = (TextView)findViewById(R.id.product_description_details);
        productName = (TextView)findViewById(R.id.product_name_details);

        getProductDetails(productID);
    }
}
