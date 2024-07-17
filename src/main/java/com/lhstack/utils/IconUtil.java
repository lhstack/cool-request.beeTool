package com.lhstack.utils;

import com.intellij.openapi.util.IconLoader;
import com.intellij.util.Icons;

import javax.swing.*;

public class IconUtil {

    public static Icon getIcon(String source) {
        try {
            return IconLoader.findIcon(IconUtil.class.getResource("/" + source));
        } catch (Exception e) {
            return Icons.ADD_ICON;
        }
    }
}
