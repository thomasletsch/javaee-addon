package org.vaadin.addons.javaee.fields.factory;

import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.addons.javaee.jpa.EntityContainer;

import com.vaadin.ui.TextField;

public class DecimalFieldCreator<FIELD extends TextField> extends NumberFieldCreator<FIELD> {

    public DecimalFieldCreator(TranslationService translationService, EntityContainer<?> container, String fieldName, Class<FIELD> fieldType) {
        super(translationService, container, fieldName, fieldType);
    }

}
