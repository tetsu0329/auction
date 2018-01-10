package com.example.kuroro.auction;

/**
 * Created by Kuroro on 1/9/2018.
 */

public class UserList {
    public String userID;
    public String userName;
    public String userAddress;
    public String userPhoto;
    public String userContact;
    public String userEmail;
    public String affiliation;
    public String userStatus;

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public String getUserContact() {
        return userContact;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getAffiliation(){
        return affiliation;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public UserList(String userID, String userName, String userAddress, String userPhoto, String userContact, String userEmail, String affiliation, String userStatus) {
        this.userID = userID;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userPhoto = userPhoto;
        this.userContact = userContact;
        this.userEmail = userEmail;
        this.affiliation = affiliation;
        this.userStatus = userStatus;
    }
    public UserList (){

    }
}
