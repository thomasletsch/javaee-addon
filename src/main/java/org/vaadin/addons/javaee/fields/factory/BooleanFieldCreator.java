package org.vaadin.addons.javaee.fields.factory;

import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.addons.javaee.jpa.EntityContainer;

import com.vaadin.ui.CheckBox;

public class BooleanFieldCreator extends AbstractFieldCreator<CheckBox> {

    public BooleanFieldCreator(TranslationService translationService, EntityContainer<?> container, String fieldName,
            Class<CheckBox> fieldType) {
        super(translationService, container, fieldName, fieldType);
    }

    @Override
    protected Class<CheckBox> getDefaultFieldType() {
        return CheckBox.class;
    }

}
