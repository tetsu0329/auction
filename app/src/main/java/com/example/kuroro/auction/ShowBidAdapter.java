package com.example.kuroro.auction;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Kuroro on 1/12/2018.
 */

public class ShowBidAdapter extends ArrayAdapter<BidList> {
    private Context context;
    private int resource;
    private List<BidList> listImage;

    public ShowBidAdapter (Context context, int resource, List<BidList> objects){
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        listImage = objects;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = layoutInflater.inflate(R.layout.bid_row_layout,parent,false);
        TextView txtname = row.findViewById(R.id.textView);
        TextView txtdays = row.findViewById(R.id.textView5);
        TextView txtprice = row.findViewById(R.id.textView7);

        try{

            txtname.setText(listImage.get(position).getBidName());
            txtdays.setText(listImage.get(position).getBidDays());
            txtprice.setText(listImage.get(position).getBidPrice());

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return row;
    }
}
