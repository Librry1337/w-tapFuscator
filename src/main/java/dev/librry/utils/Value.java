

package dev.librry.utils;

public abstract class Value<T> {
    private String owner;
    private String name;
    private String description;
    private T object;
    private DeprecationLevel deprecation;

    public Value(String owner, String name, DeprecationLevel deprecation, T object) {
        this(owner, name, "", deprecation, object);
    }

    public Value(String owner, String name, String description, DeprecationLevel deprecation, T object) {
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.deprecation = deprecation;
        this.object = object;
    }

    public String getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public DeprecationLevel getDeprecation() {
        return deprecation;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return String.format("%s::%s = %s", owner, name, object);
    }
}