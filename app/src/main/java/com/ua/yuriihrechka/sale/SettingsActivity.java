package com.ua.yuriihrechka.sale;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private CircleImageView profileImageView;

    private EditText fullNameET;
    private EditText userPhoneET;
    private EditText addressET;

    private TextView profileChangeTextBtn;
    private TextView closeTextBtn;
    private TextView saveTextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        init();
    }

    private void init() {

        profileImageView = (CircleImageView)findViewById(R.id.settings_profile_image);

        fullNameET = (EditText)findViewById(R.id.settings_full_name);
        userPhoneET = (EditText)findViewById(R.id.settings_phone_number);
        addressET = (EditText)findViewById(R.id.settings_address);

        profileChangeTextBtn = (TextView) findViewById(R.id.profile_image_change_btn);
        closeTextBtn = (TextView)findViewById(R.id.close_settings);
        saveTextBtn = (TextView)findViewById(R.id.update_account_settings);
    }
}
