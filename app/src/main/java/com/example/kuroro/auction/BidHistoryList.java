package com.example.kuroro.auction;

/**
 * Created by Kuroro on 1/13/2018.
 */

public class BidHistoryList {
    private String bidHistoryID;
    private String offerPrice;
    private String bidID;
    private String userID;
    private String bidtime;

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

    public String getBidtime() { return bidtime; }

    public BidHistoryList(String bidHistoryID, String offerPrice, String bidID, String userID, String bidtime) {
        this.bidHistoryID = bidHistoryID;
        this.offerPrice = offerPrice;
        this.bidID = bidID;
        this.userID = userID;
        this.bidtime = bidtime;
    }
    public BidHistoryList () {}
}
