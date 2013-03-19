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
package org.vaadin.addons.javaee.container;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;

import org.vaadin.addons.javaee.container.jpa.JPAEntityProvider;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.googlecode.javaeeutils.reflection.ReflectionUtils;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.filter.And;
import com.vaadin.data.util.filter.UnsupportedFilterException;

public abstract class AbstractEntityContainer<ENTITY extends PersistentEntity> implements EntityContainer<ENTITY> {

    private static final long serialVersionUID = 1L;

    @EJB
    protected JPAEntityProvider jpaEntityProvider;

    protected List<Container.ItemSetChangeListener> itemSetChangeListeners = new ArrayList<>();

    protected List<Container.PropertySetChangeListener> propertySetChangeListeners = new ArrayList<>();

    protected List<Filter> filters = new ArrayList<>();

    protected List<SortDefinition> sortDefinitions = new ArrayList<>();

    protected Map<String, Class<?>> properties = new HashMap<>();

    protected Class<ENTITY> entityClass;

    protected boolean needsFiltering = false;

    protected boolean filterSet = false;

    public AbstractEntityContainer(Class<ENTITY> entityClass) {
        this.entityClass = entityClass;
        initProperties(entityClass);
    }

    @Override
    public abstract List<ENTITY> findAllEntities();

    @Override
    public Class<ENTITY> getEntityClass() {
        return entityClass;
    }

    protected void initProperties(Class<ENTITY> entityClass) {
        for (Field field : ReflectionUtils.getAllInstanceFields(entityClass)) {
            if (!Modifier.isStatic(field.getModifiers())) {
                properties.put(field.getName(), field.getType());
            }
        }
    }

    @Override
    public void loadItemWithRelations(EntityItem<ENTITY> item) {
        refreshItem(item);
    }

    @Override
    public Item getItem(Object itemId) {
        return getItem((Long) itemId);
    }

    @Override
    public Item addItem(Object itemId) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object addItemAfter(Object previousItemId) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Item addItemAfter(Object previousItemId, Object newItemId) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<?> getContainerPropertyIds() {
        return properties.keySet();
    }

    @Override
    public boolean addContainerProperty(Object propertyId, Class<?> type, Object defaultValue) throws UnsupportedOperationException {
        properties.put((String) propertyId, type);
        notifyPropertySetChanged();
        return true;
    }

    @Override
    public Property<?> getContainerProperty(Object itemId, Object propertyId) {
        Item item = getItem(itemId);
        return item == null ? null : item.getItemProperty(propertyId);
    }

    @Override
    public boolean removeContainerProperty(Object propertyId) throws UnsupportedOperationException {
        boolean result = properties.remove(propertyId.toString()) != null;
        notifyPropertySetChanged();
        return result;
    }

    @Override
    public Class<?> getCollectionType(String propertyId) {
        return jpaEntityProvider.getType(entityClass, propertyId);
    }

    @Override
    public Class<?> getType(Object propertyId) {
        Class<?> type = properties.get(propertyId);
        if (type == null) {
            type = ReflectionUtils.getType(entityClass, (String) propertyId);
            if (type != null) {
                properties.put((String) propertyId, type);
            }
        }
        return type;
    }

    private void notifyPropertySetChanged() {
        PropertySetChangeEvent event = new EntityPropertySetChangeEvent<ENTITY>(this);
        for (Container.PropertySetChangeListener listener : this.propertySetChangeListeners) {
            listener.containerPropertySetChange(event);
        }
    }

    protected void notifyItemSetChanged() {
        ItemSetChangeEvent event = new EntityItemSetChangeEvent<ENTITY>(this);
        for (Container.ItemSetChangeListener listener : this.itemSetChangeListeners) {
            listener.containerItemSetChange(event);
        }
    }

    @Override
    public void addListener(PropertySetChangeListener listener) {
        propertySetChangeListeners.add(listener);
    }

    @Override
    public void addPropertySetChangeListener(PropertySetChangeListener listener) {
        propertySetChangeListeners.add(listener);
    }

    @Override
    public void removePropertySetChangeListener(PropertySetChangeListener listener) {
        propertySetChangeListeners.remove(listener);
    }

    @Override
    public void removeListener(PropertySetChangeListener listener) {
        propertySetChangeListeners.remove(listener);
    }

    @Override
    public void addItemSetChangeListener(ItemSetChangeListener listener) {
        itemSetChangeListeners.add(listener);
    }

    @Override
    public void addListener(ItemSetChangeListener listener) {
        itemSetChangeListeners.add(listener);
    }

    @Override
    public void removeItemSetChangeListener(ItemSetChangeListener listener) {
        itemSetChangeListeners.remove(listener);
    }

    @Override
    public void removeListener(ItemSetChangeListener listener) {
        itemSetChangeListeners.remove(listener);
    }

    protected Filter getContainerFilter() {
        if (filters.isEmpty()) {
            return null;
        }
        if (filters.size() == 1) {
            return filters.get(0);
        }
        Filter filter = new And(filters.toArray(new Filter[] {}));
        return filter;
    }

    @Override
    public <T extends Annotation> T getAnnotation(String fieldName, Class<T> annotationClass) {
        return ReflectionUtils.getAnnotation(entityClass, fieldName, annotationClass);
    }

    protected boolean needProcess() {
        return !needsFiltering || filterSet;
    }

    @Override
    public void needsFiltering() {
        needsFiltering = true;
    }

    @Override
    public Object firstItemId() {
        if (!needProcess()) {
            return null;
        }
        List<ENTITY> entities = findAllEntities();
        if (entities.isEmpty()) {
            return null;
        }
        return entities.get(0).getId();
    }

    @Override
    public Object lastItemId() {
        if (!needProcess()) {
            return null;
        }
        List<ENTITY> entities = findAllEntities();
        if (entities.isEmpty()) {
            return null;
        }
        return entities.get(entities.size() - 1).getId();
    }

    @Override
    public Object nextItemId(Object itemId) {
        if (!needProcess()) {
            return null;
        }
        List<ENTITY> entities = findAllEntities();
        if (entities.isEmpty()) {
            return null;
        }
        int index = entities.indexOf(getItem((Long) itemId).getEntity());
        if (index == -1 || index >= entities.size() - 1) {
            return null;
        }
        return entities.get(index + 1).getId();
    }

    @Override
    public Object prevItemId(Object itemId) {
        if (!needProcess()) {
            return null;
        }
        List<ENTITY> entities = findAllEntities();
        if (entities.isEmpty()) {
            return null;
        }
        int index = entities.indexOf(getItem((Long) itemId).getEntity());
        if (index <= 0) {
            return null;
        }
        return entities.get(index - 1).getId();
    }

    @Override
    public boolean isFirstId(Object itemId) {
        if (itemId == null) {
            return false;
        }
        if (!needProcess()) {
            return false;
        }
        List<ENTITY> entities = findAllEntities();
        if (entities.isEmpty()) {
            return false;
        }
        int index = entities.indexOf(getItem((Long) itemId).getEntity());
        return index == 0;
    }

    @Override
    public boolean isLastId(Object itemId) {
        if (itemId == null) {
            return false;
        }
        if (!needProcess()) {
            return false;
        }
        List<ENTITY> entities = findAllEntities();
        if (entities.isEmpty()) {
            return false;
        }
        int index = entities.indexOf(getItem((Long) itemId).getEntity());
        return index == entities.size() - 1;
    }

    @Override
    public List<String> getPropertyNames() {
        return new ArrayList<String>(properties.keySet());
    }

    @Override
    public void addContainerFilter(Filter filter) throws UnsupportedFilterException {
        filterSet = true;
        this.filters.add(filter);
        notifyItemSetChanged();
    }

    @Override
    public void removeContainerFilter(Filter filter) {
        this.filters.remove(filter);
        notifyItemSetChanged();
    }

    @Override
    public void removeAllContainerFilters() {
        this.filters.clear();
        filterSet = false;
        notifyItemSetChanged();
    }

    @Override
    public void sort(Object[] propertyId, boolean[] ascending) {
        sortDefinitions.clear();
        for (int i = 0; i < propertyId.length; i++) {
            sortDefinitions.add(new SortDefinition(propertyId[i].toString(), (ascending.length > i) ? ascending[i] : true));
        }
        notifyItemSetChanged();
    }

    @Override
    public Collection<?> getSortableContainerPropertyIds() {
        return getPropertyNames();
    }

    @Override
    public Collection<?> getItemIds() {
        if (!needProcess()) {
            return Collections.EMPTY_LIST;
        }
        List<ENTITY> entitys = findAllEntities();
        List<Long> ids = new ArrayList<Long>(entitys.size());
        for (ENTITY entity : entitys) {
            ids.add(entity.getId());
        }
        return ids;
    }

    @Override
    public boolean containsId(Object itemId) {
        if (!needProcess()) {
            return false;
        }
        for (ENTITY item : findAllEntities()) {
            if (item.getId().equals(itemId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        if (!needProcess()) {
            return 0;
        }
        return findAllEntities().size();
    }

    @Override
    public void refreshCache() {
    }
}
