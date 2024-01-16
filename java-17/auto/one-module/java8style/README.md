## Java 17. Auto Loading (Java 8 style)
---
**Task:** load application classes using a custom class loader (`CustomClassLoader`) in Java 8 style (without `module-info.java`).

### Solution
---
Project with three classes:
- [`Main`](src/ru/ispras/j17/auto/onemodule/j8style/Main.java) - a class containing a `main` method creating an instance of the `Cat` class and calling the `Cat::talk` method;
- [`Cat`](src/ru/ispras/j17/auto/onemodule/j8style/Cat.java) - loadable class with the `talk` method, which prints the string *"Meow"* to `stdout`;
- [`CustomClassLoader`](src/ru/ispras/j17/auto/onemodule/j8style/CustomClassLoader.java) - a class that is an implementation of a custom class loader.

To change the system loader to a custom `CustomClassLoader` in [documentation of the `ClassLoader.getSystemClassLoader()` method](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/ClassLoader.html#getSystemClassLoader()) it is written that for this it is necessary for the JVM to pass the name of the new *system class loader* through the argument `java.system.class.loader`, and also to define a public constructor with a parameter of type `ClassLoader`, which will be passed as `AppClassLoader` when created.

### Run
---
Using `classpath`:

```shell
java17 -Djava.system.class.loader=ru.ispras.j17.auto.onemodule.j8style.CustomClassLoader \
-cp . ru.ispras.j17.auto.onemodule.j8style.Main
```

or

```shell
java17 -Djava.system.class.loader=ru.ispras.j17.auto.onemodule.j8style.CustomClassLoader \
-jar java8style-1.0.jar
```

Output:

```shell
OpenJDK 64-Bit Server VM warning: Archived non-system classes are disabled because the java.system.class.loader property is specified (value = "ru.ispras.j17.auto.onemodule.j8style.CustomClassLoader"). To use archived non-system classes, this property must not be set

Main Class Module is unnamed module @2c7b84de
Cat Class Module is unnamed module @2c7b84de
System ClassLoader is ru.ispras.j17.auto.onemodule.j8style.CustomClassLoader@355da254
Main Class ClassLoader is ru.ispras.j17.auto.onemodule.j8style.CustomClassLoader@355da254
Cat Class ClassLoader is ru.ispras.j17.auto.onemodule.j8style.CustomClassLoader@355da254
Meow
```

> **Note:**
> `OpenJDK 64-Bit Server VM warning` occurs because the system loader was replaced and, as a result, archiving of classes using the JVM was disabled. This is the so-called [**Class Data Sharing (CDS)**](https://habr.com/ru/articles/472638/) optimization.

Using `modulepath`:

```shell
java17 -Djava.system.class.loader=ru.ispras.j17.auto.onemodule.j8style.CustomClassLoader \
-p . -m java8style
```

Output:

```shell
Main Class Module is module java8style
Cat Class Module is module java8style
System ClassLoader is ru.ispras.j17.auto.onemodule.j8style.CustomClassLoader@4617c264
Main Class ClassLoader is jdk.internal.loader.ClassLoaders$AppClassLoader@28d93b30
Cat Class ClassLoader is jdk.internal.loader.ClassLoaders$AppClassLoader@28d93b30
Meow
```

### Explanation
---
![Java 17. Auto Loading (Java 8 style).jpg](../../../../img/Java%2017.%20Auto%20Loading%20(Java%208%20style).jpg)

- When a `CustomClassLoader` is created, its parent becomes `jdk.internal.loader.ClassLoaders$AppClassLoader`;
- Since `CustomClassLoader` is a system class loader, all classes to be loaded are first delegated to it, it loads application classes, and the rest is transferred to `jdk.internal.loader.ClassLoaders$AppClassLoader`.
- Both `Main` and `Cat` classes are loaded using `CustomClassLoader` in the case of `classpath`, and are in the same *unnamed* module, which is bound to the loader.
- However, if you run `java8style-1.0.jar`, then using the `-p` and `-m` options, all classes will be loaded into `module java8style`, the system loader has changed, but the classes `Main` and `Cat` are loaded using `AppClassLoader`. I have no answer to the question of why this happened, and my attempts to find out turned out to be a failure. I can only assume that this has something to do with the purpose of class loaders. In the **Module API** there is such an entity as a layer ([ModuleLayer](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/ModuleLayer.html)). The layer contains a map that assigns each module its loader, only this is a special type of loader called `public final class Loader extends SecureClassLoader`. Looking at the code of this class, you can see that this is an unusual class loader and it is closely related to the modular system, and operates on classes that are not available to us, and the class itself is `final`. In connection with this assumption, it is not clear to me how you can write a custom loader that can do everything that the `Loader` class can do in order to become its replacement.

### Notes
---
- If you do not define a public constructor in `CustomClassLoader`, the JVM initialization error will occur:

```
Error occurred during initialization of VM
java.lang.Error: java.lang.NoSuchMethodException: ru.ispras.j8.auto.CustomClassLoader.<init>(java.lang.ClassLoader)
```

- Since the system class loader has been replaced with `CustomClassLoader`, the JVM now refuses to cache loaded classes in memory and issues a warning about this:

```
OpenJDK 64-Bit Server VM warning: Archived non-system classes are disabled because the java.system.class.loader property is specified (value = "com.habr.j8.auto.CustomClassLoader"). To use archived non-system classes, this property must not be set`.
```

There was a disabling of [CDS (Class Data Sharing) optimization](https://habr.com/ru/articles/472638/) in the JVM.