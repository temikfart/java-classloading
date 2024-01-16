## Java 17. Manual Loading (loading and loadable with dependency)
---
**Task:** load any application class from another module (modules are NOT linked through `module-info.java`) using a custom class loader (`CustomClassLoader`). A loadable class module has a dependency described via `module-info.java`.

### Solution
---
Modules (declared via `module-info.java`):
- `manual.fewmodules.separately.withdeps.loading`:
    - [`Main`](loading/src/ru/ispras/j17/manual/fewmodules/separately/nodeps/loading/Main.java) - a class containing the `main` method, where an instance of the `CustomClassLoader` class is created, the `Cat` class is loaded using it, creating an instance of the class `Cat` and calling the `Cat::talk` method;
    - [`CustomClassLoader`](loading/src/ru/ispras/j17/manual/fewmodules/separately/nodeps/loading/CustomClassLoader.java) - a class that is an implementation of a custom class loader;
- `manual.fewmodules.separately.withdeps.loadable`:
    - [`Cat`](loadable/src/ru/ispras/j17/manual/fewmodules/separately/nodeps/loadable/Cat.java) - loadable class with the `talk` method, which prints the string *"Meow"* to `stdout`;
- `manual.fewmodules.separately.withdeps.dependency`:
    - [Dog](dependency/src/ru/ispras/j17/manual/fewmodules/separately/withdeps/dependency/Dogjava) - used in the module `manual.fewmodules.separately.withdeps.loadable` class with a `talk` method that prints the string *"Woof"* to `stdout`.

### Run
---
Using `modulepath`:

```shell
java17 -p . -m manual.fewmodules.separately.withdeps.loading
```

Output:

```shell
[loading] Main Class Module is module manual.fewmodules.separately.withdeps.loading
[loading] Main Class ClassLoader is jdk.internal.loader.ClassLoaders$AppClassLoader@5c647e05
ClassLoader for [loadable] module is jdk.internal.loader.Loader@30f39991
[loadable] Main Class ClassLoader is jdk.internal.loader.Loader@30f39991
[loadable] Cat Class ClassLoader is jdk.internal.loader.Loader@30f39991
[dependency] Dog Class ClassLoader is jdk.internal.loader.Loader@30f39991
[loadable] Main Class Module is module manual.fewmodules.separately.withdeps.loadable
[loadable] Cat Class Module is module manual.fewmodules.separately.withdeps.loadable
[dependency] Dog Class Module is module manual.fewmodules.separately.withdeps.dependency
Meow
Woof
```

### Explanation
---
![Java 17. Manual Loading (loading and loadable with dependency).jpg](../../../../../img/Java%2017.%20Manual%20Loading%20(loading%20and%20loadable%20with%20dependency).jpg)

*Same as in [Java 17. Manual Loading (dependent and dependency)](https://github.com/temikfart/java-classloading/tree/main/java-17/manual/few-modules/together)*.
