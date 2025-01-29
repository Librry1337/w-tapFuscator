package dev.librry.utils;

import lombok.Setter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;


import lombok.Setter;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.CodeSizeEvaluator;
import org.objectweb.asm.tree.*;

import static org.objectweb.asm.Opcodes.*;

import org.objectweb.asm.tree.*;

import static org.objectweb.asm.Opcodes.*;

public class AsmUtils {
    public static boolean isPushInt(AbstractInsnNode insn) {
        int op = insn.getOpcode();
        return (op >= Opcodes.ICONST_M1 && op <= Opcodes.ICONST_5)
                || op == Opcodes.BIPUSH
                || op == Opcodes.SIPUSH
                || (op == Opcodes.LDC && ((LdcInsnNode) insn).cst instanceof Integer);
    }

    public static int getPushedInt(AbstractInsnNode insn) {
        int op = insn.getOpcode();
        if (op >= Opcodes.ICONST_M1 && op <= Opcodes.ICONST_5) {
            return op - Opcodes.ICONST_0;
        }
        if (op == Opcodes.BIPUSH || op == Opcodes.SIPUSH) {
            return ((IntInsnNode) insn).operand;
        }
        if (op == Opcodes.LDC) {
            Object cst = ((LdcInsnNode) insn).cst;
            if (cst instanceof Integer) {
                return (int) cst;
            }
        }
        throw new IllegalArgumentException("insn is not a push int instruction");
    }


    public static ClassNode generateClassByName(String name) {
        ClassNode classNode = new ClassNode();
        classNode.visit(V1_8, (ACC_PUBLIC), name, null, "java/lang/Object", null);

        MethodNode methodNode = new MethodNode(ACC_PUBLIC, "<init>", "()V", null, null);
        methodNode.instructions.add(new VarInsnNode(ALOAD, 0));
        methodNode.instructions.add(new MethodInsnNode(INVOKESPECIAL, "java/lang/Object", "<init>", "()V"));
        methodNode.instructions.add(new InsnNode(RETURN));
        classNode.methods.add(methodNode);


        return classNode;
    }

    public static int codeSize(MethodNode methodNode) {
        CodeSizeEvaluator evaluator = new CodeSizeEvaluator(null);
        methodNode.accept(evaluator);
        return evaluator.getMaxSize();
    }


    public static AbstractInsnNode pushInt(int value) {
        if (value >= -1 && value <= 5) {
            return new InsnNode(Opcodes.ICONST_0 + value);
        }
        if (value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE) {
            return new IntInsnNode(Opcodes.BIPUSH, value);
        }
        if (value >= Short.MIN_VALUE && value <= Short.MAX_VALUE) {
            return new IntInsnNode(Opcodes.SIPUSH, value);
        }
        return new LdcInsnNode(value);
    }

    public static boolean isPushLong(AbstractInsnNode insn) {
        int op = insn.getOpcode();
        return op == Opcodes.LCONST_0
                || op == Opcodes.LCONST_1
                || (op == Opcodes.LDC && ((LdcInsnNode) insn).cst instanceof Long);
    }

    public static long getPushedLong(AbstractInsnNode insn) {
        int op = insn.getOpcode();
        if (op == Opcodes.LCONST_0) {
            return 0;
        }
        if (op == Opcodes.LCONST_1) {
            return 1;
        }
        if (op == Opcodes.LDC) {
            Object cst = ((LdcInsnNode) insn).cst;
            if (cst instanceof Long) {
                return (long) cst;
            }
        }
        throw new IllegalArgumentException("insn is not a push long instruction");
    }

    public static AbstractInsnNode pushLong(long value) {
        if (value == 0) {
            return new InsnNode(Opcodes.LCONST_0);
        }
        if (value == 1) {
            return new InsnNode(Opcodes.LCONST_1);
        }
        return new LdcInsnNode(value);
    }


    /**
     * @author Pants
     */
    public class ASM {

        @Setter
        private InsnList insn;

        public ASM() {
        }

        public ASM(InsnList insn) {
            this.insn = insn;
        }

        private int getConst(int i) {
            switch (i) {
                case 0:
                    return ICONST_0;
                case 1:
                    return ICONST_1;
                case 2:
                    return ICONST_2;
                case 3:
                    return ICONST_3;
                case 4:
                    return ICONST_4;
                case 5:
                    return ICONST_5;
                default:
                    return -1;
            }
        }

        /**
         * Pushes the correct int to the stack.
         * 'ICONST_#' if it's less than 6
         * 'BIPUSH #' if it's -127 to -1, and 6 to 127
         * 'SIPUSH #' if it's anything else
         *
         * @param i The int to push to the stack
         */
        public void pushInt(int i) {
            final int ICONST = getConst(i);

            if (ICONST != -1) {
                insn.add(new InsnNode(ICONST));
            } else if (i > -128 && i < 128) {
                insn.add(new IntInsnNode(BIPUSH, i));
            } else {
                insn.add(new IntInsnNode(SIPUSH, i));
            }
        }

        public void ldc(String type) {
            insn.add(new LdcInsnNode(type));
        }

        public void ldcType(String type) {
            insn.add(new LdcInsnNode(Type.getType(type)));
        }

        public void dup() {
            insn.add(new InsnNode(DUP));
        }

        public void isub() {
            insn.add(new InsnNode(ISUB));
        }

        public void aastore() {
            insn.add(new InsnNode(AASTORE));
        }

        public void aaload() {
            insn.add(new InsnNode(AALOAD));
        }

        public void field(int opcode, String owner, String name, String desc) {
            insn.add(new FieldInsnNode(opcode, owner, name, desc));
        }

        public void aload(int i) {
            insn.add(new VarInsnNode(ALOAD, i));
        }


    }
}


