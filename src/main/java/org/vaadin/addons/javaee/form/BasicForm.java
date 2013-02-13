package org.vaadin.addons.javaee.form;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.vaadin.addons.javaee.fields.factory.FieldFactory;
import org.vaadin.addons.javaee.fields.spec.FieldSpecification;
import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.addons.javaee.jpa.EntityContainer;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.Container.Filter;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public abstract class BasicForm<ENTITY extends PersistentEntity> extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    protected EntityFieldGroup<ENTITY> fieldGroup;

    @Inject
    protected FieldFactory fieldFactory;

    @Inject
    protected TranslationService translationService;

    protected EntityContainer<ENTITY> entityContainer;

    protected Class<ENTITY> entityClass;

    private Map<String, FormSection> sections = new HashMap<String, FormSection>();

    public BasicForm(Class<ENTITY> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityContainer<ENTITY> getContainer();

    public Filter getValuesAsFilter() {
        return fieldGroup.getValuesAsFilter();
    }

    protected ENTITY getDefaultValue() throws InstantiationException, IllegalAccessException {
        return entityClass.newInstance();
    }

    @PostConstruct
    protected void init() {
        entityContainer = getContainer();
        removeAllComponents();
        sections.clear();
        fieldGroup = new EntityFieldGroup<ENTITY>();
        initFields();
    }

    /**
     * Can be overwritten
     */
    protected void initFields() {
        List<String> fieldNames = entityContainer.getPropertyNames();
        initFields(fieldNames);
    }

    /**
     * Can be overwritten
     */
    protected void initFields(List<String> fieldNames) {
        FormSection section = getFormSection(entityClass.getSimpleName());
        initFields(section, fieldNames);
    }

    /**
     * Can be overwritten
     */
    protected void initFieldsWithSpec(List<FieldSpecification> fieldSpecs) {
        FormSection section = getFormSection(entityClass.getSimpleName());
        initFieldsWithSpec(section, fieldSpecs);
    }

    /**
     * Can be overwritten
     */
    protected void initFieldsWithSpec(FormSection section, List<FieldSpecification> fieldSpecs) {
        for (FieldSpecification fieldSpec : fieldSpecs) {
            addField(section, fieldSpec);
        }
        focusFirstField();
    }

    /**
     * Can be overwritten
     */
    protected void initFields(FormSection section, List<String> fieldNames) {
        for (String fieldName : fieldNames) {
            addField(section, fieldName);
        }
        focusFirstField();
    }

    protected void addField(FormSection section, String fieldName) {
        addField(section, new FieldSpecification(fieldName));
    }

    protected void addField(FormSection section, FieldSpecification fieldSpec) {
        Field<?> field = fieldFactory.createField(entityContainer, fieldSpec);
        addField(section, fieldSpec, field);
    }

    protected void addField(FormSection section, FieldSpecification fieldSpec, Field<?> field) {
        fieldGroup.bind(field, fieldSpec.getName());
        Label label = new Label(translationService.getText(section.getName() + "." + fieldSpec.getName()) + ":");
        label.setStyleName("rightalign");
        section.addField(fieldSpec, label, field);
    }

    protected void addComponent(FormSection section, FieldSpecification fieldSpec, Component component) {
        Label label = new Label(translationService.getText(section.getName() + "." + fieldSpec.getName()) + ":");
        label.setStyleName("rightalign");
        section.addComponent(fieldSpec, label, component);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (Entry<String, FormSection> entry : sections.entrySet()) {
            entry.getValue().setEnabled(enabled);
        }
    }

    public boolean isValid() {
        return fieldGroup.isValid();
    }

    public Field<?> getField(String name) {
        return fieldGroup.getField(name);
    }

    public void focusFirstField() {
        fieldGroup.getFirstField().focus();
    }

    protected FormSection getDefaultSection() {
        return sections.entrySet().iterator().next().getValue();
    }

    protected FormSection getFormSection(String name) {
        FormSection section = getFormSectionInternal(name);
        if (section == null) {
            section = new FormSection(name, translationService.getText(name));
            addFormSection(name, section);
            addComponent(section);
        }
        return section;
    }

    protected void addFormSection(String name, FormSection section) {
        sections.put(name, section);
    }

    protected FormSection getFormSectionInternal(String name) {
        return sections.get(name);
    }

}