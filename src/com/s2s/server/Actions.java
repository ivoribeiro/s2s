package com.s2s.server;

import com.s2s.Mutual.Protocol;
import com.s2s.Mutual.ProtocolInterface;
import com.s2s.models.Route;
import com.s2s.models.Slacker;
import com.s2s.repository.Clients;
import com.s2s.repository.Groups;
import com.s2s.repository.Repository;
import com.s2s.repository.Routes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

public class Actions implements ProtocolInterface {

    private Slacker slacker;
    private Clients clients;
    private Routes routes;
    private Groups groups;

    public Actions(Map<String, Repository> repositoryMap, Slacker slacker) {
        this.slacker = slacker;
        this.clients = (Clients) repositoryMap.get("Clients");
        this.routes = (Routes) repositoryMap.get("Routes");
        this.groups = (Groups) repositoryMap.get("Groups");
    }

    public void helpers() throws IOException {
        String message = "";
        for (Route route : this.routes.getModels()) {
            message = message + route.getHelper() + "\n";
        }
        this.slacker.sendResponse(message);
    }

    public void register(String username, String password) {
        if (this.clients.exists(username) == null) {
            this.slacker.setUsername(username);
            this.slacker.setPassword(password);
            this.clients.addClient(this.slacker);
            this.slacker.sendResponse(Protocol.successMessage("Success register"));
        } else {
            this.slacker.sendResponse(Protocol.errorMessage("The user already exists"));
        }
    }

    public void login(String username, String password, String serverPort) {
        Integer port = Integer.parseInt(serverPort);
        this.login(username, password, port);
    }

    /**
     * Authenticates a user
     *
     * @param username
     * @param password
     */
    public void login(String username, String password, int serverPort) {
        Slacker client = this.clients.exists(username, password);
        if (client != null) {
            Slacker newClient = this.clients.login(client, serverPort);
            this.slacker.sendResponse(Protocol.successMessage("Success login"));
        } else {
            this.slacker.sendResponse("Invalid data");
        }
    }

    /**
     * Get's the online users
     */
    @Override
    public void getOnlineUsers() {
        Clients clients = this.clients.onlineUsers();
        if (clients.getModels().size() != 0) {
            String message = "";
            for (Slacker slacker : clients.getModels()) {
                message = message + slacker + "\n";
            }
            this.slacker.sendResponse(Protocol.successMessage(message));
        } else {
            this.slacker.sendResponse(Protocol.successMessage("No online users"));
        }
    }

    /**
     * Logout's a user
     */
    public void logout() {
        this.slacker.setLoged(false);
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

    /**
     * Process a route action call with reflection method invoke
     *
     * @param route
     */
    public void processAction(Route route, String... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Actions.class.getMethod(route.getAction(), route.getArgTypes()).invoke(this, args);
    }
}
