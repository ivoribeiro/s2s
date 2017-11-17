package com.s2s.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    private int tcpPort;

    public static void main(String args[]) {
        try {
            if (args.length > 0) {
                Server server = new Server(Integer.parseInt(args[0]));
                server.start();

            } else {
                Server server = new Server(1024);
                server.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Server(int tcpPort) throws IOException {
        super("Server Thread");
        this.tcpPort = tcpPort;
    }

    public void run() {
        boolean active = true;
        try {
            ServerSocket serverSocket = null;
            serverSocket = new ServerSocket(this.tcpPort);
            System.out.println("Server: Tcp Unicast socket listning on " + serverSocket.getInetAddress().getHostAddress() + ":" + serverSocket.getLocalPort());
            while (active) {
                // client connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("Server: New client connection on " + clientSocket.getInetAddress().getHostAddress() + " Socket internal port:" + clientSocket.getPort());
                Protocol protocol = new Protocol(clientSocket, clientSocket.getInputStream(), clientSocket.getOutputStream());
                protocol.start();
            }
            serverSocket.close();

        } catch (IOException e) {
            System.err.println("Could not listen");
            System.exit(-1);
        }
    }

}
