package com.example.projet.Config;

import io.jsonwebtoken.security.Keys;

import java.util.Base64;

public class KeyGenerator {
    public static void main(String[] args) {
        var key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);

       
        String base64EncodedKey = Base64.getEncoder().encodeToString(key.getEncoded());

        System.out.println("Base64 Encoded Secret Key: " + base64EncodedKey);
    }
}
