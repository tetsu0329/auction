package com.example.kuroro.auction;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Kuroro on 1/12/2018.
 */

public class ShowHistoryAdapter extends ArrayAdapter<BidHistoryList> {
    private Context context;
    private int resource;
    private List<BidHistoryList> listImage;
    DatabaseReference mDatabaseRef;

    public ShowHistoryAdapter(Context context, int resource, List<BidHistoryList> objects){
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        listImage = objects;
    }
    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = layoutInflater.inflate(R.layout.historyrow,parent,false);
        final TextView txtname = row.findViewById(R.id.textView1);
        TextView txtdays = row.findViewById(R.id.textView2);
        TextView txtprice = row.findViewById(R.id.textView3);


        try{
            String offprice = "P "+listImage.get(position).getOfferPrice() + ".00";
            txtname.setText(listImage.get(position).getBidID());
            mDatabaseRef = FirebaseDatabase.getInstance().getReference("userinfo");

            Query search = mDatabaseRef.orderByChild("userID").equalTo(listImage.get(position).getUserID());
            search.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        UserList productUpload = snapshot.getValue(UserList.class);
                        txtname.setText(productUpload.getUserName());
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            txtprice.setText(offprice);
            txtdays.setText(listImage.get(position).getBidtime());
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return row;
    }
}