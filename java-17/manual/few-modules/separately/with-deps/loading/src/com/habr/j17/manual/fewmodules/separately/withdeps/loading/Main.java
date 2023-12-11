package com.habr.j17.manual.fewmodules.separately.withdeps.loading;

import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        try {
            Path loadableModulePath = Paths.get("../../../loadable/build/libs/loadable-1.0.jar");
            String loadableModuleName = "manual.fewmodules.separately.withdeps.loadable";
            Path dependencyModulePath = Paths.get("../../../dependency/build/libs/dependency-1.0.jar");
            String dependencyModuleName = "manual.fewmodules.separately.withdeps.dependency";

            String mainClassName = "com.habr.j17.manual.fewmodules.separately.withdeps.loadable.Main";

            ModuleLayer layer = createLayer(
                    loadableModulePath, loadableModuleName,
                    dependencyModulePath, dependencyModuleName
            );
            ClassLoader loadableCL = layer.findLoader(loadableModuleName);
            Class<?> mainClass = loadableCL.loadClass(mainClassName);

            System.out.println("[loading] Main Class Module is " + Main.class.getModule());
            System.out.println("[loading] Main Class ClassLoader is " + Main.class.getClassLoader());
            System.out.println("ClassLoader for [loadable] module is " + loadableCL);

            Method mainMethod = mainClass.getMethod("main", String[].class);
            mainMethod.invoke(null, (Object) args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static ModuleLayer createLayer(Path mp1, String mn1, Path mp2, String mn2) {
        ModuleFinder finder = ModuleFinder.of(mp1, mp2);

        ModuleLayer bootLayer = ModuleLayer.boot();
        Configuration parentConfig = bootLayer.configuration();
        Configuration config = parentConfig.resolve(finder, ModuleFinder.of(), Set.of(mn1, mn2));
        return bootLayer.defineModulesWithOneLoader(config, new CustomClassLoader());
    }
}
