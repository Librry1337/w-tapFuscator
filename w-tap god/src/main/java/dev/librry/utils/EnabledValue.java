
package dev.librry.utils;

public class EnabledValue extends BooleanValue {

    public EnabledValue(String owner, DeprecationLevel deprecated, Boolean object) {
        super(owner, "Enabled", deprecated, object);
    }

    public EnabledValue(String owner, String description, DeprecationLevel deprecation, Boolean object) {
        super(owner, "Enabled", description, deprecation, object);
    }

    @Override
    public String toString() {
        return String.format("%s = %s", getOwner(), getObject());
    }
}