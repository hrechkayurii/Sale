package com.ua.yuriihrechka.sale;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConfirmFinalOrderActivity extends AppCompatActivity {

    private EditText nameET, phoneET, addressET, cityET;
    private Button confirmBtn;
    private String totalAmount = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        init();

        Toast.makeText(ConfirmFinalOrderActivity.this, totalAmount + " $", Toast.LENGTH_LONG).show();
    }

    private void init() {

        confirmBtn = (Button)findViewById(R.id.confirm_final_order_btn);
        nameET = (EditText)findViewById(R.id.shipment_name);
        phoneET = (EditText)findViewById(R.id.shipment_phone_number);
        addressET = (EditText)findViewById(R.id.shipment_address);
        cityET = (EditText)findViewById(R.id.shipment_city);

        totalAmount = getIntent().getStringExtra("totalPrice");

    }
}
