package com.s2s.repository;


import com.s2s.models.Group;
import com.s2s.models.Slacker;
import com.s2s.mutual.Protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Groups extends Repository<String, Group> {
    public Groups(HashMap<String, Group> groups) {
        super(groups);
    }

    public Groups() {
        super();
    }

    public Groups getUserGroups(String username) {
        Groups userGroups = new Groups();
        if (this.getModels().size() != 0) {
            for (Map.Entry<String, Group> entry : this.getModels().entrySet()) {
                Group group = entry.getValue();
                if (group.getClients().exists(username) != null) {
                    userGroups.addModel(group.getGroupName(),group);
                }
            }
        }
        return userGroups;
    }
}