package com.example.web.Encryption;

public class EncryptionType2 implements Encryption {
    @Override
    public String performEncryption(String text) {
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            if (Character.isLetter(currentChar)) {
                int shift = 3;
                char encryptedChar = (char) ((currentChar - 'a' + shift) % 26 + 'a');
                ciphertext.append(encryptedChar);
            } else {
                ciphertext.append(currentChar);
            }
        }
        return ciphertext.toString();
    }
}
