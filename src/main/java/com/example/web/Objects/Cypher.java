package com.example.web.Objects;

public class Cypher {

    private int key;
    private String name;

    public Cypher(int key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getKey() {
        return key;
    }
}
