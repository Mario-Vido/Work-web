package com.example.web.Service;


import com.example.web.Interface.CypherInterface;

import java.util.HashMap;

public class EncryptionService implements CypherInterface {
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

    @Override
    public String callDecryptionType1(String text) {
        return null;
    }

    @Override
    public String callDecryptionType2(String text) {
        return null;
    }
}
