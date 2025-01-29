package dev.librry.utils;

import dev.librry.Obfuscator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StringUtils {

    private final static char[] DICT_SPACES = new char[]{'\u2000', '\u2001', '\u2002', '\u2003', '\u2004', '\u2005', '\u2006', '\u2007', '\u2008', '\u2009', '\u200A', '\u200B', '\u200C', '\u200D', '\u200E', '\u200F'};

    private static final StringBuilder sb = new StringBuilder();
    private static List<String> strings = new ArrayList<>();
    private static void generateCombinations(String dictionary, int maxIndex, int index)
    {
        if(index == maxIndex)
            strings.add(sb.toString());
        else dictionary.chars().forEach(value -> {
            sb.append((char)value);
            generateCombinations(dictionary, maxIndex, index + 1);
            sb.deleteCharAt(sb.length() - 1);
        });
    }


    public static String getAlphabet() {
        return IntStream.rangeClosed('A', 'z')
                .mapToObj(operand -> (char) operand)
                .filter(Character::isLetter)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }


    private static List<String> alphabetCombinations = null;
    public static List<String> getAlphabetCombinations() {
        if(alphabetCombinations == null) {
            strings = new ArrayList<>();
            for (int i = 2; i <= 3; i++)
                generateCombinations(getAlphabet(), i, 0);
            alphabetCombinations = Collections.unmodifiableList(strings);
        } return alphabetCombinations;
    }


    public static String crazyString(Obfuscator obfuscator, int length) {
        char[] buildString = new char[length];
        for (int i = 0; i < length; i++)
            buildString[i] = DICT_SPACES[obfuscator.getRandom().nextInt(DICT_SPACES.length)];
        return new String(buildString);
    }
}

