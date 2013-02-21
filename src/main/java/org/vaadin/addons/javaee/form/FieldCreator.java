package org.vaadin.addons.javaee.form;

import javax.inject.Inject;

import org.vaadin.addons.javaee.fields.factory.FieldFactory;
import org.vaadin.addons.javaee.fields.spec.FieldSpecification;
import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.addons.javaee.jpa.EntityContainer;

import com.vaadin.ui.Field;
import com.vaadin.ui.Label;

public class FieldCreator {

    @Inject
    protected TranslationService translationService;

    @Inject
    protected FieldFactory fieldFactory;

    private EntityContainer<?> container;

    private EntityFieldGroup<?> fieldGroup;

    public FieldCreator(TranslationService translationService, FieldFactory fieldFactory, EntityContainer<?> container,
            EntityFieldGroup<?> fieldGroup) {
        this.translationService = translationService;
        this.fieldFactory = fieldFactory;
        this.container = container;
        this.fieldGroup = fieldGroup;
    }

    public Field<?> createField(String fieldName) {
        return createField(new FieldSpecification(fieldName));
    }

    public Field<?> createField(FieldSpecification fieldSpec) {
        Field<?> field = fieldFactory.createField(container, fieldSpec);
        if (fieldSpec.getFieldWidth() != null) {
            field.setWidth(fieldSpec.getFieldWidth());
        }
        fieldGroup.bind(field, fieldSpec.getName());
        return field;
    }

    public void bindField(FieldSpecification fieldSpec, Field<?> field) {
        fieldGroup.bind(field, fieldSpec.getName());
    }

    public Label createLabel(FormSection section, FieldSpecification fieldSpec) {
        Label label = new Label(translationService.getText(section.getName() + "." + fieldSpec.getName()) + ":");
        label.setStyleName("rightalign");
        if (fieldSpec.getLabelWidth() != null) {
            label.setWidth(fieldSpec.getLabelWidth());
        }
        return label;
    }
}
