package com.example.kuroro.auction;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

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

public class OwnPushNotifAdapter extends ArrayAdapter<PushNotifHistoryList> {
    private Context context;
    private int resource;
    private List<PushNotifHistoryList> listImage;
    String sname, bidName, userID;

    public OwnPushNotifAdapter(Context context, int resource, List<PushNotifHistoryList> objects){
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
        row = layoutInflater.inflate(R.layout.pushnotifrow,parent,false);
        final TextView txtdays = row.findViewById(R.id.textView1);
        TextView txtname = row.findViewById(R.id.textView3);

        txtdays.setText(listImage.get(position).getNotifDate());
        txtname.setText(listImage.get(position).getNotifComment());

        return row;
    }
}