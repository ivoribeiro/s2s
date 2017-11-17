package com.s2s.p2p;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private int tcpPort;
    private String mainServerHost;

    public Server(int tcpPort, String mainServerHost) throws IOException {
        super("Slacker Server Thread");
        this.tcpPort = tcpPort;
        this.mainServerHost = mainServerHost;
    }

    public void run() {
        boolean active = true;
        try {
            DatagramSocket datagramSocket = null;
            ServerSocket serverSocket = null;
            serverSocket = new ServerSocket(this.tcpPort);
            System.out.println("Slacker Server: Unicast listning on " + serverSocket.getInetAddress().getHostAddress() + ":" + serverSocket.getLocalPort());
            datagramSocket = new DatagramSocket(4445);
            System.out.println("Slacker Server: Multicast listning on " + serverSocket.getLocalPort() + ".");
            InetAddress group = InetAddress.getByName("230.0.0.1");
            //Connect to the main server via  a tcp unicast socket
            Socket mainServerSocket = new Socket(this.mainServerHost, 2048);

            //ServerMessageHandler mh = new ServerMessageHandler(datagramSocket, group);
            while (active) {
                // client connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("Slacker Server: New client connection on " + clientSocket.getInetAddress().getHostAddress() + "Socket port:" + clientSocket.getPort());
                //ServerMessageListener serverMessageListener = new ServerMessageListener(mh, clientSocket, clientSocket.getInputStream());
                //serverMessageListener.start();
            }
            datagramSocket.close();
            serverSocket.close();

        } catch (IOException e) {
            System.err.println("Could not listen");
            System.exit(-1);
        }
    }
}
