package dev.librry.decrypt;

import java.util.Scanner;
import java.util.regex.Pattern;

public class xorbrute {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите зашифрованную строку: ");
        String encryptedText = scanner.nextLine();

        brutter(encryptedText);
    }

    private static String decrypt(String string, int key) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char ch : string.toCharArray()) {
            stringBuilder.append((char) (ch ^ key));
        }
        return stringBuilder.toString();
    }

    private static void brutter(String encryptedText) {
        Pattern validTextPattern = Pattern.compile("^[0-9a-zA-Zа-яА-Я ]+$");

        for (int key = 0; key < 900000; key++) {
            String decrypted = decrypt(encryptedText, key);
            if (validTextPattern.matcher(decrypted).matches()) {
                System.out.println("Possible decryption (key " + key + "): " + decrypted);
            }
        }

        for (int key = -1; key > -900000; key--) {
            String decrypted = decrypt(encryptedText, key);
            if (validTextPattern.matcher(decrypted).matches()) {
                System.out.println("Possible decryption (key " + key + "): " + decrypted);
            }
        }
    }
}
