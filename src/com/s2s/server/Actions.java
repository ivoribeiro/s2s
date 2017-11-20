package com.s2s.server;

import com.s2s.models.Route;
import com.s2s.models.Slacker;
import com.s2s.repository.Clients;
import com.s2s.repository.Groups;
import com.s2s.repository.Repository;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class Actions {

    private Slacker slacker;
    private Clients clients;
    private Groups groups;

    public Actions(Map<String, Repository> repositoryMap, Slacker slacker) {
        this.slacker = slacker;
        this.clients = (Clients) repositoryMap.get("Clients");
        this.groups = (Groups) repositoryMap.get("Groups");
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

    public void login(String username, String password) {
        Slacker client = this.clients.exists(username, password);
        if (client != null) {
            Slacker newClient = this.clients.login(client);
            System.out.println("Slacker Autenticado");
        } else {
            System.out.println("Dados Inválidos");
        }
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
