package com.example.kuroro.auction;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by Kuroro on 1/12/2018.
 */

public class ShowWinAdapter2 extends ArrayAdapter<FinalBid> {
    private Context context;
    private int resource;
    private List<FinalBid> listImage;
    DatabaseReference mDatabaseRef, mDatabaseRef2;
    String sname, bidName, userID;

    public ShowWinAdapter2(Context context, int resource, List<FinalBid> objects){
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        listImage = objects;
    }
    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = layoutInflater.inflate(R.layout.historyrow2,parent,false);
        final TextView txtname = row.findViewById(R.id.textView1);
        TextView txtdays = row.findViewById(R.id.textView2);
        TextView txtprice = row.findViewById(R.id.textView3);
        Button btnnotify = row.findViewById(R.id.button6);


        try{
            String offprice = "P "+listImage.get(position).getOfferPrice() + ".00";
            txtname.setText(listImage.get(position).getBidID());
            mDatabaseRef = FirebaseDatabase.getInstance().getReference("userinfo");
            mDatabaseRef2 = FirebaseDatabase.getInstance().getReference("bid");
            userID = listImage.get(position).getUserID();
            Query search = mDatabaseRef.orderByChild("userID").equalTo(listImage.get(position).getUserID());
            search.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        UserList productUpload = snapshot.getValue(UserList.class);
                        txtname.setText(productUpload.getUserName());
                        sname = productUpload.getUserName();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            Query search2 = mDatabaseRef2.orderByChild("bidID").equalTo(listImage.get(position).getBidID());
            search2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot2) {
                        for(DataSnapshot snapshot2: dataSnapshot2.getChildren()){
                            BidList productUpload = snapshot2.getValue(BidList.class);
                            bidName = productUpload.getBidName();
                        }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            txtprice.setText(offprice);
            txtdays.setText(listImage.get(position).getBidTime());
        }
        catch(Exception e){
            e.printStackTrace();
        }
        btnnotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference mDatabaseRef3 = FirebaseDatabase.getInstance().getReference("pushnotif").child(userID);
                String uploadID = mDatabaseRef3.push().getKey();
                String notifmessage = "Bidder: "+sname+ " you have won the bidding in "+ bidName;
                PushNotifList pushNotification = new PushNotifList (uploadID,userID,notifmessage);
                mDatabaseRef3.child(uploadID).setValue(pushNotification);
            }
        });
        return row;
    }
}