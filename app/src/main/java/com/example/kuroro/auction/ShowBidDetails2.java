//package com.example.kuroro.auction;
//
//import android.app.Dialog;
//import android.app.ProgressDialog;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.squareup.picasso.Picasso;
//
//import java.util.Calendar;
//
//public class ShowBidDetails2 extends Fragment {
//
//    FirebaseAuth auth;
//    StorageReference mStorageRef;
//    DatabaseReference mDatabaseRef, mDatabaseRef2, mDatabaseRef3, mDatabaseRef4, mDatabaseRef5;
//    ImageView imageView, imageView2, imageView3;
//    TextView textView, textView2, textView3, textView4;
//    ImageButton button;
//    String bidID;
//    ProgressDialog progressDialog;
//    Dialog dialog;
//    String pricee, sname;
//    String mydate;
//    public ShowBidDetails2() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_show_bid_details, container, false);
//
//        Bundle bundle = this.getArguments();
//        bidID = bundle.getString("bidIDD");
//
//        imageView = view.findViewById(R.id.imageView);
//        imageView2 = view.findViewById(R.id.imageView2);
//        imageView3 = view.findViewById(R.id.imageView3);
//
//        textView = view.findViewById(R.id.textView8);
//        textView2 = view.findViewById(R.id.textView9);
//        textView3 = view.findViewById(R.id.textView10);
//        textView4 = view.findViewById(R.id.textView11);
//
//        auth = FirebaseAuth.getInstance();
//        mStorageRef = FirebaseStorage.getInstance().getReference();
//        mDatabaseRef = FirebaseDatabase.getInstance().getReference("bid").child(bidID);
//        mDatabaseRef2 = FirebaseDatabase.getInstance().getReference();
//        mDatabaseRef3 = FirebaseDatabase.getInstance().getReference("bidhistory").child(bidID);
//        mDatabaseRef4 = FirebaseDatabase.getInstance().getReference("finalbid");
//        mDatabaseRef5 = FirebaseDatabase.getInstance().getReference("finalbid").child(bidID);
//
//        imageView.setVisibility(View.GONE);
//        imageView2.setVisibility(View.GONE);
//        imageView3.setVisibility(View.GONE);
//
//        progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage("Please wait while loading Item information..... ");
//        progressDialog.show();
//
//        mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
//
//        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if(dataSnapshot.hasChild("bidImage3")){
//                    progressDialog.dismiss();
//                    imageView.setVisibility(View.VISIBLE);
//                    imageView2.setVisibility(View.VISIBLE);
//                    imageView3.setVisibility(View.VISIBLE);
//
//                    Query search = mDatabaseRef2.child("bid").orderByChild("bidID").startAt(bidID).endAt(bidID+"\uf8ff");
//                    search.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot2) {
//                            for (DataSnapshot snapshot2 : dataSnapshot2.getChildren())
//                            {
//                                final BidList3 bidList3 = snapshot2.getValue(BidList3.class);
//                                textView.setText(bidList3.getBidName());
//                                textView3.setText(bidList3.getCondition());
//                                textView4.setText(bidList3.getBiddersNote());
//                                Query search2 = mDatabaseRef2.child("finalbid").orderByChild("bidID").startAt(bidID).endAt(bidID+"\uf8ff");
//                                search2.addListenerForSingleValueEvent(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(DataSnapshot dataSnapshot3) {
////                                        for (DataSnapshot snapshot3 : dataSnapshot3.getChildren())
////                                        {
////                                            FinalBid bidList4 = snapshot3.getValue(FinalBid.class);
////                                            textView2.setText(bidList4.getOfferPrice());
////                                            pricee = bidList4.getOfferPrice();
////                                        }
//                                        if(dataSnapshot3.exists()){
//                                            for (DataSnapshot snapshot3 : dataSnapshot3.getChildren())
//                                            {
//                                                FinalBid bidList4 = snapshot3.getValue(FinalBid.class);
//                                                String txtfortxtwin = "Current Winning Bid: "+ bidList4.getOfferPrice();
//                                                textView2.setText(txtfortxtwin);
//                                                pricee = bidList4.getOfferPrice();
//
//                                            }
//                                        }
//                                        else{
//                                            String txtfortxtwin = "Current Winning Bid: "+ bidList3.getBidPrice();
//                                            textView2.setText(txtfortxtwin);
//                                            pricee = bidList3.getBidPrice();
//                                        }
//                                    }
//                                    @Override
//                                    public void onCancelled(DatabaseError databaseError) {
//
//                                    }
//                                });
//                                Picasso.with(getActivity()).load(bidList3.getBidImage1()).into(imageView);
//                                Picasso.with(getActivity()).load(bidList3.getBidImage2()).into(imageView2);
//                                Picasso.with(getActivity()).load(bidList3.getBidImage3()).into(imageView3);
//                            }
//                        }
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//                else if(dataSnapshot.hasChild("bidImage2")){
//                    progressDialog.dismiss();
//                    imageView.setVisibility(View.VISIBLE);
//                    imageView2.setVisibility(View.VISIBLE);
//                    imageView3.setVisibility(View.GONE);
//
//                    Query search = mDatabaseRef2.child("bid").orderByChild("bidID").startAt(bidID).endAt(bidID+"\uf8ff");
//                    search.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot2) {
//                            for (DataSnapshot snapshot2 : dataSnapshot2.getChildren())
//                            {
//                                final BidList2 bidList3 = snapshot2.getValue(BidList2.class);
//                                textView.setText(bidList3.getBidName());
//                                textView3.setText(bidList3.getCondition());
//                                textView4.setText(bidList3.getBiddersNote());
//                                Query search2 = mDatabaseRef2.child("finalbid").orderByChild("bidID").startAt(bidID).endAt(bidID+"\uf8ff");
//                                search2.addListenerForSingleValueEvent(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(DataSnapshot dataSnapshot3) {
////                                        for (DataSnapshot snapshot3 : dataSnapshot3.getChildren())
////                                        {
////                                            FinalBid bidList4 = snapshot3.getValue(FinalBid.class);
////                                            textView2.setText(bidList4.getOfferPrice());
////                                            pricee = bidList4.getOfferPrice();
////                                        }
//                                        if(dataSnapshot3.exists()){
//                                            for (DataSnapshot snapshot3 : dataSnapshot3.getChildren())
//                                            {
//                                                FinalBid bidList4 = snapshot3.getValue(FinalBid.class);
//                                                String txtfortxtwin = "Current Winning Bid: "+ bidList4.getOfferPrice();
//                                                textView2.setText(txtfortxtwin);
//                                                pricee = bidList4.getOfferPrice();
//
//                                            }
//                                        }
//                                        else{
//                                            String txtfortxtwin = "Current Winning Bid: "+ bidList3.getBidPrice();
//                                            textView2.setText(txtfortxtwin);
//                                            pricee = bidList3.getBidPrice();
//                                        }
//                                    }
//                                    @Override
//                                    public void onCancelled(DatabaseError databaseError) {
//
//                                    }
//                                });
//                                Picasso.with(getActivity()).load(bidList3.getBidImage1()).into(imageView);
//                                Picasso.with(getActivity()).load(bidList3.getBidImage2()).into(imageView2);
//                            }
//                        }
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//                else if(dataSnapshot.hasChild("bidImage1")){
//                    progressDialog.dismiss();
//                    imageView.setVisibility(View.VISIBLE);
//                    imageView2.setVisibility(View.GONE);
//                    imageView3.setVisibility(View.GONE);
//
//                    Query search = mDatabaseRef2.child("bid").orderByChild("bidID").startAt(bidID).endAt(bidID+"\uf8ff");
//                    search.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot2) {
//                            for (DataSnapshot snapshot2 : dataSnapshot2.getChildren())
//                            {
//                                final BidList bidList3 = snapshot2.getValue(BidList.class);
//                                textView.setText(bidList3.getBidName());
//                                textView3.setText(bidList3.getCondition());
//                                textView4.setText(bidList3.getBiddersNote());
//                                Query search2 = mDatabaseRef2.child("finalbid").orderByChild("bidID").startAt(bidID).endAt(bidID+"\uf8ff");
//                                search2.addListenerForSingleValueEvent(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(DataSnapshot dataSnapshot3) {
////                                        for (DataSnapshot snapshot3 : dataSnapshot3.getChildren())
////                                        {
////                                            FinalBid bidList4 = snapshot3.getValue(FinalBid.class);
////                                            textView2.setText(bidList4.getOfferPrice());
////                                            pricee = bidList4.getOfferPrice();
////                                        }
//                                        if(dataSnapshot3.exists()){
//                                            for (DataSnapshot snapshot3 : dataSnapshot3.getChildren())
//                                            {
//                                                FinalBid bidList4 = snapshot3.getValue(FinalBid.class);
//                                                String txtfortxtwin = "Current Winning Bid: "+ bidList4.getOfferPrice();
//                                                textView2.setText(txtfortxtwin);
//                                                pricee = bidList4.getOfferPrice();
//
//                                            }
//                                        }
//                                        else{
//                                            String txtfortxtwin = "Current Winning Bid: "+ bidList3.getBidPrice();
//                                            textView2.setText(txtfortxtwin);
//                                            pricee = bidList3.getBidPrice();
//                                        }
//                                    }
//                                    @Override
//                                    public void onCancelled(DatabaseError databaseError) {
//
//                                    }
//                                });
//                                Picasso.with(getActivity()).load(bidList3.getBidImage1()).into(imageView);
//                            }
//                        }
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//        button = view.findViewById(R.id.makebid);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                makeBid();
//            }
//        });
//        return view;
//    }
//    public void makeBid (){
//        final Query search = mDatabaseRef2.child("bid").orderByChild("bidID").equalTo(bidID);
//        search.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (final DataSnapshot snapshot: dataSnapshot.getChildren()) {
//                    BidList bidList = snapshot.getValue(BidList.class);
//                    final String userIDD = bidList.getUserID();
//                    final String bidName = bidList.getBidName();
//                    Query search2 = mDatabaseRef2.child("userinfo").orderByChild("userID").equalTo(userIDD);
//                    search2.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot1) {
//                            for(DataSnapshot snapshot1: dataSnapshot1.getChildren()){
//                                UserList account = snapshot1.getValue(UserList.class);
//                                sname = account.getUserName();
//                                dialog = new Dialog(getActivity());
//                                dialog.setTitle("Make Bid");
//                                dialog.setContentView(R.layout.showbidbox);
//                                TextView name = dialog.findViewById(R.id.textbox9);
//                                name.setText(account.getUserName());
//
//                                ImageView imageView = dialog.findViewById(R.id.imageView4);
//                                Picasso.with(getActivity()).load(account.getUserPhoto()).into(imageView);
//
//                                final TextView textView2 = dialog.findViewById(R.id.textView10);
//                                textView2.setText(account.getUserAddress());
//
//                                final Button ask = dialog.findViewById(R.id.bidnow);
//                                ask.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        String userID = auth.getCurrentUser().getUid();
//                                        String requestID = mDatabaseRef.push().getKey();
//                                        EditText editText = dialog.findViewById(R.id.editText9);
//                                        String nums = editText.getText().toString();
//                                        int req = Integer.parseInt(nums);
//                                        int max = Integer.parseInt(pricee);
//                                        if(max>=req){
//                                            mDatabaseRef4.child(bidID).removeValue();
//                                            FinalBid finalBid = new FinalBid(requestID, nums, bidID, userID, mydate);
//                                            mDatabaseRef4.child(bidID).setValue(finalBid);
//
//                                            BidHistoryList requestTour = new BidHistoryList(requestID,nums,bidID,userID, mydate);
//                                            mDatabaseRef3.child(requestID).setValue(requestTour);
//
////                                            dialog.dismiss();
////                                            Intent intent = new Intent(getContext(), MainActivity.class);
////                                            startActivity(intent);
//                                            ask.setEnabled(false);
//                                            editText.setEnabled(false);
//                                            mDatabaseRef5.getRef().child("offerPrice").setValue(nums);
//
//                                            final DatabaseReference mDatabaseRef3 = FirebaseDatabase.getInstance().getReference("pushnotif").child(userIDD);
//                                            String uploadID = mDatabaseRef3.push().getKey();
//                                            String notifmessage = sname+ " has placed bid (PHP "+nums+ ".00) in: "+ bidName;
//                                            PushNotifList pushNotification = new PushNotifList (uploadID,userIDD,notifmessage);
//                                            mDatabaseRef3.child(uploadID).setValue(pushNotification);
//
//                                            final DatabaseReference mDatabaseRef4 = FirebaseDatabase.getInstance().getReference("pushhistorynotif").child(userIDD);
//                                            PushNotifHistoryList pushNotifHistoryList = new PushNotifHistoryList (uploadID,userIDD,notifmessage,mydate);
//                                            mDatabaseRef4.child(uploadID).setValue(pushNotifHistoryList);
//
//
//                                            Toast.makeText(getActivity(),"Your Bid has been placed", Toast.LENGTH_SHORT).show();
//                                            //notifyAll();
//
//                                        }
//                                        else{
//                                            Toast.makeText(getActivity(),"Lower Bid Required", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
//
//                                dialog.show();
//                            }
//                        }
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//}
package com.example.kuroro.auction;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ShowBidDetails2 extends Fragment {

    FirebaseAuth auth;
    StorageReference mStorageRef;
    DatabaseReference mDatabaseRef, mDatabaseRef2, mDatabaseRef3, mDatabaseRef4, mDatabaseRef5, mDatabaseRef6, mDatabaseRef7;
    ImageView imageView, imageView2, imageView3;
    TextView textView, textView2, textView3, textView4;
    ImageButton button;
    Button ask;
    String bidID, userIDD, data;
    ProgressDialog progressDialog;
    Dialog dialog;
    String pricee, bidName, sname;
    String mydate;
    public ShowBidDetails2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_bid_details, container, false);

        Bundle bundle = this.getArguments();
        bidID = bundle.getString("bidIDD");

        imageView = view.findViewById(R.id.imageView);
        imageView2 = view.findViewById(R.id.imageView2);
        imageView3 = view.findViewById(R.id.imageView3);

        textView = view.findViewById(R.id.textView8);
        textView2 = view.findViewById(R.id.textView9);
        textView3 = view.findViewById(R.id.textView10);
        textView4 = view.findViewById(R.id.textView11);

        auth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("bid").child(bidID);
        mDatabaseRef2 = FirebaseDatabase.getInstance().getReference();
        mDatabaseRef3 = FirebaseDatabase.getInstance().getReference("bidhistory").child(bidID);
        mDatabaseRef4 = FirebaseDatabase.getInstance().getReference("finalbid");
        mDatabaseRef5 = FirebaseDatabase.getInstance().getReference("finalbid").child(bidID);
        mDatabaseRef6 = FirebaseDatabase.getInstance().getReference("autobid").child(bidID);
        mDatabaseRef7 = FirebaseDatabase.getInstance().getReference("autobid");


        imageView.setVisibility(View.GONE);
        imageView2.setVisibility(View.GONE);
        imageView3.setVisibility(View.GONE);

        mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait while loading Item information..... ");
        progressDialog.show();

        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("bidImage3")){
                    progressDialog.dismiss();
                    imageView.setVisibility(View.VISIBLE);
                    imageView2.setVisibility(View.VISIBLE);
                    imageView3.setVisibility(View.VISIBLE);

                    Query search = mDatabaseRef2.child("bid").orderByChild("bidID").startAt(bidID).endAt(bidID+"\uf8ff");
                    search.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2) {
                            for (DataSnapshot snapshot2 : dataSnapshot2.getChildren())
                            {
                                final BidList3 bidList3 = snapshot2.getValue(BidList3.class);
                                textView.setText(bidList3.getBidName());
                                bidName = bidList3.getBidName();
                                textView3.setText(bidList3.getCondition());
                                textView4.setText(bidList3.getBiddersNote());
                                Query search2 = mDatabaseRef2.child("finalbid").orderByChild("bidID").startAt(bidID).endAt(bidID+"\uf8ff");
                                search2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot3) {
//                                        for (DataSnapshot snapshot3 : dataSnapshot3.getChildren())
//                                        {
//                                            FinalBid bidList4 = snapshot3.getValue(FinalBid.class);
//                                            textView2.setText(bidList4.getOfferPrice());
//                                            pricee = bidList4.getOfferPrice();
//                                        }
                                        if(dataSnapshot3.exists()){
                                            for (DataSnapshot snapshot3 : dataSnapshot3.getChildren())
                                            {
                                                FinalBid bidList4 = snapshot3.getValue(FinalBid.class);
                                                String txtfortxtwin = "PHP "+ bidList4.getOfferPrice() + ".00";
                                                textView2.setText(txtfortxtwin);
                                                pricee = bidList4.getOfferPrice();

                                            }
                                        }
                                        else{
                                            String txtfortxtwin = "PHP "+ bidList3.getBidPrice() + ".00";
                                            textView2.setText(txtfortxtwin);
                                            pricee = bidList3.getBidPrice();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                Picasso.with(getActivity()).load(bidList3.getBidImage1()).placeholder(R.drawable.add_animation).into(imageView);
                                Picasso.with(getActivity()).load(bidList3.getBidImage2()).placeholder(R.drawable.add_animation).into(imageView2);
                                Picasso.with(getActivity()).load(bidList3.getBidImage3()).placeholder(R.drawable.add_animation).into(imageView3);
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else if(dataSnapshot.hasChild("bidImage2")){
                    progressDialog.dismiss();
                    imageView.setVisibility(View.VISIBLE);
                    imageView2.setVisibility(View.VISIBLE);
                    imageView3.setVisibility(View.GONE);

                    Query search = mDatabaseRef2.child("bid").orderByChild("bidID").startAt(bidID).endAt(bidID+"\uf8ff");
                    search.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2) {
                            for (DataSnapshot snapshot2 : dataSnapshot2.getChildren())
                            {
                                final BidList2 bidList3 = snapshot2.getValue(BidList2.class);
                                textView.setText(bidList3.getBidName());
                                textView3.setText(bidList3.getCondition());
                                textView4.setText(bidList3.getBiddersNote());
                                Query search2 = mDatabaseRef2.child("finalbid").orderByChild("bidID").startAt(bidID).endAt(bidID+"\uf8ff");
                                search2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot3) {
//                                        for (DataSnapshot snapshot3 : dataSnapshot3.getChildren())
//                                        {
//                                            FinalBid bidList4 = snapshot3.getValue(FinalBid.class);
//                                            textView2.setText(bidList4.getOfferPrice());
//                                            pricee = bidList4.getOfferPrice();
//                                        }
                                        if(dataSnapshot3.exists()){
                                            for (DataSnapshot snapshot3 : dataSnapshot3.getChildren())
                                            {
                                                FinalBid bidList4 = snapshot3.getValue(FinalBid.class);
                                                String txtfortxtwin = "PHP "+ bidList4.getOfferPrice() + ".00";
                                                textView2.setText(txtfortxtwin);
                                                pricee = bidList4.getOfferPrice();

                                            }
                                        }
                                        else{
                                            String txtfortxtwin = "PHP "+ bidList3.getBidPrice() + ".00";
                                            textView2.setText(txtfortxtwin);
                                            pricee = bidList3.getBidPrice();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                Picasso.with(getActivity()).load(bidList3.getBidImage1()).placeholder(R.drawable.add_animation).into(imageView);
                                Picasso.with(getActivity()).load(bidList3.getBidImage2()).placeholder(R.drawable.add_animation).into(imageView2);
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else if(dataSnapshot.hasChild("bidImage1")){
                    progressDialog.dismiss();
                    imageView.setVisibility(View.VISIBLE);
                    imageView2.setVisibility(View.GONE);
                    imageView3.setVisibility(View.GONE);

                    Query search = mDatabaseRef2.child("bid").orderByChild("bidID").startAt(bidID).endAt(bidID+"\uf8ff");
                    search.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2) {
                            for (DataSnapshot snapshot2 : dataSnapshot2.getChildren())
                            {
                                final BidList bidList3 = snapshot2.getValue(BidList.class);
                                textView.setText(bidList3.getBidName());
                                bidName = bidList3.getBidName();
                                textView3.setText(bidList3.getCondition());
                                textView4.setText(bidList3.getBiddersNote());
                                Query search2 = mDatabaseRef2.child("finalbid").orderByChild("bidID").startAt(bidID).endAt(bidID+"\uf8ff");
                                search2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot3) {
//                                        for (DataSnapshot snapshot3 : dataSnapshot3.getChildren())
//                                        {
//                                            FinalBid bidList4 = snapshot3.getValue(FinalBid.class);
//                                            textView2.setText(bidList4.getOfferPrice());
//                                            pricee = bidList4.getOfferPrice();
//                                        }
                                        if(dataSnapshot3.exists()){
                                            for (DataSnapshot snapshot3 : dataSnapshot3.getChildren())
                                            {
                                                FinalBid bidList4 = snapshot3.getValue(FinalBid.class);
                                                String txtfortxtwin = "PHP "+ bidList4.getOfferPrice() + ".00";
                                                textView2.setText(txtfortxtwin);
                                                pricee = bidList4.getOfferPrice();

                                            }
                                        }
                                        else{
                                            String txtfortxtwin = "PHP "+ bidList3.getBidPrice() + ".00";
                                            textView2.setText(txtfortxtwin);
                                            pricee = bidList3.getBidPrice();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                Picasso.with(getActivity()).load(bidList3.getBidImage1()).placeholder(R.drawable.add_animation).into(imageView);
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        button = view.findViewById(R.id.makebid);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeBid();
            }
        });
        return view;
    }
    public void makeBid (){
        final Query search = mDatabaseRef2.child("bid").orderByChild("bidID").equalTo(bidID);
        search.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    BidList bidList = snapshot.getValue(BidList.class);
                    userIDD = bidList.getUserID();
                    final String bidName = bidList.getBidName();
                    Query search2 = mDatabaseRef2.child("userinfo").orderByChild("userID").equalTo(userIDD);
                    search2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot1) {
                            for(DataSnapshot snapshot1: dataSnapshot1.getChildren()){
                                UserList account = snapshot1.getValue(UserList.class);
                                dialog = new Dialog(getActivity());
                                dialog.setTitle("Make Bid");
                                dialog.setContentView(R.layout.showbidbox);
                                final TextView name = dialog.findViewById(R.id.textbox9);
                                name.setText(account.getUserName());

                                ImageView imageView = dialog.findViewById(R.id.imageView4);
                                Picasso.with(getActivity()).load(account.getUserPhoto()).into(imageView);

                                final TextView textView2 = dialog.findViewById(R.id.textView10);
                                textView2.setText(account.getUserAddress());

                                CheckBox checkBox = dialog.findViewById(R.id.checkBox1);
                                final EditText maxbid = dialog.findViewById(R.id.editText10);
                                final EditText incbid = dialog.findViewById(R.id.editText11);
                                final TextView txtmaxbid = dialog.findViewById(R.id.textView1);
                                final TextView txtincbid = dialog.findViewById(R.id.textView2);
                                maxbid.setVisibility(View.GONE);
                                incbid.setVisibility(View.GONE);
                                txtmaxbid.setVisibility(View.GONE);
                                txtincbid.setVisibility(View.GONE);

                                data = "GONE";

                                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                        if (compoundButton.isChecked()) {
                                            maxbid.setVisibility(View.VISIBLE);
                                            incbid.setVisibility(View.VISIBLE);
                                            txtmaxbid.setVisibility(View.VISIBLE);
                                            txtincbid.setVisibility(View.VISIBLE);
                                            data = "VISIBLE";
                                        }
                                        else{
                                            maxbid.setVisibility(View.GONE);
                                            incbid.setVisibility(View.GONE);
                                            txtmaxbid.setVisibility(View.GONE);
                                            txtincbid.setVisibility(View.GONE);
                                            data = "GONE";
                                        }
                                    }
                                });

                                ask = dialog.findViewById(R.id.bidnow);
                                ask.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if(data.equals("GONE")){
                                            autobid();
                                        }
                                        else{
                                            insert();
                                        }

                                    }
                                });

                                dialog.show();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void finalbid(){
        final String userID = auth.getCurrentUser().getUid();
        Query search2 = mDatabaseRef2.child("userinfo").orderByChild("userID").equalTo(userID);
        search2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot2) {
                for(DataSnapshot snapshot2: dataSnapshot2.getChildren()){
                    UserList account = snapshot2.getValue(UserList.class);
                    sname = account.getUserName();
                    String requestID = mDatabaseRef.push().getKey();
                    EditText editText = dialog.findViewById(R.id.editText9);


                    String nums = editText.getText().toString();
                    int req = Integer.parseInt(nums);
                    int max = Integer.parseInt(pricee);
                    if(max>=req){
                        mDatabaseRef4.child(bidID).removeValue();
                        FinalBid finalBid = new FinalBid(requestID, nums, bidID, userID, mydate);
                        mDatabaseRef4.child(bidID).setValue(finalBid);

                        BidHistoryList requestTour = new BidHistoryList(requestID,nums,bidID,userID, mydate);
                        mDatabaseRef3.child(requestID).setValue(requestTour);

                        //                                            dialog.dismiss();
                        //                                            Intent intent = new Intent(getContext(), MainActivity.class);
                        //                                            startActivity(intent);
                        ask.setEnabled(false);
                        editText.setEnabled(false);
                        mDatabaseRef5.getRef().child("offerPrice").setValue(nums);
                        Toast.makeText(getActivity(),"Your Bid has been placed", Toast.LENGTH_SHORT).show();

                        final DatabaseReference mDatabaseRef3 = FirebaseDatabase.getInstance().getReference("pushnotif").child(userIDD);
                        String uploadID = mDatabaseRef3.push().getKey();
                        String notifmessage = sname+ " has placed bid (PHP "+nums+ ".00) in: "+ bidName;
                        PushNotifList pushNotification = new PushNotifList (uploadID,userIDD,notifmessage);
                        mDatabaseRef3.child(uploadID).setValue(pushNotification);

                        final DatabaseReference mDatabaseRef4 = FirebaseDatabase.getInstance().getReference("pushhistorynotif").child(userIDD);
                        PushNotifHistoryList pushNotifHistoryList = new PushNotifHistoryList (uploadID,userIDD,notifmessage,mydate);
                        mDatabaseRef4.child(uploadID).setValue(pushNotifHistoryList);


                        //notifyAll();

                    }
                    else{
                        Toast.makeText(getActivity(),"Lower Bid Required", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void autobid(){
        EditText editText = dialog.findViewById(R.id.editText9);

        String nums = editText.getText().toString();
        final int req = Integer.parseInt(nums);
        int max = Integer.parseInt(pricee);
        if(max>=req) {
            mDatabaseRef6.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    /** Bidder turn off autobid but there is no existing autobid **/
                    if (!dataSnapshot.exists()) {
                        finalbid();
                    }
                    /** Bidder turn off autobid but there is existing autobid **/
                    else {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            AutobidGS account = snapshot.getValue(AutobidGS.class);
                            String autobid = account.getMaxBid();
                            String autoinc = account.getIncBid();
                            String userid = account.getUserID();

                            int existingmax = Integer.parseInt(autobid);
                            int existinginc = Integer.parseInt(autoinc);
                            if (existingmax <= req) {
                                int newoffer = req - existinginc;
                                final String stringoffer = String.valueOf(newoffer);
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("finalbid").child(bidID);
                                databaseReference.getRef().child("offerPrice").setValue(stringoffer);
                                databaseReference.getRef().child("bidTime").setValue(mydate);

                                final String userID = auth.getCurrentUser().getUid();
                                Query search2 = mDatabaseRef2.child("userinfo").orderByChild("userID").equalTo(userID);
                                search2.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot2) {
                                        for (DataSnapshot snapshot2 : dataSnapshot2.getChildren()) {
                                            UserList account = snapshot2.getValue(UserList.class);
                                            sname = account.getUserName();
                                            final DatabaseReference mDatabaseRef3 = FirebaseDatabase.getInstance().getReference("pushnotif").child(userIDD);
                                            String uploadID = mDatabaseRef3.push().getKey();
                                            String notifmessage = sname+ " has placed bid (PHP "+stringoffer+ ".00) in: "+ bidName;
                                            PushNotifList pushNotification = new PushNotifList (uploadID,userIDD,notifmessage);
                                            mDatabaseRef3.child(uploadID).setValue(pushNotification);

                                            final DatabaseReference mDatabaseRef4 = FirebaseDatabase.getInstance().getReference("pushhistorynotif").child(userIDD);
                                            PushNotifHistoryList pushNotifHistoryList = new PushNotifHistoryList (uploadID,userIDD,notifmessage,mydate);
                                            mDatabaseRef4.child(uploadID).setValue(pushNotifHistoryList);
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                                Toast.makeText(getActivity(), "Someone offered a lower bid, Try again?", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                mDatabaseRef6.removeValue();
                                finalbid();
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else{
            Toast.makeText(getActivity(),"Lower Bid Required", Toast.LENGTH_SHORT).show();
        }
    }
    /** if the bidder requests autobid **/
    public void insert(){
        EditText editText = dialog.findViewById(R.id.editText9);

        String nums = editText.getText().toString();
        int req = Integer.parseInt(nums);
        int max = Integer.parseInt(pricee);
        if(max>=req) {
            mDatabaseRef6.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        /** Insert new data in autobid**/
                        EditText edmax = dialog.findViewById(R.id.editText10);
                        EditText edint = dialog.findViewById(R.id.editText11);
                        String newmaxBid = edmax.getText().toString();
                        String newintBid = edint.getText().toString();
                        String uploadID = mDatabaseRef6.push().getKey();
                        AutobidGS autobidGS = new AutobidGS(bidID, newintBid, newmaxBid, auth.getCurrentUser().getUid());
                        mDatabaseRef6.child(uploadID).setValue(autobidGS);
                        finalbid();
                    } else {
                        Toast.makeText(getActivity(), "Auto Bidding is currently unavailable in this bid", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else{
            Toast.makeText(getActivity(),"Lower Bid Required", Toast.LENGTH_SHORT).show();
        }
    }

}

