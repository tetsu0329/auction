package com.example.kuroro.auction;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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

import static android.app.Activity.RESULT_OK;


public class UploadBidding extends Fragment{

    int minteger = 1;
    Button btnincrease, btndecrease, browse1, browse2, browse3, upload;
    TextView integernum;
    EditText name, price;
    RadioButton englishradio, dutchradio, sealedradio, buymeradio;
    RadioGroup radioGroup;

    int RESULT_IMAGE = 1;
    int RESULT_IMAGE2 = 2;
    int RESULT_IMAGE3 = 3;

    ImageView imageView, imageView2, imageView3;

    private DatabaseReference mDatabaseRef;
    private StorageReference mStorageRef;

    public static final String FB_STORAGE_PATH = "bid/";

    Uri selectedImage, selectedImage2, selectedImage3;
    String bid = "";

    FirebaseAuth auth;
    public UploadBidding() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_bidding, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        auth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("bid");


        btnincrease = view.findViewById(R.id.increase);
        integernum  = view.findViewById(R.id.integer_number);

        imageView = view.findViewById(R.id.imageView);
        imageView2 = view.findViewById(R.id.imageView2);
        imageView3 = view.findViewById(R.id.imageView3);

        englishradio = view.findViewById(R.id.englishbid);
        dutchradio = view.findViewById(R.id.dutchbid);
        sealedradio = view.findViewById(R.id.sealedbid);
        buymeradio = view.findViewById(R.id.buymebid);

        radioGroup = view.findViewById(R.id.radiogroup);


        name = view.findViewById(R.id.edittext);
        price = view.findViewById(R.id.editText8);

        btnincrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minteger = minteger + 1;
                display(minteger);
            }
        });
        btndecrease = view.findViewById(R.id.decrease);
        btndecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (minteger>1){
                    minteger = minteger - 1;
                    display(minteger);
                }
                else{
                    display(minteger);
                }
            }
        });
        browse1 = view.findViewById(R.id.browse1);
        browse1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_IMAGE);
            }
        });
        browse2 = view.findViewById(R.id.browse2);
        browse2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_IMAGE2);
            }
        });
        browse3 = view.findViewById(R.id.browse3);
        browse3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_IMAGE3);
            }
        });
        upload = view.findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId= radioGroup.getCheckedRadioButtonId();


                if(selectedId==R.id.englishbid){
                    bid = "English";
                }
                if(selectedId==R.id.dutchbid){
                    bid = "Dutch";
                }
                if(selectedId==R.id.sealedbid){
                    bid = "Sealed";
                }
                if(selectedId==R.id.buymebid){
                    bid = "BuyMe";
                }
                if(selectedImage3 != null){

                }
                else if(selectedImage2 != null){

                }
                else if(selectedImage != null){
                    String userID = auth.getCurrentUser().getUid();
                    String bidName = name.getText().toString();
                    String bidPrice = price.getText().toString();
                    String bidDays = integernum.getText().toString();
                    String bidType = bid;
                    upload1(userID, bidName, bidPrice, bidDays, bidType);
                }


            }
        });
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_IMAGE && resultCode == RESULT_OK && null != data) {
            selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = this.getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }
        if (requestCode == RESULT_IMAGE2 && resultCode == RESULT_OK && null != data) {
            selectedImage2 = data.getData();
            String[] filePathColumn2 = {MediaStore.Images.Media.DATA};

            Cursor cursor2 = this.getActivity().getContentResolver().query(selectedImage2,
                    filePathColumn2, null, null, null);
            cursor2.moveToFirst();

            int columnIndex2 = cursor2.getColumnIndex(filePathColumn2[0]);
            String picturePath = cursor2.getString(columnIndex2);
            cursor2.close();

            imageView2.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }
        if (requestCode == RESULT_IMAGE3 && resultCode == RESULT_OK && null != data) {
            selectedImage3 = data.getData();
            String[] filePathColumn3 = {MediaStore.Images.Media.DATA};

            Cursor cursor3 = this.getActivity().getContentResolver().query(selectedImage3,
                    filePathColumn3, null, null, null);
            cursor3.moveToFirst();

            int columnIndex = cursor3.getColumnIndex(filePathColumn3[0]);
            String picturePath = cursor3.getString(columnIndex);
            cursor3.close();

            imageView3.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }
    }
    private void display(int number) {
        String word = ""+number;
        integernum.setText(word);
    }
    public void upload1(final String userID, final String bidName, final String bidPrice, final String bidDays, final String bidType){
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Uploading Item in the Auction");
        dialog.show();

        StorageReference ref = mStorageRef.child(FB_STORAGE_PATH + System.currentTimeMillis() + "." + getImageExt(selectedImage));
        ref.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                dialog.dismiss();

                String uploadID = mDatabaseRef.push().getKey();
                BidList bidList = new BidList(uploadID,userID, bidName, bidPrice, bidDays, bidType, taskSnapshot.getDownloadUrl().toString());
                mDatabaseRef.child(uploadID).setValue(bidList);
                Toast.makeText(getActivity(), "Item is now in the Auction", Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                dialog.dismiss();

                Toast.makeText(getActivity(), "Item Failed Uploading", Toast.LENGTH_SHORT).show();
            }
        })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>()
                {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        dialog.setMessage("Setting up Item Image "+ (int)progress+ "%");
                    }
                });
    }
    public void upload2(){

    }
    public void upload3(){

    }
    public String getImageExt(Uri uri){
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

}
