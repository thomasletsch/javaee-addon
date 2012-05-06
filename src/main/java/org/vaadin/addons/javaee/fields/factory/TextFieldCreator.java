package org.vaadin.addons.javaee.fields.factory;

import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.addons.javaee.jpa.EntityContainer;

import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.TextField;

public class TextFieldCreator<FIELD extends AbstractTextField> extends AbstractFieldCreator<FIELD> {

    public TextFieldCreator(TranslationService translationService, EntityContainer<?> container, String fieldName, Class<FIELD> fieldType) {
        super(translationService, container, fieldName, fieldType);
    }

    @Override
    protected void initializeField(FIELD field) {
        field.setNullRepresentation("");
    };

    @Override
    @SuppressWarnings("unchecked")
    protected Class<FIELD> getDefaultFieldType() {
        return (Class<FIELD>) TextField.class;
    }

}
