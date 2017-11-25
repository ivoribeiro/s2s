package com.s2s.client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientServer extends Thread {

    private Socket mainServerSocket;
    private String mainServerHost;
    private int mainServerPort;
    private int serverPort;
    private static int DEFAULT_MAIN_SERVER_PORT = 1024;
    private static int DEFAULT_SERVER_PORT = 2048;
    private static String DEFAULT_MAIN_SERVER_HOST = "localhost";
    private boolean closed = false;

    public static void main(String[] args) {
        new ClientServer(DEFAULT_MAIN_SERVER_HOST, DEFAULT_MAIN_SERVER_PORT, DEFAULT_SERVER_PORT).start();
    }

    public ClientServer(String mainServerHost, int mainServerPort, int serverPort) {
        this.mainServerHost = mainServerHost;
        this.mainServerPort = mainServerPort;
        this.serverPort = serverPort;
    }

    /**
     * Mains Server connection
     */
    public void run() {
        try {
            this.mainServerSocket = new Socket(this.mainServerHost, this.mainServerPort);
            BufferedReader inputLine = new BufferedReader(new InputStreamReader(System.in));
            if (mainServerSocket != null) {
                try {
                    System.out.println("Connected to the main server " + this.mainServerSocket.getInetAddress().getHostName() + ":" + this.mainServerSocket.getPort() + " Porta local:" + this.mainServerSocket.getLocalPort());
                    MainServerMessageHandler mainServerMessageHandler = new MainServerMessageHandler(this.mainServerSocket.getInputStream()
                            , this.mainServerSocket.getOutputStream());
                    mainServerMessageHandler.start();
                    while (!closed) {
                        mainServerMessageHandler.request(inputLine.readLine().trim());
                    }
                    // Actions actions = new Actions(mainServerMessageHandler);
                    //new Client(actions).start();
                } catch (IOException e) {
                    System.err.println("IOException:  " + e);
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + this.mainServerHost);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                    + "the connection to: " + this.mainServerHost);
            System.exit(1);
        }
    }
}
