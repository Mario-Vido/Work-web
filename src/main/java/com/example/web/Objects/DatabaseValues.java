package com.example.web.Objects;

import java.sql.Timestamp;

public class DatabaseValues {
    private final int id;
    private final String input;
    private final String output;
    private final String cypher;
    private final java.sql.Timestamp timestamp;
    private final Integer idOfUser;

    public DatabaseValues(int id, String input, String output, String cypher, Timestamp timestamp, Integer idOfUser) {
        this.id = id;
        this.input = input;
        this.output = output;
        this.cypher = cypher;
        this.timestamp = timestamp;
        this.idOfUser = idOfUser;
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

    public Integer getIdOfUser() {
        return idOfUser;
    }
}

