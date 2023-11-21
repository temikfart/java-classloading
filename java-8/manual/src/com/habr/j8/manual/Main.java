package com.habr.j8.manual;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        ClassLoader customClassLoader = new CustomClassLoader();
        try {
            Class<?> catClass = customClassLoader.loadClass("com.habr.j8.manual.Cat");
            Object cat = catClass.getDeclaredConstructor().newInstance();

            System.out.println("System ClassLoader is " + ClassLoader.getSystemClassLoader());
            System.out.println("Cat Class ClassLoader is " + catClass.getClassLoader());

            Method talkMethod = catClass.getMethod("talk");
            talkMethod.invoke(cat);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException
                 | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
