package dev.librry.transformers;

import dev.librry.Obfuscator;
import dev.librry.utils.NodeUtils;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

import java.util.*;

public class ObfuscatorNewTech extends Transformer {
    public ObfuscatorNewTech(Obfuscator obf) {
        super(obf);
    }

    private static final Random random = new Random();

    @Override
    public void visit(ClassNode classNode) {
        if (!NodeUtils.isClassValid(classNode)) {
            return;
        }
        classNode.version = Math.max(classNode.version, Opcodes.V11);
        int condyIndex = 0;
        Map<String, String> stringToBootstrap = new HashMap<>();
        Map<String, int[]> stringToKeys = new HashMap<>();

        for (MethodNode method : classNode.methods) {
            if (!NodeUtils.isMethodValid(method)) continue;
            InsnList instructions = method.instructions;
            ListIterator<AbstractInsnNode> iter = instructions.iterator();
            while (iter.hasNext()) {
                AbstractInsnNode insn = iter.next();
                if (insn.getOpcode() == Opcodes.LDC && ((LdcInsnNode) insn).cst instanceof String) {
                    String original = (String) ((LdcInsnNode) insn).cst;
                    if (original.isEmpty()) continue;
                    int key1 = random.nextInt();
                    int key2 = random.nextInt();
                    String encrypted = encryptString(original, key1, key2);
                    String bootstrapName = "condyBootstrap$" + (condyIndex);
                    stringToBootstrap.put(encrypted, bootstrapName);
                    stringToKeys.put(encrypted, new int[]{key1, key2});

                    Handle bsm = new Handle(
                            Opcodes.H_INVOKESTATIC,
                            classNode.name,
                            bootstrapName,
                            "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/Class;Ljava/lang/String;II)Ljava/lang/String;",
                            false
                    );
                    Object[] bsmArgs = new Object[]{encrypted, key1, key2};
                    LdcInsnNode condy = new LdcInsnNode(new ConstantDynamic(
                            "obfStr$" + condyIndex,
                            "Ljava/lang/String;",
                            bsm,
                            bsmArgs
                    ));
                    instructions.insert(insn, condy);
                    instructions.remove(insn);
                    condyIndex++;
                }
            }
        }

        for (Map.Entry<String, String> entry : stringToBootstrap.entrySet()) {
            String encrypted = entry.getKey();
            String bootstrapName = entry.getValue();
            int[] keys = stringToKeys.get(encrypted);
            int key1 = keys[0];
            int key2 = keys[1];

            MethodNode bsm = new MethodNode(
                    Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC,
                    bootstrapName,
                    "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/Class;Ljava/lang/String;II)Ljava/lang/String;",
                    null,
                    null
            );
            bsm.visitCode();
            // char[] chars = encrypted.toCharArray();
            bsm.visitVarInsn(Opcodes.ALOAD, 3);
            bsm.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "toCharArray", "()[C", false);
            bsm.visitVarInsn(Opcodes.ASTORE, 6);

            // for (int i = 0; i < chars.length; i++)
            bsm.visitInsn(Opcodes.ICONST_0);
            bsm.visitVarInsn(Opcodes.ISTORE, 7);
            Label loopStart = new Label();
            Label loopEnd = new Label();
            bsm.visitLabel(loopStart);
            bsm.visitVarInsn(Opcodes.ILOAD, 7);
            bsm.visitVarInsn(Opcodes.ALOAD, 6);
            bsm.visitInsn(Opcodes.ARRAYLENGTH);
            bsm.visitJumpInsn(Opcodes.IF_ICMPGE, loopEnd);

            // chars[i] = (char) (chars[i] ^ (key1 ^ (key2 + i)));
            bsm.visitVarInsn(Opcodes.ALOAD, 6);
            bsm.visitVarInsn(Opcodes.ILOAD, 7);
            bsm.visitVarInsn(Opcodes.ALOAD, 6);
            bsm.visitVarInsn(Opcodes.ILOAD, 7);
            bsm.visitInsn(Opcodes.CALOAD);
            bsm.visitVarInsn(Opcodes.ILOAD, 4); // key1
            bsm.visitVarInsn(Opcodes.ILOAD, 5); // key2
            bsm.visitVarInsn(Opcodes.ILOAD, 7);
            bsm.visitInsn(Opcodes.IADD);
            bsm.visitInsn(Opcodes.IXOR);
            bsm.visitInsn(Opcodes.IXOR);
            bsm.visitInsn(Opcodes.I2C);
            bsm.visitInsn(Opcodes.CASTORE);

            // i++
            bsm.visitIincInsn(7, 1);
            bsm.visitJumpInsn(Opcodes.GOTO, loopStart);
            bsm.visitLabel(loopEnd);

            // return new String(chars);
            bsm.visitTypeInsn(Opcodes.NEW, "java/lang/String");
            bsm.visitInsn(Opcodes.DUP);
            bsm.visitVarInsn(Opcodes.ALOAD, 6);
            bsm.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/String", "<init>", "([C)V", false);
            bsm.visitInsn(Opcodes.ARETURN);
            bsm.visitMaxs(6, 8);
            bsm.visitEnd();
            classNode.methods.add(bsm);
        }
    }

    private String encryptString(String input, int key1, int key2) {
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (chars[i] ^ (key1 ^ (key2 + i)));
        }
        return new String(chars);
    }
}
