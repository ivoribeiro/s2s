package com.s2s.repository;

import com.s2s.models.Route;
import com.s2s.Mutual.VerbEnum;
import com.s2s.models.Slacker;

import java.util.HashMap;
import java.util.Map;

public class Routes extends Repository<String, Route> {

    public Routes() {
        super();

        VerbEnum verb = VerbEnum.GET;
        String path = "help";
        String key = verb + path;
        Route r1 = new Route(verb, path, "helpers", new Class[]{}, null);
        r1.setHelper("Return the server protocol helpers");
        this.addModel(key, r1);

        verb = VerbEnum.POST;
        path = "users";
        key = verb + path;
        Route r2 = new Route(verb, path, "register", new Class[]{String.class, String.class}, "logedOut");
        r2.setHelper("Create's a new user");
        this.addModel(key, r2);

        verb = VerbEnum.POST;
        path = "users/login";
        key = verb + path;
        Route r3 = new Route(verb, path, "login", new Class[]{String.class, String.class}, null);
        r3.setHelper("Authenticate's user");
        this.addModel(key, r3);

        verb = VerbEnum.POST;
        path = "users/logout";
        key = verb + path;
        Route r4 = new Route(verb, path, "logout", new Class[]{}, "logedIn");
        r4.setHelper("Logout's the authenticated user");
        this.addModel(key, r4);

        verb = VerbEnum.GET;
        path = "users";
        key = verb + path;
        Route r5 = new Route(verb, path, "getOnlineUsers", new Class[]{}, "logedIn");
        r5.setHelper("Get's all the online users");
        this.addModel(key, r5);
    }

    /**
     * Checks if a route exists on the list
     *
     * @param verb
     * @param path
     * @return
     */
    public Route exists(VerbEnum verb, String path) {
        return super.exists(verb + path);
    }
}
