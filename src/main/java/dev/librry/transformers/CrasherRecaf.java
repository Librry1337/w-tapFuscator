package dev.librry.transformers;

import dev.librry.Obfuscator;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.Random;


// helped by shaoriy
public class CrasherRecaf extends Transformer {

    private static final int VIRTUAL_CALL_COUNT = 9900;  // минимально будет 3000
    private static final Random random = new Random();

    public CrasherRecaf(Obfuscator obf) {
        super(obf);
    }

    @Override
    public void visit(ClassNode classNode) {
        addObfuscatedMethods(classNode);
        addRandomFields(classNode);
    }

    private void addObfuscatedMethods(ClassNode classNode) {

        for (int i = 0; i < 119; i++) {
            String methodName = generateRandomMethodName();
            MethodNode methodNode = new MethodNode(Opcodes.ACC_PUBLIC | Opcodes.ACC_BRIDGE | Opcodes.ACC_SYNTHETIC, methodName, "()V", null, null);
            classNode.methods.add(methodNode);


            InsnList instructions = new InsnList();
            instructions.add(new LdcInsnNode(generateRandomString()));

            for (int j = 0; j < VIRTUAL_CALL_COUNT; j++) {


                instructions.add(new MethodInsnNode(
                        Opcodes.INVOKEVIRTUAL,
                        "java/lang/Object",
                        "toString",
                        "()Ljava/lang/String;",
                        false
                ));
            }

            instructions.add(new InsnNode(Opcodes.RETURN));
            methodNode.instructions.add(instructions);
        }
    }

    private void addRandomFields(ClassNode classNode) {
        for (int i = 0; i < 10; i++) {
            String fieldName = generateRandomFieldName();
            FieldNode fieldNode = new FieldNode(Opcodes.ACC_PRIVATE | Opcodes.ACC_SYNTHETIC, fieldName, "Ljava/lang/String;", null, null);
            classNode.fields.add(fieldNode);
        }
    }

    private String generateRandomMethodName() {
        StringBuilder methodName = new StringBuilder();
        methodName.append("\u2060\uD835\uDD0A");
        methodName.append((char)(random.nextInt(26) + '☺'));
        methodName.append(random.nextInt(1000));
        methodName.append("\u0378");
        return methodName.toString();
    }

    private String generateRandomFieldName() {
        StringBuilder fieldName = new StringBuilder();
        fieldName.append("\u2060\uD835\uDD0A");
        fieldName.append((char)(random.nextInt(26) + '☺'));
        fieldName.append(random.nextInt(1000));
        return fieldName.toString();
    }

    private String generateRandomString() {

        int length = random.nextInt(10) + 5;
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            randomString.append((char)(random.nextInt(26) + '☺'));
        }
        return randomString.toString();
    }
}
