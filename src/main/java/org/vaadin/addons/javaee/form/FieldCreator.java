package org.vaadin.addons.javaee.form;

import javax.inject.Inject;

import org.vaadin.addons.javaee.fields.factory.FieldFactory;
import org.vaadin.addons.javaee.fields.spec.FieldSpecification;
import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.addons.javaee.jpa.EntityContainer;

import com.vaadin.ui.Field;

public class FieldCreator {

    @Inject
    protected TranslationService translationService;

    @Inject
    protected FieldFactory fieldFactory;

    public Field<?> createField(EntityContainer<?> container, EntityFieldGroup<?> fieldGroup, String fieldName) {
        return createField(container, fieldGroup, new FieldSpecification(fieldName));
    }

    public Field<?> createField(EntityContainer<?> container, EntityFieldGroup<?> fieldGroup, FieldSpecification fieldSpec) {
        Field<?> field = fieldFactory.createField(container, fieldSpec);
        if (fieldSpec.getFieldWidth() != null) {
            field.setWidth(fieldSpec.getFieldWidth());
        }
        fieldGroup.bind(field, fieldSpec.getName());
        return field;
    }

    public void bindField(EntityFieldGroup<?> fieldGroup, FieldSpecification fieldSpec, Field<?> field) {
        fieldGroup.bind(field, fieldSpec.getName());
    }

}
