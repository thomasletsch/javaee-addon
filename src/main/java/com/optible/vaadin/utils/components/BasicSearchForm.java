package com.optible.vaadin.utils.components;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javaeeutils.jpa.PersistentEntity;

import com.optible.vaadin.utils.fields.FieldFactory;
import com.optible.vaadin.utils.jpa.EntityContainer;
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
