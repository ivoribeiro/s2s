package com.s2s.repository;

import com.s2s.models.Slacker;

import java.util.ArrayList;

public class Clients extends Repository<Slacker> {
    public Clients(ArrayList<Slacker> slackers) {
        super(slackers);
    }

    public Clients() {
        super();
    }
}
