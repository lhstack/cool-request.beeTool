package com.lhstack.utils;

import com.lhstack.tabbed.encoding.actions.BaseEncodingAction;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Spi {

    public static <T> List<T> scan(Predicate<Class<?>> predicate, Class<T> classType, String... basePackages) {
        ClassLoader classLoader = Spi.class.getClassLoader();
        return Stream.of(basePackages).flatMap(basePackage -> {
            List<Class<?>> classes = new ArrayList<>();
            try {
                String dir = basePackage.replaceAll("\\.", "/");
                Enumeration<URL> resources = classLoader.getResources(dir);
                while (resources.hasMoreElements()) {
                    URL url = resources.nextElement();
                    URLConnection urlConnection = url.openConnection();
                    if (urlConnection instanceof JarURLConnection) {
                        JarURLConnection jarURLConnection = (JarURLConnection) urlConnection;
                        Enumeration<JarEntry> entries = jarURLConnection.getJarFile().entries();
                        while (entries.hasMoreElements()) {
                            JarEntry jarEntry = entries.nextElement();
                            String entryName = jarEntry.getName();
                            if (StringUtils.endsWith(entryName, ".class")) {
                                entryName = entryName.substring(0, entryName.lastIndexOf("."));
                                String className = entryName.replaceAll("/", ".");
                                try {
                                    if(StringUtils.startsWith(className,basePackage)){
                                        Class<?> clazz = Class.forName(className);
                                        if (predicate.test(clazz)) {
                                            classes.add(clazz);
                                        }
                                    }
                                } catch (Throwable ignore) {

                                }
                            }
                        }
                    }
                }
            } catch (Throwable ignore) {

            }
            return classes.stream();
        }).map(clazz -> {
            try {
                return clazz.getConstructor().newInstance();
            } catch (Throwable ignore) {
                return null;
            }
        }).filter(Objects::nonNull).map(classType::cast).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        System.out.println(BaseEncodingAction.class.getSimpleName());
    }

    public static <T> List<T> getInstances(String source, Class<T> clazz) throws Throwable {
        InputStream in = Spi.class.getModule().getResourceAsStream(source);
        if (in != null) {
            List<T> instances = new BufferedReader(new InputStreamReader(in)).lines().map(item -> {
                try {
                    return Class.forName(item).getConstructor().newInstance();
                } catch (Throwable e) {
                    return null;
                }
            }).filter(Objects::nonNull).map(clazz::cast).collect(Collectors.toList());
            in.close();
            return instances;
        }
        return Collections.emptyList();
    }
}
