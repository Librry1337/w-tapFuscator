package dev.librry.transformers;

import org.objectweb.asm.tree.*;
import org.objectweb.asm.Opcodes;
import dev.librry.Obfuscator;

import java.util.ArrayList;

public class TrashCodeTransformer extends Transformer {

	public TrashCodeTransformer(Obfuscator Obf) {
		super(Obf);
	}

	@Override
	public void visit(ClassNode classNode) {
		for (FieldNode fn : classNode.fields) {
			if (fn != null && (fn.access & Opcodes.ACC_DEPRECATED) == 0) {
				fn.access |= Opcodes.ACC_DEPRECATED;
			}
		}

		for (MethodNode mn : classNode.methods) {
			if (mn != null && (mn.access & Opcodes.ACC_DEPRECATED) == 0) {
				mn.access |= Opcodes.ACC_DEPRECATED;
			}
		}

		addTrashMethods(classNode);
	}

	private void addTrashMethods(ClassNode classNode) {
		for (int i = 0; i < 231; i++) {
			addDummyMethod(classNode, i);
		}
	}

	private void addDummyMethod(ClassNode classNode, int index) {
		String methodName = "anonteam1337" + index;

		MethodNode dummyMethod = new MethodNode(
				Opcodes.ACC_PRIVATE,
				methodName,
				"()V",
				null,
				null
		);


		InsnList instructions = new InsnList();



		instructions.add(new IntInsnNode(Opcodes.BIPUSH, 42));
		instructions.add(new VarInsnNode(Opcodes.ISTORE, 0));

		instructions.add(new VarInsnNode(Opcodes.ILOAD, 0));
		instructions.add(new IntInsnNode(Opcodes.BIPUSH, 10));
		instructions.add(new InsnNode(Opcodes.IADD));

		instructions.add(new IntInsnNode(Opcodes.BIPUSH, 5));
		instructions.add(new InsnNode(Opcodes.IADD));

		instructions.add(new IntInsnNode(Opcodes.BIPUSH, 100));
		instructions.add(new VarInsnNode(Opcodes.ISTORE, 0));


		instructions.add(new InsnNode(Opcodes.RETURN));

		dummyMethod.instructions = instructions;

		classNode.methods.add(dummyMethod);
	}
}
