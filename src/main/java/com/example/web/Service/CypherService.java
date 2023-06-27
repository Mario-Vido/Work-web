package com.example.web.Service;

import com.example.web.Interface.CypherInterface;
import com.example.web.Objects.Cypher;
import java.util.*;

public class CypherService implements CypherInterface {
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
            HashMap<Character, Character> letterMap = new HashMap<>();
            for (char c = 'a'; c <= 'z'; c++) {
                char replacement = (char) ('z' - (c - 'a'));
                letterMap.put(Character.toLowerCase(c), Character.toLowerCase(replacement));
                letterMap.put(Character.toUpperCase(c), Character.toUpperCase(replacement));
            }
            StringBuilder replacedString = new StringBuilder();
            for (char c : input.toCharArray()) {
                if (Character.isLetter(c)) {
                    char replacement = letterMap.get(Character.toLowerCase(c));
                    replacedString.append(Character.isUpperCase(c) ? Character.toUpperCase(replacement) : replacement);
                } else {
                    replacedString.append(c);
                }
            }
            return replacedString.toString();
        } else {
            StringBuilder plaintext = new StringBuilder();
            int shift = 3;
            for (int i = 0; i < input.length(); i++) {
                char currentChar = input.charAt(i);
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
    }

    public String performDecryption(Cypher cypher, String input){
        if (cypher.getName().equals("Cypher 0")) {
            HashMap<Character, Character> letterMap = new HashMap<>();
            for (char c = 'z'; c >= 'a'; c--) {
                char replacement = (char) ('a' + ('z' - c));
                letterMap.put(Character.toLowerCase(c), Character.toLowerCase(replacement));
                letterMap.put(Character.toUpperCase(c), Character.toUpperCase(replacement));
            }

            StringBuilder replacedString = new StringBuilder();
            for (char c : input.toCharArray()) {
                if (Character.isLetter(c)) {
                    char replacement = letterMap.get(Character.toLowerCase(c));
                    replacedString.append(Character.isUpperCase(c) ? Character.toUpperCase(replacement) : replacement);
                } else {
                    replacedString.append(c);
                }
            }
            return replacedString.toString();
        }else{
            StringBuilder plaintext = new StringBuilder();
            int shift = 3;
            for (int i = 0; i < input.length(); i++) {
                char currentChar = input.charAt(i);
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
    }
}
