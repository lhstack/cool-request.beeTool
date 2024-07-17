package com.lhstack.tabbed;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.components.JBTabbedPane;
import com.lhstack.utils.Spi;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class ComposeTabbedView extends JPanel {

    private final Project project;

    public ComposeTabbedView(Project project) {
        this.setLayout(new GridLayout(1, 1));
        this.project = project;
        this.init();
    }

    private void init() {
        JBTabbedPane tabbedPane = new JBTabbedPane();
        try {
            List<TabbedView> tabbedViews = Spi.getInstances("tabbeds", TabbedView.class);
            tabbedViews.sort(Comparator.comparing(TabbedView::ordered));
            tabbedViews.forEach(tabbedView -> {
                tabbedView.project(project);
                if (tabbedView.getIcon() != null) {
                    tabbedPane.addTab(tabbedView.getTitle(), tabbedView.getIcon(), tabbedView.getPanel());
                } else {
                    tabbedPane.addTab(tabbedView.getTitle(), tabbedView.getPanel());
                }
            });
        } catch (Throwable e) {
            Messages.showInfoMessage(e.getMessage(), "BeanTool编码器初始化失败");
        }
//        GridLayoutPanel beanToJson = new GridLayoutPanel(1, 2).adds(new MultiLanguageTextField(JavaLanguage.INSTANCE, this.project, ""),new MultiLanguageTextField(JsonLanguage.INSTANCE, this.project, ""));
//        GridLayoutPanel jsonToBean = new GridLayoutPanel(1, 2).adds(new MultiLanguageTextField(JsonLanguage.INSTANCE, this.project, ""),new MultiLanguageTextField(JavaLanguage.INSTANCE, this.project, ""));
//        tabbedPane.addTab("BeanToJson", beanToJson);
//        tabbedPane.addTab("JsonToBean", jsonToBean);
        this.add(tabbedPane);
    }

    /**
     * 触发show
     *
     * @param project
     */
    public void triggerShow(Project project) {

    }

    /**
     * 触发close
     *
     * @param project
     */
    public void triggerClose(Project project) {

    }
}
