package com.habr.j17.auto.onemodule.j8style;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class CustomClassLoader extends ClassLoader {
    public CustomClassLoader(ClassLoader parent) {
        super(parent);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Class<?> c = findLoadedClass(name);
        if (c != null)
            return c;

        String APP_GROUP = "com.habr";
        if (name.startsWith(APP_GROUP)) {
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
        try (InputStream inputStream = getResourceAsStream(classFile)) {
            if (inputStream == null)
                throw new ClassNotFoundException();

            byte[] bytecode = inputStream.readAllBytes();
            return defineClass(name, bytecode, 0, bytecode.length);
        } catch (IOException e) {
            throw new ClassNotFoundException();
        }
    }
}
