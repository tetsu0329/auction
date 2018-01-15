package com.example.kuroro.auction;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kuroro on 1/12/2018.
 */

public class ShowBidAdapter extends ArrayAdapter<BidList> {
    private Context context;
    private int resource;
    private List<BidList> listImage;
    DatabaseReference mDatabaseRef2;

    public ShowBidAdapter (Context context, int resource, List<BidList> objects){
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        listImage = objects;
    }
    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        mDatabaseRef2 = FirebaseDatabase.getInstance().getReference();
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = layoutInflater.inflate(R.layout.bid_row_layout,parent,false);
        TextView txtname = row.findViewById(R.id.textView);
        TextView txtdays = row.findViewById(R.id.textView5);
        TextView txtprice = row.findViewById(R.id.textView7);
        final TextView txtwin = row.findViewById(R.id.textView8);

        try{
            String bidID = listImage.get(position).getBidID();
            txtname.setText(listImage.get(position).getBidName());
            txtdays.setText(listImage.get(position).getBidDays());
            String textfortxtprice = "Starting Bid: " + listImage.get(position).getBidPrice();
            txtprice.setText(textfortxtprice);

            Query search2 = mDatabaseRef2.child("finalbid").orderByChild("bidID").startAt(bidID).endAt(bidID+"\uf8ff");
            search2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot3) {
                    if(dataSnapshot3.exists()){
                        for (DataSnapshot snapshot3 : dataSnapshot3.getChildren())
                        {
                            FinalBid bidList4 = snapshot3.getValue(FinalBid.class);
                            String txtfortxtwin = "Current Winning Bid: "+ bidList4.getOfferPrice();
                            txtwin.setText(txtfortxtwin);

                        }
                    }
                    else{
                        String txtfortxtwin = "Current Winning Bid: "+ listImage.get(position).getBidPrice();
                        txtwin.setText(txtfortxtwin);
                    }

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return row;
    }
}
