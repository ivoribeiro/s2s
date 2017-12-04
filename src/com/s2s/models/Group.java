package com.s2s.models;

import com.s2s.repository.Clients;

import java.io.IOException;
import java.net.*;
import java.util.UUID;

public class Group extends Thread {
    private String groupId;
    private String groupName;
    private String groupAddress;
    private Clients slackers;
    private int multicastPort;
    DatagramSocket multicasSocket;
    InetAddress multicastGroup;


    public Group(String name, Slacker creator) {
        this.groupId = UUID.randomUUID().toString();
        this.slackers = new Clients();
        this.groupName = name;
        this.slackers.addClient(creator);
        this.multicastPort = utils.PortGen.getMultiCastPort();
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
        byte[] buf = new byte[256];
        buf = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, this.multicastGroup, this.multicastPort);
        this.multicasSocket.send(packet);
    }

    public void run() {
        try {
            DatagramSocket socket = new DatagramSocket(this.multicastPort);
            InetAddress group = InetAddress.getByName("230.0.0.1");
            this.multicasSocket = socket;
            this.multicastGroup = group;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

}
