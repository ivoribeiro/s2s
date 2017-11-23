package com.s2s.models;

import java.io.*;
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
    private BufferedReader in;
    private BufferedWriter out;

    public Slacker(Socket clientSocket, String address, int port) throws IOException {
        this.id = UUID.randomUUID().toString();
        this.clientSocket = clientSocket;
        this.address = address;
        this.port = port;
        this.loged = false;
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        Writer ouw = new OutputStreamWriter(clientSocket.getOutputStream());
        this.out = new BufferedWriter(ouw);
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
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
}
