package ru.ispras.j17.manual.fewmodules.separately.nodeps.hack.loadable;

public class Main {
    public static void main(String[] args) {
        Cat cat = new Cat();

        System.out.println("[loadable] Main Class ClassLoader is " + Main.class.getClassLoader());
        System.out.println("[loadable] Cat Class ClassLoader is " + Cat.class.getClassLoader());

        System.out.println("[loadable] Main Class Module is " + Main.class.getModule());
        System.out.println("[loadable] Cat Class Module is " + Cat.class.getModule());

        cat.talk();
    }
}
