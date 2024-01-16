## Java Class Loading examples
---
This repository contains the code for all the examples from the article on Habr. Each example comes with its own README.md.

Each example demonstrates how class loaders are associated with `classpath`/`modulepath`. Examples using Java versions 8 and 17 are considered, where the main difference is the modular system that appeared in Java 9.

Examples:
- Java 8
    - [Manual Loading](java-8/manual)
    - [Auto Loading](java-8/auto)
- Java 17
    - Manual
        - One Module
            - [Java 8 Style](java-17/manual/one-module/java8style)
            - [Java 17 Style](java-17/manual/one-module/java17style)
        - Few Modules
            - [Together](java-17/manual/few-modules/together)
            - Separately
                - [No Dependencies](java-17/manual/few-modules/separately/no-deps)
                - [No Dependencies (hack)](java-17/manual/few-modules/separately/no-deps-hack)
                - [With Dependencies](java-17/manual/few-modules/separately/with-deps)
    - Auto
        - One Module
            - [Java 8 Style](java-17/auto/one-module/java8style)
            - [Java 17 Style](java-17/auto/one-module/java17style)
        - Few Modules
            - [Together](java-17/auto/few-modules/together)

### Used classes
---
Each example uses just a few classes for demonstration purposes. Below are the features of each of them.

#### class `Main`
---
A class with a `main` method, in which, depending on the example, either the simplest classes `Cat` or `Dog` are loaded, or instances of the simplest classes are created. At the end of the method, the `talk` method is called on an instance of the simplest class.

Between instantiation and calling the `talk` method, information about class loaders and/or the name of the modules to which the classes belong is printed.

#### class `CustomClassLoader`
---
A custom class loader that almost completely satisfies the [**delegation model**](https://docs.oracle.com/javase/tutorial/ext/basics/load.html), except that it first tries to load the class , and only then delegate to the parent loader.

Methods:
- `loadClass`:
    1. First, an attempt is made to find a class among the loaded classes;
    2. Then an attempt is made to load the class yourself, if it is from the application;
    3. If the class is not from the application, then the loading is delegated to the ancestor.
- `findClass`: Loads the bytecode of a class (if found) and defines it as a class.

If you try to load a class from the `java.*` package, the JVM will throw `java.lang.SecurityException: Prohibited package name: java.*`. To determine that the class is from an application, it checks that [binary name](https://docs.oracle.com/javase/specs/jls/se17/html/jls-13.html#jls-13.1) begins with `ru.ispras`.

#### class `Cat`
---
The simplest class to load with a single `talk` method that prints the word *"Meow"* to `stdout`.

#### class `Dog`
---
The simplest class to load with a single `talk` method that prints the word *"Woof"* to `stdout`.
