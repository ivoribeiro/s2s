package com.s2s.Mutual;

public class Protocol {

    public static Boolean isError(String message) {
        String[] splited = message.split(":");
        return splited[0].equals(VerbEnum.ERROR);
    }

    public static Boolean isSuccess(String message) {
        String[] splited = message.split(":");
        return splited[0].equals(VerbEnum.SUCCESS);
    }

    /**
     * Returns a success message
     *
     * @param message
     * @return
     */
    public static String successMessage(String action, String message) {
        return VerbEnum.SUCCESS + ":" + action + ":" + message;
    }

    /**
     * Returns a info message
     *
     * @param message
     * @return
     */
    public static String infoMessage(String message) {
        return VerbEnum.INFO + ":" + message;
    }

    /**
     * Returns a error message
     *
     * @param message
     * @return
     */
    public static String errorMessage(String message) {
        return VerbEnum.ERROR + ":" + message;
    }

    /**
     * Returns a register message
     *
     * @param username
     * @param password
     * @return
     */
    public static String register(String username, String password) {
        return VerbEnum.POST + " " + "users" + " " + username + " " + password;
    }


    /**
     * Returns a login message
     *
     * @param username
     * @param password
     * @return
     */
    public static String login(String username, String password) {
        return VerbEnum.POST + " " + "users/login" + " " + username + " " + password;
    }

    /**
     * Returns a get online users message
     *
     * @return
     */
    public static String onlineUsers() {
        return VerbEnum.GET + " " + "users";
    }

    /**
     * Returns a message to create a group
     *
     * @return
     */
    public static String createGroup(String name) {
        return VerbEnum.POST + " " + "groups" + " " + name;
    }

    /**
     * Returns a message to delete a group
     *
     * @return
     */
    public static String deleteGroup(String name) {
        return VerbEnum.DELETE + " " + "groups" + " " + name;
    }

    /**
     * Returns a message to delete a group
     *
     * @return
     */
    public static String leaveGroup(String name) {
        return VerbEnum.DELETE + " " + "groups/leave";
    }

}
