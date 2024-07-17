package com.lhstack.tabbed;

import com.intellij.openapi.project.Project;

import javax.swing.*;

public interface TabbedView {

    String getTitle();

    JPanel getPanel();

    default Icon getIcon(){
        return null;
    }

    default int ordered(){
        return 0;
    }

    default void project(Project project){

    }
}
