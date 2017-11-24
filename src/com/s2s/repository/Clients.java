package com.s2s.repository;

import com.s2s.models.Route;
import com.s2s.models.Slacker;

import java.util.ArrayList;

public class Clients extends Repository<Slacker> {
    public Clients(ArrayList<Slacker> slackers) {
        super(slackers);
    }

    public Clients() {
        super();
    }

    public void addClient(Slacker slacker) {
        this.addModel(slacker);
    }

    public Slacker exists(String username) {
        for (Slacker slacker : this.getModels()) {
            if (slacker.getUsername().equals(username)) {
                return slacker;
            }
        }
        return null;
    }

    public Slacker exists(String username, String password) {
        for (Slacker slacker : this.getModels()) {
            if (slacker.getUsername().equals(username) && slacker.getPassword().equals(password)) {
                return slacker;
            }
        }
        return null;
    }

    private Slacker updateClient(Slacker old, Slacker newOne) {
        int index = this.getModels().indexOf(old);
        this.getModels().set(index, newOne);
        return this.getModels().get(index);
    }

    public Slacker login(Slacker slacker, int serverPort) {
        Slacker old = slacker;
        slacker.setLoged(true);
        slacker.setPort(serverPort);
        return this.updateClient(old, slacker);
    }

    public Clients onlineUsers() {
        Clients online = new Clients();
        for (Slacker slacker : this.getModels()) {
            if (slacker.isLoged()) {
                online.addClient(slacker);
            }
        }
        return online;
    }
}
