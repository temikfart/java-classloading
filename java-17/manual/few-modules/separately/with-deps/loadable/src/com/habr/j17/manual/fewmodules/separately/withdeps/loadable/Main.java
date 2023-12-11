package com.habr.j17.manual.fewmodules.separately.withdeps.loadable;

import com.habr.j17.manual.fewmodules.separately.withdeps.dependency.Dog;

public class Main {
    public static void main(String[] args) {
        Cat cat = new Cat();
        Dog dog = new Dog();

        System.out.println("[loadable] Main Class ClassLoader is " + Main.class.getClassLoader());
        System.out.println("[loadable] Cat Class ClassLoader is " + Cat.class.getClassLoader());
        System.out.println("[dependency] Dog Class ClassLoader is " + Dog.class.getClassLoader());

        System.out.println("[loadable] Main Class Module is " + Main.class.getModule());
        System.out.println("[loadable] Cat Class Module is " + Cat.class.getModule());
        System.out.println("[dependency] Dog Class Module is " + Dog.class.getModule());

        cat.talk();
        dog.talk();
    }
}
