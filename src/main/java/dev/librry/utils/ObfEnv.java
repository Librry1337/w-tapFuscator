package dev.librry.utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObfEnv {
    public static String MAIN_CLASS = null;
    public static String NEW_MAIN_CLASS = null;
    public static String ADVANCE_STRING_NAME = null;
    public static Map<String, String> classNameObfMapping = new HashMap<>();
    public static Map<String, List<String>> ignoredClassMethodsMapping = new HashMap<>();
    public static final HashMap<String, ArrayList<String>> newStringInClass = new HashMap<>();
}