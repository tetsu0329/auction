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

    private List<BidList> bidList;
    ListView listView;
    ProgressDialog progressDialog;
    DatabaseReference mDatabaseRef;
    ShowBidAdapter showBidAdapter;
    FirebaseAuth firebaseAuth;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_bid_history);
//        bidList = new ArrayList<>();
//        listView = findViewById(R.id.listView);
//        firebaseAuth = FirebaseAuth.getInstance();
//        userID = firebaseAuth.getCurrentUser().getUid();
//        Bundle bundle = getIntent().getExtras();
//        String bidID = bundle.getString("key");
//        Toast.makeText(getApplicationContext(), bidID, Toast.LENGTH_LONG).show();
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//        });
//
//        progressDialog = new ProgressDialog(getApplicationContext());
//        progressDialog.setMessage("Please wait while loading Items in your Auction..... ");
//        progressDialog.show();
//        progressDialog.setCancelable(false);
//
//        mDatabaseRef = FirebaseDatabase.getInstance().getReference("bid");
//        Query search = mDatabaseRef.orderByChild("userID").equalTo(userID);
//        search.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                bidList.clear();
//                progressDialog.dismiss();
//                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
//                    BidList productUpload = snapshot.getValue(BidList.class);
//                    bidList.add(productUpload);
//                }
//                showBidAdapter = new ShowBidAdapter(getApplicationContext(), R.layout.ownbid_row_layout, bidList);
//                listView.setAdapter(showBidAdapter);
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }
}
