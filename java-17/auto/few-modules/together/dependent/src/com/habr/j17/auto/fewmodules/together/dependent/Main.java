package com.habr.j17.auto.fewmodules.together.dependent;

import com.habr.j17.auto.fewmodules.together.dependency.Cat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        Cat cat = new Cat();

        System.out.println("System ClassLoader is " + ClassLoader.getSystemClassLoader());
        System.out.println("Cat Class ClassLoader is " + cat.getClass().getClassLoader());

        System.out.println("Main Class Module is " + Main.class.getModule());
        System.out.println("Cat Class Module is " + cat.getClass().getModule());

        cat.talk();
    }
}
