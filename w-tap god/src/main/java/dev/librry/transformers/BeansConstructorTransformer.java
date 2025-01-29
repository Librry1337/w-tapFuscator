package dev.librry.transformers;

import dev.librry.Obfuscator;
import org.objectweb.asm.tree.ClassNode;


public class BeansConstructorTransformer extends Transformer {

    public BeansConstructorTransformer(Obfuscator obf) {
        super(obf);
    }

    @Override
    public void visit(ClassNode classNode) {
        classNode.methods.forEach(methodNode -> {
            if (methodNode.visibleAnnotations == null)
                return;

            methodNode.visibleAnnotations
                    .stream()
                    .filter(annotationNode -> annotationNode.desc.equals("Ljava/beans/ConstructorProperties;"))
                    .findFirst().ifPresent(a -> methodNode.visibleAnnotations.remove(a));
            ;
        });
    }
}