package dev.librry.transformers;

import java.util.Random;
import dev.librry.utils.ProcessorCallback;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import dev.librry.Obfuscator;
import org.objectweb.asm.Opcodes;

public abstract class Transformer implements Opcodes {

    protected final Obfuscator obf;
    protected final Random random;

    public Transformer(Obfuscator obf) {
        this.obf = obf;
        this.random = obf.getRandom();
    }

    public void visit(ClassReader classReader, ClassNode classNode, int pass) {
    }

    public void visit(ClassNode node) {
        transform(node);
    }



    public void after() {
    }

    public void process(ProcessorCallback callback, ClassNode node) {
        transform(node);
    }


    public void transform(ClassNode classNode) {
    }



}





