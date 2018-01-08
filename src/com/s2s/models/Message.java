package com.s2s.models;

import java.util.Date;

public class Message {
    private String user1;
    private String user2;
    private String message;
    private Date date;

    public Message(String source, String message) {
        this.user1 = source;
        this.message = message;
        this.date = new Date();
    }

    @Override
    public String toString() {
        return user1 + ": " + message;
    }
}
