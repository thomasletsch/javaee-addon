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
package org.vaadin.addons.javaee.jpa;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.filter.And;
import com.vaadin.data.util.filter.UnsupportedFilterException;

public class EntityContainer<ENTITY extends PersistentEntity> implements Container, Container.Ordered, Container.Filterable,
        Container.ItemSetChangeNotifier, Container.PropertySetChangeNotifier {

    @SuppressWarnings("unused")
    private static Log log = LogFactory.getLog(EntityContainer.class);

    @EJB
    private JPAEntityProvider jpaEntityProvider;

    private List<Container.ItemSetChangeListener> itemSetChangeListeners = new ArrayList<>();

    private List<Container.PropertySetChangeListener> propertySetChangeListeners = new ArrayList<>();

    private List<Filter> filters = new ArrayList<>();

    protected Map<String, Class<?>> properties = new HashMap<>();

    protected Class<ENTITY> entityClass;

    private boolean enabled = true;

    public EntityContainer(Class<ENTITY> entityClass) {
        this.entityClass = entityClass;
        initProperties(entityClass);
    }

    public EntityContainer(Class<ENTITY> entityClass, boolean enabled) {
        this(entityClass);
        this.enabled = enabled;
    }

    public Class<ENTITY> getEntityClass() {
        return entityClass;
    }

    public void updateItem(EntityItem<ENTITY> item) {
        assert (item.getEntity().getId() != null);
        jpaEntityProvider.updateEntity(item.getEntity());
    }

    public void enable() {
        this.enabled = true;
        notifyItemSetChanged();
    }

    private void initProperties(Class<ENTITY> entityClass) {
        for (Field field : ReflectionUtils.getAllFields(entityClass)) {
            properties.put(field.getName(), field.getType());
        }
    }

    public EntityItem<ENTITY> getItem(Long itemId) {
        ENTITY entity = jpaEntityProvider.get(entityClass, itemId);
        EntityItem<ENTITY> item = new EntityItem<ENTITY>(this, entity);
        return item;
    }

    @Override
    public Item getItem(Object itemId) {
        return getItem((Long) itemId);
    }

    @Override
    public Collection<?> getItemIds() {
        List<ENTITY> entitys = jpaEntityProvider.find(entityClass, getContainerFilter());
        List<Long> ids = new ArrayList<Long>(entitys.size());
        for (ENTITY entity : entitys) {
            ids.add(entity.getId());
        }
        return ids;
    }

    @Override
    public int size() {
        if (!enabled) {
            return 0;
        }
        int size = jpaEntityProvider.size(entityClass, getContainerFilter()).intValue();
        return size;
    }

    @Override
    public boolean containsId(Object itemId) {
        boolean contains = jpaEntityProvider.contains(entityClass, (Long) itemId);
        return contains;
    }

    @Override
    public boolean removeItem(Object itemId) throws UnsupportedOperationException {
        boolean result = jpaEntityProvider.remove(entityClass, (Long) itemId);
        notifyItemSetChanged();
        return result;
    }

    @Override
    public boolean removeAllItems() throws UnsupportedOperationException {
        boolean result = jpaEntityProvider.removeAll(entityClass, getContainerFilter());
        notifyItemSetChanged();
        return result;
    }

    @Override
    public Object addItem() throws UnsupportedOperationException {
        Long itemId = jpaEntityProvider.createEntity(entityClass).getId();
        notifyItemSetChanged();
        return itemId;
    }

    public EntityItem<ENTITY> addItem(ENTITY entity) {
        jpaEntityProvider.createEntity(entity);
        EntityItem<ENTITY> item = new EntityItem<ENTITY>(this, entity);
        return item;
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
    public Class<?> getType(Object propertyId) {
        Class<?> type = properties.get(propertyId);
        if (type == null) {
            type = ReflectionUtils.getType(entityClass, (String) propertyId);
            if (type != null)
                properties.put((String) propertyId, type);
        }
        return type;
    }

    @Override
    public Object firstItemId() {
        ENTITY entity = jpaEntityProvider.getFirst(entityClass, getContainerFilter());
        return getNullOrPk(entity);
    }

    @Override
    public Object lastItemId() {
        ENTITY entity = jpaEntityProvider.getLast(entityClass, getContainerFilter());
        return getNullOrPk(entity);
    }

    @Override
    public Object nextItemId(Object itemId) {
        ENTITY entity = jpaEntityProvider.getNext(entityClass, (Long) itemId, getContainerFilter());
        return getNullOrPk(entity);
    }

    @Override
    public Object prevItemId(Object itemId) {
        ENTITY entity = jpaEntityProvider.getPrev(entityClass, (Long) itemId, getContainerFilter());
        return getNullOrPk(entity);
    }

    @Override
    public boolean isFirstId(Object itemId) {
        if (itemId == null)
            return false;
        return itemId.equals(firstItemId());
    }

    @Override
    public boolean isLastId(Object itemId) {
        if (itemId == null)
            return false;
        return itemId.equals(lastItemId());
    }

    public List<String> getPropertyNames() {
        return new ArrayList<String>(properties.keySet());
    }

    @Override
    public void addContainerFilter(Filter filter) throws UnsupportedFilterException {
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
        notifyItemSetChanged();
    }

    private Object getNullOrPk(ENTITY entity) {
        if (entity == null)
            return null;
        else
            return entity.getId();
    }

    private void notifyPropertySetChanged() {
        PropertySetChangeEvent event = new JPAPropertySetChangeEvent<ENTITY>(this);
        for (Container.PropertySetChangeListener listener : this.propertySetChangeListeners)
            listener.containerPropertySetChange(event);
    }

    private void notifyItemSetChanged() {
        ItemSetChangeEvent event = new JPAItemSetChangeEvent<ENTITY>(this);
        for (Container.ItemSetChangeListener listener : this.itemSetChangeListeners)
            listener.containerItemSetChange(event);
    }

    @Override
    public void addListener(PropertySetChangeListener listener) {
        propertySetChangeListeners.add(listener);
    }

    @Override
    public void removeListener(PropertySetChangeListener listener) {
        propertySetChangeListeners.remove(listener);
    }

    @Override
    public void addListener(ItemSetChangeListener listener) {
        itemSetChangeListeners.add(listener);
    }

    @Override
    public void removeListener(ItemSetChangeListener listener) {
        itemSetChangeListeners.remove(listener);
    }

    private Filter getContainerFilter() {
        if (filters.isEmpty()) {
            return null;
        }
        if (filters.size() == 1) {
            return filters.get(0);
        }
        Filter filter = new And(filters.toArray(new Filter[] {}));
        return filter;
    }

    public <T extends Annotation> T getAnnotation(String fieldName, Class<T> annotationClass) {
        return ReflectionUtils.getAnnotation(entityClass, fieldName, annotationClass);
    }

}