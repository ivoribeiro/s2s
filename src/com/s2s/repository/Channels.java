package com.s2s.repository;

import com.s2s.models.Message;
import com.s2s.models.Slacker;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

public class Channels extends Repository<String, Channel> {
    public Channels() {
        super();
    }

    public Channels(HashMap<String, Channel> channels) {
        super(channels);
    }

    public void addMessage(String source, String message) {
        Channel temp = this.exists(source);
        if (temp != null) {
            temp.addModel(new Timestamp(System.currentTimeMillis()), new Message(source, message));
        }
        // if dont exists create a channel
        else {
            Channel channel = new Channel();
            channel.addModel(new Timestamp(System.currentTimeMillis()), new Message(source, message));
            this.addModel(source, channel);
        }
    }
}
