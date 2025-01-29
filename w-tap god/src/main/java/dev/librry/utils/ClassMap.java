package dev.librry.utils;

import lombok.Getter;
import lombok.Setter;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


@Getter
@Setter
public class ClassMap {

    public final List<FieldObj> fields;
    public final List<MethodObj> methods;

    private final String className;

    private String obfClassName;
    private String parent;
    private List<String> interfaces;

    private boolean library = false;

    public ClassMap(String className) {
        this.methods = new ArrayList<>();
        this.fields = new ArrayList<>();

        this.interfaces = new ArrayList<>();
        this.className = className;
    }

    
    public MethodObj getMethod(String methodId) throws MethodNotFoundException {
        Optional<MethodObj> methodObj = methods.stream().filter(m -> m.getFullMethod().equals(methodId)).findFirst();

        if (methodObj.isPresent())
            return methodObj.get();
        else
            throw new MethodNotFoundException(methodId);
    }

   
    public MethodObj getMethodFromShort(String methodId) throws MethodNotFoundException {
        Optional<MethodObj> methodObj = methods.stream().filter(m -> m.getMethod().equals(methodId)).findFirst();

        if (methodObj.isPresent())
            return methodObj.get();
        else
            throw new MethodNotFoundException(methodId);
    }

    public FieldObj getField(String fieldName) {
        Optional<FieldObj> fieldObj = fields.stream().filter(f -> f.getFieldName().equals(fieldName)).findFirst();

        return fieldObj.orElse(null);
    }

    
    public void addField(FieldObj fieldObj) {
        this.fields.add(fieldObj);
    }

   
    public void addMethod(MethodObj methodObj) {
        this.methods.add(methodObj);
    }

    public boolean isObfuscated() {
        return obfClassName != null;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public boolean hasImplementedClasses() {
        return !interfaces.isEmpty();
    }

    
    public boolean hasAnnotation(Class c, List availableAnnotations) {
        if (availableAnnotations != null) {
            Iterator<AnnotationNode> annotations = availableAnnotations.iterator();

            while (annotations.hasNext()) {
                AnnotationNode annotation = annotations.next();

                if (annotation.desc.equals("L" + c.getName().replace(".", "/") + ";")) {
                    return true;
                }
            }
        }
        return false;
    }

    
    public void removeAnnotation(Class c, List availableAnnotations) {
        if (availableAnnotations != null) {
            Iterator<AnnotationNode> annotations = availableAnnotations.iterator();

            while (annotations.hasNext()) {
                AnnotationNode annotation = annotations.next();

                if (annotation.desc.equals("L" + c.getName().replace(".", "/") + ";")) {
                    annotations.remove();
                    return;
                }
            }
        }
    }
}