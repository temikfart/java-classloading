package com.habr.j8.auto;

public class Main {
    public static void main(String[] args) {
        Cat cat = new Cat();
        System.out.println("System ClassLoader is " + ClassLoader.getSystemClassLoader());
        System.out.println("Cat Class ClassLoader is " + cat.getClass().getClassLoader());
        cat.talk();
    }
}
