package com.lhstack.tabbed.encoding.actions;

import com.intellij.openapi.fileTypes.PlainTextFileType;
import com.intellij.openapi.project.Project;
import com.lhstack.components.MultiLanguageTextField;
import org.jetbrains.annotations.Nls;

public abstract class BaseEncodingAction {

    protected Project project;

    protected MultiLanguageTextField sourceTextField;


    protected MultiLanguageTextField targetTextField;

    /**
     * 编码类型
     *
     * @return
     */
    public abstract String encodingType();

    public abstract String encodingName();


    public BaseEncodingAction setProject(Project project) {
        this.project = project;
        return this;
    }

    public BaseEncodingAction setSourceDocument(MultiLanguageTextField sourceTextField) {
        this.sourceTextField = sourceTextField;
        return this;
    }

    public BaseEncodingAction setTargetDocument(MultiLanguageTextField targetTextField) {
        this.targetTextField = targetTextField;
        return this;
    }

    public abstract void doAction();

    public int ordered() {
        return 0;
    }

    @Override
    public @Nls String toString() {
        return this.encodingName();
    }

    public void onSelect() {
        this.sourceTextField.changeLanguageFileType(PlainTextFileType.INSTANCE);
    }
}
