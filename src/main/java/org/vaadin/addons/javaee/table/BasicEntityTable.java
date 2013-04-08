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
package org.vaadin.addons.javaee.table;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.vaadin.addons.javaee.container.EntityContainer;
import org.vaadin.addons.javaee.container.EntityItem;
import org.vaadin.addons.javaee.fields.factory.GlobalFieldFactory;
import org.vaadin.addons.javaee.i18n.TranslationKeys;
import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.dialogs.ConfirmDialog;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.Container;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.filter.UnsupportedFilterException;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MultiSelectMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.Reindeer;

/**
 * Base entity table to be subclassed for concrete implementations.
 * 
 * @author thomas.letsch.de@gmail.com
 * 
 * @param <ENTITY>
 *            The persistent entity
 */
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

    /**
     * Adds a generated column with a delete button for each row. Clicking on this button will cause a remove item on the underlying
     * container, thus deleting the entity behind the current row.
     */
    public void addDeleteColumn() {
        addColumn(TranslationKeys.TITLE_DELETE, new DeleteColumnGenerator());
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

    public void refreshCache() {
        getContainer().refreshCache();
    }

    public final class DeleteColumnGenerator implements Table.ColumnGenerator {

        private static final long serialVersionUID = 1L;

        @Override
        public Object generateCell(Table source, final Object itemId, Object columnId) {
            HorizontalLayout layout = new HorizontalLayout();
            layout.setMargin(false);
            Button deleteButton = new Button("", new DeleteButtonListener(itemId));
            deleteButton.setIcon(new ThemeResource("icons/silk/delete.png"));
            deleteButton.setStyleName(Reindeer.BUTTON_SMALL);
            layout.addComponent(deleteButton);
            return layout;
        }

    }

    public final class DeleteButtonListener implements Button.ClickListener {

        private final Object itemId;

        private static final long serialVersionUID = 1L;

        private DeleteButtonListener(Object itemId) {
            this.itemId = itemId;
        }

        @Override
        public void buttonClick(ClickEvent event) {
            ConfirmDialog.show(UI.getCurrent(), translationService.getText(TranslationKeys.TITLE_DELETE),
                    translationService.getText(TranslationKeys.MESSAGE_REALLY_DELETE), translationService.getText(TranslationKeys.YES),
                    translationService.getText(TranslationKeys.NO), new RowDeletionConfirmListener(itemId));
        }

    }

    public final class RowDeletionConfirmListener implements ConfirmDialog.Listener {

        private static final long serialVersionUID = 1L;

        private Object itemId;

        public RowDeletionConfirmListener(Object itemId) {
            this.itemId = itemId;
        }

        @Override
        public void onClose(ConfirmDialog dialog) {
            if (dialog.isConfirmed()) {
                getContainer().removeItem(itemId);
            }
        }
    }

}
