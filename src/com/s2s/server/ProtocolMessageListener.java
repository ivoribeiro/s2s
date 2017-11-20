package com.s2s.server;

import com.s2s.models.Slacker;
import com.s2s.repository.Repository;
import com.s2s.repository.Routes;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class ProtocolMessageListener extends Thread {
    private Socket client;
    private BufferedReader in;
    private BufferedWriter out;
    private Router router;
    private Slacker slacker;
    Map<String, Repository> repos;

    ProtocolMessageListener(Map<String, Repository> repos, Slacker slacker) throws IOException {
        super("ProtocolMessageListener Thread");
        this.slacker = slacker;
        this.repos = repos;
        this.in = new BufferedReader(new InputStreamReader(slacker.getClientSocket().getInputStream()));
        Writer ouw = new OutputStreamWriter(slacker.getClientSocket().getOutputStream());
        this.out = new BufferedWriter(ouw);
        this.router = new Router(slacker, repos);
    }

    public void run() {
        String inputLine;
        try {
            while ((inputLine = this.in.readLine()) != null) {
                System.out.println("Server ProtocolMessageListener Message Listener: Received message");
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
            this.out.write("Error: Invalid number of args" + "\r\n");
            this.out.flush();
        } else {
            try {
                this.router.processRoute(params);
            } catch (Exception error) {
                this.out.write(error.getMessage() + "\r\n");
                this.out.flush();
            }
        }
    }
}
