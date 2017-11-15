package com.s2s;

import java.util.UUID;

public class Slacker {
    private String id;
    private String ip;
    private int port;

    public Slacker(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.id = UUID.randomUUID().toString();
    }
}
