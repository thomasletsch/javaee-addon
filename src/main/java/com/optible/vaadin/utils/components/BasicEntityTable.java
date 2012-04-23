package com.optible.vaadin.utils.components;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.javaeeutils.jpa.PersistentEntity;

import com.optible.vaadin.utils.i18n.TranslationService;
import com.optible.vaadin.utils.jpa.EntityContainer;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.filter.UnsupportedFilterException;
import com.vaadin.ui.Table;

@Dependent
public abstract class BasicEntityTable<ENTITY extends PersistentEntity> extends Table implements Container.Filterable {

    private static final int BATCH_SIZE = 10;

    @Inject
    private TranslationService translationService;

    private EntityContainer<ENTITY> jpaContainer;

    public BasicEntityTable(Class<ENTITY> entityClass) {
        setDebugId(entityClass.getSimpleName() + "Table");
    }

    public abstract EntityContainer<ENTITY> getContainer();

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
        this.jpaContainer = getContainer();
        init(jpaContainer);
    }

    protected void init(EntityContainer<ENTITY> jpaContainer) {
        this.jpaContainer = jpaContainer;
        setImmediate(true);
        setEditable(false);
        setMultiSelect(false);
        setMultiSelectMode(MultiSelectMode.DEFAULT);
        setSelectable(true);
        setBuffered(true);
        setPageLength(BATCH_SIZE);

        setContainerDataSource(jpaContainer);
        setVisibleColumns(new Object[] {});
        initColumns();
    }

    protected void addColumn(String name) {
        Class<?> type = jpaContainer.getType(name);
        addColumn(name, type);
    }

    protected void addColumn(String name, Class<?> type) {
        addContainerProperty(name, type, null, translationService.get(name, getLocale()), null, null);
    }

    protected void addColumn(String name, Converter<String, ?> converter) {
        addColumn(name);
        setConverter(name, converter);
    }

    protected void addColumn(String name, ColumnGenerator columnGenerator) {
        addColumn(name);
        addGeneratedColumn(name, columnGenerator);
    }

    public void enableRefresh() {
        jpaContainer.enable();
    }

    @Override
    public void addContainerFilter(Filter filter) throws UnsupportedFilterException {
        jpaContainer.addContainerFilter(filter);
    }

    @Override
    public void removeContainerFilter(Filter filter) {
        jpaContainer.removeContainerFilter(filter);
    }

    @Override
    public void removeAllContainerFilters() {
        jpaContainer.removeAllContainerFilters();
    }

    @SuppressWarnings("unchecked")
    public ENTITY getSelectedEntity() {
        Long id = (Long) getValue();
        BeanItem<ENTITY> item = (BeanItem<ENTITY>) getItem(id);
        ENTITY entity = item.getBean();
        return entity;
    }
}
