package ru.ispras.j17.manual.fewmodules.separately.nodeps.hack.loading;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class CustomClassLoader extends ClassLoader {
    private final String PKG_PREFIX = "ru.ispras";
    private Module module = null;

    public void setModule(Module module) {
        this.module = module;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (name.startsWith("ispras"))
            name = "ru." + name;
        Class<?> c = findLoadedClass(name);
        if (c != null)
            return c;

        if (name.startsWith(PKG_PREFIX)) {
            c = findClass(name);
            if (c != null) {
                System.out.println("CCL: Loading " + name);
                return c;
            }
        }

        System.out.println("CCL: Delegating " + name);
        return super.loadClass(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String classFile = name.replace('.', File.separatorChar) + ".class";
        System.out.println("CCL: Finding " + name);
        try (InputStream inputStream = module.getResourceAsStream(classFile)) {
            if (inputStream == null)
                throw new ClassNotFoundException();

            byte[] bytecode = inputStream.readAllBytes();
            return defineClass(name, bytecode, 0, bytecode.length);
        } catch (IOException e) {
            throw new ClassNotFoundException();
        }
    }
}
