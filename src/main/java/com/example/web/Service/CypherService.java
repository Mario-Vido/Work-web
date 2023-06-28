package com.example.web.Service;

import com.example.web.Interface.CypherInterface;
import com.example.web.Objects.Cypher;
import java.util.*;

public class CypherService implements CypherInterface {

    EncryptionAndDecryptionService service = new EncryptionAndDecryptionService();
    public List<Cypher> createCyphers(int count) {
        List<Cypher> cyphers = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            String name = "Cypher " + i;
            Cypher cypher = new Cypher(name);
            cyphers.add(cypher);
        }

        return cyphers;
    }

    public Map<String, Cypher> createCypherMap(List<Cypher> cypherList) {
        Map<String, Cypher> cypherMap = new HashMap<>();

        for (Cypher cypher : cypherList) {
            String name = cypher.getName();
            cypherMap.put(name, cypher);
        }

        return cypherMap;
    }

    public String generateStringFromKeys(Map<String, Cypher> cypherMap) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String key : cypherMap.keySet()) {
            stringBuilder.append(key).append(", ");
        }

        if (stringBuilder.length() > 2) {
            stringBuilder.setLength(stringBuilder.length() - 2);
        }
        return stringBuilder.toString();
    }

    public String performEncryption(Cypher cypher, String input) {
        if (cypher.getName().equals("Cypher 0")) {
            return service.performEncryptionType0(input);
        } else {
            return  service.performEncryptionType1(input);
        }
    }

    public String performDecryption(Cypher cypher, String input){
        if (cypher.getName().equals("Cypher 0")) {
            return  service.performDecryptionType0(input);
        }else{
            return  service.performDecryptionType1(input);
        }
    }
}
