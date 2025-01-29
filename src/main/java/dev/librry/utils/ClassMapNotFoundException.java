package dev.librry.utils;

public class ClassMapNotFoundException extends Exception {

    public ClassMapNotFoundException() {
    }

    public ClassMapNotFoundException(String message){
        super("Class Map not found: " + message);
    }

}