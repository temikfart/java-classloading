package com.habr.j17.manual.fewmodules.separately.nodeps.loading;

import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        try {
            Path modulePath = Paths.get("../../../loadable/build/libs/loadable-1.0.jar");
            String moduleName = "manual.fewmodules.separately.nodeps.loadable";
            String mainClassName = "com.habr.j17.manual.fewmodules.separately.nodeps.loadable.Main";

            ModuleLayer layer = createLayer(modulePath, moduleName);
            ClassLoader loadableCL = layer.findLoader(moduleName);
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

    private static ModuleLayer createLayer(Path modulePath, String moduleName) {
        ModuleFinder finder = ModuleFinder.of(modulePath);

        ModuleLayer bootLayer = ModuleLayer.boot();
        Configuration parentConfig = bootLayer.configuration();
        Configuration config = parentConfig.resolve(finder, ModuleFinder.of(), Set.of(moduleName));
        return bootLayer.defineModulesWithOneLoader(config, new CustomClassLoader());
    }
}
