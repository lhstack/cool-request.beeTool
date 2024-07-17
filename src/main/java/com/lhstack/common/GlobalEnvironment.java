package com.lhstack.common;

import java.util.Locale;

public class GlobalEnvironment {

    private String language = Locale.CHINESE.getLanguage();

    private static final GlobalEnvironment INSTANCE = new GlobalEnvironment();

    public static GlobalEnvironment getInstance(){
        return INSTANCE;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
