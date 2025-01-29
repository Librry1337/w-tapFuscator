
package dev.librry.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

public class DESEncryptionAlgorithm implements IStringEncryptionAlgorithm {
    public static String decrypt(String obj, String key) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(key.getBytes(StandardCharsets.UTF_8)), 8), "DES");

            Cipher des = Cipher.getInstance("DES");
            des.init(Cipher.DECRYPT_MODE, keySpec);

            return new String(des.doFinal(Base64.getDecoder().decode(obj.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String encrypt(String obj, String key) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(key.getBytes(StandardCharsets.UTF_8)), 8), "DES");

            Cipher des = Cipher.getInstance("DES");
            des.init(Cipher.ENCRYPT_MODE, keySpec);

            return new String(Base64.getEncoder().encode(des.doFinal(obj.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}