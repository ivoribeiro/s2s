package com.s2s.repository;

import com.s2s.models.Route;
import com.s2s.server.VerbEnum;

public class Routes extends Repository<Route> {

    public Routes() {
        super();
        this.addModel(new Route(VerbEnum.POST, "users", "register", new Class[]{String.class, String.class}, null));
        this.addModel(new Route(VerbEnum.POST, "users/login", "login", new Class[]{String.class, String.class}, null));
        this.addModel(new Route(VerbEnum.GET, "users", "getOnlineUsers", new Class[]{}, "logedIn"));
    }

    /**
     * Checks if a route exists on the list
     *
     * @param verb
     * @param path
     * @return
     */
    public Route exists(VerbEnum verb, String path) {
        for (Route route : this.getModels()) {
            if (route.getVerb().equals(verb) && route.getPath().equals(path)) {
                return route;
            }
        }
        return null;
    }
}
