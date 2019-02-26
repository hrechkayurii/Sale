package com.ua.yuriihrechka.sale;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ua.yuriihrechka.sale.Model.Cart;
import com.ua.yuriihrechka.sale.ViewHolder.CartViewHolder;

public class AdminUserProductsActivity extends AppCompatActivity {

    private RecyclerView productsList;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference cartListRef;

    private String userID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_products);

        userID = getIntent().getStringExtra("uid");
        productsList = findViewById(R.id.products_list);
        productsList.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        productsList.setLayoutManager(mLayoutManager);

        cartListRef = FirebaseDatabase.getInstance().getReference()
                .child("CartList").child("AdminView")
                .child(userID);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartListRef, Cart.class)
                .build();



        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter =
                new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {

                        holder.txtProductName.setText(model.getName());
                        holder.txtProductPrice.setText("Price " + model.getPrice()+" $");
                        holder.txtProductQuantity.setText("Quantity = " + model.getQuantity());


                    }

                    @NonNull
                    @Override
                    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_items_layout, viewGroup, false);
                        CartViewHolder cartViewHolder = new CartViewHolder(v);
                        return cartViewHolder;
                    }
                };

        productsList.setAdapter(adapter);
        adapter.startListening();
    }
}
