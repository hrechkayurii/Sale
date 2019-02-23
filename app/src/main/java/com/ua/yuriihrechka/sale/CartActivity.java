package com.ua.yuriihrechka.sale;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

public class CartActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button nextBtn;
    private TextView txtTotalAmount;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initElementOnView();
    }

    private void initElementOnView() {

        mRecyclerView = findViewById(R.id.cart_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        nextBtn = (Button)findViewById(R.id.next_process_btn);
        txtTotalAmount = (TextView)findViewById(R.id.total_price);


    }
}
