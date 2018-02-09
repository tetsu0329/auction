package com.example.kuroro.auction;

/**
 * Created by Khimchi on 2/6/2018.
 */

public class ContactGS {
    String contactUsID;
    String contactUsTitle;
    String contactUsComment;
    String contactUsDate;
    String contactUser;

    public ContactGS(String contactUsID, String contactUsTitle, String contactUsComment, String contactUsDate, String contactUser) {
        this.contactUsID = contactUsID;
        this.contactUsTitle = contactUsTitle;
        this.contactUsComment = contactUsComment;
        this.contactUsDate = contactUsDate;
        this.contactUser = contactUser;
    }

    public String getContactUsID() {
        return contactUsID;
    }

    public String getContactUsTitle() {
        return contactUsTitle;
    }

    public String getContactUsComment() {
        return contactUsComment;
    }

    public String getContactUsDate() {
        return contactUsDate;
    }
}
