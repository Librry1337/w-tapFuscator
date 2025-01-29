package dev.librry.utils;

import static org.objectweb.asm.Opcodes.*;


public class AccessHelper {

    public static boolean isPublic(int mod) {
        return (mod & ACC_PUBLIC) != 0;
    }


    public static boolean isProtected(int mod) {
        return (mod & ACC_PROTECTED) != 0;
    }

    public static boolean isPrivate(int mod) {
        return (mod & ACC_PRIVATE) != 0;
    }


    public static boolean isStatic(int mod) {
        return (mod & ACC_STATIC) != 0;
    }


    public static boolean isNative(int mod) {
        return (mod & ACC_NATIVE) != 0;
    }


    public static boolean isAbstract(int mod) {
        return (mod & ACC_ABSTRACT) != 0;
    }


    public static boolean isFinal(int mod) {
        return (mod & ACC_FINAL) != 0;
    }


    public static boolean isSynthetic(int mod) {
        return (mod & ACC_SYNTHETIC) != 0;
    }


    public static boolean isVolatile(int mod) {
        return (mod & ACC_VOLATILE) != 0;
    }


    public static boolean isBridge(int mod) {
        return (mod & ACC_BRIDGE) != 0;
    }


    public static boolean isSynchronized(int mod) {
        return (mod & ACC_SYNCHRONIZED) != 0;
    }


    public static boolean isInterface(int mod) {
        return (mod & ACC_INTERFACE) != 0;
    }


    public static boolean isEnum(int mod) {
        return (mod & ACC_ENUM) != 0;
    }


    public static boolean isAnnotation(int mod) {
        return (mod & ACC_ANNOTATION) != 0;
    }


    public static boolean isDeprecated(int mod) {
        return (mod & ACC_DEPRECATED) != 0;
    }


    public static boolean isVoid(String desc) {
        return desc.endsWith("V");
    }

  
    public static boolean isBoolean(String desc) {
        return desc.endsWith("Z");
    }


    public static boolean isChar(String desc) {
        return desc.endsWith("C");
    }

    public static boolean isByte(String desc) {
        return desc.endsWith("B");
    }

    public static boolean isShort(String desc) {
        return desc.endsWith("S");
    }


    public static boolean isInt(String desc) {
        return desc.endsWith("I");
    }


    public static boolean isFloat(String desc) {
        return desc.endsWith("F");
    }


    public static boolean isLong(String desc) {
        return desc.endsWith("J");
    }


    public static boolean isDouble(String desc) {
        return desc.endsWith("D");
    }


    public static boolean isArray(String desc) {
        return desc.startsWith("[");
    }


    public static boolean isObject(String desc) {
        return desc.endsWith(";");
    }


    public static boolean isMethodReturnTypeGeneric(String desc) {
        return desc.contains(")T");
    }


    public static boolean isFieldGeneric(String desc, String signature) {
        return signature != null && desc != null && signature.startsWith("T") && signature.endsWith(";") && Character.isUpperCase(signature.charAt(1))
                && desc.contains("java/lang/Object");

    }
}