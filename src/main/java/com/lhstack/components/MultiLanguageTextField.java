package com.lhstack.components;

import com.intellij.ide.highlighter.HighlighterFactory;
import com.intellij.openapi.editor.EditorSettings;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.project.Project;
import com.intellij.ui.LanguageTextField;
import org.jetbrains.annotations.NotNull;

public class MultiLanguageTextField extends LanguageTextField {

    private LanguageFileType languageFileType;

    private final DocumentCreator documentCreator;

    public MultiLanguageTextField(LanguageFileType languageFileType, Project project, String text) {
        this(languageFileType, project, text, new SimpleDocumentCreator());
    }

    public MultiLanguageTextField(LanguageFileType languageFileType, Project project, String text, DocumentCreator documentCreator) {
        super(languageFileType.getLanguage(), project, text, documentCreator, false);
        this.languageFileType = languageFileType;
        this.documentCreator = documentCreator;
    }

    public MultiLanguageTextField changeLanguageFileType(LanguageFileType languageFileType) {
        if(this.languageFileType != languageFileType){
            this.languageFileType = languageFileType;
            String text = this.getDocument().getText();
            this.setDocument(this.documentCreator.createDocument(text, languageFileType.getLanguage(), getProject()));
        }
        return this;
    }


    @Override
    protected @NotNull EditorEx createEditor() {
        EditorEx editor = super.createEditor();
        EditorSettings settings = editor.getSettings();
        settings.setLineNumbersShown(true);
        settings.setFoldingOutlineShown(true);
        settings.setAllowSingleLogicalLineFolding(true);
        settings.setRightMarginShown(true);
        editor.setHorizontalScrollbarVisible(true);
        editor.setVerticalScrollbarVisible(true);
        editor.setHighlighter(HighlighterFactory.createHighlighter(this.getProject(), this.languageFileType));
        return editor;
    }

}
