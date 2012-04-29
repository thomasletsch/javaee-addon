/*******************************************************************************
 * Copyright 2012 Thomas Letsch
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *******************************************************************************/
package org.vaadin.addons.javaee.form;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.vaadin.addons.javaee.fields.FieldFactory;
import org.vaadin.addons.javaee.jpa.EntityContainer;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.filter.And;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.ui.Field;
import com.vaadin.ui.GridLayout;

@Dependent
public class BasicSearchForm<ENTITY extends PersistentEntity> extends GridLayout {

    private static Log log = LogFactory.getLog(BasicSearchForm.class);

    private FieldGroup fieldGroup;

    @Inject
    private FieldFactory fieldFactory;

    private Class<ENTITY> entityClass;

    public BasicSearchForm(Class<ENTITY> entityClass) {
        this.entityClass = entityClass;
        fieldGroup = new FieldGroup();
        try {
            BeanItem<ENTITY> itemDataSource = new BeanItem<ENTITY>(getDefaultValue());
            fieldGroup.setItemDataSource(itemDataSource);
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("Could not instanciate " + entityClass, e);
        }
        setColumns(3);
    }

    @PostConstruct
    protected void initFields() {
        List<String> fieldNames = getContainer().getPropertyNames();
        initFields(fieldNames);
    }

    protected ENTITY getDefaultValue() throws InstantiationException, IllegalAccessException {
        return entityClass.newInstance();
    }

    public Filter getValuesAsFilter() {
        List<Filter> filters = new ArrayList<>();
        for (Field<?> field : fieldGroup.getFields()) {
            if (field.isModified()) {
                filters.add(new SimpleStringFilter(fieldGroup.getPropertyId(field), getStringValue(field), false, false));
            }
        }
        Filter filter = new And(filters.toArray(new Filter[] {}));
        return filter;
    }

    protected EntityContainer<ENTITY> getContainer() {
        return null;
    }

    protected void initFields(List<String> fieldNames) {
        for (String fieldName : fieldNames) {
            Field<?> field = fieldFactory.createField(getContainer(), fieldName);
            field.setDebugId(entityClass.getSimpleName() + "." + fieldName);
            fieldGroup.bind(field, fieldName);
            addComponent(field);
        }
        fieldGroup.getFields().iterator().next().focus();
    }

    private String getStringValue(Field<?> field) {
        if (field.getValue() != null)
            return field.getValue().toString();
        else
            return null;
    }

}
