## Java 17. Auto Loading (Java 17 style)
---
**Task:** load application classes using a custom class loader (`CustomClassLoader`) in Java 8 style (with `module-info.java`).

### Solution
---
Modules (declared via `module-info.java`):
- `auto.onemodule.j17style`:
    - [`Main`](src/ru/ispras/j17/auto/onemodule/j8style/Main.java) - a class containing a `main` method creating an instance of the `Cat` class and calling the `Cat::talk` method;
    - [`Cat`](src/ru/ispras/j17/auto/onemodule/j8style/Cat.java) - loadable class with the `talk` method, which prints the string *"Meow"* to `stdout`;
    - [`CustomClassLoader`](src/ru/ispras/j17/auto/onemodule/j8style/CustomClassLoader.java) - a class that is an implementation of a custom class loader.

To change the system loader to a custom `CustomClassLoader` in [documentation of the `ClassLoader.getSystemClassLoader()` method](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/ClassLoader.html#getSystemClassLoader()) it is written that for this it is necessary for the JVM to pass the name of the new *system class loader* through the argument `java.system.class.loader`, and also to define a public constructor with a parameter of type `ClassLoader`, which will be passed as `AppClassLoader` when created.

### Run
---
> Using `classpath` the results are the same as in [[Java 17. Auto Loading (Java 8 style)]].

Using `modulepath`:

```shell
java17 -Djava.system.class.loader=ru.ispras.j17.auto.onemodule.j17style.CustomClassLoader \
-p . -m auto.onemodule.j17style
```

Output:

```shell
Error occurred during initialization of VM
java.lang.Error: class java.lang.ClassLoader (in module java.base) cannot access class ru.ispras.j17.auto.onemodule.j17style.CustomClassLoader (in module auto.onemodule.j17style) because module auto.onemodule.j17style does not export ru.ispras.j17.auto.onemodule.j17style to module java.base
```

We get an error because the `java.base` module cannot access the `CustomClassLoader` class. Solved by simply exporting the package to `module-info.java`:

```java
// module-info.java
module auto.onemodule.j17style {
    exports ru.ispras.j17.auto.onemodule.j17style to java.base;
}
```

New output:

```shell
Main Class Module is module auto.onemodule.j17style
Cat Class Module is module auto.onemodule.j17style
System ClassLoader is ru.ispras.j17.auto.onemodule.j17style.CustomClassLoader@76ed5528
Main Class ClassLoader is jdk.internal.loader.ClassLoaders$AppClassLoader@4554617c
Cat Class ClassLoader is jdk.internal.loader.ClassLoaders$AppClassLoader@4554617c
Meow
```

### Explanation
---
![Java 17. Auto Loading (Java 17 style).jpg](../../../../img/Java%2017.%20Auto%20Loading%20(Java%2017%20style).jpg)

- Regarding [Java 17. Auto Loading (Java 8 style)](../java8style) is different with the 
  module name `manual.onemodule.j17style` in the `jar` file, which is anchored with `module-info .java`, so this module is considered an **Application Module**, and not an **Automatic Module**.

### Notes
---
*Same as in [Java 17. Auto Loading (Java 8 style)](../java8style)*.