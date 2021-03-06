package com.s2s.client;

import com.s2s.models.Message;
import com.s2s.models.Slacker;
import com.s2s.models.User;
import com.s2s.mutual.Protocol;
import com.s2s.mutual.ProtocolInterface;
import com.s2s.models.Route;
import com.s2s.repository.Channels;
import com.s2s.repository.Repository;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Map;

public class ClientActions implements ProtocolInterface {

    ClientProtocolMessageListener mainServerMessageHandler;
    Slacker slacker;
    Channels channels;

    public ClientActions(ClientProtocolMessageListener serverHandler) {
        this.mainServerMessageHandler = serverHandler;
    }

    public ClientActions(Slacker slacker, Map<String, Repository> repositoryMap) {
        this.slacker = slacker;
        this.channels = (Channels) repositoryMap.get("Channels");
    }

    /**
     * Regist's a user
     *
     * @param username
     * @param password
     */
    @Override
    public void register(String username, String password) {
        String registerMessage = Protocol.register(username, password);
        mainServerMessageHandler.request(registerMessage);
    }

    /**
     * Logins into the server
     *
     * @param username
     * @param password
     */
    @Override
    public void login(String username, String password) {
        String loginMessage = Protocol.login(username, password);
        mainServerMessageHandler.request(loginMessage);
    }

    /**
     * Get's the online users
     */
    @Override
    public void getOnlineUsers() {
        String onlineUsersMessage = Protocol.onlineUsers();
        mainServerMessageHandler.request(onlineUsersMessage);
    }

    /**
     * Send a private message to a user
     *
     * @param username
     * @param message
     */
    @Override
    public void sendMessage(String username, String message) {

    }

    /**
     * Send a group message
     *
     * @param groupName
     * @param message
     */
    @Override
    public void sendGroupMessage(String groupName, String message) {

    }

    /**
     * Creates a group
     *
     * @param name
     */
    @Override
    public void createGroup(String name) {
    }

    public void createdGroup(String group, String port) {
        this.listenGroup(group, port);
    }

    public void listenGroup(String group, String port) {
        System.out.println("Listen Group on " + group + " " + port);
        new MulticastListner(group, Integer.parseInt(port)).start();
    }

    public void logedOut() {

    }

    /**
     * Deletes a group
     *
     * @param name
     */
    @Override
    public void deleteGroup(String name) {

    }

    /**
     * Leaves a group
     *
     * @param name
     */
    @Override
    public void leaveGroup(String name) {

    }

    /**
     * Leaves a group
     *
     * @param name
     */
    @Override
    public void joinGroup(String name) {
    }

    /**
     * Get existing groups
     */
    @Override
    public void getGroups() {

    }

    /**
     * Listen to the user groups for new messages
     *
     * @param group
     */
    @Override
    public void getMyGroups(String group) {
        System.out.println(group);
    }

    /**
     * Saves the user messages
     */
    @Override
    public void saveUserMessages() {

    }

    public void saveUserMessagesEvent(String message) throws Exception {
        Message.saveMessagesOnFile(this.channels, this.slacker.getUser().getUsername());
    }

    public void messageReceived(String sender, String message) {
        System.out.println(sender + "-" + message);
        this.channels.addMessage(sender, message);
    }

    public void successMessage(String message) {
        System.out.println(message);
    }

    public void successLogin(String username) {
        System.out.println("Sucess Login");
        this.slacker.setUser(new User(username, ""));
    }

    public void infoMessage(String message) {
        System.out.println(message);
    }

    public void errorMessage(String message) {
        System.out.println("Error: " + message);
    }

    public void processAction(Route route, String... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        com.s2s.client.ClientActions.class.getMethod(route.getAction(), route.getArgTypes()).invoke(this, args);
    }

    protected class MulticastListner extends Thread {
        private String group;
        private int port;
        private boolean listning = true;

        public MulticastListner(String group, int port) {
            this.group = group;
            this.port = port;
        }

        public void stopListen() {
            this.listning = false;
        }

        public void run() {
            MulticastSocket socket = null;
            try {
                socket = new MulticastSocket(this.port);
                socket.joinGroup(InetAddress.getByName(this.group));
                while (this.listning) {
                    byte buf[] = new byte[1024];
                    DatagramPacket pack = new DatagramPacket(buf, buf.length);
                    socket.receive(pack);
                    System.out.println("Received data from: " + pack.getAddress().toString() +
                            ":" + pack.getPort() + " with length: " +
                            pack.getLength());
                    System.out.write(pack.getData(), 0, pack.getLength());
                    System.out.println();
                }
                socket.leaveGroup(InetAddress.getByName(group));
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
