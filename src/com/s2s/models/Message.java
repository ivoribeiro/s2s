package com.s2s.models;

import com.s2s.mutual.Protocol;
import com.s2s.repository.Channel;
import com.s2s.repository.Channels;
import com.s2s.repository.Clients;
import com.s2s.repository.Users;
import utils.FileUtil;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.Map;

public class Message implements java.io.Serializable {
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

    public static boolean saveChannels(Channels channels, String user) throws Exception {
        FileUtil.createFolder("users/" + user + "/messages");
        saveMessagesOnFile(channels, user);
        return true;
    }

    public static void saveMessagesOnFile(Channels channels, String user) throws Exception {

        if (channels.getModels().size() != 0) {
            StringBuilder message = new StringBuilder();
            for (Map.Entry<String, Channel> entry : channels.getModels().entrySet()) {
                Channel channel = entry.getValue();
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users/" + user + "/" + entry.getKey() + ".txt"));
                oos.writeObject(channel.getModels());
                System.out.println("Done");
            }
        } else {
            System.out.println("You dont have channels to save");
        }

    }
}
