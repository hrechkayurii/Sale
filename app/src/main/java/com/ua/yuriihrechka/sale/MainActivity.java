package com.ua.yuriihrechka.sale;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ua.yuriihrechka.sale.Model.Users;
import com.ua.yuriihrechka.sale.Prevalent.Prevalent;

import io.paperdb.Paper;


public class MainActivity extends AppCompatActivity {


    private Button joinNowButton, loginButton;

    private ProgressDialog mProgressDialog;
    private String parentDBName = "Users";

    private DatabaseReference rootRefDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Paper.init(this);

        setContentView(R.layout.activity_main);

        init();


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        joinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        String userPhoneKey = Paper.book().read(Prevalent.userPhoneKey);
        String userPasswordKey = Paper.book().read(Prevalent.userPasswordKey);

        if (userPhoneKey != null && userPasswordKey != "") {
            if (TextUtils.isEmpty(userPhoneKey) && !TextUtils.isEmpty(userPasswordKey)) {
                allowAccess(userPhoneKey, userPasswordKey);

                mProgressDialog.setTitle("Login account");
                mProgressDialog.setMessage("Please wait...");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();
            }
        }
    }

    private void allowAccess(final String phone, final String password) {


        final DatabaseReference rootRefDB;
        rootRefDB = FirebaseDatabase.getInstance().getReference();

        rootRefDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(parentDBName).child(phone).exists()) {

                    Users usersData = dataSnapshot.child(parentDBName).child(phone)
                            .getValue(Users.class);

                    if (usersData.getPhone().equals(phone)) {

                        if (usersData.getPassword().equals(password)) {

                            Toast.makeText(MainActivity.this, "Login OK", Toast.LENGTH_LONG).show();
                            mProgressDialog.dismiss();

                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(MainActivity.this, "incorrect password", Toast.LENGTH_LONG).show();
                            mProgressDialog.dismiss();
                        }

                    }

                } else {
                    Toast.makeText(MainActivity.this, "Not account", Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Creatr new account", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void init() {

        joinNowButton = (Button) findViewById(R.id.main_join_now_btn);
        loginButton = (Button) findViewById(R.id.main_login_btn);

        mProgressDialog = new ProgressDialog(this);

    }
}
