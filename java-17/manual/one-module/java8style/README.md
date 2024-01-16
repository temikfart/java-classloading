## Java 17. Manual Loading (Java 8 style)
---
**Task:** load any application class using a custom class loader (`CustomClassLoader`) in Java 8 style (without `module-info.java`).

### Solution
---
Project with three classes:
- [`Main`](src/ru/ispras/j17/manual/onemodule/j8style/Main.java) - a class containing the `main` method, where an instance of the `CustomClassLoader` class is created, the `Cat` class is loaded using it, creating an instance of the class `Cat` and calling the `Cat::talk` method;
- [`Cat`](src/ru/ispras/j17/manual/onemodule/j8style/Cat.java) - loadable class with the `talk` method, which prints the string *"Meow"* to `stdout`;
- [`CustomClassLoader`](src/ru/ispras/j17/manual/onemodule/j8style/CustomClassLoader.java) - a class that is an implementation of a custom class loader.

### Run
---
Using `classpath`:

```shell
java17 -cp . ru.ispras.j17.manual.onemodule.j8style.Main
```

or

```shell
java17 -jar java8style-1.0.jar
```

Output:

```
Main Class Module is unnamed module @99e937b
Cat Class Module is unnamed module @6d6f6e28
Main Class ClassLoader is jdk.internal.loader.ClassLoaders$AppClassLoader@531d72ca
Cat Class ClassLoader is ru.ispras.j17.manual.onemodule.j8style.CustomClassLoader@15db974
Meow
```

Using `modulepath`:

```shell
java17 -p . -m java8style
```

Output:

```shell
Main Class Module is module java8style
Cat Class Module is unnamed module @3a71f4dd
Main Class ClassLoader is jdk.internal.loader.ClassLoaders$AppClassLoader@28d93b30
Cat Class ClassLoader is ru.ispras.j17.manual.onemodule.j8style.CustomClassLoader@5ca881b5
Meow
```

### Explanation
---
![Java 17. Manual Loading (Java 8 style).jpg](../../../../img/Java%2017.%20Manual%20Loading%20(Java%208%20style).jpg)

- When a `CustomClassLoader` is created, its parent becomes `jdk.internal.loader.ClassLoaders$AppClassLoader`;
- All classes will be under `unnamed module` if launched using `classpath`. Experimentally, it was possible to find out that each class loader is related to two modules: firstly, as a class, it refers to the class loader module that loaded this class, and secondly, it has its own *unnamed* module (which can be obtained using the method `getUnnamedModule`), which will contain all the classes that it will load on its own. Therefore, in this example, two *unnamed* modules appear: the first is loaded by the system loader and the `Main` class is located there, and the second is loaded by the `CustomClassLoader` loader, where the `Cat` class is already located.
- If we run the `java8style-1.0.jar` file, then using the `-p` and `-m` options (using `modulepath`), we will get an **Automatic Module** with the name `java8style`, which is implicitly obtained by the algorithm from [documentation for `ModuleFinder`](https://docs.oracle.com/javase/9/docs/api/java/lang/module/ModuleFinder.html#of-java.nio.file.Path...-).

### Notes
---
- `CustomClassLoader` gets some system classes to be loaded first, for example classes from `java.*` packages. If you try to load a class from the `java.*` package, the JVM will throw `java.lang.SecurityException: Prohibited package name: java.*`;