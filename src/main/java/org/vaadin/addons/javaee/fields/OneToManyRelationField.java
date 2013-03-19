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

    /**
     * Sometimes the underlying value is changed already as being part of the current entity object graph. setValue would then do nothing.
     * No setInternalValue would be called and no table data source refresh be done. Use setValueForced for that case.
     */
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
