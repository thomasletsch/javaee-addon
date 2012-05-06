package org.vaadin.addons.javaee.fields.factory;

import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.addons.javaee.jpa.EntityContainer;

import com.vaadin.ui.TextField;

public class NumberFieldCreator<FIELD extends TextField> extends TextFieldCreator<FIELD> {

    public NumberFieldCreator(TranslationService translationService, EntityContainer<?> container, String fieldName, Class<FIELD> fieldType) {
        super(translationService, container, fieldName, fieldType);
    }

    @Override
    protected void initializeField(FIELD field) {
    };

}
