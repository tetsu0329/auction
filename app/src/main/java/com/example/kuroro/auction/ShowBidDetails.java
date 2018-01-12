package com.example.kuroro.auction;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

public class ShowBidDetails extends Fragment {

    FirebaseAuth auth;
    String bidID, bidPrice;
    public ShowBidDetails() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_bid_details, container, false);
        Bundle bundle = getActivity().getIntent().getExtras();
        bidID = bundle.getString("bidIDD");
        bidPrice = bundle.getString("bidPricee");
        return view;
    }

}
