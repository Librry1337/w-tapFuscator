package dev.librry.transformers;

import dev.librry.Obfuscator;
import dev.librry.utils.RandomUtils;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.List;
import java.util.Random;

public class ShuffleTransformer extends Transformer {

    private Random random;

    public ShuffleTransformer(Obfuscator obfuscator) {
        super(obfuscator);
        this.random = obfuscator.getRandom();
    }

    @Override
    public void visit(ClassNode classNode) {
        // Запутывание имен наша лучшая разработка
        for (int i = 0; i < classNode.fields.size() - 1; i++) {
            for (int j = i + 1; j < classNode.fields.size(); j++) {
                List<FieldNode> swapped = RandomUtils.swap(random, classNode.fields.get(i), classNode.fields.get(j));
                classNode.fields.set(i, swapped.get(0));
                classNode.fields.set(j, swapped.get(1));
            }
        }

        for (int i = 0; i < classNode.methods.size() - 1; i++) {
            for (int j = i + 1; j < classNode.methods.size(); j++) {
                List<MethodNode> swapped = RandomUtils.swap(random, classNode.methods.get(i), classNode.methods.get(j));
                classNode.methods.set(i, swapped.get(0));
                classNode.methods.set(j, swapped.get(1));
            }
        }
    }
}
