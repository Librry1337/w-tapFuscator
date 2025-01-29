package dev.librry.utils;


public class MethodNotFoundException extends Exception {
    public MethodNotFoundException(String message) {
        super("Method not found: " + message);
    }
}