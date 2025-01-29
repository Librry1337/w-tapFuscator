package dev.librry.transformers;


import dev.librry.Obfuscator;
import dev.librry.utils.NodeUtils;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class StaticInitializionProcessor extends Transformer {
    private static Random random = new Random();

    public StaticInitializionProcessor(Obfuscator obf) {
        super(obf);
    }


    @Override
    public void visit(ClassNode node) {
        HashMap<FieldNode, Object> objs = new HashMap<>();
        for (FieldNode field : node.fields) {
            if (field.value != null) {
                if ((field.access & Opcodes.ACC_STATIC) != 0 && (field.value instanceof String || field.value instanceof Integer)) {
                    objs.put(field, field.value);
                    field.value = null;
                }
            }
        }
        InsnList toAdd = new InsnList();
        for (Map.Entry<FieldNode, Object> fieldNodeObjectEntry : objs.entrySet()) {
            if (fieldNodeObjectEntry.getValue() instanceof String) {
                toAdd.add(new LdcInsnNode(fieldNodeObjectEntry.getValue()));
            }
            if (fieldNodeObjectEntry.getValue() instanceof Integer) {
                toAdd.add(NodeUtils.generateIntPush((Integer) fieldNodeObjectEntry.getValue()));
            }
            toAdd.add(new FieldInsnNode(Opcodes.PUTSTATIC, node.name, fieldNodeObjectEntry.getKey().name, fieldNodeObjectEntry.getKey().desc));
        }
        MethodNode clInit = NodeUtils.getMethod(node, "<clinit>");
        if (clInit == null) {
            clInit = new MethodNode(Opcodes.ACC_STATIC, "<clinit>", "()V", null, new String[0]);
            node.methods.add(clInit);
        }

        if (clInit.instructions == null || clInit.instructions.getFirst() == null) {
            clInit.instructions = toAdd;
            clInit.instructions.add(new InsnNode(Opcodes.RETURN));
        } else {
            clInit.instructions.insertBefore(clInit.instructions.getFirst(), toAdd);
        }
    }

}