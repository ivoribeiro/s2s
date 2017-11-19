package com.s2s.repository;


import com.s2s.models.Group;

import java.util.ArrayList;

public class Groups extends Repository<Group> {
    public Groups(ArrayList<Group> groups) {
        super(groups);
    }
}