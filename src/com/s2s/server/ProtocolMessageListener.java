package com.s2s.server;

import com.s2s.models.Slacker;
import com.s2s.mutual.Protocol;
import com.s2s.repository.Repository;
import com.s2s.repository.Routes;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class ProtocolMessageListener extends Thread {
    private Socket client;
    private Router router;
    private Slacker slacker;
    Map<String, Repository> repos;

    ProtocolMessageListener(Map<String, Repository> repos, Slacker slacker) throws IOException {
        super("ProtocolMessageListener Thread");
        this.slacker = slacker;
        this.repos = repos;
        this.router = new Router(slacker, repos);
    }

    public void run() {
        String inputLine;
        try {
            while ((inputLine = this.slacker.getIn().readLine()) != null) {
                System.out.println("Server Protocol Message Listener: Received message");
                this.processMessage(inputLine);
            }
        } catch (IOException e) {
            System.out.println("Error reading buffer");
            e.printStackTrace();
        }
    }

    /**
     * Takes the message split by space and send to the
     * protocol action router
     *
     * @param message
     */
    private void processMessage(String message) throws IOException {
        String[] params = message.split(" ", -1);
        if (params.length < 2) {
            this.slacker.getOut().write(Protocol.errorMessage("Invalid number of args") + "\r\n");
            this.slacker.getOut().flush();
        } else {
            try {
                this.router.processRoute(params);
            } catch (Error error) {
                this.slacker.getOut().write(Protocol.errorMessage(error.getMessage() + "\r\n"));
                this.slacker.getOut().flush();
            }
        }
    }
}
