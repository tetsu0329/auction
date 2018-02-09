package com.example.kuroro.auction;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.util.Calendar;

public class ShowBidDetails4 extends Fragment {

    FirebaseAuth auth;
    StorageReference mStorageRef;
    DatabaseReference mDatabaseRef, mDatabaseRef2, mDatabaseRef3, mDatabaseRef4, mDatabaseRef5;
    ImageView imageView, imageView2, imageView3;
    TextView textView, textView2;
    Button button;
    String bidID;
    ProgressDialog progressDialog;
    Dialog dialog;
    String pricee, sname;
    String mydate;
    TextView m_response;
    PayPalConfiguration m_config;
    String m_paypalclientid = "AbvqhPMuuSRcBVHDh0aoSZm4EwPZguCB0CGRozeNQtvNFI5nZ0U2jjLTuzs7s4ZEj59T_61FrYkhWlFu";
    Intent m_service;
    int m_requestcode = 999;
    public ShowBidDetails4() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_bid_details2, container, false);

        Bundle bundle = this.getArguments();
        bidID = bundle.getString("bidIDD");

        imageView = view.findViewById(R.id.imageView);
        imageView2 = view.findViewById(R.id.imageView2);
        imageView3 = view.findViewById(R.id.imageView3);

        textView = view.findViewById(R.id.textView8);
        textView2 = view.findViewById(R.id.textView9);

        auth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("bid").child(bidID);
        mDatabaseRef2 = FirebaseDatabase.getInstance().getReference();
        mDatabaseRef3 = FirebaseDatabase.getInstance().getReference("bidhistory").child(bidID);
        mDatabaseRef4 = FirebaseDatabase.getInstance().getReference("finalbid");
        mDatabaseRef5 = FirebaseDatabase.getInstance().getReference("finalbid").child(bidID);

        imageView.setVisibility(View.GONE);
        imageView2.setVisibility(View.GONE);
        imageView3.setVisibility(View.GONE);

        //payconfig
        m_response = view.findViewById(R.id.textView13);

        m_config = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(m_paypalclientid);
        m_service = new Intent(getContext(), PayPalService.class);
        m_service.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, m_config);
        getActivity().startService(m_service);

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
                                                textView2.setText(bidList4.getOfferPrice());
                                                pricee = bidList4.getOfferPrice();
                                            }
                                        }
                                        else{
                                            textView2.setText(bidList3.getBidPrice());
                                            pricee = bidList3.getBidPrice();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                Picasso.with(getActivity()).load(bidList3.getBidImage1()).into(imageView);
                                Picasso.with(getActivity()).load(bidList3.getBidImage2()).into(imageView2);
                                Picasso.with(getActivity()).load(bidList3.getBidImage3()).into(imageView3);
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
                                                textView2.setText(bidList4.getOfferPrice());
                                                pricee = bidList4.getOfferPrice();
                                            }
                                        }
                                        else{
                                            textView2.setText(bidList3.getBidPrice());
                                            pricee = bidList3.getBidPrice();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                Picasso.with(getActivity()).load(bidList3.getBidImage1()).into(imageView);
                                Picasso.with(getActivity()).load(bidList3.getBidImage2()).into(imageView2);
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
                                                textView2.setText(bidList4.getOfferPrice());
                                                pricee = bidList4.getOfferPrice();
                                            }
                                        }
                                        else{
                                            textView2.setText(bidList3.getBidPrice());
                                            pricee = bidList3.getBidPrice();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                Picasso.with(getActivity()).load(bidList3.getBidImage1()).into(imageView);
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

        button = view.findViewById(R.id.BuyNow);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeBid();
            }
        });
        return view;
    }
    public void makeBid (){
        PayPalPayment payment = new PayPalPayment(new BigDecimal(pricee), "PHP", "Online Auction Payment with Paypal",
                PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(getContext(), PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, m_config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, m_requestcode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == m_requestcode){
            if(resultCode == Activity.RESULT_OK){
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                if(confirmation!=null){
                    String state = confirmation.getProofOfPayment().getState();

                    if(state.equals("approved")){
                        m_response.setText("payment approved");
                    }
                    else{
                        m_response.setText("error in the payment");
                    }
                }
                else{
                    m_response.setText("confirmation is null");
                }
            }
        }
    }
}
