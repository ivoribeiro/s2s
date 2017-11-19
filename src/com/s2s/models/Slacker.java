package com.s2s.models;

import java.net.Socket;
import java.util.UUID;

public class Slacker {
    private String id;
    private Socket clientSocket;
    private String address;
    private int port;
    private String username;
    private String password;
    private boolean loged;

    public Slacker(Socket clientSocket, String address, int port) {
        this.id = UUID.randomUUID().toString();
        this.clientSocket = clientSocket;
        this.address = address;
        this.port = port;
        this.loged = false;
    }

    public String getUsername() {
        return this.username;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLoged(boolean loged) {
        this.loged = loged;
    }
}
