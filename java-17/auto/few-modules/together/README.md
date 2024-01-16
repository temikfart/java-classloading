## Java 17. Auto Loading (dependent and dependency)
---
**Task:** load the classes of an application consisting of two modules (the modules are linked via `module-info.java`) using a custom class loader (`CustomClassLoader`).

### Solution
---
Modules (declared via `module-info.java`):
- `manual.fewmodules.together.dependent`:
    - [`Main`](dependent/src/ru/ispras/j17/auto/fewmodules/together/dependent/Main.java) - a class containing a `main` 
      method creating an instance of the `Cat` class and calling the `Cat::talk` method;
    - [`CustomClassLoader`](dependent/src/ru/ispras/j17/auto/fewmodules/together/dependent/CustomClassLoader.java) - a 
      class that is an implementation of a custom class loader;
- `manual.fewmodules.together.dependency`:
    - [`Cat`](dependency/src/ru/ispras/j17/auto/fewmodules/together/dependency/Cat.java) - loadable class with the 
      `talk` method, which prints the string *"Meow"* to `stdout`.

To change the system loader to a custom `CustomClassLoader` in [documentation of the `ClassLoader.getSystemClassLoader()` method](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/ClassLoader.html#getSystemClassLoader()) it is written that for this it is necessary for the JVM to pass the name of the new *system class loader* through the argument `java.system.class.loader`, and also to define a public constructor with a parameter of type `ClassLoader`, which will be passed as `AppClassLoader` when created.

### Run
---
Using `modulepath`:

```shell
java17 -Djava.system.class.loader=ru.ispras.j17.auto.fewmodules.together.dependent.CustomClassLoader \
-p dependent-1.0.jar:dependency-1.0.jar -m auto.fewmodules.together.dependent
```

Output:

```shell
Main Class Module is module auto.fewmodules.together.dependent
Cat Class Module is module auto.fewmodules.together.dependency
System ClassLoader is ru.ispras.j17.auto.fewmodules.together.dependent.CustomClassLoader@5a07e868
Main Class ClassLoader is jdk.internal.loader.ClassLoaders$AppClassLoader@42a57993
Cat Class ClassLoader is jdk.internal.loader.ClassLoaders$AppClassLoader@42a57993
Meow
```

### Explanation
---
![Java 17. Auto Loading (dependent and dependency).jpg](../../../../img/Java%2017.%20Auto%20Loading%20(dependent%20and%20dependency).jpg)

- Regarding [Java 17. Auto Loading (Java 17 style)](../../one-module/java17style) the only difference is that there are two 
  modules that 
  are 
  loaded using `AppClassLoader`.

### Notes
---
*Same as in [Java 17. Auto Loading (Java 17 style)](../../one-module/java17style)*.

- In `module-info.java` of the `dependency` module you need to export the package with the `Cat` class, and in `module-info.java` of the `dependent` module, specify the dependency on the `dependency` module and export the custom class loader `CustomClassLoader`:

```java
// dependency/src/module-info.java
module auto.fewmodules.together.dependency {
    exports ru.ispras.j17.auto.fewmodules.together.dependency;
}
```

```java
// dependent/src/module-info.java
module.auto.fewmodules.together.dependent {
    requires auto.fewmodules.together.dependency:

    exports ru.ispras.j17.auto.fewmodules.together.dependent to java.base;
}
```
