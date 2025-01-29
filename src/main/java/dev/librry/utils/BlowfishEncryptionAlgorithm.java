

package dev.librry.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public class BlowfishEncryptionAlgorithm implements IStringEncryptionAlgorithm {
    public static String decrypt(String obj, String key) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(key.getBytes(StandardCharsets.UTF_8)), "Blowfish");

            Cipher des = Cipher.getInstance("Blowfish");
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
            SecretKeySpec keySpec = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(key.getBytes(StandardCharsets.UTF_8)), "Blowfish");

            Cipher des = Cipher.getInstance("Blowfish");
            des.init(Cipher.ENCRYPT_MODE, keySpec);

            return new String(Base64.getEncoder().encode(des.doFinal(obj.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}