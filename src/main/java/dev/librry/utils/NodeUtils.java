package dev.librry.utils;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.function.Predicate;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import dev.librry.Obfuscator;

import static org.objectweb.asm.Opcodes.*;
import static org.objectweb.asm.Opcodes.ICONST_5;



public class NodeUtils {
	public static ClassNode toNode(final String className) throws IOException {
		final ClassReader classReader = new ClassReader(Obfuscator.class.getResourceAsStream("/" + className.replace('.', '/') + ".class"));
		final ClassNode classNode = new ClassNode();
		classReader.accept(classNode, 0);
		return classNode;
	}
	private static HashMap<Type, String> TYPE_TO_WRAPPER = new HashMap<>();

	static {
		TYPE_TO_WRAPPER.put(Type.INT_TYPE, "java/lang/Integer");
		TYPE_TO_WRAPPER.put(Type.VOID_TYPE, "java/lang/Void");
		TYPE_TO_WRAPPER.put(Type.BOOLEAN_TYPE, "java/lang/Boolean");
		TYPE_TO_WRAPPER.put(Type.CHAR_TYPE, "java/lang/Character");
		TYPE_TO_WRAPPER.put(Type.BYTE_TYPE, "java/lang/Byte");
		TYPE_TO_WRAPPER.put(Type.SHORT_TYPE, "java/lang/Short");
		TYPE_TO_WRAPPER.put(Type.FLOAT_TYPE, "java/lang/Float");
		TYPE_TO_WRAPPER.put(Type.LONG_TYPE, "java/lang/Long");
		TYPE_TO_WRAPPER.put(Type.DOUBLE_TYPE, "java/lang/Double");
	}

	public static MethodNode getMethod(final ClassNode classNode, final String name) {
		for (final MethodNode method : classNode.methods)
			if (method.name.equals(name))
				return method;
		return null;
	}

	public static boolean isClassValid(ClassNode node) {
		return (node.access & Opcodes.ACC_ENUM) == 0 && (node.access & Opcodes.ACC_INTERFACE) == 0;
	}

	public static boolean isMethodValid(MethodNode method) {
		return !Modifier.isNative(method.access) && !Modifier.isAbstract(method.access) && method.instructions.size() != 0;
	}

	public static AbstractInsnNode getTypeNode(Type type) {
		if (TYPE_TO_WRAPPER.containsKey(type)) {
			return new FieldInsnNode(Opcodes.GETSTATIC, TYPE_TO_WRAPPER.get(type), "TYPE", "Ljava/lang/Class;");
		}
		return new LdcInsnNode(type);
	}
	public static int getIntValue(AbstractInsnNode node) {
		if (node.getOpcode() >= ICONST_M1 && node.getOpcode() <= ICONST_5) {
			return node.getOpcode() - 3;
		}
		if (node.getOpcode() == SIPUSH || node.getOpcode() == BIPUSH) {
			return ((IntInsnNode) node).operand;
		}
		if (node instanceof LdcInsnNode && ((LdcInsnNode) node).cst instanceof Integer) {
			return (int) ((LdcInsnNode) node).cst;
		}

		throw new IllegalArgumentException(node + " isn't an integer node");
	}

	public static AbstractInsnNode methodCall(ClassNode classNode, MethodNode methodNode) {
		int opcode = Opcodes.INVOKEVIRTUAL;

		if (Modifier.isInterface(classNode.access)) {
			opcode = Opcodes.INVOKEINTERFACE;
		}
		if (Modifier.isStatic(methodNode.access)) {
			opcode = Opcodes.INVOKESTATIC;
		}
		if (methodNode.name.startsWith("<")) {
			opcode = Opcodes.INVOKESPECIAL;
		}

		return new MethodInsnNode(opcode, classNode.name, methodNode.name, methodNode.desc, false);
	}



	public static void insertOn(InsnList instructions, Predicate<AbstractInsnNode> predicate, InsnList toAdd) {
		for (AbstractInsnNode abstractInsnNode : instructions.toArray()) {
			if (predicate.test(abstractInsnNode)) {
				instructions.insertBefore(abstractInsnNode, toAdd);
			}
		}
	}



	public static boolean isIntegerNumber(AbstractInsnNode ain) {
		if (ain.getOpcode() == BIPUSH || ain.getOpcode() == SIPUSH) {
			return true;
		}
		if (ain.getOpcode() >= ICONST_M1 && ain.getOpcode() <= ICONST_5) {
			return true;
		}
		if (ain instanceof LdcInsnNode) {
			LdcInsnNode ldc = (LdcInsnNode) ain;
			return ldc.cst instanceof Integer;
		}
		return false;
	}



	public static AbstractInsnNode generateIntPush(int i) {
		return new LdcInsnNode(i);
	}
	public static AbstractInsnNode nullValueForType(Type returnType) {
		switch (returnType.getSort()) {
		case Type.BOOLEAN:
		case Type.BYTE:
		case Type.CHAR:
		case Type.SHORT:
		case Type.INT:
			return new InsnNode(Opcodes.ICONST_0);
		case Type.FLOAT:
			return new InsnNode(Opcodes.FCONST_0);
		case Type.DOUBLE:
			return new InsnNode(Opcodes.DCONST_0);
		case Type.LONG:
			return new InsnNode(Opcodes.LCONST_0);
		case Type.ARRAY:
		case Type.OBJECT:
			return new InsnNode(Opcodes.ACONST_NULL);
		default:
			throw new UnsupportedOperationException();
		}
	}
}