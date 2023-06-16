package com.example.web.Encryption;

import java.util.HashMap;

public class EncryptionType1 implements Encryption {
    @Override
    public String performEncryption(String text) {
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
}
