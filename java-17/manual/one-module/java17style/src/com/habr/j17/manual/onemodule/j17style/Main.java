package com.habr.j17.manual.onemodule.j17style;

import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        ClassLoader customClassLoader = new CustomClassLoader();
        try {
            String catClassName = "com.habr.j17.manual.onemodule.j17style.Cat";
            Class<?> catClass = customClassLoader.loadClass(catClassName);
            Object cat = catClass.getDeclaredConstructor().newInstance();

            System.out.println("Main Class Module is " + Main.class.getModule());
            System.out.println("Cat Class Module is " + catClass.getModule());

            System.out.println("Main Class ClassLoader is " + Main.class.getClassLoader());
            System.out.println("Cat Class ClassLoader is " + catClass.getClassLoader());

            Method talkMethod = catClass.getMethod("talk");
            talkMethod.invoke(cat);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
