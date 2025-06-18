package dev.librry.transformers;


import dev.librry.Obfuscator;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.Random;

// NORENDER PROJECT BY ARURIUI
public class CrasherJDGUI extends Transformer {

    public CrasherJDGUI(Obfuscator obf) {
        super(obf);
    }

    public static String generateName(int length) {
        Random random = new Random();
        StringBuilder name = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int choice = random.nextInt(3);
            if (choice == 0) {
                name.append("\t");
            } else if (choice == 1) {
                name.append("\n");
            } else {
                char chineseChar = (char) (0x4E00 + random.nextInt(0x9FA5 - 0x4E00 + 1));
                name.append(chineseChar);
            }
        }

        return name.toString();
    }

    @Override
    public void visit(ClassNode classNode) {
        for (int i = 0; i < 1500; i++) {
          classNode.fields.add(new FieldNode(Modifier.PUBLIC | Modifier.FINAL, generateName(400), "I","", 1));
          //classNode.methods.add(new MethodNode(Modifier.PUBLIC | Modifier.FINAL, generateName(5), "()V","", null));
        }
        Collections.shuffle(classNode.fields);
       //Collections.shuffle(classNode.methods);
    }
}