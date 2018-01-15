package com.example.kuroro.auction;

/**
 * Created by Kuroro on 1/13/2018.
 */

public class FinalBid {
    private String bidHistoryID;
    private String offerPrice;
    private String bidID;
    private String userID;

    public String getBidHistoryID() {
        return bidHistoryID;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public String getBidID() {
        return bidID;
    }

    public String getUserID() {
        return userID;
    }

    public FinalBid(String bidHistoryID, String offerPrice, String bidID, String userID) {
        this.bidHistoryID = bidHistoryID;
        this.offerPrice = offerPrice;
        this.bidID = bidID;
        this.userID = userID;
    }
    public FinalBid () {}
}
