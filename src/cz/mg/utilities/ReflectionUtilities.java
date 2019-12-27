package cz.mg.utilities;

import cz.mg.collections.Collection;
import cz.mg.collections.list.chainlist.ChainList;

import java.lang.reflect.*;


public class ReflectionUtilities {
    public static Collection<Field> getObjectFields(Object o){
        if(o == null) return getClassFields(null);
        return getClassFields(o.getClass());
    }

    public static Collection<Method> getObjectMethods(Object o){
        if(o == null) return getClassMethods(null);
        return getClassMethods(o.getClass());
    }

    public static Collection<Field> getClassFields(Class c){
        ChainList<Field> allFields = new ChainList<>();
        for (Class cc = c; cc != null; cc = cc.getSuperclass()) {
            ChainList<Field> someFields = new ChainList<>();
            for (Field field : cc.getDeclaredFields()) {
                if(!Modifier.isStatic(field.getModifiers())){
                    field.setAccessible(true);
                    someFields.addLast(field);
                }
            }
            allFields.addCollectionFirst(someFields);
        }
        return allFields;
    }

    public static Collection<Field> getStaticClassFields(Class c){
        ChainList<Field> allFields = new ChainList<>();
        for (Class cc = c; cc != null; cc = cc.getSuperclass()) {
            ChainList<Field> someFields = new ChainList<>();
            for (Field field : cc.getDeclaredFields()) {
                if(Modifier.isStatic(field.getModifiers())){
                    field.setAccessible(true);
                    someFields.addLast(field);
                }
            }
            allFields.addCollectionFirst(someFields);
        }
        return allFields;
    }

    public static Collection<Method> getClassMethods(Class c){
        ChainList<Method> allMethods = new ChainList<>();
        for (Class cc = c; cc != null; cc = cc.getSuperclass()) {
            ChainList<Method> someMethods = new ChainList<>();
            for (Method method : cc.getDeclaredMethods()) {
                if(!Modifier.isStatic(method.getModifiers())){
                    method.setAccessible(true);
                    someMethods.addLast(method);
                }
            }
            allMethods.addCollectionFirst(someMethods);
        }
        return allMethods;
    }

    public static Collection<Method> getStaticClassMethods(Class c){
        ChainList<Method> allMethods = new ChainList<>();
        for (Class cc = c; cc != null; cc = cc.getSuperclass()) {
            ChainList<Method> someMethods = new ChainList<>();
            for (Method method : cc.getDeclaredMethods()) {
                if(Modifier.isStatic(method.getModifiers())){
                    method.setAccessible(true);
                    someMethods.addLast(method);
                }
            }
            allMethods.addCollectionFirst(someMethods);
        }
        return allMethods;
    }

    public static Collection<Constructor> getClassConstructors(Class c){
        ChainList<Constructor> allConstructors = new ChainList<>();
        for (Class cc = c; cc != null; cc = cc.getSuperclass()) {
            ChainList<Constructor> someConstructors = new ChainList<>();
            for (Constructor constructor : cc.getConstructors()) {
                if(!Modifier.isStatic(constructor.getModifiers())){
                    constructor.setAccessible(true);
                    someConstructors.addLast(constructor);
                }
            }
            allConstructors.addCollectionFirst(someConstructors);
        }
        return allConstructors;
    }

    public static boolean objectof(Object object, Class superclass){
        return superclass.isInstance(object);
    }

    public static boolean typeof(Field field, Class superclass){
        return superclass.isAssignableFrom(field.getType());
    }

    public static boolean subclassof(Class subclass, Class superclass){
        return superclass.isAssignableFrom(subclass);
    }

    public static Object readObjectField(Object o, Field field){
        try {
            return field.get(o);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeObjectField(Object o, Field field, Object value){
        try {
            field.set(o, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object callObjectMethod(Object o, Method method, Object... arguments){
        try {
            return method.invoke(o, arguments);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object createObject(Constructor constructor, Object... arguments){
        try {
            return constructor.newInstance(arguments);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public static Constructor findClassConstructor(Class c, Class... parameters){
        try {
            return c.getConstructor(parameters);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public static Method findClassMethod(Class c, String methodName, Class... parameters){
        for(Method method : getClassMethods(c)){
            if(method.getName().equals(methodName)){
                if(matchMethodParameterTypes(method, parameters)) return method;
            }
        }
        throw new RuntimeException("Could not find method \"" + methodName + "\" in class \"" + c.getSimpleName() + "\".");
    }

    public static Field findClassField(Class c, String fieldName){
        for(Field field : getClassFields(c)){
            if(field.getName().equals(fieldName)) return field;
        }
        throw new RuntimeException("Could not find field \"" + fieldName + "\" in class \"" + c.getSimpleName() + "\".");
    }

    public static boolean matchMethodParameterTypes(Method method, Class... parameters){
        Class[] expectedParameters = parameters;
        Class[] actualParameters = method.getParameterTypes();
        if(expectedParameters.length != actualParameters.length) return false;
        for(int i = 0; i < parameters.length; i++){
            if(expectedParameters[i] != actualParameters[i]) return false;
        }
        return true;
    }

    public static boolean matchMethodParameters(Method method, Object... parameters){
        Object[] expectedParameters = parameters;
        Class[] actualParameters = method.getParameterTypes();
        if(expectedParameters.length != actualParameters.length) return false;
        for(int i = 0; i < parameters.length; i++){
            if(expectedParameters == null) return true;
            if(expectedParameters[i].getClass() != actualParameters[i]) return false;
        }
        return true;
    }

    public static boolean matchConstructorParameterTypes(Constructor constructor, Class... parameters){
        Class[] expectedParameters = parameters;
        Class[] actualParameters = constructor.getParameterTypes();
        if(expectedParameters.length != actualParameters.length) return false;
        for(int i = 0; i < parameters.length; i++){
            if(expectedParameters[i] != actualParameters[i]) return false;
        }
        return true;
    }

    public static boolean matchConstructorParameters(Constructor constructor, Object... parameters){
        Object[] expectedParameters = parameters;
        Class[] actualParameters = constructor.getParameterTypes();
        if(expectedParameters.length != actualParameters.length) return false;
        for(int i = 0; i < parameters.length; i++){
            if(expectedParameters == null) return true;
            if(expectedParameters[i].getClass() != actualParameters[i]) return false;
        }
        return true;
    }
}
