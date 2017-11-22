package com.s2s.p2p;

import java.io.*;
import java.net.*;

public class Server extends Thread {
    private int mainServerPort;
    private String mainServerHost;
    private Socket socket;
    private BufferedWriter out;
    private InputStream in;
    private MessageListner messageListner;
    private int clientServerPort;

    public Server(int mainServerPort, String mainServerHost, int clientServerPort) throws IOException {
        super("Slacker Server Thread");
        this.mainServerPort = mainServerPort;
        this.mainServerHost = mainServerHost;
        this.clientServerPort = clientServerPort;
    }

    private void request(String message) throws IOException {
        this.out.write(message + "\r\n");
        this.out.flush();
    }

    public boolean login(String username, String password) throws IOException {

        this.request("POST users/login " + username + " " + password);
        String response = this.messageListner.getResponse();
        if (response.equals("SUCESS")) {
            return true;
        } else {
            return false;
        }
    }

    public void run() {
        try {
            this.socket = new Socket(this.mainServerHost, this.mainServerPort);
            System.out.println("Ligado ao servidor " + this.socket.getInetAddress().getHostName() + ":" + this.socket.getPort() + " Porta local:" + this.socket.getLocalPort());
            Writer ouw = new OutputStreamWriter(this.socket.getOutputStream());
            this.out = new BufferedWriter(ouw);
            this.in = this.socket.getInputStream();
            this.messageListner = new MessageListner(in);
            this.messageListner.start();
            //  ClientMessageListener clientMessageListner = new ClientMessageListener();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + this.mainServerHost);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                    + "the connection to: " + this.mainServerHost);
            System.exit(1);
        }
    }

    public void serving() {
        boolean active = true;
        try {
            DatagramSocket datagramSocket = null;
            ServerSocket serverSocket = null;
            serverSocket = new ServerSocket(this.clientServerPort);
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


    protected class MessageListner extends Thread {
        private BufferedReader in;
        private String response;

        public MessageListner(InputStream in) {
            this.in = new BufferedReader(new InputStreamReader(in));
        }

        public void run() {
            String inputLine;
            try {
                while ((inputLine = this.in.readLine()) != null) {
                    System.out.println("Client message listner: Mensagem recebida");
                    System.out.println(inputLine);
                    this.response = inputLine;
                }
                //close the buffer reader
                this.in.close();
            } catch (IOException e) {
                System.out.println("Error reading buffer");
                e.printStackTrace();
            }
        }

        public String getResponse() {
            return this.response;
        }
    }

}
