package com.example.web.Decryption;

import java.util.HashMap;

public class DecryptionType1 implements Decryption{
    @Override
    public String performDecryption(String text) {
        HashMap<Character, Character> letterMap = new HashMap<>();
        for (char c = 'z'; c >= 'a'; c--) {
            char replacement = (char) ('a' + ('z' - c));
            letterMap.put(c, replacement);
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
