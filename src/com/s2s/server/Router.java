package com.s2s.server;

import java.util.ArrayList;
import java.util.Arrays;

public class Router {
    private ArrayList<Route> routes;
    private Actions actions;

    public Router() {
        this.routes = new ArrayList<Route>();
        this.routes.add(new Route(VerbEnum.POST, "users", "register", new Class[]{String.class, String.class}));
        this.routes.add(new Route(VerbEnum.POST, "users/login", "login", new Class[]{}));
        this.routes.add(new Route(VerbEnum.GET, "users", "getOnlineUsers", new Class[]{}));
        this.actions = new Actions();
    }

    /**
     * Checks if a route exists on the list
     *
     * @param verb
     * @param path
     * @return
     */
    public Route exists(VerbEnum verb, String path) {
        for (Route route : this.routes) {
            if (route.getVerb().equals(verb) && route.getPath().equals(path)) {
                return route;
            }
        }
        return null;
    }

    public void processRoute(String[] params) {
        Verb verbClass = null;
        VerbEnum verb = Verb.exists(params[0]);
        //  if the verb exists checks if the route is on routes list
        if (verb != null) {
            Route route = this.exists(verb, params[1]);
            if (route != null) {
                try {
                    String[] args = new String[0];
                    if (params.length > 2) {
                        args = Arrays.copyOfRange(params, 2, params.length);
                    }
                    this.actions.processAction(route, args);
                } catch (Exception ex) {
                    if (ex instanceof IllegalArgumentException) {
                        throw new Error("Error: Wrong action params number");
                    } else {
                        System.out.println(ex);
                    }
                }
            } else {
                throw new Error("Error: Route don't exists");
            }
        } else {
            throw new Error("Error: Unknown verb");
        }
    }
}
