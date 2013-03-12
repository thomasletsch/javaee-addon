package org.vaadin.addons.javaee.form;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.vaadin.addons.javaee.container.EntityContainer;
import org.vaadin.addons.javaee.fields.factory.GlobalFieldFactory;
import org.vaadin.addons.javaee.fields.spec.FieldSpecification;
import org.vaadin.addons.javaee.i18n.TranslationService;

import com.vaadin.ui.Field;

@Singleton
public class FieldCreator {

    @Inject
    protected TranslationService translationService;

    @Inject
    protected GlobalFieldFactory fieldFactory;

    public Field<?> createField(EntityContainer<?> container, FieldSpecification fieldSpec) {
        Field<?> field = fieldFactory.createField(container, fieldSpec);
        if (fieldSpec.getFieldWidth() != null) {
            field.setWidth(fieldSpec.getFieldWidth());
        }
        return field;
    }

}
