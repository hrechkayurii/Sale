package com.ua.yuriihrechka.sale;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ua.yuriihrechka.sale.Model.Users;
import com.ua.yuriihrechka.sale.Prevalent.Prevalent;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;

    private EditText inputPhoneNumber;
    private EditText inputPassword;

    private TextView adminLink, notAdminLink;

    private ProgressDialog mProgressDialog;

    private DatabaseReference rootRefDB;
    private String parentDBName = "Users";
    private CheckBox chkBoxRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        rootRefDB = FirebaseDatabase.getInstance().getReference();

        init();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginUser();

            }
        });

        adminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setText("Login admin");
                adminLink.setVisibility(View.INVISIBLE);
                notAdminLink.setVisibility(View.VISIBLE);
                parentDBName = "Admins";
            }
        });

        notAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setText("Login");
                adminLink.setVisibility(View.VISIBLE);
                notAdminLink.setVisibility(View.INVISIBLE);
                parentDBName = "Users";
            }
        });
    }

    private void loginUser() {

        String phone = inputPhoneNumber.getText().toString();
        String password = inputPassword.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "phone is empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "password is empty", Toast.LENGTH_SHORT).show();
        } else {
            mProgressDialog.setTitle("Login account");
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();

            allowAccessToAccount(phone, password);
        }

    }

    private void allowAccessToAccount(final String phone, final String password) {


        if (chkBoxRememberMe.isChecked()) {
            Paper.book().write(Prevalent.userPhoneKey, phone);
            Paper.book().write(Prevalent.userPasswordKey, password);
        }

        //final DatabaseReference rootRefDB;
        //rootRefDB = FirebaseDatabase.getInstance().getReference();

        rootRefDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(parentDBName).child(phone).exists()) {

                    Users usersData = dataSnapshot.child(parentDBName).child(phone).getValue(Users.class);

                    if (usersData.getPhone().equals(phone)) {

                        if (usersData.getPassword().equals(password)) {

                            if (parentDBName.equals("Admins")) {

                                Toast.makeText(LoginActivity.this, "Admin login OK", Toast.LENGTH_LONG).show();
                                mProgressDialog.dismiss();

                                Intent intent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
                                startActivity(intent);

                            } else if (parentDBName.equals("Users")) {

                                Toast.makeText(LoginActivity.this, "Login OK", Toast.LENGTH_LONG).show();
                                mProgressDialog.dismiss();

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);

                            }

                        } else {
                            Toast.makeText(LoginActivity.this, "incorrect password", Toast.LENGTH_LONG).show();
                            mProgressDialog.dismiss();
                        }

                    }

                } else {
                    Toast.makeText(LoginActivity.this, "Not account", Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Creatr new account", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void init() {

        loginButton = (Button) findViewById(R.id.login_btn);

        inputPhoneNumber = (EditText) findViewById(R.id.login_phone_number_input);
        inputPassword = (EditText) findViewById(R.id.login_password_input);
        chkBoxRememberMe = (CheckBox) findViewById(R.id.remember_me_chkb);

        mProgressDialog = new ProgressDialog(this);

        adminLink = (TextView) findViewById(R.id.admin_panel_link);
        notAdminLink = (TextView) findViewById(R.id.not_admin_panel_link);

    }

}

