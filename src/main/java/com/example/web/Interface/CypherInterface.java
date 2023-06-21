package com.example.web.Interface;

import com.example.web.Objects.Cypher;

import java.util.HashMap;

public interface CypherInterface {
    String callEncryptionType1(String text);
    String callEncryptionType2(String text);

    String callDecryptionType1(String text);
    String callDecryptionType2(String text);

    String createStringFromKeys(HashMap<String, Cypher> cypherMap, Cypher[] cyphers);

    public HashMap<String, Cypher> creatingCypherMap(HashMap<String, Cypher> cypherMap,Cypher[] cyphers);
}
