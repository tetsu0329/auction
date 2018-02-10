package com.example.kuroro.auction;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        final TextView txtprice = row.findViewById(R.id.textView7);
        final TextView txtwin = row.findViewById(R.id.textView8);
        final TextView txttype = row.findViewById(R.id.textView9);
        final ImageView imageButton = row.findViewById(R.id.imageView5);

        try{
            if(listImage.get(position).getBidType().equals("BuyMe")){
                ImageView imageView = row.findViewById(R.id.imageButton);
                imageView.setImageResource(R.drawable.buyme);
            }
            String bidID = listImage.get(position).getBidID();
            txtname.setText(listImage.get(position).getBidName());

            txttype.setText(listImage.get(position).getBidType());
            Picasso.with(context).load(listImage.get(position).getBidImage1()).into(imageButton);
            String textfortxtprice = "PHP " + listImage.get(position).getBidPrice() + ".00";
            txtprice.setText(textfortxtprice);

            String d = listImage.get(position).getBidDays();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd yyyy");
            Date d1 = dateFormat.parse(d);

            Time today = new Time(Time.getCurrentTimezone());
            today.setToNow();
            int day2 = today.monthDay;
            int month2 = today.month;
            int year2 = today.year-1900;

            Date d2 = new Date(year2, month2, day2);

            long diff = d1.getTime() - d2.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);

            String daysleft = String.valueOf(diffDays) + " days left";
            txtdays.setText(daysleft);

            Query search2 = mDatabaseRef2.child("finalbid").orderByChild("bidID").startAt(bidID).endAt(bidID+"\uf8ff");
            search2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot3) {
                    if(dataSnapshot3.exists()){
                        for (DataSnapshot snapshot3 : dataSnapshot3.getChildren())
                        {
                            if(txttype.getText().toString().equals("Sealed")){
                            String txtfortxtwin = "Current Winning Bid: Sealed";
                            txtwin.setText(txtfortxtwin);
                        }
                            else{
                                FinalBid bidList4 = snapshot3.getValue(FinalBid.class);
                                String txtfortxtwin = "PHP "+ bidList4.getOfferPrice() +".00";
                                txtwin.setText(txtfortxtwin);
                            }
                        }
                    }
                    else{
                        if(txttype.getText().toString().equals("Sealed")){
                            String txtfortxtwin = "Current Winning Bid: Sealed";
                            txtwin.setText(txtfortxtwin);
                        }
                        else{
                            String txtfortxtwin = "PHP "+ listImage.get(position).getBidPrice() +".00";
                            txtwin.setText(txtfortxtwin);
                        }

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
