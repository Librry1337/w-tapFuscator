package dev.librry.transformers;

import dev.librry.Obfuscator;
import dev.librry.utils.*;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NumberObfuscationProcessor extends Transformer {
    private static Random random = new Random();
    private static final String PROCESSOR_NAME = "NumberObfuscation";

    private static boolean lenghtMode = true;
    private static boolean xorMode = true;
    private static boolean simpleMathMode = true;
    private static NumberObfuscationProcessor INSTANCE;
    private EnabledValue enabled = new EnabledValue(PROCESSOR_NAME, DeprecationLevel.GOOD, true);
    private BooleanValue extractToArray = new BooleanValue(PROCESSOR_NAME, "Extract to Array", DeprecationLevel.GOOD, true);
    private BooleanValue obfuscateZero = new BooleanValue(PROCESSOR_NAME, "Obfuscate Zero", DeprecationLevel.GOOD, true);
    private BooleanValue shift = new BooleanValue(PROCESSOR_NAME, "Shift", DeprecationLevel.OK, false);
    private BooleanValue and = new BooleanValue(PROCESSOR_NAME, "And", DeprecationLevel.OK, false);
    private BooleanValue multipleInstrucstions = new BooleanValue(PROCESSOR_NAME, "Multiple Instructions", DeprecationLevel.GOOD, true);

    public NumberObfuscationProcessor(Obfuscator obf) {
        super(obf);
        INSTANCE = this;
    }

    public static InsnList getInstructionsMultipleTimes(int value, int iterations) {
        InsnList list = new InsnList();
        list.add(NodeUtils.generateIntPush(value));

        for (int i = 0; i < (INSTANCE != null && INSTANCE.multipleInstrucstions.getObject() ? iterations : 1); i++) {
            list = obfuscateInsnList(list);
        }
        return list;
    }

    public static InsnList obfuscateInsnList(InsnList list) {
        for (AbstractInsnNode abstractInsnNode : list.toArray()) {
            if (NodeUtils.isIntegerNumber(abstractInsnNode)) {
                int number = NodeUtils.getIntValue(abstractInsnNode);

                if (number == Integer.MIN_VALUE) {
                    continue;
                }
                list.insert(abstractInsnNode, getInstructions(number));
                list.remove(abstractInsnNode);
            }
        }
        return list;
    }

    public static InsnList getInstructions(int value) {
        InsnList methodInstructions = new InsnList();
        methodInstructions.add(new VarInsnNode(Opcodes.ILOAD, 0));
        methodInstructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "dev/librry/transformers/FlowObfuscatorTransformer", "getSharedObfValue", "(I)I", false));
        return methodInstructions;
    }

    private static int[] splitToAnd(int number) {
        int number2 = random.nextInt(Short.MAX_VALUE) & ~number;

        return new int[]{~number2, number2 | number};
    }

    private static int[] splitToLShift(int number) {
        int shift = 0;

        while ((number & ~0x7ffffffffffffffEL) == 0 && number != 0) {
            number = number >> 1;
            shift++;
        }
        return new int[]{number, shift};
    }

    @Override
    public void visit(ClassNode node) {
        if (!enabled.getObject()) return;

        int i = 0;
        String fieldName = NameUtils.generateFieldName(node.name);
        List<Integer> integerList = new ArrayList<>();
        for (MethodNode method : node.methods) {
            for (AbstractInsnNode abstractInsnNode : method.instructions.toArray()) {
                if (abstractInsnNode == null) {
                    System.out.println(method.name + method.desc);
                    throw new RuntimeException();
                }
                if (NodeUtils.isIntegerNumber(abstractInsnNode)) {
                    int number = NodeUtils.getIntValue(abstractInsnNode);

                    if (number == Integer.MIN_VALUE) {
                        continue;
                    }
                    if (!Modifier.isInterface(node.access) && extractToArray.getObject()) {
                        int containedSlot = -1;
                        int j = 0;
                        for (Integer integer : integerList) {
                            if (integer == number) containedSlot = j;
                            j++;
                        }
                        if (containedSlot == -1) integerList.add(number);
                        method.instructions.insertBefore(abstractInsnNode, new FieldInsnNode(Opcodes.GETSTATIC, node.name, fieldName, "[I"));
                        method.instructions.insertBefore(abstractInsnNode, NodeUtils.generateIntPush(containedSlot == -1 ? i : containedSlot));
                        method.instructions.insertBefore(abstractInsnNode, new InsnNode(Opcodes.IALOAD));
                        method.instructions.remove(abstractInsnNode);
                        if (containedSlot == -1) i++;
                        method.maxStack += 2;
                    } else {
                        method.maxStack += 4;

                        method.instructions.insertBefore(abstractInsnNode, getInstructionsMultipleTimes(number, random.nextInt(2) + 1));
                        method.instructions.remove(abstractInsnNode);
                    }
                }
            }
        }
        if (i != 0) {
            node.fields.add(new FieldNode(((node.access & Opcodes.ACC_INTERFACE) != 0 ? Opcodes.ACC_PUBLIC : Opcodes.ACC_PRIVATE) | Opcodes.ACC_STATIC, fieldName, "[I", null, null));
            MethodNode clInit = NodeUtils.getMethod(node, "<clinit>");
            if (clInit == null) {
                clInit = new MethodNode(Opcodes.ACC_STATIC, "<clinit>", "()V", null, new String[0]);
                node.methods.add(clInit);
            }
            if (clInit.instructions == null)
                clInit.instructions = new InsnList();

            InsnList toAdd = new InsnList();

            toAdd.add(NodeUtils.generateIntPush(i));

            toAdd.add(new IntInsnNode(Opcodes.NEWARRAY, Opcodes.T_INT));
            toAdd.add(new FieldInsnNode(Opcodes.PUTSTATIC, node.name, fieldName, "[I"));

            for (int j = 0; j < i; j++) {
                toAdd.add(new FieldInsnNode(Opcodes.GETSTATIC, node.name, fieldName, "[I"));
                toAdd.add(NodeUtils.generateIntPush(j));
                toAdd.add(getInstructionsMultipleTimes(integerList.get(j), random.nextInt(2) + 1));
                toAdd.add(new InsnNode(Opcodes.IASTORE));
            }

            MethodNode generateIntegers = new MethodNode(((node.access & Opcodes.ACC_INTERFACE) != 0 ? Opcodes.ACC_PUBLIC : Opcodes.ACC_PRIVATE) | Opcodes.ACC_STATIC, NameUtils.generateMethodName(node, "()V"), "()V", null, new String[0]);
            generateIntegers.instructions = toAdd;
            generateIntegers.instructions.add(new InsnNode(Opcodes.RETURN));
            generateIntegers.maxStack = 6;
            node.methods.add(generateIntegers);

            if (clInit.instructions == null || clInit.instructions.getFirst() == null) {
                clInit.instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC, node.name, generateIntegers.name, generateIntegers.desc, false));
                clInit.instructions.add(new InsnNode(Opcodes.RETURN));
            } else {
                clInit.instructions.insertBefore(clInit.instructions.getFirst(), new MethodInsnNode(Opcodes.INVOKESTATIC, node.name, generateIntegers.name, generateIntegers.desc, false));
            }
        }
    }
}
