package com.ua.yuriihrechka.sale;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ua.yuriihrechka.sale.Prevalent.Prevalent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

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

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                check();

            }
        });
    }

    private void check() {

        if (TextUtils.isEmpty(nameET.getText().toString())){
            Toast.makeText(this, "Enter name", Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(phoneET.getText().toString())){
            Toast.makeText(this, "Enter phone number", Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(addressET.getText().toString())){
            Toast.makeText(this, "Enter address", Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(cityET.getText().toString())){
            Toast.makeText(this, "Enter your city", Toast.LENGTH_LONG).show();
        }
        else {
            confirmOrder();

        }

    }

    private void confirmOrder() {

        String saveCurrentDate, saveCurrentTime;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());


        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());


        final DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference()
                .child("Orders").child(Prevalent.currentOnlineUser.getPhone());

        HashMap<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("totalAmount", totalAmount);
        ordersMap.put("name", nameET.getText().toString());
        ordersMap.put("phone", phoneET.getText().toString());
        ordersMap.put("date", saveCurrentDate);
        ordersMap.put("time", saveCurrentTime);
        ordersMap.put("address", addressET.getText().toString());
        ordersMap.put("city", cityET.getText().toString());
        ordersMap.put("state", "not shipped");

        orderRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){

                    FirebaseDatabase.getInstance().getReference()
                            .child("CartList")
                            .child("UserView")
                            .child(Prevalent.currentOnlineUser.getPhone())
                            .removeValue()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){

                                Toast.makeText(ConfirmFinalOrderActivity.this, "order OK", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(ConfirmFinalOrderActivity.this, HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            }
                        }
                    });
                }

            }
        });

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
