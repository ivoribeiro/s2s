package com.s2s.server;

import com.s2s.models.Route;
import com.s2s.models.Slacker;

import java.lang.reflect.InvocationTargetException;

public class Middlewares {

    Slacker slacker;

    public Middlewares(Slacker slacker) {
        this.slacker = slacker;
    }

    public void updateSlacker(Slacker newOne) {
        this.slacker = newOne;
    }

    public boolean logedIn() throws IllegalAccessException {
        if (this.slacker.isLoged()) {
            return true;
        } else {
            throw new IllegalAccessException();
        }
    }

    public boolean logedOut() throws IllegalAccessException {
        if (!this.slacker.isLoged()) {
            return true;
        } else {
            throw new IllegalAccessException();
        }
    }

    public void processMiddleware(Route route) throws Throwable {
        try {
            Middlewares.class.getMethod(route.getMiddleware()).invoke(this);
        } catch (InvocationTargetException cause) {
            throw cause.getCause();
        }
    }
}

