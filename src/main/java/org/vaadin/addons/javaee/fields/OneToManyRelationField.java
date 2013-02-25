package org.vaadin.addons.javaee.fields;

import java.util.Collection;

import org.vaadin.addons.javaee.i18n.TranslationService;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Table;

public class OneToManyRelationField<ENTITY extends PersistentEntity> extends CustomField<Collection<ENTITY>> {

    private static final long serialVersionUID = 1L;

    private Table table;

    private Collection<ENTITY> items;

    private Class<ENTITY> entityClass;

    private TranslationService translationService;

    private BeanItemContainer<ENTITY> tableDataSource;

    public OneToManyRelationField() {
        table = new Table();
        table.setPageLength(5);
        table.setSelectable(false);
        table.setEditable(true);
    }

    @Override
    protected Component initContent() {
        return table;
    }

    @Override
    protected void setInternalValue(Collection<ENTITY> newValue) {
        tableDataSource.removeAllItems();
        tableDataSource.addAll(newValue);
        items = newValue;
    }

    public void setConverter(String propertyId, Converter<String, ?> converter) {
        table.setConverter(propertyId, converter);
    }

    public void setVisibleColumns(String... propertyIds) {
        table.setVisibleColumns(propertyIds);
        for (String propertyId : propertyIds) {
            table.setColumnHeader(propertyId, translationService.getText(propertyId));
        }
    }

    @Override
    protected Collection<ENTITY> getInternalValue() {
        return items;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<? extends Collection<ENTITY>> getType() {
        return (Class<? extends Collection<ENTITY>>) Collection.class;
    }

    public Class<ENTITY> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<ENTITY> entityClass) {
        this.entityClass = entityClass;
        tableDataSource = new BeanItemContainer<ENTITY>(entityClass);
        table.setContainerDataSource(tableDataSource);
    }

    public TranslationService getTranslationService() {
        return translationService;
    }

    public void setTranslationService(TranslationService translationService) {
        this.translationService = translationService;
    }

    public void setRows(int rows) {
        table.setPageLength(rows);
    }
}
