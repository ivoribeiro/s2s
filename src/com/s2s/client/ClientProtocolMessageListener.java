package com.s2s.client;

import com.s2s.models.Slacker;
import com.s2s.repository.Repository;
import com.s2s.repository.Routes;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class ClientProtocolMessageListener extends Thread {
    private Router router;
    private Slacker slacker;
    Map<String, Repository> repos;

    ClientProtocolMessageListener(Slacker slacker, Map<String, Repository> repos) throws IOException {
        super("ProtocolMessageListener Thread");
        this.slacker = slacker;
        this.repos = repos;
        this.router = new Router(slacker, repos);
    }

    public void run() {
        String inputLine;
        try {
            while ((inputLine = this.slacker.getIn().readLine()) != null) {
                this.processMessage(inputLine);
            }
            this.slacker.getIn().close();
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
        System.out.println(message);
        String[] params = message.split(":", -1);
        if (params.length < 2) {
            System.out.println("Error: Invalid number of args" + "\r\n");
        } else {
            try {
                this.router.processRoute(params);
            } catch (Error error) {
                System.out.println(error.getMessage() + "\r\n");
            }
        }
    }

    /**
     * Writes a request to the server
     *
     * @param message
     * @throws IOException
     */
    public void request(String message) {
        try {
            this.slacker.getOut().write(message + "\r\n");
            this.slacker.getOut().flush();
        } catch (IOException e) {
            System.out.println("Error sending the request");
            e.printStackTrace();
        }
    }


}
