package org.vaadin.addons.javaee.fields.factory;

import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.addons.javaee.jpa.EntityContainer;

import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.DateField;

public class LocalDateFieldCreator<FIELD extends DateField> extends AbstractFieldCreator<FIELD> {

    public LocalDateFieldCreator(TranslationService translationService, EntityContainer<?> container, String fieldName,
            Class<FIELD> fieldType) {
        super(translationService, container, fieldName, fieldType);
    }

    @Override
    protected void initializeField(FIELD field) {
        super.initializeField(field);
        field.setResolution(Resolution.DAY);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Class<FIELD> getDefaultFieldType() {
        return (Class<FIELD>) DateField.class;
    }

}
