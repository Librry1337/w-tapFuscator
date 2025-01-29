package dev.librry.utils;

import lombok.Data;

import static dev.librry.utils.AccessHelper.isStatic;



@Data
public class MethodObj {

    private String obfPkg, obfMethodName, obfMethodDesc;

    private final String pkg, methodName, methodDesc;
    private final int access;

    private boolean obfuscationDisable = false;

    public MethodObj(String pkg, String methodName, String methodDesc, int access) {
        this.pkg = pkg;
        this.methodName = methodName;
        this.methodDesc = methodDesc;
        this.access = access;
    }


    public String getFullMethod() {
        return pkg + "." + methodName + methodDesc;
    }


    public String getMethod() {
        return methodName + methodDesc;
    }


    public boolean isObfuscated() {
        return obfMethodName != null;
    }


    public boolean isSafeMethod() {
        return !(methodName.startsWith("<") //<init>, <clinit>
                || (methodName.equals("main") && methodDesc.equals("([Ljava/lang/String;)V") && isStatic(access)));
    }

}