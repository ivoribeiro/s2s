package com.s2s.server;

import com.s2s.Mutual.Protocol;
import com.s2s.Mutual.VerbEnum;
import com.s2s.models.Route;
import com.s2s.models.Slacker;
import com.s2s.repository.Repository;
import com.s2s.repository.Routes;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;

public class Router {
    private Routes routes;
    private Actions actions;
    private Middlewares middlewares;

    public Router(Slacker slacker, Map<String, Repository> repositoryMap) {
        this.routes = (Routes) repositoryMap.get("Routes");
        this.actions = new Actions(repositoryMap, slacker);
        this.middlewares = new Middlewares(slacker);
    }

    public void processRoute(String[] params) throws Error {
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
                    if (route.getMiddleware() != null) {
                        try {
                            this.middlewares.processMiddleware(route);
                        } catch (Exception ex) {
                            if (ex instanceof IllegalAccessException) {
                                throw new Error("Error: Blocked on middleware");
                            } else {
                                System.out.println(ex);
                            }
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                    this.actions.processAction(route, args);
                } catch (IllegalArgumentException ex) {
                    throw new Error("Wrong action params number");
                } catch (InvocationTargetException ex) {
                    Throwable real = ex.getTargetException();
                    real.printStackTrace();
                    throw new Error(Protocol.errorMessage("Internal server error"));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    throw new Error(Protocol.errorMessage("IllegalAccessException"));
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                    throw new Error(Protocol.errorMessage("NoSuchMethodException"));
                }
            } else {
                throw new Error("Route don't exists");
            }
        } else {
            throw new Error("Error: Unknown verb");
        }
    }
}
