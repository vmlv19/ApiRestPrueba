package com.prueba.ApiRestPrueba.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AesEncryptionUtil {

    private static final String SECRET_KEY = "prueba12345678901234567890123456";
    private static final String ALGORITM = "AES";

    public static String encrypt(String value) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITM);
            Cipher cipher = Cipher.getInstance(ALGORITM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);

        } catch (Exception e) {
            throw new RuntimeException("Error encrypting password", e);
        }
    }

    public static String decrypt(String value) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITM);
            Cipher cipher = Cipher.getInstance(ALGORITM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decoded = Base64.getDecoder().decode(value);
            return new String(cipher.doFinal(decoded));

        } catch (Exception e) {
            throw new RuntimeException("Error decrypting password", e);
        }
    }
}