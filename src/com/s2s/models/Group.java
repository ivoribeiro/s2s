package com.s2s.models;

import com.s2s.repository.Clients;

import java.io.IOException;
import java.net.*;
import java.util.UUID;

public class Group extends Thread {
    private String groupId;
    private String groupName;
    private Clients slackers;
    private String group;
    private int multicastPort;
    DatagramSocket multicasSocket;

    public Group(String name, Slacker creator) {
        this.groupId = UUID.randomUUID().toString();
        this.slackers = new Clients();
        this.groupName = name;
        this.slackers.addClient(creator);
        this.multicastPort = utils.PortGen.getMultiCastPort();
        this.group = "225.4.5.6";
    }

    public String getGroupName() {
        return this.groupName;
    }

    public Clients getClients() {
        return this.slackers;
    }

    public void leave(Slacker slacker) {
        this.getClients().delete(slacker.getUsername());
    }

    /**
     * Send a message to all the  group clients
     *
     * @param message
     */
    public void sendMessage(String message) throws IOException {
        MulticastSocket socket = new MulticastSocket();
        byte[] buf;
        buf = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length,
                InetAddress.getByName(this.group), this.multicastPort);
        socket.send(packet);
    }
}
