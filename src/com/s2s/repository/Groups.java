package com.s2s.repository;


import com.s2s.models.Group;

import java.util.ArrayList;
import java.util.HashMap;

public class Groups extends Repository<String, Group> {
    public Groups(HashMap<String, Group> groups) {
        super(groups);
    }

    public Groups() {
        super();
    }
}