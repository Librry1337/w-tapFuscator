

package dev.librry.utils;

public class BooleanValue extends Value<Boolean> {

    public BooleanValue(String owner, String name, DeprecationLevel deprecation, Boolean object) {
        super(owner, name, deprecation, object);
    }

    public BooleanValue(String owner, String name, String description, DeprecationLevel deprecation, Boolean object) {
        super(owner, name, description, deprecation, object);
    }
}