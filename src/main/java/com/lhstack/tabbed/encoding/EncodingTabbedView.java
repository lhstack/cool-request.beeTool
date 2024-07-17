package com.lhstack.tabbed.encoding;

import com.intellij.openapi.fileTypes.PlainTextFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.LanguageTextField;
import com.lhstack.components.MultiLanguageTextField;
import com.lhstack.panel.GridLayoutPanel;
import com.lhstack.tabbed.TabbedView;
import com.lhstack.tabbed.encoding.actions.BaseEncodingAction;
import com.lhstack.utils.Spi;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class EncodingTabbedView implements TabbedView {

    private Project project;

    @Override
    public String getTitle() {
        return "数据编码";
    }

    @Override
    public JPanel getPanel() {
        LanguageTextField.SimpleDocumentCreator simpleDocumentCreator = new LanguageTextField.SimpleDocumentCreator();
        MultiLanguageTextField sourceTextField = new MultiLanguageTextField(PlainTextFileType.INSTANCE, project, "", simpleDocumentCreator);
        MultiLanguageTextField targetTextField = new MultiLanguageTextField(PlainTextFileType.INSTANCE, project, "", simpleDocumentCreator);
        targetTextField.setEnabled(false);
        BaseEncodingAction[] comboBoxActions = new BaseEncodingAction[0];
        try {
            List<BaseEncodingAction> actions = Spi.scan(clazz -> {
                return !clazz.getName().equals(BaseEncodingAction.class.getName());
            }, BaseEncodingAction.class,"com.lhstack.tabbed.encoding.actions");
            actions.sort(Comparator.comparing(BaseEncodingAction::ordered));
            comboBoxActions = new BaseEncodingAction[actions.size()];
            for (int i = 0; i < actions.size(); i++) {
                BaseEncodingAction action = actions.get(i);
                action.setSourceDocument(sourceTextField).setTargetDocument(targetTextField).setProject(project);
                comboBoxActions[i] = action;
            }
        } catch (Throwable e) {
            Messages.showInfoMessage(e.getMessage(), "获取编码器视图失败提示");
        }
        JPanel toolBar = getToolBar(comboBoxActions);
        SimpleToolWindowPanel simpleToolWindowPanel = new SimpleToolWindowPanel(true, false);
        simpleToolWindowPanel.setToolbar(toolBar);
        simpleToolWindowPanel.setContent(new GridLayoutPanel(1, 3).adds(sourceTextField, targetTextField));
        return simpleToolWindowPanel;
    }

    private @NotNull JPanel getToolBar(BaseEncodingAction[] comboBoxActions) {
        AtomicReference<BaseEncodingAction> selectAction = new AtomicReference<>(comboBoxActions[0]);
        ComboBox<BaseEncodingAction> comboBox = new ComboBox<>(comboBoxActions);
        comboBox.addActionListener(e -> {
            ComboBox<BaseEncodingAction> source = (ComboBox<BaseEncodingAction>) e.getSource();
            BaseEncodingAction selectedItem = (BaseEncodingAction) source.getSelectedItem();
            if (Objects.nonNull(selectedItem)) {
                selectedItem.onSelect();
                selectAction.set(selectedItem);
            }
        });
        JButton button = new JButton("转换");
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    selectAction.get().doAction();
                }
            }
        });
        JPanel toolBar = new JPanel();
        toolBar.add(comboBox);
        toolBar.add(button);
        return toolBar;
    }

    public void project(Project project) {
        this.project = project;
    }
}
