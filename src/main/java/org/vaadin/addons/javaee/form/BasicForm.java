/*******************************************************************************
 * Copyright 2013 Thomas Letsch (contact@thomas-letsch.de)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.vaadin.addons.javaee.form;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.vaadin.addons.javaee.container.EntityContainer;
import org.vaadin.addons.javaee.fields.factory.GlobalFieldFactory;
import org.vaadin.addons.javaee.fields.spec.FieldSpecification;
import org.vaadin.addons.javaee.i18n.TranslationService;
import org.wamblee.inject.InjectorBuilder;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.Container.Filter;
import com.vaadin.ui.Field;
import com.vaadin.ui.VerticalLayout;

public abstract class BasicForm<ENTITY extends PersistentEntity> extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    protected EntityFieldGroup<ENTITY> fieldGroup;

    @Inject
    protected GlobalFieldFactory fieldFactory;

    @Inject
    protected TranslationService translationService;

    protected Class<ENTITY> entityClass;

    private Map<String, FormSection> sections = new HashMap<String, FormSection>();

    public BasicForm(Class<ENTITY> entityClass) {
        this.entityClass = entityClass;
        setId(entityClass.getSimpleName() + "Form");
    }

    protected abstract EntityContainer<ENTITY> getContainer();

    public Filter getValuesAsFilter() {
        return fieldGroup.getValuesAsFilter();
    }

    protected ENTITY getDefaultValue() {
        try {
            return entityClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    protected void init() {
        removeAllComponents();
        sections.clear();
        fieldGroup = new EntityFieldGroup<ENTITY>();
        initFields();
    }

    /**
     * Can be overwritten
     */
    protected void initFields() {
        List<String> fieldNames = getContainer().getPropertyNames();
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
            section.addField(fieldSpec);
        }
        focusFirstField();
    }

    /**
     * Can be overwritten
     */
    protected void initFields(FormSection section, List<String> fieldNames) {
        for (String fieldName : fieldNames) {
            section.addField(fieldName);
        }
        focusFirstField();
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
        return getFormSection(entityClass.getSimpleName());
    }

    protected FormSection getFormSection(String name) {
        FormSection section = getFormSectionInternal(name);
        if (section == null) {
            section = new FormSection();
            section.setName(name);
            InjectorBuilder.getInjector().inject(section);
            initFormSection(section);
        }
        return section;
    }

    protected void initFormSection(FormSection section) {
        section.setContainer(getContainer());
        section.setFieldGroup(fieldGroup);
        section.init();
        addFormSection(section);
    }

    protected void addFormSection(FormSection section) {
        sections.put(section.getName(), section);
        addComponent(section);
    }

    protected FormSection getFormSectionInternal(String name) {
        return sections.get(name);
    }

    @SuppressWarnings("unchecked")
    public <ENTITY_TYPE> void addHiddenField(String fieldName, ENTITY_TYPE entity) {
        FieldSpecification fieldSpec = new FieldSpecification(fieldName);
        Field<ENTITY_TYPE> field = (Field<ENTITY_TYPE>) fieldGroup.getField(fieldName);
        if (field == null) {
            field = fieldFactory.createField(getContainer(), fieldSpec);
            fieldGroup.bind(field, fieldName);
        }
        field.setValue(entity);
    }

}
