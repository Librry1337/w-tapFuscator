package dev.librry.transformers;

import dev.librry.Obfuscator;
import dev.librry.utils.StringUtils;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


// хуйня?
public class RenamerTransformer extends Transformer {

    protected final HashMap<String, String> map = new HashMap<>();
    protected int index = 0;

    public enum RenameType {
        ALPHABET,
        INVISIBLE,
        IlIlIlIlIl
    }

    protected RenameType renameType = RenameType.ALPHABET;

    public RenamerTransformer(Obfuscator obf) {
        super(obf);
    }

    @Override
    public void transform(ClassNode classNode) {
        if (shouldSkipClass(classNode.name)) {
            return;
        }

        for (MethodNode method : classNode.methods) {
            if (shouldRenameMethod(method)) {
                String newName = registerMap(method.name);
                method.name = newName;
            }
        }
        for (FieldNode field : classNode.fields) {
            if (shouldRenameField(field)) {
                String newName = registerMap(field.name);
                field.name = newName;
            }
        }
    }

    private boolean shouldSkipClass(String className) {
        return className.startsWith("java/") ||
                className.startsWith("javax/") ||
                className.startsWith("sun/") ||
                className.startsWith("android/");
    }

    private boolean shouldRenameMethod(MethodNode method) {
        return !method.name.equals("<init>") &&
                !method.name.equals("<clinit>") &&
                !method.name.startsWith("lambda$");
    }

    private boolean shouldRenameField(FieldNode field) {
        return !field.name.startsWith("$");
    }

    protected String registerMap(String key) {
        if (isMapRegistered(key)) {
            return map.get(key);
        }

        var str = switch(renameType) {
            case ALPHABET -> StringUtils.getAlphabetCombinations().get(index);
            case INVISIBLE -> String.valueOf((char)(index + '\u3050'));
            case IlIlIlIlIl -> getRandomUniqueIl(400);
        };
        map.put(key, str);
        index++;
        return str;
    }

    private final List<String> IlList = new ArrayList<>();
    private String getRandomUniqueIl(int length) {
        String s;
        do {
            s = IntStream.range(0, length)
                    .mapToObj(i -> (ThreadLocalRandom.current().nextBoolean()) ? "I" : "l")
                    .collect(Collectors.joining());
        } while (IlList.contains(s));
        IlList.add(s);
        return s;
    }

    protected boolean isMapRegistered(String key) {
        return map.containsKey(key);
    }

    protected void registerMap(String key, String value) {
        map.put(key, value);
    }

    public HashMap<String, String> getMap() {
        return map;
    }

    public void setRenameType(RenameType type) {
        this.renameType = type;
    }

    @Override
    public void after() {
        map.clear();
        index = 0;
        IlList.clear();
    }
}