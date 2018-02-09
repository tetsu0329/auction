package com.example.kuroro.auction;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowAllBid extends Fragment {

    private List<BidList> bidList;
    ListView listView;
    ProgressDialog progressDialog;
    DatabaseReference mDatabaseRef;
    ShowBidAdapter showBidAdapter;
    public ShowAllBid() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_all_bid, container, false);
        bidList = new ArrayList<>();
        listView = view.findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BidList bidList2 = (BidList) adapterView.getAdapter().getItem(i);
                String userID2 = bidList2.getBidID();
                Toast.makeText(getActivity(), bidList2.getBidType(), Toast.LENGTH_SHORT).show();
                if(bidList2.getBidType().equals("English")){
                    Bundle bundle = new Bundle();
                    bundle.putString("bidIDD",userID2);
                    ShowBidDetails fragment2 = new ShowBidDetails();
                    fragment2.setArguments(bundle);

                    android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.contentmain, fragment2);
                    fragmentTransaction.commit();
                }
                if(bidList2.getBidType().equals("Dutch")){
                    Bundle bundle = new Bundle();
                    bundle.putString("bidIDD",userID2);
                    ShowBidDetails2 fragment2 = new ShowBidDetails2();
                    fragment2.setArguments(bundle);

                    android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.contentmain, fragment2);
                    fragmentTransaction.commit();
                }
                if(bidList2.getBidType().equals("Sealed")){
                    Bundle bundle = new Bundle();
                    bundle.putString("bidIDD",userID2);
                    ShowBidDetails3 fragment2 = new ShowBidDetails3();
                    fragment2.setArguments(bundle);

                    android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.contentmain, fragment2);
                    fragmentTransaction.commit();
                }
                if(bidList2.getBidType().equals("BuyMe")){
                    Bundle bundle = new Bundle();
                    bundle.putString("bidIDD",userID2);
                    ShowBidDetails4 fragment4 = new ShowBidDetails4();
                    fragment4.setArguments(bundle);

                    android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.contentmain, fragment4);
                    fragmentTransaction.commit();
                }
            }
        });

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait while loading Items in the Auction..... ");
        progressDialog.show();
        progressDialog.setCancelable(false);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("bid");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bidList.clear();
                progressDialog.dismiss();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    BidList productUpload = snapshot.getValue(BidList.class);
                    bidList.add(productUpload);
                }
                showBidAdapter = new ShowBidAdapter(getActivity(), R.layout.bid_row_layout, bidList);
                listView.setAdapter(showBidAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }
}
