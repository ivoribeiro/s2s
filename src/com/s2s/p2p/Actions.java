package com.s2s.p2p;

import com.s2s.Mutual.Protocol;
import com.s2s.Mutual.ProtocolInterface;

public class Actions implements ProtocolInterface {

    MainServerMessageHandler mainServerMessageHandler;

    public Actions(MainServerMessageHandler serverHandler) {
        this.mainServerMessageHandler = serverHandler;
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
    public void login(String username, String password, int serverPort) {
        String loginMessage = Protocol.login(username, password, serverPort);
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
}
