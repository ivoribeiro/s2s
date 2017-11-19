package com.s2s.models;

import com.s2s.repository.Clients;

import java.util.UUID;

public class Group {
    private String groupId;
    private String groupAddress;
    private Clients slackers;

    public Group() {
        this.groupId = UUID.randomUUID().toString();
        this.slackers = new Clients();
    }

    public Group(String groupId, String groupAddress, Clients slackers) {
        this.groupId = groupId;
        this.groupAddress = groupAddress;
        this.slackers = slackers;
    }

}
