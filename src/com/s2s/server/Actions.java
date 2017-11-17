package com.s2s.server;

import java.lang.reflect.InvocationTargetException;

public class Actions {

    public Actions() {

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
