package com.s2s.server;

import com.s2s.models.Route;
import com.s2s.models.Slacker;

import java.lang.reflect.InvocationTargetException;

public class Actions {

    private Slacker slacker = null;

    public Actions(Slacker slacker) {
        this.slacker = slacker;
    }

    public void register(String username, String password) {
        System.out.println(username);
        System.out.println(password);
    }

    public void getOnlineUsers() {
        System.out.println("Onlineusers");
    }

    /**
     * Process a route action call with reflection method invoke
     *
     * @param route
     */
    public void processAction(Route route, String... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Actions.class.getMethod(route.getAction(), route.getArgTypes()).invoke(this, args);
    }
}
