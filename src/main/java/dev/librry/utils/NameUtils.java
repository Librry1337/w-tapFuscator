
package dev.librry.utils;


import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class NameUtils {

    private final static char[] DICT_SPACES = new char[]{
            '\u2000', '\u2001', '\u2002', '\u2003', '\u2004', '\u2005', '\u2006', '\u2007', '\u2008', '\u2009', '\u200A', '\u200B', '\u200C', '\u200D', '\u200E', '\u200F'
    };
    private static HashMap<String, Integer> packageMap = new HashMap<>();
    private static Map<String, HashMap<String, Integer>> USED_METHODNAMES = new HashMap<>();
    private static Map<String, Integer> USED_FIELDNAMES = new HashMap<>();
    //    private static boolean iL = true;
    private static int localVars = Short.MAX_VALUE;
    private static Random random = new Random();
    private static int METHODS = 0;
    private static int FIELDS = 0;
    private static boolean usingCustomDictionary = false;
    private static List<String> classNames = new ArrayList<>();
    private static List<String> names = new ArrayList<>();
    private static String chars = "Il";

    @SuppressWarnings("SameParameterValue")
    private static int randInt(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    public static void setup() {
        USED_METHODNAMES.clear();
        USED_FIELDNAMES.clear();
        packageMap.clear();
    }

        public static String generateSpaceString(int length) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                stringBuilder.append(" ");
            }
            return stringBuilder.toString();
        }

    public static String generateClassName() {
        return generateClassName("");
    }


    public static String generateClassName(String packageName) {
        if (!packageMap.containsKey(packageName))
            packageMap.put(packageName, 0);

        int id = packageMap.get(packageName);
        packageMap.put(packageName, id + 1);

        return getName(classNames, id);
//        return ClassNameGenerator.className(Utils.random(2, 5));
    }

    private static String getName(List<String> dictionary, int id) {
        if (usingCustomDictionary && id < dictionary.size()) {
            return dictionary.get(id);
        }

        return convertToBase(id, chars);
    }


    public static String crazyString(int len) {
        char[] buildString = new char[len];
        for (int i = 0; i < len; i++) {
            buildString[i] = DICT_SPACES[random.nextInt(DICT_SPACES.length)];
        }
        return new String(buildString);
    }

    public static String convertToBase(int i, String str) {
        StringBuilder sb = new StringBuilder();

        do {
            sb.append(str.charAt(i % str.length()));
            i /= str.length();
        } while (i != 0);

        return sb.toString();
    }

    public static String generateMethodName(final String className, String desc) {
//        if (!USED_METHODNAMES.containsKey(className)) {
//            USED_METHODNAMES.put(className, new HashMap<>());
//        }
//
//        HashMap<String, Integer> descMap = USED_METHODNAMES.get(className);
//
//        if (!descMap.containsKey(desc)) {
//            descMap.put(desc, 0);
//        }
////        System.out.println("0 " + className + "/" + desc + ":" + descMap);
//
//        int i = descMap.get(desc);
//
//        descMap.put(desc, i + 1);
//
////        System.out.println(USED_METHODNAMES);
//
//
//        String name = getName(names, i);
//
//        return name;
        return getName(names, METHODS++);
    }

    public static String generateMethodName(final ClassNode classNode, String desc) {
        return generateMethodName(classNode.name, desc);
    }

    public static String generateFieldName(final String className) {
//        if (!USED_FIELDNAMES.containsKey(className)) {
//            USED_FIELDNAMES.put(className, 0);
//        }
//
//        int i = USED_FIELDNAMES.get(className);
//        USED_FIELDNAMES.put(className, i + 1);
//
//        return getName(names, i);
        return getName(names, FIELDS++);
    }

    public static String generateFieldName(final ClassNode classNode) {
        return generateFieldName(classNode.name);
    }

    public static String generateLocalVariableName(final String className, final String methodName) {
        return generateLocalVariableName();
    }

    public static String generateLocalVariableName() {
        if (localVars == 0) {
            localVars = Short.MAX_VALUE;
        }
        return convertToBase(localVars--, chars);
    }



        public static String getRandomString() {
            StringBuilder randomString = new StringBuilder();
            String randomStringChars = "\u91CE\u5C4B\u98A8\u706F\u96FB\u7A7A\u96F2\u82B1\u661F\u9732\u521D\u590F\u79CB\u51AC\u6625\u5F7C\u5F7C\u5973\u5BB6\u53CB\u611B\u795E\u4ECF\u796D\u7948\u795D\u5E78\u798F\u75C5\u5065\u5E0C\u9858\u529B\u80FD\u5922\u7720\u9192\u9053\u6B69\u8D70\u6B4C\u8E0A\u7B11\u6CE3\u601D\u8003\u8A00\u8A71\u805E\u7B54\u554F\u7B46\u7D19\u624B\u8DB3\u773C\u53E3\u9F3B\u8033\u8155\u6307\u8098\u819D\u8170\u8179\u80F8\u5FC3\u8166\u80BA\u814E\u809D\u819A\u808C\u9AA8\u9AEA\u8840\u6C57\u6D99\u98A8\u5442\u98DF\u9152\u98F2\u98FD\u9913\u904A\u898B\u8074\u8AAD\u8CB7\u58F2\u8077\u8077\u696D\u5B66\u6559\u5B66\u6821\u6559\u5BA4\u5148\u5F8C\u524D\u5F8C\u7537\u5973\u5B50\u7236\u6BCD\u5144\u59C9\u5F1F\u59B9\u5177\u5BB6\u5177\u672C\u66F8\u9CE5\u9B5A\u866B\u72AC\u732B\u9F8D\u864E\u72FC\u72D0\u718A\u8C61\u733F\u7F8A\u99AC\u725B\u7F8E\u611B\u559C\u6012\u54C0\u697D\u5E78\u5E0C\u9759\u52C7\u8CE2\u8AA0\u5FCD\u5F37\u5F31\u5FEB\u9045\u660E\u6697\u7D05\u9752\u7DD1\u9EC4\u767D\u9ED2\u91D1\u9280\u9244\u9285\u98A8\u96E8\u96EA\u82B1\u6728\u5C71\u5DDD\u6D77\u7A7A\u5929\u5730\u706B\u6C34\u98A8\u96F2\u661F\u592A\u967D\u6708\u96FB\u6620\u7A7A\u96F7\u9707\u5730\u9707\u706B\u5C71\u6D77\u5CB8\u5CF6\u68EE\u8349\u829D\u82B1\u8336\u84EE\u84EE\u685C\u7AF9\u677E\u6749\u6885\u6797\u679C\u5B9F\u84EE\u95A2\u8336\u8336\u84EE\u685C\u7AF9\u677E\u6749\u6885\u6797\u679C\u5B9F\u8349\u7F8E\u7A7A\u96F2\u5C71\u6C34\u58F0\u7B1B\u6B4C\u6B4C\u8A69\u8A69\u8A5E\u66F2\u821E\u821E\u8E0A\u7B11\u7B11\u7B11\u6CE3\u601D\u601D\u8003\u8003\u8003\u8003\u8003\u8A00\u8A71\u7D19\u96D1\u8A8C";
            ArrayList<String> randomChars = new ArrayList<>();
            for (char randomChar : randomStringChars.toCharArray()) {
                randomChars.add(String.valueOf(randomChar));
            }
            for (int index = 0; index < 6; index++) {
                randomString.append(randomChars.get(new Random().nextInt(randomChars.size())));
            }
            return String.valueOf(randomString);
        }


    public static String unicodeString(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append((char) randInt(128, 250));
        }
        return stringBuilder.toString();
    }

    public static void mapClass(String old, String newName) {
        if (USED_METHODNAMES.containsKey(old)) {
            USED_METHODNAMES.put(newName, USED_METHODNAMES.get(old));
        }
        if (USED_FIELDNAMES.containsKey(old)) {
            USED_FIELDNAMES.put(newName, USED_FIELDNAMES.get(old));
        }
    }

    public static String getPackage(String in) {
        int lin = in.lastIndexOf('/');

        if (lin == 0) throw new IllegalArgumentException("Illegal class name");

        return lin == -1 ? "" : in.substring(0, lin);
    }
}