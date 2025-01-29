package dev.librry.utils;


import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class StreamUtils {

    public static void copy(InputStream in, OutputStream out) throws IOException {
        int read;
        byte[] buffer = new byte[0x1000];
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

	public static byte[] readAll(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[0x1000];
        int read;
        while((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
        return out.toByteArray();
    }

	// NOP
	//if (RANDOM.nextInt(144) == 0)
	//	method.instructions.insertBefore(insnNode, new InsnNode(Opcodes.NOP));
	// DUP SWAP

	//					for (int i2 = 0; i2 < 2 + random.nextInt(5); i2++) {
	//						method.instructions.insert(new InsnNode(Opcodes.SWAP));
	//					}

	public static String toNode(final String className) throws IOException {
		return "";
	}

	public static StreamUtils getMethod(final String classNode, final String name) {
		return null;
	}
	}
