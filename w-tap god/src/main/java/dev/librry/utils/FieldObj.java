package dev.librry.utils;

import lombok.Data;


@Data
public class FieldObj {

    private final String pkg, fieldName;
    private String obfPkg, obfFieldName;

    public FieldObj(String pkg, String fieldName) {
        this.pkg = pkg;
        this.fieldName = fieldName;
    }

    public String getField() {
        return pkg + "." + fieldName;
    }

    
    public boolean isObfuscated() {
        return obfFieldName != null;
    }

}