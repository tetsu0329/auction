package com.example.kuroro.auction;

/**
 * Created by Kuroro on 1/11/2018.
 */

public class BidList3 {
    public String bidID;
    public String userID;
    public String bidName;
    public String bidPrice;
    public String winningBid;
    public String bidDays;
    public String bidType;
    public String bidImage1;
    public String bidImage2;
    public String bidImage3;
    public String condition;
    public String biddersNote;
    public String quantity;


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

    public String getBidImage2() { return bidImage2; }

    public String getBidImage3() {return bidImage3; }

    public String getCondition(){ return condition; }

    public String getBiddersNote() { return biddersNote; }

    public String getQuantity() { return quantity; }

    public BidList3(String bidID, String userID, String bidName, String bidPrice, String winningBid, String bidDays, String bidType, String bidImage1, String bidImage2, String bidImage3, String condition, String biddersNote, String quantity) {
        this.bidID = bidID;
        this.userID = userID;
        this.bidName = bidName;
        this.bidPrice = bidPrice;
        this.winningBid = winningBid;
        this.bidDays = bidDays;
        this.bidType = bidType;
        this.bidImage1 = bidImage1;
        this.bidImage2 = bidImage2;
        this.bidImage3 = bidImage3;
        this.condition = condition;
        this.biddersNote = biddersNote;
        this.quantity = quantity;
    }
    public BidList3(){}
}
