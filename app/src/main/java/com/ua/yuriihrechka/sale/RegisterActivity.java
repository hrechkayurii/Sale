package com.ua.yuriihrechka.sale;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    private Button createAccountButton;
    private EditText inputName;
    private EditText inputPhoneNumber;
    private EditText inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();


    }

    private void init() {

        createAccountButton = (Button)findViewById(R.id.register_btn);
        inputName = (EditText)findViewById(R.id.register_username_input);
        inputPhoneNumber = (EditText)findViewById(R.id.register_phone_number_input);
        inputPassword = (EditText)findViewById(R.id.register_password_input);
    }
}
