package dev.librry.transformers;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

import dev.librry.Obfuscator;

import java.util.UUID;

public class molitvaotantidebag extends Transformer {

    // посвещенно для Foggot
    private static final String[] koran = new String[]{
            "Зевс, Меркурий, Аполон.",
            "Сохрани мой код святой!",
            "Будет он заговорён.",
            "Словно щит над головой!",
            "педик какой то",
            "Как вы носите своего ребенка?",
            "Куда тебе скинуть эти бабки дай",
            "public class BetaGuiImageBytes {\n" +
                    "   public static byte[] image = new byte[]{-119, 80, 78, 71, 13, 10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 24, 0, 0, 0, 24, 8, 6, 0, 0, 0, -32, 119, 61, -8, 0, 0, 0, 9, 112, 72, 89, 115, 0, 0, 11, 19, 0, 0, 11, 19, 1, 0, -102, -100, 24, 0, 0, 1, -90, 73, 68, 65, 84, 120, -100, -19, -108, -65, 74, -126, 97, 20, -58, 63, 75, 11, -46, -55, 65, 12, -125, -18, 32, -70, 3, 21, -70, 1, -93, -79, -96, -67, -59, -20, 31, 93, -128, -111, -125, -48, 21, 4, 54, 55, 52, 4, 77, -111, -27, 36, -127, 75, -47, 80, 52, 73, 127, 22, -105, 12, 106, -22, 23, 7, -113, -16, -11, 113, -34, 79, -85, -91, -95, 7, 94, -48, -13, 60, -25, 60, -25, 61, -25, 85, -49, -5, -57, -97, 6, -112, 4, 86, -128, 99, -96, 13, -68, -23, 105, 107, 76, -72, -28, 79, 10, 71, -127, 109, -96, -53, 96, 116, 85, 27, 29, -74, -8, 20, -48, -28, -5, 104, 74, -18, -96, -30, -45, -64, -67, -111, 124, 4, 100, -127, -72, -98, 60, 80, 55, 116, -110, 59, -19, 42, -98, 0, 110, -116, -92, -83, -112, -122, -54, -122, 94, 106, 36, 44, 113, -51, -22, -36, -21, 113, 99, 64, 5, 120, 4, -98, -12, 115, 12, -120, 0, 39, 70, 94, 45, 88, 60, -25, -104, 107, 86, -7, 93, -125, -37, 81, 110, -50, -111, -101, -13, 27, 52, 28, -94, -124, -14, 15, 6, -41, 82, 110, 4, -104, -48, -35, -52, -24, -117, -110, -101, 54, -122, 49, -120, 43, 47, 99, -15, -29, -64, -100, -13, -41, 125, 110, -6, 3, -14, 66, 62, 12, -125, -68, -14, -107, 64, -15, -120, -58, 23, -128, 11, -32, 85, -49, 57, 48, -17, 114,",
    };



    public molitvaotantidebag(Obfuscator obf) {
        super(obf);
    }

    @Override
    public void visit(ClassNode classNode) {
        if ((classNode.access & Opcodes.ACC_INTERFACE) != 0) return; // Пропускаем интерфейсы

        for (int i = 0; i < koran.length; i++) {
            String str = koran[i];
            String fieldName = "!!!!ALARM_ANTIDEBAG_BY_BULBASH" + i + "_" + UUID.randomUUID().toString().replace("-", "");

            generateGarbageCode(classNode, str);

            classNode.fields.add(new FieldNode(
                    Opcodes.ACC_FINAL | Opcodes.ACC_PRIVATE,
                    fieldName,
                    "Ljava/lang/String;",
                    null,
                    str
            ));
        }
    }


    private void generateGarbageCode(ClassNode classNode, String str) {
        String randomMethodName = "BABAH_ALLHAH" + UUID.randomUUID().toString().replace("-", "ГОЙДА");
        classNode.methods.add(new org.objectweb.asm.tree.MethodNode(
                Opcodes.ACC_PRIVATE,
                randomMethodName,
                "()V",
                null,
                null
        ));


        org.objectweb.asm.tree.InsnList instructions = new org.objectweb.asm.tree.InsnList();
        instructions.add(new org.objectweb.asm.tree.LdcInsnNode(str));
        instructions.add(new org.objectweb.asm.tree.MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/String", "toUpperCase", "()Ljava/lang/String;", false)); // Преобразуем в верхний регистр
        instructions.add(new org.objectweb.asm.tree.InsnNode(Opcodes.RETURN));

        classNode.methods.get(classNode.methods.size() - 1).instructions = instructions;
    }

    @Override
    public void after() {
    }
}
