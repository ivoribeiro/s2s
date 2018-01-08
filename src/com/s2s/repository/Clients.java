package com.s2s.repository;

import com.s2s.models.Slacker;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Clients extends Repository<String, Slacker> {
    public Clients(HashMap<String, Slacker> slackers) {
        super(slackers);
    }

    public Clients() {
        super();
    }

    public void addClient(Slacker client) {
        this.addModel(client.getUser().getUsername(), client);
    }

    private Slacker updateClient(Slacker old, Slacker newOne) {
        this.getModels().replace(old.getUser().getUsername(), newOne);
        return this.getModels().get(newOne.getUser().getUsername());
    }

    public Slacker login(Slacker slacker, int serverPort) {
        Slacker old = slacker;
        slacker.setLoged(true);
        slacker.setPort(serverPort);
        Slacker exists = exists(slacker.getUser().getUsername());
        if (exists != null) {
            return this.updateClient(old, slacker);
        } else {
            // adds the client to instance
            this.addClient(slacker);
            return slacker;
        }
    }

    public boolean logout(Slacker slacker) {
        Slacker old = slacker;
        slacker.setLoged(false);
        Slacker updated = this.updateClient(old, slacker);
        return updated != null;
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
