package dev.librry.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class XOREncryptionAlgorithm implements IStringEncryptionAlgorithm {

    public static String decrypt(String obj, String key) {
        obj = new String(Base64.getDecoder().decode(obj.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder sb = new StringBuilder();
        char[] keyChars = key.toCharArray();
        int i = 0;
        for (char c : obj.toCharArray()) {
            sb.append((char) (c ^ keyChars[i % keyChars.length]));
            i++;
        }
        return sb.toString();
    }

    @Override
    public String encrypt(String obj, String key) {
        StringBuilder sb = new StringBuilder();
        char[] keyChars = key.toCharArray();
        int i = 0;
        for (char c : obj.toCharArray()) {
            sb.append((char) (c ^ keyChars[i % keyChars.length]));
            i++;
        }
        return new String(Base64.getEncoder().encode(sb.toString().getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }
}