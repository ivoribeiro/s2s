package com.s2s.p2p;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MessageListner extends Thread {
    private BufferedReader in;
    private String response;

    public MessageListner(InputStream in) {
        this.in = new BufferedReader(new InputStreamReader(in));
    }

    public MessageListner() {
        super("Message Listner Thread");
    }

    public void run() {
        String inputLine;
        try {
            while ((inputLine = this.in.readLine()) != null) {
                System.out.println("ClientServer: Mensagem recebida");
                System.out.println(inputLine);
                this.response = inputLine;
            }
            //close the buffer reader
            this.in.close();
        } catch (IOException e) {
            System.out.println("Error reading buffer");
            e.printStackTrace();
        }
    }

    public String getResponse() {
        return this.response;
    }
}