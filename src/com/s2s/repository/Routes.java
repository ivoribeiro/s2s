package com.s2s.repository;

import com.s2s.models.Route;
import com.s2s.mutual.VerbEnum;

public class Routes extends Repository<String, Route> {

    public Routes() {
        super();

        VerbEnum verb = VerbEnum.GET;
        String path = "help";
        String key = verb + path;
        Route r1 = new Route(verb, path, "help", new Class[]{}, null);
        r1.setHelper("Return the server protocol helpers");
        this.addModel(key, r1);

        verb = VerbEnum.POST;
        path = "users";
        key = verb + path;
        Route r2 = new Route(verb, path, "register", new Class[]{String.class, String.class}, "logedOut");
        r2.setHelper("Create's a new user");
        this.addModel(key, r2);

        verb = VerbEnum.POST;
        path = "users/login";
        key = verb + path;
        Route r3 = new Route(verb, path, "login", new Class[]{String.class, String.class}, null);
        r3.setHelper("Authenticate's user");
        this.addModel(key, r3);

        verb = VerbEnum.POST;
        path = "users/logout";
        key = verb + path;
        Route r4 = new Route(verb, path, "logout", new Class[]{}, "logedIn");
        r4.setHelper("Logout's the authenticated user");
        this.addModel(key, r4);

        verb = VerbEnum.GET;
        path = "users";
        key = verb + path;
        Route r5 = new Route(verb, path, "getOnlineUsers", new Class[]{}, "logedIn");
        r5.setHelper("Get's all the online users");
        this.addModel(key, r5);

        verb = VerbEnum.POST;
        path = "users/message";
        key = verb + path;
        Route r6 = new Route(verb, path, "sendMessage", new Class[]{String.class, String.class}, "logedIn");
        r6.setHelper("Send's a message to an online user");
        this.addModel(key, r6);

        verb = VerbEnum.GET;
        path = "groups";
        key = verb + path;
        Route r7 = new Route(verb, path, "getGroups", new Class[]{}, "logedIn");
        r7.setHelper("Get existent groups");
        this.addModel(key, r7);

        verb = VerbEnum.POST;
        path = "groups";
        key = verb + path;
        Route r8 = new Route(verb, path, "createGroup", new Class[]{String.class}, "logedIn");
        r8.setHelper("Get existent groups");
        this.addModel(key, r8);

        verb = VerbEnum.POST;
        path = "groups/message";
        key = verb + path;
        Route r9 = new Route(verb, path, "sendGroupMessage", new Class[]{String.class, String.class}, "logedIn");
        r9.setHelper("Send's a message to a group");
        this.addModel(key, r9);

        verb = VerbEnum.DELETE;
        path = "groups";
        key = verb + path;
        Route r10 = new Route(verb, path, "deleteGroup", new Class[]{String.class}, "logedIn");
        r10.setHelper("Delete a group");
        this.addModel(key, r10);

        verb = VerbEnum.POST;
        path = "groups/join";
        key = verb + path;
        Route r11 = new Route(verb, path, "joinGroup", new Class[]{String.class}, "logedIn");
        r11.setHelper("Join a group");
        this.addModel(key, r11);
        verb = VerbEnum.POST;

        path = "groups/leave";
        key = verb + path;
        Route r12 = new Route(verb, path, "leaveGroup", new Class[]{String.class}, "logedIn");
        r12.setHelper("Leaves a group");
        this.addModel(key, r12);

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
