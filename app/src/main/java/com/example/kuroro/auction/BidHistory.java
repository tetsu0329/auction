package com.example.kuroro.auction;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BidHistory extends Fragment {
    private List<BidList> bidList;
    ListView listView;
    ProgressDialog progressDialog;
    DatabaseReference mDatabaseRef;
    ShowBidAdapter showBidAdapter;
    FirebaseAuth firebaseAuth;
    String userID;
    public BidHistory() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_own, container, false);
        bidList = new ArrayList<>();
        listView = view.findViewById(R.id.listView);
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BidList bidList2 = (BidList) adapterView.getAdapter().getItem(i);
                String userID2 = bidList2.getBidID();
                Toast.makeText(getActivity(), bidList2.getBidType(), Toast.LENGTH_SHORT).show();
                if(bidList2.getBidType().equals("English")){

                }
                if(bidList2.getBidType().equals("Dutch")){

                }
                if(bidList2.getBidType().equals("Sealed")){

                }
            }
        });

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait while loading Items in your Auction..... ");
        progressDialog.show();
        progressDialog.setCancelable(false);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("bid");
        Query search = mDatabaseRef.orderByChild("userID").equalTo(userID);
        search.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bidList.clear();
                progressDialog.dismiss();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    BidList productUpload = snapshot.getValue(BidList.class);
                    bidList.add(productUpload);
                }
                showBidAdapter = new ShowBidAdapter(getActivity(), R.layout.ownbid_row_layout, bidList);
                listView.setAdapter(showBidAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }

}
