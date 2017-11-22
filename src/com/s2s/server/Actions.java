package com.s2s.server;

import com.s2s.models.Route;
import com.s2s.models.Slacker;
import com.s2s.repository.Clients;
import com.s2s.repository.Groups;
import com.s2s.repository.Repository;
import com.s2s.repository.Routes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class Actions {

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

    private void sendResponse(String message) throws IOException {
        this.slacker.getOut().write(message + "\r\n");
        this.slacker.getOut().flush();
    }

    public void helpers() throws IOException {
        String message = "";
        for (Route route : this.routes.getModels()) {
            message = message + route.getHelper() + "\n";
        }
        this.sendResponse(message);
    }

    public void register(String username, String password) {
        if (this.clients.exists(username) == null) {
            this.slacker.setUsername(username);
            this.slacker.setPassword(password);
            this.clients.addClient(this.slacker);
            System.out.println("Novo Slacker");
        } else {
            System.out.println("Username já existe");
        }
    }

    /**
     * Authenticates a user
     *
     * @param username
     * @param password
     */
    public void login(String username, String password) throws IOException {
        Slacker client = this.clients.exists(username, password);
        if (client != null) {
            Slacker newClient = this.clients.login(client);
            this.sendResponse("Success:Logged in");
        } else {
            this.sendResponse("Error:Invalid data");
        }
    }

    /**
     * Logout's a user
     */
    public void logout() {
        this.slacker.setLoged(false);
    }

    public void getOnlineUsers() {
        System.out.println("Onlineusers");
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
