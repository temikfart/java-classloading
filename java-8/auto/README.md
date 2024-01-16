## Java 8. Auto Loading
---
**Task:** load application classes using a custom class loader (`CustomClassLoader`).

### Solution
---
Project with three classes:
- [`Main`](src/ru/ispras/j8/auto/Main.java) - a class containing a `main` method creating an instance of the `Cat` class and calling the `Cat::talk` method;
- [`Cat`](src/ru/ispras/j8/auto/Cat.java) - loadable class with the `talk` method, which prints the string *"Meow"* to `stdout`;
- [`CustomClassLoader`](src/ru/ispras/j8/auto/CustomClassLoader.java) - a class that is an implementation of a custom class loader.

To change the system loader to a custom `CustomClassLoader` in [documentation of the `ClassLoader.getSystemClassLoader()` method](https://docs.oracle.com/javase/8/docs/api/java/lang/ClassLoader.html#getSystemClassLoader--) it is written that for this it is necessary for the JVM to pass the name of the new *system class loader* through the argument `java.system.class.loader`, and also to define a public constructor with a parameter of type `ClassLoader`, which will be passed as `AppClassLoader` when created.

### Run
---
```shell
java8 -Djava.system.class.loader=ru.ispras.j8.auto.CustomClassLoader -cp . ru.ispras.j8.auto.Main
```

or

```shell
java8 -Djava.system.class.loader=com.habr.j8.auto.CustomClassLoader -jar auto-1.0.jar
```

Output:

```
System ClassLoader is ru.ispras.j8.auto.CustomClassLoader@75b84c92
Main Class ClassLoader is ru.ispras.j8.auto.CustomClassLoader@75b84c92
Cat Class ClassLoader is ru.ispras.j8.auto.CustomClassLoader@75b84c92
Meow
```

### Explanation
---
![Java 8. Auto Loading.jpg](../../img/Java%208.%20Auto%20Loading.jpg)

- When a `CustomClassLoader` is created, its parent becomes `sun.misc.Launcher$AppClassLoader`;
- Since `CustomClassLoader` is a system class loader, all classes to be loaded are first delegated to it, it loads application classes, and the rest is transferred to `sun.misc.Launcher$AppClassLoader`.

### Notes
---
- If you do not define a public constructor in `CustomClassLoader`, the JVM initialization error will occur:

```
Error occurred during initialization of VM
java.lang.Error: java.lang.NoSuchMethodException: ru.ispras.j8.auto.CustomClassLoader.<init>(java.lang.ClassLoader)
```
