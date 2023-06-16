package com.example.web.Encryption;

public class EncryptionType2 implements Encryption {
    @Override
    public String performEncryption(String text) {
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            if (Character.isLetter(currentChar)) {
                int shift=3;
                char decryptedChar = (char) ((currentChar - 'a' - shift + 26) % 26 + 'a');
                plaintext.append(decryptedChar);
            } else {
                plaintext.append(currentChar);
            }
        }
        return plaintext.toString();
    }
}
