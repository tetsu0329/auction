package com.example.kuroro.auction;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class BidHistoryView extends AppCompatActivity {

    private List<BidHistoryList> bidList;
    ListView listView;
    private List<FinalBid> bidList2;
    ListView listView2;
    ProgressDialog progressDialog;
    DatabaseReference mDatabaseRef, mDatabaseRef2;
    ShowHistoryAdapter showBidAdapter;
    ShowWinAdapter2 showBidAdapter2;
    FirebaseAuth firebaseAuth;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_bid_history);
        bidList = new ArrayList<>();
        bidList2 = new ArrayList<>();
        listView = findViewById(R.id.listview1);
        listView2 = findViewById(R.id.listview2);
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();
        Bundle bundle = getIntent().getExtras();
        String bidID = bundle.getString("key");
        progressDialog = new ProgressDialog(BidHistoryView.this);
        progressDialog.setMessage("Please wait while loading your history..... ");
        progressDialog.show();
        progressDialog.setCancelable(false);


        mDatabaseRef = FirebaseDatabase.getInstance().getReference("bidhistory").child(bidID);
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    bidList.clear();
                    progressDialog.dismiss();
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        BidHistoryList productUpload = snapshot.getValue(BidHistoryList.class);
                        bidList.add(productUpload);
                    }
                    progressDialog.dismiss();
                    showBidAdapter = new ShowHistoryAdapter(getApplicationContext(), R.layout.historyrow, bidList);
                    listView.setAdapter(showBidAdapter);
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mDatabaseRef2 = FirebaseDatabase.getInstance().getReference("finalbid");
        Query search = mDatabaseRef2.orderByChild("bidID").equalTo(bidID);
        search.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot2) {
                bidList2.clear();
                for(DataSnapshot snapshot2: dataSnapshot2.getChildren()){
                    FinalBid productUpload2 = snapshot2.getValue(FinalBid.class);
                    bidList2.add(productUpload2);
                }
                showBidAdapter2 = new ShowWinAdapter2(getApplicationContext(), R.layout.historyrow2, bidList2);
                listView2.setAdapter(showBidAdapter2);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
}
}