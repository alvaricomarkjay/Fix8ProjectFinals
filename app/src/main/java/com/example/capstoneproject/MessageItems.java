package com.example.capstoneproject;

public class MessageItems {

    String fullname, chat_id, dphoto, ddate_time, dmessage, dstatus, duser1, duser2;

    public MessageItems(String fullname, String chat_id, String dphoto, String ddate_time, String dmessage, String dstatus, String duser1, String duser2) {
        this.fullname = fullname;
        this.chat_id = chat_id;
        this.dphoto = dphoto;
        this.ddate_time = ddate_time;
        this.dmessage = dmessage;
        this.dstatus = dstatus;
        this.duser1 = duser1;
        this.duser2 = duser2;
    }

    public String getFullname() { return fullname; }

    public String getChat_id() {
        return chat_id;
    }

    public String getPhoto() {
        return dphoto;
    }

    public String getDdate_time() {
        return ddate_time;
    }

    public String getDmessage() {
        return dmessage;
    }

    public String getDstatus() {
        return dstatus;
    }

    public String getDuser1() {
        return duser1;
    }

    public String getDuser2() {
        return duser2;
    }
}
