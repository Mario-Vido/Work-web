package com.example.web.Service;

import com.example.web.Interface.CypherInterface;
import com.example.web.Objects.Cypher;

import java.util.*;

public class CypherService implements CypherInterface {
    public String callEncryptionType1(String text) {
        HashMap<Character, Character> letterMap = new HashMap<>();
        for (char c = 'a'; c <= 'z'; c++) {
            char replacement = (char) ('z' - (c - 'a'));
            letterMap.put(Character.toLowerCase(c), Character.toLowerCase(replacement));
            letterMap.put(Character.toUpperCase(c), Character.toUpperCase(replacement));
        }
        StringBuilder replacedString = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char replacement = letterMap.get(Character.toLowerCase(c));
                replacedString.append(Character.isUpperCase(c) ? Character.toUpperCase(replacement) : replacement);
            } else {
                replacedString.append(c);
            }
        }
        return replacedString.toString();
    }

    public String callEncryptionType2(String text){
        StringBuilder plaintext = new StringBuilder();
        int shift = 3;
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            if (Character.isLetter(currentChar)) {
                char decryptedChar;
                if (Character.isUpperCase(currentChar)) {
                    decryptedChar = (char) (((currentChar - 'A' + shift) % 26) + 'A');
                } else {
                    decryptedChar = (char) (((currentChar - 'a' + shift) % 26) + 'a');
                }
                plaintext.append(decryptedChar);
            } else {
                plaintext.append(currentChar);
            }
        }
        return plaintext.toString();
    }

    public String callDecryptionType1(String text){
        HashMap<Character, Character> letterMap = new HashMap<>();
        for (char c = 'z'; c >= 'a'; c--) {
            char replacement = (char) ('a' + ('z' - c));
            letterMap.put(Character.toLowerCase(c), Character.toLowerCase(replacement));
            letterMap.put(Character.toUpperCase(c), Character.toUpperCase(replacement));
        }

        StringBuilder replacedString = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char replacement = letterMap.get(Character.toLowerCase(c));
                replacedString.append(Character.isUpperCase(c) ? Character.toUpperCase(replacement) : replacement);
            } else {
                replacedString.append(c);
            }
        }
        return replacedString.toString();
    }
    public String callDecryptionType2(String text){
        StringBuilder plaintext = new StringBuilder();
        int shift = 3;
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            if (Character.isLetter(currentChar)) {
                char decryptedChar;
                if (Character.isUpperCase(currentChar)) {
                    decryptedChar = (char) (((currentChar - 'A' - shift + 26) % 26) + 'A');
                } else {
                    decryptedChar = (char) (((currentChar - 'a' - shift + 26) % 26) + 'a');
                }
                plaintext.append(decryptedChar);
            } else {
                plaintext.append(currentChar);
            }
        }
        return plaintext.toString();
    }

//    public String createStringFromKeys(HashMap<String, Cypher> cypherMap, Cypher[] cyphers){
//        for (Cypher cypher : cyphers) {
//            cypherMap.put(cypher.getName(), cypher);
//        }
//        Set<String> names = cypherMap.keySet();
//        return String.join(",", names);
//    }
//    public void creteCyphers(Cypher[] cyphers){
//        for (int i = 0; i < cyphers.length; i++) {
//            String name = "Cypher " + i;
//
//            cyphers[i] = new Cypher(name);
//        }
//    }
    public List<Cypher> createCyphers(int count) {
        List<Cypher> cyphers = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            String name = "Cypher " + i;
            Cypher cypher = new Cypher(name);
            cyphers.add(cypher);
        }

        return cyphers;
    }

//    public HashMap<String, Cypher> creatingHashMap(HashMap<String, Cypher> cypherMap,Cypher[] cyphers){
//        for (Cypher cypher : cyphers) {
//            cypherMap.put(cypher.getName(), cypher);
//        }
//        return cypherMap;
//    }
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

        // Remove the trailing comma and space
        if (stringBuilder.length() > 2) {
            stringBuilder.setLength(stringBuilder.length() - 2);
        }

        return stringBuilder.toString();
    }
}
