package com.s2s.models;

import com.s2s.server.VerbEnum;

public class Route {
    private VerbEnum verb;
    private String path;
    private String action;
    private Class[] types;
    private String middleware;

    public Route(VerbEnum verb, String path, String action, Class[] types, String middleware) {
        this.verb = verb;
        this.path = path;
        this.action = action;
        this.types = types;
        this.middleware = middleware;
    }

    public String getPath() {
        return this.path;
    }

    public String getAction() {
        return this.action;
    }

    public VerbEnum getVerb() {
        return this.verb;
    }

    public Class[] getArgTypes() {
        return this.types;
    }

    public String getMiddleware() {
        return this.middleware;
    }
}
