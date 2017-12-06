package com.s2s.server;

import com.s2s.Mutual.Protocol;
import com.s2s.Mutual.ProtocolInterface;
import com.s2s.models.Group;
import com.s2s.models.Route;
import com.s2s.models.Slacker;
import com.s2s.repository.Clients;
import com.s2s.repository.Groups;
import com.s2s.repository.Repository;
import com.s2s.repository.Routes;
import utils.PortGen;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

public class Actions implements ProtocolInterface {

    private Slacker slacker;
    private Clients clients;
    private Routes routes;
    private Groups groups;
    private Router router;

    public Actions(Router router, Map<String, Repository> repositoryMap, Slacker slacker) {
        this.slacker = slacker;
        this.router = router;
        this.clients = (Clients) repositoryMap.get("Clients");
        this.routes = (Routes) repositoryMap.get("Routes");
        this.groups = (Groups) repositoryMap.get("Groups");
    }

    public void helpers() throws IOException {
        String message = "";
        for (Map.Entry<String, Route> entry : this.routes.getModels().entrySet()) {
            Route route = entry.getValue();
            message = message + route.getHelper() + "\n";
        }
        this.slacker.sendResponse(Protocol.successMessage("successMessage", message));
    }

    public void register(String username, String password) {
        if (this.clients.exists(username) == null) {
            //TODO create user dir and saves in the file
            try {
                Slacker newOne = new Slacker(this.slacker.getClientSocket());
                newOne.setUsername(username);
                newOne.setPassword(password);
                this.clients.addClient(newOne);
                this.slacker.sendResponse(Protocol.successMessage("successMessage", "Success register"));
            } catch (IOException e) {
                this.slacker.sendResponse(Protocol.errorMessage("IO error"));
                e.printStackTrace();
            }
        } else {
            this.slacker.sendResponse(Protocol.errorMessage("The user already exists"));
        }
    }

    /**
     * Authenticates a user
     *
     * @param username
     * @param password
     */
    public void login(String username, String password) {
        Slacker client = this.clients.validate(username, password);
        if (client != null) {
            this.clients.login(client, PortGen.getMultiCastPort());
            this.router.updateSlacker(client);
            this.slacker = client;
            this.slacker.sendResponse(Protocol.successMessage("successMessage", "successLogin"));
        } else {
            this.slacker.sendResponse(Protocol.errorMessage("invalidData"));
        }
    }

    /**
     * Logout's a user
     */
    public void logout() {
        if (this.clients.logout(this.slacker)) {
            this.slacker.sendResponse(Protocol.successMessage("successMessage", "successLogout"));
        } else this.slacker.sendResponse(Protocol.errorMessage("errorLogout"));
    }

    /**
     * Get's the online users
     */
    @Override
    public void getOnlineUsers() {
        Clients clients = this.clients.onlineUsers();
        if (clients.getModels().size() != 0) {
            StringBuilder message = new StringBuilder();
            for (Map.Entry<String, Slacker> entry : clients.getModels().entrySet()) {
                Slacker slacker = entry.getValue();
                message.append(slacker).append("\n");
            }
            this.slacker.sendResponse(Protocol.successMessage("successMessage", message.toString().trim()));
        } else {
            this.slacker.sendResponse(Protocol.infoMessage("No online users"));
        }
    }

    /**
     * Send a private message to a user
     *
     * @param username
     * @param message
     */
    @Override
    public void sendMessage(String username, String message) {
        Slacker slacker = this.clients.exists(username);
        if (slacker != null) {
            if (slacker.isLoged()) {
                slacker.sendResponse(Protocol.successMessage("successMessage", message));
            } else {
                this.slacker.sendResponse(Protocol.infoMessage("The requested user isn't online"));
            }
        } else {
            this.slacker.sendResponse(Protocol.errorMessage("The requested user doesn't exist"));
        }
    }

    /**
     * Send a group message
     *
     * @param groupName
     * @param message
     */
    @Override
    public void sendGroupMessage(String groupName, String message) {
        Group group = this.groups.exists(groupName);
        if (group != null) {
            try {
                group.sendMessage(message);
                slacker.sendResponse(Protocol.successMessage("successMessage", "successGroupMessage"));
            } catch (IOException e) {
                slacker.sendResponse(Protocol.errorMessage("Impossible to send the message to the group"));
                e.printStackTrace();
            }
        } else {
            slacker.sendResponse(Protocol.errorMessage("The group doesn't exist"));
        }
    }

    /**
     * Creates a group
     *
     * @param groupName
     */
    @Override
    public void createGroup(String groupName) {
        Group group = this.groups.exists(groupName);
        if (group == null) {
            Group newOne = new Group(groupName, this.slacker);
            this.groups.addModel(newOne.getGroupName(), newOne);
            slacker.sendResponse(Protocol.successMessage("successMessage", "createdGroup"));
        } else {
            this.slacker.sendResponse(Protocol.errorMessage("The group name already exists"));
        }
    }

    /**
     * Deletes a group
     *
     * @param groupName
     */
    @Override
    public void deleteGroup(String groupName) {
        Group group = this.groups.exists(groupName);
        if (group != null) {
            if (group.getClients().getModels().size() == 0) {
                this.groups.delete(groupName);
                slacker.sendResponse(Protocol.successMessage("successMessage", "deletedGroup"));
            } else {
                slacker.sendResponse(Protocol.errorMessage("notEmptyGroup"));
            }
        } else {
            this.slacker.sendResponse(Protocol.errorMessage("The group doesn't exists"));
        }
    }

    /**
     * Leaves a group
     *
     * @param groupName
     */
    @Override
    public void leaveGroup(String groupName) {
        Group group = this.groups.exists(groupName);
        if (group != null) {
            group.leave(this.slacker);
            slacker.sendResponse(Protocol.successMessage("successMessage", "leavedGroup"));
            // if is empty now, delete the group
            if (group.getClients().getModels().size() == 0) {
                this.deleteGroup(groupName);
                slacker.sendResponse(Protocol.successMessage("successMessage", "deletedGroup"));
            }
        } else {
            this.slacker.sendResponse(Protocol.errorMessage("The group doesn't exists"));
        }
    }

    /**
     * Leaves a group
     *
     * @param groupName
     */
    @Override
    public void joinGroup(String groupName) {
        Group group = this.groups.exists(groupName);
        if (group != null) {

            if (group.getClients().exists(this.slacker.getUsername()) != null) {
                this.slacker.sendResponse(Protocol.errorMessage("Already at the group"));
            } else {
                group.getClients().addClient(this.slacker);
                this.slacker.sendResponse(Protocol.successMessage("successMessage", "successAdded"));
            }
        } else {
            this.slacker.sendResponse(Protocol.errorMessage("The group doesn't exists"));
        }
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
