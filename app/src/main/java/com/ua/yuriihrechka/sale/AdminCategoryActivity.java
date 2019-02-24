package com.ua.yuriihrechka.sale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView t_shirts, sports_t_shirts, female_dresses, sweather;
    private ImageView glasses, purses_bags, hats, shoess;
    private ImageView headphoness, laptops, watches, mobiles;

    private Button logoutBtn, checkOrdersBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        logoutBtn = (Button)findViewById(R.id.action_logout);
        checkOrdersBtn = (Button)findViewById(R.id.check_orders_btn);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminCategoryActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        checkOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);

                startActivity(intent);

            }
        });
    }

    @Override
    public void onClick(View v) {


        Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
        switch (v.getId()) {

            case R.id.t_shirts:
                intent.putExtra("category", "t_shirts");
                break;
            case R.id.sports_t_shirts:
                intent.putExtra("category", "sports_t_shirts");
                break;
            case R.id.female_dresses:
                intent.putExtra("category", "female_dresses");
                break;
            case R.id.sweather:
                intent.putExtra("category", "sweather");
                break;
            case R.id.glasses:
                intent.putExtra("category", "glasses");
                break;
            case R.id.purses_bags:
                intent.putExtra("category", "purses_bags");
                break;
            case R.id.hats:
                intent.putExtra("category", "hats");
                break;
            case R.id.shoess:
                intent.putExtra("category", "shoess");
                break;
            case R.id.headphoness:
                intent.putExtra("category", "headphoness");
                break;
            case R.id.laptops:
                intent.putExtra("category", "laptops");
                break;
            case R.id.watches:
                intent.putExtra("category", "watches");
                break;
            case R.id.mobiles:
                intent.putExtra("category", "mobiles");
                break;

        }

        startActivity(intent);

    }
}
