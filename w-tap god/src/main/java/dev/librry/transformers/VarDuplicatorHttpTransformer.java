package dev.librry.transformers;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import dev.librry.Obfuscator;


// не советую использовать т.к jar файл становится 3 хромосомным
public class VarDuplicatorHttpTransformer extends Transformer {

	public VarDuplicatorHttpTransformer(Obfuscator Obf) {
		super(Obf);
	}

	@Override
	public void visit(ClassNode classNode) {
		for (MethodNode mn : classNode.methods) {
			for (AbstractInsnNode ain : mn.instructions.toArray()) {
				if (ain.getType() == AbstractInsnNode.VAR_INSN) {
					VarInsnNode vin = (VarInsnNode) ain;
					if (vin.getOpcode() == Opcodes.ASTORE) {
						mn.instructions.insertBefore(vin, new InsnNode(Opcodes.DUP));
						mn.instructions.insertBefore(vin, new InsnNode(Opcodes.ACONST_NULL));
						mn.instructions.insertBefore(vin, new InsnNode(Opcodes.SWAP));
						AbstractInsnNode next = vin.getNext();

						mn.instructions.insertBefore(next, new InsnNode(Opcodes.POP));
						mn.instructions.insertBefore(next, new VarInsnNode(Opcodes.ASTORE, vin.var));

						mn.instructions.insertBefore(vin, new TypeInsnNode(Opcodes.NEW, "java/net/URL"));
						mn.instructions.insertBefore(vin, new InsnNode(Opcodes.DUP));
						mn.instructions.insertBefore(vin, new LdcInsnNode("https://www.hiraeth.tech/"));
						mn.instructions.insertBefore(vin, new MethodInsnNode(Opcodes.INVOKESPECIAL, "java/net/URL", "<init>", "(Ljava/lang/String;)V", false));
						mn.instructions.insertBefore(vin, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/net/URL", "openConnection", "()Ljava/net/URLConnection;", false));
						mn.instructions.insertBefore(vin, new TypeInsnNode(Opcodes.CHECKCAST, "java/net/HttpURLConnection"));
						mn.instructions.insertBefore(vin, new InsnNode(Opcodes.DUP));
						mn.instructions.insertBefore(vin, new LdcInsnNode("GET"));
						mn.instructions.insertBefore(vin, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/net/HttpURLConnection", "setRequestMethod", "(Ljava/lang/String;)V", false));
						mn.instructions.insertBefore(vin, new InsnNode(Opcodes.DUP));
						mn.instructions.insertBefore(vin, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/net/HttpURLConnection", "getResponseCode", "()I", false));
						mn.instructions.insertBefore(vin, new InsnNode(Opcodes.POP));
						mn.instructions.insertBefore(vin, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/net/HttpURLConnection", "disconnect", "()V", false));
					}
				}
			}
		}
	}
}
