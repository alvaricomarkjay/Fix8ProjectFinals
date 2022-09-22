package com.example.capstoneproject;

public class MessageA_Item {

    String image, username, cMessage, time, nullName, uname;

    public MessageA_Item(String image, String username, String cMessage, String time, String nullName, String uname) {
        this.image = image;
        this.username = username;
        this.cMessage = cMessage;
        this.time = time;
        this.nullName = nullName;
        this.uname = uname;

    }

    public String getImage() {
        return image;
    }

    public String getUsername() {
        return username;
    }

    public String getcMessage() {
        return cMessage;
    }

    public String getTime() {
        return time;
    }

    public String getNullName() {
        return nullName;
    }

    public String getUname() {
        return uname;
    }

}
