package com.s2s.p2p;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientServer extends Thread {

    private Socket mainServerSocket;
    private String mainServerHost;
    private int mainServerPort;
    private static int DEFAULT_MAIN_SERVER_PORT = 1024;
    private static String DEFAULT_MAIN_SERVER_HOST = "localhost";
    private boolean closed = false;

    public static void main(String[] args) {
        new ClientServer(DEFAULT_MAIN_SERVER_HOST, DEFAULT_MAIN_SERVER_PORT).start();
    }

    public ClientServer(String mainServerHost, int mainServerPort) {
        this.mainServerHost = mainServerHost;
        this.mainServerPort = mainServerPort;
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
                    Actions actions = new Actions(mainServerMessageHandler);
                    new Client(actions).start();
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
