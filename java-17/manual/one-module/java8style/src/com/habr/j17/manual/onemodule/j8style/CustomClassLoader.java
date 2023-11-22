package com.habr.j17.manual.onemodule.j8style;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class CustomClassLoader extends ClassLoader {
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            Class<?> c = findLoadedClass(name);
            if (c != null)
                return c;

            String APP_GROUP = "com.habr";
            if (name.startsWith(APP_GROUP)) {
                System.out.println("CCL: Loading " + name);
                c = loadClassFromFile(name);

                if (c != null)
                    return c;
            }

            System.out.println("CCL: Delegating " + name);
            return super.loadClass(name);
        }
    }

    private Class<?> loadClassFromFile(String name) {
        String classFile = name.replace('.', File.separatorChar) + ".class";
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(classFile)) {
            if (inputStream == null)
                throw new RuntimeException();

            byte[] buffer;
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

            int nextValue;
            while ((nextValue = inputStream.read()) != -1) {
                byteStream.write(nextValue);
            }

            buffer = byteStream.toByteArray();
            return defineClass(name, buffer, 0, buffer.length);
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException("Failed to read from input stream", e);
        }
    }
}
