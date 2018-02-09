package com.example.kuroro.auction;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Calendar;
import java.util.List;

public class ContactUs extends Fragment {

    private List<BidList> bidList;
    ListView listView;
    ProgressDialog progressDialog;
    DatabaseReference mDatabaseRef;
    ShowBidAdapter showBidAdapter;
    EditText editText;
    EditText editText2;
    Button btnsubmit;
    FirebaseAuth auth;
    String mydate;
    DatabaseReference mDatabaseReference;
    public ContactUs() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        editText = view.findViewById(R.id.editText10);
        editText2 = view.findViewById(R.id.editText11);
        btnsubmit = view.findViewById(R.id.button1);
        auth = FirebaseAuth.getInstance();
        mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("contactus");
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editText.getText().toString();
                String comment = editText2.getText().toString();
                String userID = auth.getCurrentUser().getUid();
                String contactID = mDatabaseReference.push().getKey();
                ContactGS userAccount = new ContactGS(contactID, title, comment, mydate, userID);
                mDatabaseReference.child(contactID).setValue(userAccount);
            }
        });
        return view;
    }
}
