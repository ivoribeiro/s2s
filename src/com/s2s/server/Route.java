package com.s2s.server;

public class Route {
    private VerbEnum verb;
    private String path;
    private String action;
    private Class[] types;

    public Route(VerbEnum verb, String path, String action, Class[] types) {
        this.verb = verb;
        this.path = path;
        this.action = action;
        this.types = types;
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
}
