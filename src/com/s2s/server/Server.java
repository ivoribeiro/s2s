package com.s2s.server;

import com.s2s.models.Slacker;
import com.s2s.repository.Routes;

import java.io.IOException;
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
            Routes routes = new Routes();
            while (active) {
                // client connection
                Socket clientSocket = serverSocket.accept();
                String clientHost = clientSocket.getInetAddress().getHostAddress();
                int clientPort = clientSocket.getPort();
                System.out.println("Server: New client connection on " + clientHost + " Socket internal port:" + clientPort);
                Slacker slacker = new Slacker(clientSocket, clientHost, clientPort);
                ProtocolMessageListener protocol = new ProtocolMessageListener(slacker, routes);
                protocol.start();
            }
            serverSocket.close();

        } catch (IOException e) {
            System.err.println("Could not listen");
            System.exit(-1);
        }
    }

}
