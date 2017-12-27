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
