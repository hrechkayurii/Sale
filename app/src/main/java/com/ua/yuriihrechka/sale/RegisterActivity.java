package com.ua.yuriihrechka.sale;

import android.app.ProgressDialog;
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
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button createAccountButton;
    private EditText inputName;
    private EditText inputPhoneNumber;
    private EditText inputPassword;

    private ProgressDialog mProgressDialog;

    private DatabaseReference rootRefDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //FirebaseApp.initializeApp(this);
        rootRefDB = FirebaseDatabase.getInstance().getReference();

        init();

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();

            }
        });


    }

    private void createAccount() {

        String name = inputName.getText().toString();
        String phone = inputPhoneNumber.getText().toString();
        String password = inputPassword.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "name is empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "phone is empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "password is empty", Toast.LENGTH_SHORT).show();
        } else {
            mProgressDialog.setTitle("Create account");
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();

            ValidatePhoneNumber(name, phone, password);
        }
    }

    private void ValidatePhoneNumber(final String name, final String phone, final String password) {


        rootRefDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!(dataSnapshot.child("Users").child(phone).exists())) {

                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("phone", phone);
                    userDataMap.put("password", password);
                    userDataMap.put("name", name);
                    rootRefDB.child("Users").child(phone).updateChildren(userDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Account created.", Toast.LENGTH_SHORT).show();
                                        mProgressDialog.dismiss();

                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    } else {
                                        mProgressDialog.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Error create account.", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                } else {
                    Toast.makeText(RegisterActivity.this, "phone " + phone + " already exists.", Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "using another phone number.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void init() {

        createAccountButton = (Button) findViewById(R.id.register_btn);
        inputName = (EditText) findViewById(R.id.register_username_input);
        inputPhoneNumber = (EditText) findViewById(R.id.register_phone_number_input);
        inputPassword = (EditText) findViewById(R.id.register_password_input);

        mProgressDialog = new ProgressDialog(this);

    }
}
