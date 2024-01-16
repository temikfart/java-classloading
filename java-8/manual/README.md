## Java 8. Manual Loading
---
**Task:** load any application class using a custom class loader (`CustomClassLoader`).

### Solution
---
Project with three classes:
- [`Main`](src/ru/ispras/j8/manual/Main.java) - a class containing the `main` method, where an instance of the `CustomClassLoader` class is created, the `Cat` class is loaded using it, creating an instance of the class `Cat` and calling the `Cat::talk` method;
- [`Cat`](src/ru/ispras/j8/manual/Cat.java) - loadable class with the `talk` method, which prints the string *"Meow"* to `stdout`;
- [`CustomClassLoader`](src/ru/ispras/j8/manual/CustomClassLoader.java) - a class that is an implementation of a custom class loader.

### Run
---
```shell
java8 -cp . ru.ispras.j8.manual.Main
```

or

```shell
java8 -jar manual-1.0.jar
```

Output:

```
System ClassLoader is ru.ispras.j8.manual.CustomClassLoader@75b84c92
Main Class ClassLoader is ru.ispras.j8.manual.CustomClassLoader@75b84c92
Cat Class ClassLoader is ru.ispras.j8.manual.CustomClassLoader@75b84c92
Meow
```

### Explanation
---
![Java 8. Manual Loading.jpg](../../img/Java%208.%20Manual%20Loading.jpg)

- When a `CustomClassLoader` is created, its parent becomes `sun.misc.Launcher$AppClassLoader`.

### Notes
---
- `CustomClassLoader` gets some system classes to be loaded first, for example classes from `java.*` packages. If you try to load a class from the `java.*` package, the JVM will throw `java.lang.SecurityException: Prohibited package name: java.*`.
