package com.s2s.repository;

import com.s2s.models.Route;
import com.s2s.server.Router;
import com.s2s.server.VerbEnum;

public class Routes extends Repository<Route> {

    public Routes() {
        super();
        Route r1 = new Route(VerbEnum.GET, "help", "helpers", new Class[]{}, null);
        r1.setHelper("Return the server protocol helpers");
        this.addModel(r1);
        Route r2 = new Route(VerbEnum.POST, "users", "register", new Class[]{String.class, String.class}, null);
        r2.setHelper("Create's a new user");
        this.addModel(r2);
        Route r3 = new Route(VerbEnum.POST, "users/login", "login", new Class[]{String.class, String.class}, null);
        r3.setHelper("Authenticate's user");
        this.addModel(r3);
        Route r4 = new Route(VerbEnum.POST, "users/logout", "logout", new Class[]{String.class, String.class}, "logedIn");
        r4.setHelper("Logout's the authenticated user");
        this.addModel(r4);
        Route r5 = new Route(VerbEnum.GET, "users", "getOnlineUsers", new Class[]{}, "logedIn");
        r5.setHelper("Get's all the online users");
        this.addModel(r5);
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
