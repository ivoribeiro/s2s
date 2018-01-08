package com.s2s.repository;

import com.s2s.models.Route;
import com.s2s.mutual.VerbEnum;

public class ClientRoutes extends Repository<String, Route> {

    public ClientRoutes() {
        super();

        VerbEnum verb;
        String path;
        String key;

        verb = VerbEnum.SUCCESS;
        path = "successMessage";
        key = verb + path;
        Route r1 = new Route(verb, path, "successMessage", new Class[]{String.class}, null);
        r1.setHelper("successMessage event, show success messages");
        this.addModel(key, r1);

        verb = VerbEnum.INFO;
        path = "infoMessage";
        key = verb + path;
        Route r2 = new Route(verb, path, "infoMessage", new Class[]{String.class}, null);
        r2.setHelper("infoMessage event, show information messages");
        this.addModel(key, r2);

        verb = VerbEnum.ERROR;
        path = "errorMessage";
        key = verb + path;
        Route r3 = new Route(verb, path, "errorMessage", new Class[]{String.class}, null);
        r3.setHelper("errorMessage event, show error messages");
        this.addModel(key, r3);

        verb = VerbEnum.SUCCESS;
        path = "listenGroup";
        key = verb + path;
        Route r4 = new Route(verb, path, "listenGroup", new Class[]{String.class, String.class}, null);
        r4.setHelper("listen group event, new thread to group listener");
        this.addModel(key, r4);

        verb = VerbEnum.SUCCESS;
        path = "messageReceived";
        key = verb + path;
        Route r5 = new Route(verb, path, "messageReceived", new Class[]{String.class, String.class}, null);
        r5.setHelper("Message received event");
        this.addModel(key, r5);
    }

    /**
     * Checks if a route exists on the list
     *
     * @param verb
     * @param path
     * @return
     */
    public Route exists(VerbEnum verb, String path) {
        return super.exists(verb + path);
    }
}
