package io.github.onlynight.exchange.typed.text.plugin.sdk;

import io.github.onlynight.exchange.plugin.sdk.impl.BaseDocumentHandlerPlugin;
import io.github.onlynight.exchange.typed.text.plugin.sdk.doc.DocumentChText;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ChTextDocumentHandlerPlugin extends BaseDocumentHandlerPlugin<DocumentChText> {

    public static final String TYPED_TEXT_TYPE = "ch-text";

    public ChTextDocumentHandlerPlugin(String documentPath) {
        super(documentPath);
    }

    @Override
    public String typedTextType() {
        return TYPED_TEXT_TYPE;
    }

    @Override
    public List<TranslatorDocument<DocumentChText>> getDocument() {
        File file = new File(documentPath);
        File[] files = file.listFiles();
        if (files != null) {
            List<TranslatorDocument<DocumentChText>> documents = new ArrayList<>();
            for (File docFile : files) {
                if (docFile.isFile()) {
                    TranslatorDocument<DocumentChText> document =
                            convertDocument(new DocumentChText(docFile.getAbsolutePath()).parse(),
                                    docFile.getAbsolutePath());
                    documents.add(document);
                }
            }
            return documents;
        }
        return null;
    }

    @Override
    public TranslatorDocument<DocumentChText> convertDocument(DocumentChText document, String path) {
        if (document != null) {
            return new TranslatorDocument<>(path, document, document.getSentences());
        }
        return null;
    }

    @Override
    public void writeNewDocument(TranslatorDocument<DocumentChText> translatorDocument, String targetLanguage) {
        List<String> dests = translatorDocument.getDestStrings();

        StringBuilder sb = new StringBuilder();
        for (String str : dests) {
            sb.append(str);
        }

        writeNewDocument(translatorDocument.getPath(), sb, targetLanguage);
    }

    @Override
    public String getResultFolderName(String targetLanguage) {
        return "translated";
    }
}
