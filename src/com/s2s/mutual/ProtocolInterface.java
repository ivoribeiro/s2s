package com.s2s.mutual;

public interface ProtocolInterface {

    /**
     * Regist's a user
     *
     * @param username
     * @param password
     */
    public void register(String username, String password);

    /**
     * Logins into the server
     *
     * @param username
     * @param password
     */
    public void login(String username, String password);

    /**
     * Get's the online users
     */
    public void getOnlineUsers();

    /**
     * Send a private message to a user
     *
     * @param username
     * @param message
     */
    public void sendMessage(String username, String message);

    /**
     * Send a group message
     *
     * @param groupName
     * @param message
     */
    public void sendGroupMessage(String groupName, String message);

    /**
     * Creates a group
     *
     * @param name
     */
    public void createGroup(String name);

    /**
     * Deletes a group
     *
     * @param name
     */
    public void deleteGroup(String name);

    /**
     * Leaves a group
     *
     * @param name
     */
    public void leaveGroup(String name);

    /**
     * Leaves a group
     * @param name
     */
    public void joinGroup(String name);
}
