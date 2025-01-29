package dev.librry.utils;

import lombok.Data;



import java.util.*;


@Data
public class ClassCollector {

    private final List<ClassMap> classes;

    private String mainClass;

    public ClassCollector() {
        this.classes = new ArrayList<>();
    }

    
    public void addClass(ClassMap classMap) {
        this.classes.add(classMap);
    }

  
    public ClassMap getClassMap(String className) throws ClassMapNotFoundException {
        final Optional<ClassMap> classMap =
                classes.stream().filter(c -> c.getClassName().equals(className)).findFirst();

        if (classMap.isPresent()) {
            return classMap.get();
        } else {
            throw new ClassMapNotFoundException();
        }
    }

    
    public ClassMap getParent(ClassMap classMap) throws ClassMapNotFoundException {
        final String parentClassName = classMap.getParent();

        Optional<ClassMap> optional = classes.stream()
                .filter(clazz -> parentClassName.equals(clazz.getClassName()))
                .findAny();

        if (optional.isPresent())
            return optional.get();
        else
            throw new ClassMapNotFoundException(classMap.getParent());
    }

   
    public Map<MethodObj, MethodObj> getOverriddenMethods(ClassMap parentClass, ClassMap childClass) {
        final Map<MethodObj, MethodObj> methods = new HashMap<>();

        parentClass.getMethods().stream()
                .filter(MethodObj::isSafeMethod)
                .forEach(parentMethod -> {
                    for (MethodObj childMethod : childClass.getMethods()) {
                        if (childMethod.isSafeMethod() && childMethod.getMethod().equals(parentMethod.getMethod())) {
                            methods.put(parentMethod, childMethod);
                            break;
                        }
                    }
                });

        return methods;
    }

}