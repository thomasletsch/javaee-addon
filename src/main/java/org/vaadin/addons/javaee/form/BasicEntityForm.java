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
import org.vaadin.addons.javaee.jpa.EntityItem;
import org.vaadin.addons.javaee.table.BasicEntityTable;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.filter.And;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Field;
import com.vaadin.ui.GridLayout;


@Dependent
public abstract class BasicEntityForm<ENTITY extends PersistentEntity> extends GridLayout {

    private static Log log = LogFactory.getLog(BasicSearchForm.class);

    private FieldGroup fieldGroup;

    @Inject
    private FieldFactory fieldFactory;

    private Class<ENTITY> entityClass;

    private EntityContainer<ENTITY> jpaContainer;

    public BasicEntityForm(Class<ENTITY> entityClass) {
        this.entityClass = entityClass;
        fieldGroup = new FieldGroup();
        setColumns(3);
    }

    protected abstract EntityContainer<ENTITY> getContainer();

    /**
     * Can be overwritten if init from table
     */
    protected BasicEntityTable<ENTITY> getTable() {
        return null;
    }

    @PostConstruct
    protected void init() {
        this.jpaContainer = getContainer();
        initFields();
        if (getTable() != null)
            init(getTable());
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
        for (String fieldName : fieldNames) {
            addField(fieldName);
        }
        fieldGroup.getFields().iterator().next().focus();
    }

    protected void addField(String fieldName) {
        Field<?> field = fieldFactory.createField(jpaContainer, fieldName);
        addField(fieldName, field);
    }

    protected void addField(String fieldName, Field<?> field) {
        field.setDebugId(entityClass.getSimpleName() + "." + fieldName);
        fieldGroup.bind(field, fieldName);
        addComponent(field);
    }

    public void edit(Item item) {
        fieldGroup.setItemDataSource(item);
    }

    public void editNew() {
        try {
            EntityItem<ENTITY> item = new EntityItem<ENTITY>(getContainer(), getDefaultValue());
            fieldGroup.setItemDataSource(item);
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("Could not instanciate " + entityClass, e);
        }
    }

    @SuppressWarnings("unchecked")
    public ENTITY getEntity() {
        EntityItem<ENTITY> item = (EntityItem<ENTITY>) fieldGroup.getItemDataSource();
        return item.getBean();
    }

    protected ENTITY getDefaultValue() throws InstantiationException, IllegalAccessException {
        return entityClass.newInstance();
    }

    protected void init(BasicEntityTable<ENTITY> table) {
        table.addListener(new ItemClickListener() {

            @Override
            public void itemClick(ItemClickEvent event) {
                edit(event.getItem());
            }
        });
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

    private String getStringValue(Field<?> field) {
        if (field.getValue() != null)
            return field.getValue().toString();
        else
            return null;
    }

    @SuppressWarnings("unchecked")
    public void save() {
        try {
            fieldGroup.commit();
            if (fieldGroup.getItemDataSource() instanceof EntityItem) {
                EntityItem<ENTITY> entityItem = (EntityItem<ENTITY>) fieldGroup.getItemDataSource();
                entityItem.commit();
            }
        } catch (CommitException e) {
            log.error("Could not save " + entityClass.getSimpleName(), e);
        }
    }
}
