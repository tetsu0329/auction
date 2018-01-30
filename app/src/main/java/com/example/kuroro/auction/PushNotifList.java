package com.example.kuroro.auction;

/**
 * Created by Khimchi on 1/29/2018.
 */

public class PushNotifList {
    public String notifID;
    public String notifUser;
    public String notifComment;

    public String getNotifID() {
        return notifID;
    }

    public String getNotifUser() {
        return notifUser;
    }

    public String getNotifComment() {
        return notifComment;
    }

    public PushNotifList(String notifID, String notifUser, String notifComment) {

        this.notifID = notifID;
        this.notifUser = notifUser;
        this.notifComment = notifComment;
    }
    public PushNotifList() { }
}
