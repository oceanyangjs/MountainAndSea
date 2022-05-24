package com.mountain.sea.core.utils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/23 15:41
 */
public class ClassUtils {
    public ClassUtils() {
    }

    public static List<Class<?>> getAllAssignedClass(Class<?> superClass, String pk) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList();
        String path = pk.replace(".", "/");
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL url = classloader.getResource(path);
        if (url == null) {
            return classes;
        } else {
            Iterator var6 = getAllClasses(new File(url.getFile())).iterator();

            while(var6.hasNext()) {
                Class<?> c = (Class)var6.next();
                if (superClass.isAssignableFrom(c) && !superClass.equals(c)) {
                    classes.add(c);
                }
            }

            return classes;
        }
    }

    private static List<Class<?>> getAllClasses(File dir) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList();
        if (dir != null && dir.exists() && dir.listFiles() != null) {
            File[] var2 = dir.listFiles();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                File file = var2[var4];
                if (file.isDirectory()) {
                    classes.addAll(getAllClasses(file));
                }

                String fileName = file.getName();
                if (fileName.endsWith(".class")) {
                    String path = file.getPath().split("target\\\\classes\\\\")[1];
                    path = path.replace("\\", ".");
                    path = path.substring(0, path.length() - 6);
                    classes.add(Class.forName(path));
                }
            }

            return classes;
        } else {
            return classes;
        }
    }
}
