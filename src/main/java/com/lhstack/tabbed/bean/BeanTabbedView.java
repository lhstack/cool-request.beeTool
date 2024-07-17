package com.lhstack.tabbed.bean;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.project.Project;
import com.lhstack.components.MultiLanguageTextField;
import com.lhstack.tabbed.TabbedView;

import javax.swing.*;

public class BeanTabbedView implements TabbedView {
    private Project project;

    @Override
    public String getTitle() {
        return "Bean";
    }

    @Override
    public void project(Project project) {
        this.project = project;
    }

    @Override
    public JPanel getPanel() {
        return new MultiLanguageTextField(JavaFileType.INSTANCE, project, "测试");
    }
}
