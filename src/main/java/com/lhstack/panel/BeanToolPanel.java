package com.lhstack.panel;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.lhstack.tabbed.ComposeTabbedView;
import dev.coolrequest.tool.CoolToolPanel;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class BeanToolPanel implements CoolToolPanel {


    private Project project;


    private final Map<String, ComposeTabbedView> tabbedViews = new HashMap<>();

    public BeanToolPanel() {

    }

    @Override
    public JPanel createPanel() {
        try {
            return tabbedViews.computeIfAbsent(project.getLocationHash(), k -> new ComposeTabbedView(project));
        } catch (Throwable e) {
            Messages.showErrorDialog(e.getMessage(), "启动失败");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void showTool() {
        ComposeTabbedView composeTabbedView = tabbedViews.get(project.getLocationHash());
        if (composeTabbedView != null) {
            composeTabbedView.triggerShow(project);
        }
    }

    @Override
    public void closeTool() {
        ComposeTabbedView composeTabbedView = tabbedViews.get(project.getLocationHash());
        if (composeTabbedView != null) {
            composeTabbedView.triggerClose(project);
        }
    }
}
