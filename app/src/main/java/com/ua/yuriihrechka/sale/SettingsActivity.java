package com.ua.yuriihrechka.sale;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.ua.yuriihrechka.sale.Prevalent.Prevalent;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private CircleImageView profileImageView;

    private EditText fullNameET;
    private EditText userPhoneET;
    private EditText addressET;

    private TextView profileChangeTextBtn;
    private TextView closeTextBtn;
    private TextView saveTextBtn;

    private Uri imageUri;
    private String myUrl = "";
    private StorageReference mStorageReferencePictireRef;
    private String checker = "";
    private StorageTask uploadTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mStorageReferencePictireRef = FirebaseStorage.getInstance().getReference().child("ProductImages");

        init();

        userInfoDisplay(profileImageView, fullNameET, userPhoneET, addressET);


        closeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checker.equals("clicked")){

                    userInfoSaved();

                }else {

                    updateOnlyUserInfo();
                }
            }
        });

        profileChangeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker = "clicked";

                // start cropping activity for pre-acquired image saved on the device
                CropImage.activity(imageUri)
                        .setAspectRatio(1,1)
                        .start(SettingsActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
        && resultCode == RESULT_OK && data != null){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            imageUri = result.getUri();
            profileImageView.setImageURI(imageUri);
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
            startActivity(new Intent(SettingsActivity.this, SettingsActivity.class));
            finish();
        }

    }

    private void userInfoSaved() {

        if (TextUtils.isEmpty(fullNameET.getText().toString())){
            Toast.makeText(this, "Name in empty", Toast.LENGTH_LONG).show();

        } else if (TextUtils.isEmpty(addressET.getText().toString())){
            Toast.makeText(this, "Address in empty", Toast.LENGTH_LONG).show();

        } else if (TextUtils.isEmpty(userPhoneET.getText().toString())){
            Toast.makeText(this, "Phone in empty", Toast.LENGTH_LONG).show();

        } else if (checker.equals("clicked")){
            uploadImage();
            //Toast.makeText(this, "Name in empty", Toast.LENGTH_LONG).show();
        }

    }

    private void uploadImage() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Update profile");
        progressDialog.setTitle("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        if (imageUri != null){

            final  StorageReference fileRef = mStorageReferencePictireRef
                    .child(Prevalent.currentOnlineUser.getPhone() + ".jpg");

            uploadTask = fileRef.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {

                    if (!task.isSuccessful()){
                        throw task.getException();
                    }

                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    if (task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        myUrl = downloadUri.toString();

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
                        HashMap<String, Object> userMap = new HashMap<>();
                        userMap.put("name", fullNameET.getText().toString());
                        userMap.put("address", addressET.getText().toString());
                        userMap.put("phone", userPhoneET.getText().toString());
                        userMap.put("image", myUrl);
                        ref.child(Prevalent.currentOnlineUser.getPhone()).updateChildren(userMap);

                        progressDialog.dismiss();

                        startActivity(new Intent(SettingsActivity.this, HomeActivity.class));
                        Toast.makeText(SettingsActivity.this, "Updated OK", Toast.LENGTH_LONG).show();
                        finish();

                    } else {


                        progressDialog.dismiss();
                        Toast.makeText(SettingsActivity.this, "Error update", Toast.LENGTH_LONG).show();

                    }
                }
            });


        } else {

            Toast.makeText(SettingsActivity.this, "image is not selected", Toast.LENGTH_LONG).show();

        }

    }

    private void updateOnlyUserInfo() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("name", fullNameET.getText().toString());
        userMap.put("address", addressET.getText().toString());
        userMap.put("phone", userPhoneET.getText().toString());

        ref.child(Prevalent.currentOnlineUser.getPhone()).updateChildren(userMap);

        startActivity(new Intent(SettingsActivity.this, HomeActivity.class));
        Toast.makeText(SettingsActivity.this, "Updated OK", Toast.LENGTH_LONG).show();
        finish();

    }

    private void userInfoDisplay(final CircleImageView profileImageView, final EditText fullNameET, final EditText userPhoneET, final EditText addressET) {

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhone());

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    if (dataSnapshot.child("image").exists()){

                        String image = dataSnapshot.child("image").getValue().toString();
                        String name = dataSnapshot.child("name").getValue().toString();
                        String phone = dataSnapshot.child("phone").getValue().toString();
                        String address = dataSnapshot.child("address").getValue().toString();

                        Picasso.get().load(image).into(profileImageView);
                        fullNameET.setText(name);
                        userPhoneET.setText(phone);
                        addressET.setText(address);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
