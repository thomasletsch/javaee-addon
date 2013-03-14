package org.vaadin.addons.javaee.fields;

import java.util.Collection;

import javax.inject.Inject;

import org.vaadin.addons.javaee.i18n.TranslationService;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.converter.Converter.ConversionException;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TableFieldFactory;

@SuppressWarnings("rawtypes")
public class OneToManyRelationField<ENTITY extends PersistentEntity> extends CustomField<Collection> {

    private static final long serialVersionUID = 1L;

    private Table table;

    private Class<ENTITY> entityClass;

    @Inject
    private TranslationService translationService;

    private BeanItemContainer<ENTITY> tableDataSource;

    public OneToManyRelationField() {
        table = new Table();
        table.setPageLength(5);
        table.setSelectable(false);
        table.setEditable(true);
        table.setBuffered(true);
        table.setImmediate(true);
    }

    @Override
    protected Component initContent() {
        return table;
    }

    public void setValueForced(Collection newFieldValue) throws com.vaadin.data.Property.ReadOnlyException, ConversionException {
        if (newFieldValue != null && newFieldValue.equals(getInternalValue())) {
            setInternalValue(newFieldValue);
        }
        setValue(newFieldValue);
    }

    @Override
    public void setValue(Collection newFieldValue) throws com.vaadin.data.Property.ReadOnlyException, ConversionException {
        super.setValue(newFieldValue);
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    protected void setInternalValue(Collection newValue) {
        super.setInternalValue(newValue);
        tableDataSource.removeAllItems();
        tableDataSource.addAll(newValue);
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
    @SuppressWarnings("unchecked")
    protected Collection<ENTITY> getInternalValue() {
        return super.getInternalValue();
    }

    @Override
    public Class<Collection> getType() {
        return Collection.class;
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

    public void setFieldFactory(TableFieldFactory tableFieldFactory) {
        table.setTableFieldFactory(tableFieldFactory);
    }

}
