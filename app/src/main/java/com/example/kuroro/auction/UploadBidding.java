package com.example.kuroro.auction;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class UploadBidding extends Fragment{

    int minteger = 1;
    Button btnincrease, btndecrease;
    TextView integernum;
    public UploadBidding() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_bidding, container, false);
        btnincrease = view.findViewById(R.id.increase);
        integernum  = view.findViewById(R.id.integer_number);
        btnincrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minteger = minteger + 1;
                display(minteger);
            }
        });
        btndecrease = view.findViewById(R.id.decrease);
        btndecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (minteger>1){
                    minteger = minteger - 1;
                    display(minteger);
                }
                else{
                    display(minteger);
                }
            }
        });
        return view;
    }

    private void display(int number) {
        String word = ""+number;
        integernum.setText(word);
    }

}
