## Java 17. Manual Loading (loading and loadable)
---
**Task:** load any application class using a custom class loader (`CustomClassLoader`) from another module (the modules are NOT linked through `module-info.java`).

### Solution
---
Modules (declared via `module-info.java`):
- `manual.fewmodules.separately.nodeps.loading`:
    - [`Main`](loading/src/ru/ispras/j17/manual/fewmodules/separately/nodeps/loading/Main.java) - a class 
      containing the `main` method, where an instance of the `CustomClassLoader` class is created, the `Cat` class is loaded using it, creating an instance of the class `Cat` and calling the `Cat::talk` method;
    - [`CustomClassLoader`](loading/src/ru/ispras/j17/manual/fewmodules/separately/nodeps/loading/CustomClassLoader.java) - a class that is an implementation of a custom class loader;
- `manual.fewmodules.separately.nodeps.loadable`:
    - [`Cat`](loadable/src/ru/ispras/j17/manual/fewmodules/separately/nodeps/loadable/Cat.java) - loadable class with 
      the `talk` method, which prints the string *"Meow"* to `stdout`.

### Run
---
Using `modulepath`:

```shell
java17 -p loading-1.0.jar -m manual.fewmodules.separately.nodeps.loading
```

Output:

```shell
[loading] Main Class Module is module manual.fewmodules.separately.nodeps.loading
[loading] Main Class ClassLoader is jdk.internal.loader.ClassLoaders$AppClassLoader@4554617c
ClassLoader for [loadable] module is jdk.internal.loader.Loader@4a574795
[loadable] Main Class ClassLoader is jdk.internal.loader.Loader@4a574795
[loadable] Cat Class ClassLoader is jdk.internal.loader.Loader@4a574795
[loadable] Main Class Module is module manual.fewmodules.separately.nodeps.loadable
[loadable] Cat Class Module is module manual.fewmodules.separately.nodeps.loadable
Meow
```

### Explanation
---
![Java 17. Manual Loading (loading and loadable).jpg](../../../../../img/Java%2017.%20Manual%20Loading%20(loading%20and%20loadable).jpg)

The class modules are as we wanted, but the `Cat` class loader turned out to be not `CustomClassLoader` as we expected, but some `Loader`. The fact is that in the `defineModuleWithOneLoader` method, a new `Loader` loader is created (the same one that I mentioned in the **Java 8: Auto Loading** example), the parent of which becomes the passed `CustomClassLoader`.

```java
private static ModuleLayer createLayer(Path modulePath, String moduleName) {
    /* ... */
    return bootLayer.defineModulesWithOneLoader(config, new CustomClassLoader());
}
```

Indeed, in [documentation](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/ModuleLayer.html#defineModulesWithOneLoader(java.lang.module.Configuration,java.util.List,java.lang.ClassLoader):~:text=of%20parent%20layers%20in%20search%20order-,parentLoader,-%2D%20The%20parent%20class%20loader%20for%20the) so and it is written:

```java
public ModuleLayer defineModulesWithOneLoader(Configuration cf, ClassLoader parentLoader)
```

> `parentLoader` - The parent class loader for the class loader created by this method; may be `null` for the 
> bootstrap class loader.

Thus, we do not have the opportunity to somehow pass our own class loader to the layer; we can only indicate the parent for the newly created one. Moreover, according to the same documentation, class loading will be delegated to the parent only if `Loader` does not find the package in which the class is located, here you can come up with different hacks based on this delegation model.

### Notes
---
- You can rewrite the code a little and add a hack that will force `Loader` to delegate class loading to a custom class loader: [Java 17. Manual Loading (loading and loadable) - hack](https://github.com/temikfart/java-classloading/tree/main/java-17/manual/few-modules/separately/no-deps-hack)