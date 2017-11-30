package com.s2s.models;

import com.s2s.repository.Clients;

import java.util.UUID;

public class Group {
    private String groupId;
    private String groupName;
    private String groupAddress;
    private Clients slackers;

    public Group() {
        this.groupId = UUID.randomUUID().toString();
        this.slackers = new Clients();
    }

    public Group(String name, Slacker creator) {
        this.groupId = UUID.randomUUID().toString();
        this.slackers = new Clients();
        this.groupName = name;
        this.slackers.addClient(creator);
    }

    public Group(String groupId, String groupAddress, Clients slackers) {
        this.groupId = groupId;
        this.groupAddress = groupAddress;
        this.slackers = slackers;
    }

    public String getName() {
        return this.groupName;
    }

    public Clients getClients() {
        return this.slackers;
    }

    public void leave(Slacker slacker) {
        this.getClients().delete(slacker.getUsername());
    }

}
