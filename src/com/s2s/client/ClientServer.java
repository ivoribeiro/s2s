package com.s2s.client;

import com.s2s.models.Slacker;
import com.s2s.repository.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

public class ClientServer extends Thread {

    private String mainServerHost;
    private int mainServerPort;
    private int serverPort;
    private static int DEFAULT_MAIN_SERVER_PORT = 1024;
    private static int DEFAULT_SERVER_PORT = 2048;
    private static String DEFAULT_MAIN_SERVER_HOST = "localhost";
    private boolean closed = false;
    private HashMap<String, Repository> repos;
    private ClientRoutes routes;


    public static void main(String[] args) {
        new ClientServer(DEFAULT_MAIN_SERVER_HOST, DEFAULT_MAIN_SERVER_PORT, DEFAULT_SERVER_PORT).start();
    }

    public ClientServer(String mainServerHost, int mainServerPort, int serverPort) {
        this.mainServerHost = mainServerHost;
        this.mainServerPort = mainServerPort;
        this.serverPort = serverPort;
        this.initRepos();

    }

    private void initRepos() {
        this.repos = new HashMap<String, Repository>();
        this.routes = new ClientRoutes();
        this.repos.put("Routes", this.routes);
    }

    /**
     * Mains Server connection
     */
    public void run() {
        try {
            Socket mainServerSocket = null;
            mainServerSocket = new Socket(this.mainServerHost, this.mainServerPort);
            BufferedReader inputLine = new BufferedReader(new InputStreamReader(System.in));
            if (mainServerSocket != null) {
                try {
                    System.out.println("Connected to the main server " + mainServerSocket.getInetAddress().getHostName() + ":" + mainServerSocket.getPort() + " Porta local:" + mainServerSocket.getLocalPort());
                    Slacker slacker = new Slacker(mainServerSocket);
                    ClientProtocolMessageListener mainServerMessageHandler = new ClientProtocolMessageListener(slacker, repos);
                    mainServerMessageHandler.start();
                    while (!closed) {
                        mainServerMessageHandler.request(inputLine.readLine().trim());
                    }
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
