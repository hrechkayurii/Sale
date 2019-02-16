package com.ua.yuriihrechka.sale;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;

    private EditText inputPhoneNumber;
    private EditText inputPassword;

    private ProgressDialog mProgressDialog;

    private DatabaseReference rootRefDB;

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
    }

    private void loginUser() {

        String phone = inputPhoneNumber.getText().toString();
        String password = inputPassword.getText().toString();

    if (TextUtils.isEmpty(phone)) {
        Toast.makeText(this, "phone is empty", Toast.LENGTH_SHORT).show();
    } else if (TextUtils.isEmpty(password)) {
        Toast.makeText(this, "password is empty", Toast.LENGTH_SHORT).show();
    }else {
        mProgressDialog.setTitle("Login account");
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        allowAccessToAccount(phone, password);
    }

    }

    private void allowAccessToAccount(String phone, String password) {



    }


    private void init() {

        loginButton = (Button)findViewById(R.id.login_btn);

        inputPhoneNumber = (EditText)findViewById(R.id.login_phone_number_input);
        inputPassword = (EditText)findViewById(R.id.login_password_input);

        mProgressDialog = new ProgressDialog(this);

    }

}

