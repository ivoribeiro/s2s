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
}
