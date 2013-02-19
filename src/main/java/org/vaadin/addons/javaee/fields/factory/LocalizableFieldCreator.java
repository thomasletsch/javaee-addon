package org.vaadin.addons.javaee.fields.factory;

import org.vaadin.addons.javaee.fields.spec.FieldSpecification;
import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.addons.javaee.jpa.EntityContainer;

import com.vaadin.ui.Field;

public abstract class LocalizableFieldCreator<FIELD extends Field<?>> extends AbstractFieldCreator<FIELD> {

    protected final TranslationService translationService;

    public LocalizableFieldCreator(EntityContainer<?> container, FieldSpecification fieldSpec, TranslationService translationService) {
        super(container, fieldSpec);
        this.translationService = translationService;
    }

}
