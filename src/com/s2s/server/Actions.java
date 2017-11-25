package com.s2s.server;

import com.s2s.Mutual.Protocol;
import com.s2s.Mutual.ProtocolInterface;
import com.s2s.models.Group;
import com.s2s.models.Route;
import com.s2s.models.Slacker;
import com.s2s.repository.Clients;
import com.s2s.repository.Groups;
import com.s2s.repository.Repository;
import com.s2s.repository.Routes;
import utils.PortGen;

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
        for (Map.Entry<String, Route> entry : this.routes.getModels().entrySet()) {
            Route route = entry.getValue();
            message = message + route.getHelper() + "\n";
        }
        this.slacker.sendResponse(Protocol.successMessage(message));
    }

    public void register(String username, String password) {
        if (this.clients.exists(username) == null) {
            //TODO create user dir and saves in the file
            this.slacker.setUsername(username);
            this.slacker.setPassword(password);
            this.clients.addClient(this.slacker);
            this.slacker.sendResponse(Protocol.successMessage("successRegister"));
        } else {
            this.slacker.sendResponse(Protocol.errorMessage("The user already exists"));
        }
    }

    /**
     * Authenticates a user
     *
     * @param username
     * @param password
     */
    public void login(String username, String password) {
        Slacker client = this.clients.validate(username, password);
        if (client != null) {
            this.clients.login(client, PortGen.getMultiCastPort());
            this.slacker.sendResponse(Protocol.successMessage("successLogin"));
        } else {
            this.slacker.sendResponse(Protocol.errorMessage("invalidData"));
        }
    }

    /**
     * Get's the online users
     */
    @Override
    public void getOnlineUsers() {
        Clients clients = this.clients.onlineUsers();
        if (clients.getModels().size() != 0) {
            StringBuilder message = new StringBuilder();
            for (Map.Entry<String, Slacker> entry : this.clients.getModels().entrySet()) {
                Slacker slacker = entry.getValue();
                message.append(slacker).append("\n");
            }
            this.slacker.sendResponse(Protocol.successMessage(message.toString()));
        } else {
            this.slacker.sendResponse(Protocol.infoMessage("No online users"));
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
