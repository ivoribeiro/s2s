package com.s2s.server;

import com.s2s.models.Slacker;
import com.s2s.repository.Clients;
import com.s2s.repository.Groups;
import com.s2s.repository.Repository;
import com.s2s.repository.Routes;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server extends Thread {

    private int tcpPort;
    private HashMap<String, Repository> repos;
    private Routes routes;
    private Groups groups;
    private Clients clients;

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
        this.initRepos();
    }

    private void initRepos() {
        this.repos = new HashMap<String, Repository>();
        this.routes = new Routes();
        this.clients = new Clients();
        this.groups = new Groups();
        this.repos.put("Routes", this.routes);
        this.repos.put("Clients", this.clients);
        this.repos.put("Groups", this.groups);
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
                String clientHost = clientSocket.getInetAddress().getHostAddress();
                int clientPort = clientSocket.getPort();
                System.out.println("Server: New client connection on " + clientHost + " Socket internal port:" + clientPort);
                Slacker slacker = new Slacker(clientSocket);
                //adds this new connection to clients repository
                ProtocolMessageListener protocol = new ProtocolMessageListener(repos, slacker);
                protocol.start();
            }
            serverSocket.close();

        } catch (IOException e) {
            System.err.println("Could not listen");
            System.exit(-1);
        }
    }

}
