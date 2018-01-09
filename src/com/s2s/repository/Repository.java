package com.s2s.repository;


import java.util.ArrayList;
import java.util.HashMap;

public class Repository<K, V> {
    HashMap<K, V> models = null;

    public Repository(HashMap<K, V> models) {
        this.models = models;
    }

    public Repository() {
        this.models = new HashMap<>();
    }

    public void setModels(HashMap<K, V> models) {
        this.models = models;
    }

    public void addModel(K key, V model) {
        this.models.put(key, model);
    }

    public V delete(K key) {
        return this.models.remove(key);
    }

    public V update(K key, V value) {
        return this.models.put(key, value);
    }

    public HashMap<K, V> getModels() {
        return models;
    }

    public V exists(K k) {
        return this.getModels().getOrDefault(k, null);
    }
}
