package ru.ispras.j17.auto.onemodule.j17style;

public class Main {
    public static void main(String[] args) {
        Cat cat = new Cat();

        System.out.println("Main Class Module is " + Main.class.getModule());
        System.out.println("Cat Class Module is " + Cat.class.getModule());

        System.out.println("System ClassLoader is " + ClassLoader.getSystemClassLoader());
        System.out.println("Main Class ClassLoader is " + Main.class.getClassLoader());
        System.out.println("Cat Class ClassLoader is " + Cat.class.getClassLoader());

        cat.talk();
    }
}
