package com.habr.j17.auto.onemodule.j8style;

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
