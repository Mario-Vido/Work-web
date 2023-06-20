package com.example.web.Service;

import com.example.web.Decryption.Decryption;

public class DecryptionService {
    private final Decryption decription1;
    private final Decryption decryption2;

    public DecryptionService(Decryption decription1, Decryption decryption2) {
        this.decription1 = decription1;
        this.decryption2 = decryption2;
    }

    public String callDecryptionType1(String text){
        return decription1.performDecryption(text);
    }
    public String callDecryptionType2(String text){
        return  decryption2.performDecryption(text);
    }
}
