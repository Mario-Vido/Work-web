package com.example.web.Objects;

import java.security.Timestamp;

public class DatabaseValues {
    private int id;
    private String input;
    private String output;
    private String cypher;
    private java.sql.Timestamp timestamp;

    public DatabaseValues(int id, String input, String output, String cypher, java.sql.Timestamp timestamp) {
        this.id = id;
        this.input = input;
        this.output = output;
        this.cypher = cypher;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

    public String getCypher() {
        return cypher;
    }

    public java.sql.Timestamp getTimestamp() {
        return timestamp;
    }
}

