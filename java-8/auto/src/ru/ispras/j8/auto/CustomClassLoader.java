package ru.ispras.j8.auto;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class CustomClassLoader extends ClassLoader {
    private final String PKG_PREFIX = "ru.ispras";

    public CustomClassLoader(ClassLoader parent) {
        super(parent);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
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
        System.out.println("CCL: Finding " + classFile);
        try (InputStream inputStream = getResourceAsStream(classFile)) {
            if (inputStream == null)
                throw new ClassNotFoundException();

            byte[] bytecode = readAllBytes(inputStream);
            return defineClass(name, bytecode, 0, bytecode.length);
        } catch (IOException e) {
            throw new ClassNotFoundException();
        }
    }

    private byte[] readAllBytes(InputStream inputStream) throws IOException {
        int nextValue;
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        while ((nextValue = inputStream.read()) != -1) {
            byteStream.write(nextValue);
        }

        return byteStream.toByteArray();
    }
}
