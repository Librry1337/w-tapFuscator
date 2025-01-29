package dev.librry.utils;

import lombok.Setter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import static org.objectweb.asm.Opcodes.*;


public class ASM {

    @Setter
    private InsnList insn;

    public ASM() {}

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

    public void ldc(String type){
        insn.add(new LdcInsnNode(type));
    }

    public void ldcType(String type){
        insn.add(new LdcInsnNode(Type.getType(type)));
    }

    public void dup() {
        insn.add(new InsnNode(DUP));
    }

    public void isub(){
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