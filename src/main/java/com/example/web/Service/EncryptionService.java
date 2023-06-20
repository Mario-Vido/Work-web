package com.example.web.Service;

import com.example.web.Encryption.Encryption;

public class EncryptionService {
    private final Encryption encryption1;
    private final Encryption encryption2;

    public EncryptionService(Encryption encryption1, Encryption encryption2) {
        this.encryption1 = encryption1;
        this.encryption2 = encryption2;
    }
    public String callEncryptionType1(String text) {
        return encryption1.performEncryption(text);
    }

    public String callEncryptionType2(String text){
        return encryption2.performEncryption(text);
    }
}
