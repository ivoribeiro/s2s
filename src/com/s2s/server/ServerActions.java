package com.s2s.server;

import com.s2s.models.User;
import com.s2s.mutual.Protocol;
import com.s2s.mutual.ProtocolInterface;
import com.s2s.models.Group;
import com.s2s.models.Route;
import com.s2s.models.Slacker;
import com.s2s.repository.*;
import utils.FileUtil;
import utils.PortGen;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class ServerActions implements ProtocolInterface {

    private Slacker slacker;
    private Clients clients;
    private Routes routes;
    private Groups groups;
    private Router router;
    private Users users;

    public ServerActions(Router router, Map<String, Repository> repositoryMap, Slacker slacker) {
        this.slacker = slacker;
        this.router = router;
        this.clients = (Clients) repositoryMap.get("Clients");
        this.routes = (Routes) repositoryMap.get("Routes");
        this.groups = (Groups) repositoryMap.get("Groups");
        this.users = (Users) repositoryMap.get("Users");
    }

    public void help() throws IOException {
        for (Map.Entry<String, Route> entry : this.routes.getModels().entrySet()) {
            Route route = entry.getValue();
            String message = route.getVerb() + " " + route.getPath() + " " + route.getHelper() + "\n";
            String send = Protocol.successMessage("successMessage", message.trim());
            this.slacker.sendResponse(send);
        }
    }

    public void register(String username, String password) {
        if (this.clients.exists(username) == null) {
            try {
                Slacker newOne = new Slacker(this.slacker.getClientSocket());
                User user = new User(username, password);
                this.users.addModel(username, user);
                newOne.setUser(user);
                this.clients.addClient(newOne);
                Users.saveNewUser(this.users, user);
                this.slacker.sendResponse(Protocol.successMessage("successMessage", "Success register"));
            } catch (IOException e) {
                this.slacker.sendResponse(Protocol.errorMessage("IO error"));
                e.printStackTrace();
            } catch (Exception e) {
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
        User user = this.users.validate(username, password);
        if (user != null) {
            this.slacker.setUser(user);
            this.clients.login(this.slacker, PortGen.getMultiCastPort());
            this.router.updateSlacker(this.slacker);
            this.slacker.sendResponse(Protocol.successMessage("successLogin", this.slacker.getUser().getUsername()));
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
                slacker.sendResponse(Protocol.successMessage("messageReceived", this.slacker.getUser().getUsername() + ":" + message));
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

            if (group.getClients().exists(this.slacker.getUser().getUsername()) != null) {
                this.slacker.sendResponse(Protocol.errorMessage("Already at the group"));
            } else {
                group.getClients().addClient(this.slacker);
                this.slacker.sendResponse(Protocol.successMessage("listenGroup", group.getGroup() + ":" + group.getPort()));
            }
        } else {
            this.slacker.sendResponse(Protocol.errorMessage("The group doesn't exists"));
        }
    }

    /**
     * Get existing groups
     */
    public void getGroups() {
        if (this.groups.getModels().size() != 0) {
            StringBuilder message = new StringBuilder();
            for (Map.Entry<String, Group> entry : this.groups.getModels().entrySet()) {
                Group group = entry.getValue();
                message.append(group).append("\n");
            }

            this.slacker.sendResponse(Protocol.successMessage("successMessage", message.toString().trim()));
        } else {
            this.slacker.sendResponse(Protocol.infoMessage("Dont exist groups at this moment"));
        }
    }

    /**
     * Listen to the user groups for new messages
     *
     * @param group
     */
    @Override
    public void getMyGroups(String group) {

    }

    /**
     * Saves the user messages
     */
    @Override
    public void saveUserMessages() {
        this.slacker.sendResponse(Protocol.successMessage("saveYourMessages", ""));
    }

    /**
     * Process a route action call with reflection method invoke
     *
     * @param route
     */
    public void processAction(Route route, String... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ServerActions.class.getMethod(route.getAction(), route.getArgTypes()).invoke(this, args);
    }
}
