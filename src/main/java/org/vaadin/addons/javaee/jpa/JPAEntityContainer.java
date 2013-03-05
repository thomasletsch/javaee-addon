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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;

import org.vaadin.addons.javaee.container.AbstractEntityContainer;
import org.vaadin.addons.javaee.container.EntityContainer;
import org.vaadin.addons.javaee.container.EntityItem;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.Item;

public class JPAEntityContainer<ENTITY extends PersistentEntity> extends AbstractEntityContainer<ENTITY> {

    private static final long serialVersionUID = 1L;

    @EJB
    private JPAEntityProvider jpaEntityProvider;

    public JPAEntityContainer(Class<ENTITY> entityClass, JPAEntityProvider jpaEntityProvider) {
        this(entityClass);
        this.jpaEntityProvider = jpaEntityProvider;
    }

    public JPAEntityContainer(Class<ENTITY> entityClass) {
        this.entityClass = entityClass;
        initProperties(entityClass);
    }

    @Override
    public <SUB_ENTITY extends PersistentEntity> EntityContainer<SUB_ENTITY> getSubContainer(Class<SUB_ENTITY> entityClass) {
        return new JPAEntityContainer<>(entityClass, jpaEntityProvider);
    }

    @Override
    public void updateItem(EntityItem<ENTITY> item) {
        assert (item.getEntity().getId() != null);
        jpaEntityProvider.updateEntity(item.getEntity());
    }

    @Override
    public void refreshItem(EntityItem<ENTITY> item) {
        assert (item.getEntity().getId() != null);
        jpaEntityProvider.refreshEntity(item.getEntity());
    }

    @Override
    public void loadItemWithRelations(EntityItem<ENTITY> item) {
        ENTITY loadedEntity = jpaEntityProvider.loadEntityWithRelations(item.getEntity());
        item.setEntity(loadedEntity);
    }

    @Override
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
    public int size() {
        if (!needProcess()) {
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
        boolean result = jpaEntityProvider.removeAll(entityClass, getContainerFilter(), sortDefinitions);
        notifyItemSetChanged();
        return result;
    }

    @Override
    public Object addItem() throws UnsupportedOperationException {
        Long itemId = jpaEntityProvider.createEntity(entityClass).getId();
        notifyItemSetChanged();
        return itemId;
    }

    @Override
    public EntityItem<ENTITY> addItem(ENTITY entity) {
        jpaEntityProvider.createEntity(entity);
        EntityItem<ENTITY> item = new EntityItem<ENTITY>(this, entity);
        return item;
    }

    @Override
    public Class<?> getCollectionType(String propertyId) {
        return jpaEntityProvider.getType(entityClass, propertyId);
    }

    @Override
    public List<ENTITY> findAllEntities() {
        return jpaEntityProvider.find(entityClass, getContainerFilter(), sortDefinitions);
    }
}