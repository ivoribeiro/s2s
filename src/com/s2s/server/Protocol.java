package com.s2s.server;

import java.io.*;
import java.net.Socket;

public class Protocol extends Thread {
    private Socket client;
    private BufferedReader in;
    private BufferedWriter out;
    private Router router;

    Protocol(Socket socket, InputStream inputStream, OutputStream outputStream) {
        super("Protocol Thread");
        this.client = socket;
        this.in = new BufferedReader(new InputStreamReader(inputStream));
        Writer ouw = new OutputStreamWriter(outputStream);
        this.out = new BufferedWriter(ouw);
        this.router = new Router();
    }

    public void run() {
        String inputLine;
        try {
            while ((inputLine = this.in.readLine()) != null) {
                System.out.println("Server Protocol Message Listener: Received message");
                this.processMessage(inputLine);
                System.out.println(inputLine);
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
            } catch (Error error) {
                this.out.write(error.getMessage() + "\r\n");
                this.out.flush();
            }
        }
    }
}
