package com.lhstack.utils;

import com.lhstack.common.GlobalEnvironment;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18nUtils {

    public static String getString(String key) {
        Locale locale = Locale.forLanguageTag(GlobalEnvironment.getInstance().getLanguage());
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", locale);
        return resourceBundle.getString(key);
    }
}
