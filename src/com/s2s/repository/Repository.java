package com.s2s.repository;


import java.util.ArrayList;

public class Repository<T> {
    ArrayList<T> models = null;

    public Repository(ArrayList<T> models) {
        this.models = models;
    }

    public Repository() {
        this.models = new ArrayList<T>();
    }

    public void setModels(ArrayList<T> models) {
        this.models = models;
    }

    public void addModel(T model) {
        this.models.add(model);
    }

    public ArrayList<T> getModels() {
        return models;
    }
}
