package com.s2s.models;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

public class Slacker {
    private String id;
    private Socket clientSocket;
    private String address;
    private int port;
    private User user;
    private boolean loged;
    private BufferedReader in;
    private BufferedWriter out;

    public Slacker(Socket clientSocket) throws IOException {
        this.id = UUID.randomUUID().toString();
        this.clientSocket = clientSocket;
        this.address = clientSocket.getInetAddress().getHostName();
        this.loged = false;
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        Writer ouw = new OutputStreamWriter(clientSocket.getOutputStream());
        this.out = new BufferedWriter(ouw);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setLoged(boolean loged) {
        this.loged = loged;
    }

    public boolean isLoged() {
        return this.loged;
    }

    public BufferedReader getIn() {
        return in;
    }

    public BufferedWriter getOut() {
        return out;
    }

    public void sendResponse(String message) {
        try {
            this.out.write(message + "\r\n");
            this.out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return this.user.getUsername() + " " + this.address + " " + this.port;
    }

    public User getUser() {
        return this.user;
    }
}
