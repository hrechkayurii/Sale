package com.ua.yuriihrechka.sale;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.ua.yuriihrechka.sale.Model.Products;
import com.ua.yuriihrechka.sale.ViewHolder.ProductViewHolder;

public class SearchProductsActivity extends AppCompatActivity {

    private Button searchBtn;
    private EditText inputTxt;
    private RecyclerView searchList;

    private String searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_products);

        searchBtn = (Button)findViewById(R.id.search_btn);
        inputTxt = (EditText) findViewById(R.id.search_product_name);
        searchList = (RecyclerView) findViewById(R.id.search_list);

        searchList.setLayoutManager(new LinearLayoutManager(SearchProductsActivity.this));


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchInput = inputTxt.getText().toString();
                onStart();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference refDB = FirebaseDatabase.getInstance().getReference().child("Products");

        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(refDB.orderByChild("name").startAt(searchInput), Products.class)
                .build();

        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model) {

                holder.txtProductName.setText(model.getName());
                holder.txtProductDescription.setText(model.getDescription());
                holder.txtProductPrice.setText("Price = " + model.getPrice()+"$");

                Picasso.get().load(model.getImage()).into(holder.mImageView);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SearchProductsActivity.this, ProductDetailsActivity.class);
                        intent.putExtra("pid", model.getPid());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_items_layout, viewGroup, false);
                ProductViewHolder holder = new ProductViewHolder(v);
                return holder;
            }
        };

        searchList.setAdapter(adapter);
        adapter.startListening();
    }
}
