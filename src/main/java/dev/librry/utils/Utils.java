package dev.librry.utils;

import dev.librry.Obfuscator;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.LdcInsnNode;

import java.util.List;
import java.util.stream.Collectors;

import static org.objectweb.asm.Opcodes.*;



public class Utils {
    public static ClassNode lookupClass(Obfuscator obfuscator, String name) {
        List<ClassNode> classNodes = obfuscator.getClasses().stream()
                .filter(classNode -> classNode.name.equals(name))
                .collect(Collectors.toList());

        if (classNodes.isEmpty()) {
            // If no class found, generate the class
            return AsmUtils.generateClassByName(name);
        } else {
            // Return the first element from the list
            return classNodes.get(0);
        }
    }




    public static FieldNode getField(ClassNode cls, String name) {
        for (FieldNode method : cls.fields) {
            if (method.name.equals(name))
                return method;
        }
        return null;
    }
}
