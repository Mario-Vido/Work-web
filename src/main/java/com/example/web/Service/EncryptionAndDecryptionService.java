package com.example.web.Service;



import java.util.HashMap;

public class EncryptionAndDecryptionService{
    public String performEncryptionType0(String input){
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
    }

    public String performEncryptionType1(String input){
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

    public String performDecryptionType0(String input){
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
    }

    public String performDecryptionType1(String input){
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
