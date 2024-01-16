## Java 17. Manual Loading (Java 17 style)
---
**Task:** load any application class using a custom class loader (`CustomClassLoader`) in Java 17 style (with `module-info.java`).

### Solution
---
Modules (declared via `module-info.java`):
- `manual.onemodule.j17style`:
    - [`Main`](src/ru/ispras/j17/manual/onemodule/j17style/Main.java) - a class containing the `main` method, where 
      an instance of the `CustomClassLoader` class is created, the `Cat` class is loaded using it, creating an instance of the class `Cat` and calling the `Cat::talk` method;
    - [`Cat`](src/ru/ispras/j17/manual/onemodule/j17style/Cat.java) - loadable class with the `talk` method, which 
      prints the string *"Meow"* to `stdout`;
    - [`CustomClassLoader`](src/ru/ispras/j17/manual/onemodule/j17style/CustomClassLoader.java) - a class that is an 
      implementation of a custom class loader.

### Run
---
> Using `classpath` the results are the same as in [Java 17. Manual Loading (Java 8 style)](../java8style).

Using `modulepath`:

```shell
java17 -p . -m manual.onemodule.j17style
```

Output:

```shell
Main Class Module is module manual.onemodule.j17style
Cat Class Module is unnamed module @f6f4d33
Main Class ClassLoader is jdk.internal.loader.ClassLoaders$AppClassLoader@4554617c
Cat Class ClassLoader is ru.ispras.j17.manual.onemodule.j17style.CustomClassLoader@5acf9800
Meow
```

### Explanation
---
![Java 17. Manual Loading (Java 17 style).jpg](../../../../img/Java%2017.%20Manual%20Loading%20(Java%2017%20style).jpg)

- Regarding [Java 17. Manual Loading (Java 8 style)](../java8style) is different with the module name `manual.onemodule.
  j17style` 
  in the `jar` file, which is anchored with `module-info .java`, so this module is considered an **Application Module**, and not an **Automatic Module**.

### Notes
---
*Same as in [Java 17. Manual Loading (Java 8 style)](../java8style)*.
