package com.example.web.Interface;

import com.example.web.Objects.Cypher;

import java.util.List;
import java.util.Map;

public interface CypherInterface {

    String performEncryption(Cypher cypher, String input);

    String performDecryption(Cypher cypher, String input);

    Map<String, Cypher> createCypherMap(List<Cypher> cypherList);

    String generateStringFromKeys(Map<String, Cypher> cypherMap);

    List<Cypher> createCyphers(int count);
}
