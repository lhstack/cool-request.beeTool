package com.lhstack.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Spi {

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
