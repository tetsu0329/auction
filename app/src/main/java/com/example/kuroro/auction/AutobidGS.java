package com.example.kuroro.auction;

/**
 * Created by Khimchi on 2/10/2018.
 */

public class AutobidGS {
    String bidID;
    String incBid;
    String maxBid;
    String userID;

    public AutobidGS(String bidID, String incBid, String maxBid, String userID) {
        this.bidID = bidID;
        this.incBid = incBid;
        this.maxBid = maxBid;
        this.userID = userID;
    }

    public String getBidID() {
        return bidID;
    }

    public String getIncBid() {
        return incBid;
    }

    public String getMaxBid() {
        return maxBid;
    }

    public String getUserID() {
        return userID;
    }
    public AutobidGS(){ }
}
