package com.s2s.repository;

import com.s2s.models.Slacker;

import java.util.HashMap;
import java.util.Map;

public class Clients extends Repository<String, Slacker> {
    public Clients(HashMap<String, Slacker> slackers) {
        super(slackers);
    }

    public Clients() {
        super();
    }

    public void addClient(Slacker slacker) {
        this.addModel(slacker.getUsername(), slacker);
    }

    public Slacker validate(String username, String password) {
        Slacker exists = this.exists(username);
        if (exists != null) {
            if (exists.getUsername().equals(username) && exists.getPassword().equals(password)) {
                return exists;
            } else return null;
        }
        return null;
    }

    private Slacker updateClient(Slacker old, Slacker newOne) {
        this.getModels().replace(old.getUsername(), newOne);
        return this.getModels().get(newOne.getUsername());
    }

    public Slacker login(Slacker slacker, int serverPort) {
        Slacker old = slacker;
        slacker.setLoged(true);
        slacker.setPort(serverPort);
        return this.updateClient(old, slacker);
    }

    public Clients onlineUsers() {
        Clients online = new Clients();
        for (Map.Entry<String, Slacker> entry : this.getModels().entrySet()) {
            Slacker slacker = entry.getValue();
            if (slacker.isLoged()) {
                online.addClient(slacker);
            }
        }
        return online;
    }
}
