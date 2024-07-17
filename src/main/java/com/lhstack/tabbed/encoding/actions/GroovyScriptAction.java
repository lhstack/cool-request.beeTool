package com.lhstack.tabbed.encoding.actions;

import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.roots.libraries.LibraryTable;
import com.intellij.openapi.roots.libraries.LibraryTablesRegistrar;
import com.intellij.openapi.vfs.VirtualFile;
import com.lhstack.utils.I18nUtils;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.net.URL;

public class GroovyScriptAction extends BaseEncodingAction {

    @Override
    public void doAction() {
        try {
            String scriptText = sourceTextField.getText();
            if (StringUtils.isNotBlank(scriptText)) {

                ClassLoader classLoader = GroovyScriptAction.class.getClassLoader();
                GroovyShell shell = new GroovyShell(classLoader);
                if (project != null) {
                    addClassPaths(shell, project);
                }
                Script script = shell.parse(scriptText);
                Binding binding = new Binding();
                script.setBinding(binding);
                targetTextField.setText(String.valueOf(script.run()));
            }
        } catch (Throwable e) {
            targetTextField.setText(e.getMessage());
        }
    }

    private void addClassPaths(GroovyShell shell, Project project) {
        try {
            LibraryTable libraryTable = LibraryTablesRegistrar.getInstance().getLibraryTable(project);
            for (Library library : libraryTable.getLibraries()) {
                for (VirtualFile file : library.getFiles(OrderRootType.CLASSES)) {
                    URL url = new File(file.getPresentableUrl()).toURI().toURL();
                    shell.getClassLoader().addURL(url);
                }
            }
        } catch (Throwable e) {
            targetTextField.setText("添加用户项目classpath失败: " + e.getMessage());
        }
    }


    @Override
    public String encodingType() {
        return "Groovy";
    }

    @Override
    public String encodingName() {
        return I18nUtils.getString("groovy");
    }

    @Override
    public void onSelect() {
        LanguageFileType languageFileType = (LanguageFileType) FileTypeManager.getInstance().getFileTypeByExtension("groovy");
        this.sourceTextField.changeLanguageFileType(languageFileType);
    }
}
