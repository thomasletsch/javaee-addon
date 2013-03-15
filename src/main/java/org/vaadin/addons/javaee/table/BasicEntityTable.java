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
package org.vaadin.addons.javaee.table;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.vaadin.addons.javaee.container.EntityContainer;
import org.vaadin.addons.javaee.container.EntityItem;
import org.vaadin.addons.javaee.fields.factory.GlobalFieldFactory;
import org.vaadin.addons.javaee.i18n.TranslationService;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.Container;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.filter.UnsupportedFilterException;
import com.vaadin.shared.ui.MultiSelectMode;
import com.vaadin.ui.Table;

public abstract class BasicEntityTable<ENTITY extends PersistentEntity> extends Table implements Container.Filterable {

    private static final long serialVersionUID = 1L;

    private static final int DEFAULT_PAGE_SIZE = 5;

    @Inject
    protected TranslationService translationService;

    @Inject
    private GlobalFieldFactory tableFieldFactory;

    /**
     * Only query container if a filter is set
     */
    protected boolean needsFilter = true;

    public BasicEntityTable() {
    }

    public BasicEntityTable(Class<ENTITY> entityClass) {
        setId(entityClass.getSimpleName() + "Table");
    }

    protected abstract EntityContainer<ENTITY> getContainer();

    /**
     * Can be overwritten
     */
    protected void initColumns() {
        List<String> columnNames = getContainer().getPropertyNames();
        initColumns(columnNames);
    }

    /**
     * Can be overwritten
     */
    protected void initColumns(List<String> columnNames) {
        for (String columnName : columnNames) {
            addColumn(columnName);
        }
    }

    @PostConstruct
    protected void init() {
        if (needsFilter) {
            getContainer().needsFiltering();
        }
        setEditable(false);
        setMultiSelect(false);
        setMultiSelectMode(MultiSelectMode.DEFAULT);
        setSelectable(true);
        setBuffered(true);
        setPageLength(DEFAULT_PAGE_SIZE);
        setCaption(translationService.getText(getContainer().getEntityClass().getSimpleName() + "s"));

        setTableFieldFactory(tableFieldFactory);
        setContainerDataSource(getContainer());
        setVisibleColumns(new Object[] {});
        initColumns();
    }

    public void addColumn(String name) {
        Class<?> type = getContainer().getType(name);
        addColumn(name, type);
    }

    public void addColumn(String name, Class<?> type) {
        addContainerProperty(name, type, null, translationService.getText(name), null, null);
    }

    public void addColumn(String name, Converter<String, ?> converter) {
        addColumn(name);
        setConverter(name, converter);
    }

    public void addColumn(String name, ColumnGenerator columnGenerator) {
        addGeneratedColumn(name, columnGenerator);
        setColumnHeader(name, translationService.getText(name));
    }

    @Override
    public Long getValue() {
        return (Long) super.getValue();
    }

    @Override
    public void addContainerFilter(Filter filter) throws UnsupportedFilterException {
        getContainer().addContainerFilter(filter);
    }

    @Override
    public void removeContainerFilter(Filter filter) {
        getContainer().removeContainerFilter(filter);
    }

    @Override
    public void removeAllContainerFilters() {
        getContainer().removeAllContainerFilters();
    }

    public boolean isAnySelected() {
        return getValue() != null;
    }

    public ENTITY getSelectedEntity() {
        EntityItem<ENTITY> item = getSelectedEntityItem();
        ENTITY entity = item.getEntity();
        return entity;
    }

    @SuppressWarnings("unchecked")
    public EntityItem<ENTITY> getSelectedEntityItem() {
        Long id = getValue();
        EntityItem<ENTITY> item = (EntityItem<ENTITY>) getItem(id);
        return item;
    }

    public void removeSelectedItem() {
        Long id = getValue();
        getContainer().removeItem(id);
    }

    public void selectFirst() {
        select(firstItemId());
    }

    public void clearSelection() {
        select(getNullSelectionItemId());
    }

    @Override
    public void refreshRowCache() {
        super.refreshRowCache();
    }
}
