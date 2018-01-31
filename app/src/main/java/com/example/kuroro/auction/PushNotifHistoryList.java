package com.example.kuroro.auction;

/**
 * Created by Khimchi on 1/29/2018.
 */

public class PushNotifHistoryList {
    public String notifID;
    public String notifUser;
    public String notifComment;
    public String notifDate;

    public String getNotifID() {
        return notifID;
    }

    public String getNotifUser() {
        return notifUser;
    }

    public String getNotifComment() {
        return notifComment;
    }

    public String getNotifDate() { return notifDate; }

    public PushNotifHistoryList(String notifID, String notifUser, String notifComment, String notifDate) {

        this.notifID = notifID;
        this.notifUser = notifUser;
        this.notifComment = notifComment;
        this.notifDate = notifDate;
    }
    public PushNotifHistoryList() { }
}
