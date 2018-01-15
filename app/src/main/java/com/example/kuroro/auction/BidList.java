package com.example.kuroro.auction;

/**
 * Created by Kuroro on 1/11/2018.
 */

public class BidList {
    public String bidID;
    public String userID;
    public String bidName;
    public String bidPrice;
    public String winningBid;
    public String bidDays;
    public String bidType;
    public String bidImage1;

    public String getBidID() {
        return bidID;
    }

    public String getUserID() {
        return userID;
    }

    public String getBidName() {
        return bidName;
    }

    public String getBidPrice() {
        return bidPrice;
    }

    public String getWinningBid() { return  winningBid; }

    public String getBidDays() {
        return bidDays;
    }

    public String getBidType (){ return bidType; }

    public String getBidImage1() {
        return bidImage1;
    }

    public BidList(String bidID, String userID, String bidName, String bidPrice, String winningBid, String bidDays, String bidType, String bidImage1) {
        this.bidID = bidID;
        this.userID = userID;
        this.bidName = bidName;
        this.bidPrice = bidPrice;
        this.winningBid = winningBid;
        this.bidDays = bidDays;
        this.bidType = bidType;
        this.bidImage1 = bidImage1;
    }
    public BidList (){}
}
