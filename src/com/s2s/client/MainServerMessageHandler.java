package com.s2s.client;

import com.s2s.client.Router;
import com.s2s.repository.Repository;

import java.io.*;
import java.util.Map;

/**
 * Listener to the main server socket
 */
public class MainServerMessageHandler extends Thread {
    private BufferedReader in;
    private BufferedWriter out;
    private Router router;

    public MainServerMessageHandler(InputStream in, OutputStream out, Map<String, Repository> repositoryMap) {
        super("Main Server Message Handler Thread");
        this.in = new BufferedReader(new InputStreamReader(in));
        Writer ouw = new OutputStreamWriter(out);
        this.out = new BufferedWriter(ouw);
        this.router = new Router(repositoryMap);
    }

    /**
     * Writes a request to the server
     *
     * @param message
     * @throws IOException
     */
    public void request(String message) {
        try {
            this.out.write(message + "\r\n");
            this.out.flush();
        } catch (IOException e) {
            System.out.println("Error sending the request");
            e.printStackTrace();
        }
    }

    /**
     * Listen the main server for new responses
     */
    public void run() {
        String inputLine;
        try {
            while ((inputLine = this.in.readLine()) != null) {
                System.out.println("ClientServer: Message from main server");
                this.processMessage(inputLine);
            }
            this.in.close();
        } catch (IOException e) {
            System.out.println("Error reading buffer");
            e.printStackTrace();
        }
    }

    private void processMessage(String message) throws IOException {
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
}
