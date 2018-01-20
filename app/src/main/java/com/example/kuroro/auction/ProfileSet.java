package com.example.kuroro.auction;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;

public class ProfileSet extends AppCompatActivity {

    FirebaseAuth auth;
    private DatabaseReference mDatabaseRef;
    private StorageReference mStorageRef;

    EditText name, email, contact;
    Button browse, save, browse2;
    ImageView imageview, imageView2;

//    ProgressDialog progressDialog;
    public static final String FB_STORAGE_PATH = "users/";
    public static final String FB_DATABASE_PATH = "userinfo";
    private static final int RESULT_IMAGE = 1;
    private static final int RESULT_IMAGE2 = 2;

    Uri uri, uri2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_set);

        auth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH);

        name = findViewById(R.id.editText4);
        email = findViewById(R.id.editText5);
        contact = findViewById(R.id.editText6);

        browse = findViewById(R.id.button7);
        browse2 = findViewById(R.id.button9);

        imageview = findViewById(R.id.imageView2);
        imageView2 = findViewById(R.id.imageView3);

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        ProfileSet.this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        RESULT_IMAGE
                );
            }
        });

        browse2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        ProfileSet.this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        RESULT_IMAGE2
                );
            }
        });

        save = findViewById(R.id.button8);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == RESULT_IMAGE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, RESULT_IMAGE);
            }
        }
        if(requestCode == RESULT_IMAGE2){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, RESULT_IMAGE2);
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_IMAGE && resultCode == RESULT_OK && data!=null){
            uri = data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap yourselectedimage = BitmapFactory.decodeStream(inputStream);
                int nh = (int) (yourselectedimage.getHeight() * (512.0 / yourselectedimage.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(yourselectedimage, 512, nh, true);
                imageview.setImageBitmap(scaled);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        if(requestCode == RESULT_IMAGE2 && resultCode == RESULT_OK && data!=null){
            uri2 = data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(uri2);
                Bitmap yourselectedimage = BitmapFactory.decodeStream(inputStream);
                int nh = (int) (yourselectedimage.getHeight() * (512.0 / yourselectedimage.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(yourselectedimage, 512, nh, true);
                imageView2.setImageBitmap(scaled);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

    }
    public void upload(){
        final String name1 = name.getText().toString();
        final String address1 = email.getText().toString();
        final String contact1 = contact.getText().toString();

        if(contact1.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter your description", Toast.LENGTH_SHORT).show();
            contact.requestFocus();
            return;
        }
        if(address1.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter your address", Toast.LENGTH_SHORT).show();
            email.requestFocus();
            return;
        }
        if(name1.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter your name", Toast.LENGTH_SHORT).show();
            name.requestFocus();
        }
        else{
            if(uri != null && uri2 != null){
                final ProgressDialog dialog = new ProgressDialog(this);
                dialog.setTitle("Please wait");
                dialog.setCancelable(false);
                dialog.show();
                StorageReference ref = mStorageRef.child(FB_STORAGE_PATH + System.currentTimeMillis() + "." + getImageExt(uri));
                ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                        StorageReference ref2 = mStorageRef.child(FB_STORAGE_PATH + System.currentTimeMillis() + "." + getImageExt(uri2));
                        ref2.putFile(uri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot2) {
                                String userID = auth.getCurrentUser().getUid();
                                UserList userAccount = new UserList(userID, name1, address1, taskSnapshot.getDownloadUrl().toString(), contact1, auth.getCurrentUser().getEmail(), taskSnapshot2.getDownloadUrl().toString(), "Pending");
                                mDatabaseRef.child(userID).setValue(userAccount);
                                Toast.makeText(getApplicationContext(), "User Info Saved and waiting to be Approved", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ProfileSet.this, MainActivity.class);
                                startActivity(intent);
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "Affiliation Failed Uploading", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                        double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                        dialog.setMessage("Saving Affiliation (Please do not Close) "+ (int)progress+ "%");
                                    }
                                });
                    }
                })
                        .addOnFailureListener(new OnFailureListener()
                        {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Personal Information Failed Uploading", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>()
                        {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
                            {
                                double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                dialog.setMessage("Saving Personal Information (Please do not Close) "+ (int)progress+ "%");
                            }
                        });
            }
            else{
                Toast.makeText(getApplicationContext(), "Please choose an Image", Toast.LENGTH_SHORT).show();
            }

        }
    }
    public String getImageExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}
