package com.s2s.server;

import com.s2s.models.Route;
import com.s2s.models.Slacker;
import com.s2s.repository.Repository;
import com.s2s.repository.Routes;

import java.util.Arrays;
import java.util.Map;

public class Router {
    private Routes routes;
    private Actions actions;

    public Router(Slacker slacker, Map<String, Repository> repositoryMap) {
        this.routes = (Routes) repositoryMap.get("Routes");
        this.actions = new Actions(repositoryMap, slacker);
    }

    public void processRoute(String[] params) {
        Verb verbClass = null;
        VerbEnum verb = Verb.exists(params[0]);
        //  if the verb exists checks if the route is on routes list
        if (verb != null) {
            Route route = this.routes.exists(verb, params[1]);
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
