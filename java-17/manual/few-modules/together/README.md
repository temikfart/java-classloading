## Java 17. Manual Loading (dependent and dependency)
---
**Task:** load any application class using a custom class loader (`CustomClassLoader`) from another module (the modules are linked via `module-info.java`).

### Solution
---
Modules (declared via `module-info.java`):
- `manual.fewmodules.together.dependent`:
    - [`Main`](dependent/src/ru/ispras/j17/manual/fewmodules/together/dependent/Main.java) - a class containing the `main` method, where an instance of the `CustomClassLoader` class is created, the `Cat` class is loaded using it, creating an instance of the class `Cat` and calling the `Cat::talk` method;
    - [`CustomClassLoader`](dependent/src/ru/ispras/j17/manual/fewmodules/together/dependent/CustomClassLoader.java) - a class that is an implementation of a custom class loader;
- `manual.fewmodules.together.dependency`:
    - [`Cat`](dependency/src/ru/ispras/j17/manual/fewmodules/together/dependency/Cat.java) - loadable class with the `talk` method, which prints the string *"Meow"* to `stdout`.

### Run
---
Using `modulepath`:

```shell
java17 -p dependent-1.0.jar:dependency-1.0.jar -m manual.fewmodules.together.dependent
```

Output:

```
Main Class Module is module manual.fewmodules.together.dependent
Cat Class Module is unnamed module @23fc625e
Main Class ClassLoader is jdk.internal.loader.ClassLoaders$AppClassLoader@42a57993
Cat Class ClassLoader is ru.ispras.j17.manual.fewmodules.together.dependent.CustomClassLoader@3fee733d
Meow
```

### Explanation
---
![Java 17. Manual Loading (dependent and dependency).jpg](../../../../img/Java%2017.%20Manual%20Loading%20(dependent%20and%20dependency).jpg)

*Same as in [[Java 17. Manual Loading (Java 17 style)]]*.

### Notes
---
*Same as in [[Java 17. Manual Loading (Java 17 style)]]*.

- In `module-info.java` of the `dependency` module you need to export the package with the `Cat` class, and in `module-info.java` of the `dependent` module you need to specify the dependency on the `dependency` module:

```java
// dependency/src/module-info.java
module manual.fewmodules.together.depedency {
    exports ru.ispras.j17.manual.fewmodules.together.dependency;
}
```

```java
// dependent/src/module-info.java
module manual.fewmodules.together.dependent {
    requires manual.fewmodules.together.dependency;
}
```
