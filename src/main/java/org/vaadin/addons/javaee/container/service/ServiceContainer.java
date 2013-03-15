package org.vaadin.addons.javaee.container.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.addons.javaee.container.AbstractEntityContainer;
import org.vaadin.addons.javaee.container.EntityContainer;
import org.vaadin.addons.javaee.container.EntityItem;
import org.vaadin.addons.javaee.container.SortDefinition;
import org.vaadin.addons.javaee.container.jpa.JPAEntityContainer;
import org.vaadin.addons.javaee.container.jpa.JPAEntityProvider;

import com.googlecode.javaeeutils.jpa.PersistentEntity;

public abstract class ServiceContainer<ENTITY extends PersistentEntity> extends AbstractEntityContainer<ENTITY> {

    private static final long serialVersionUID = 1L;

    private static Logger log = LoggerFactory.getLogger(ServiceContainer.class);

    @EJB
    protected JPAEntityProvider jpaEntityProvider;

    private boolean refreshListCacheNeeded = true;

    private List<ENTITY> listCache = new ArrayList<>();

    private Map<Long, ENTITY> entityCache = new HashMap<Long, ENTITY>();

    public ServiceContainer(Class<ENTITY> entityClass) {
        super(entityClass);
    }

    protected abstract ENTITY createEntity(ENTITY entity);

    protected abstract ENTITY getEntity(Long entity);

    protected abstract ENTITY updateEntity(ENTITY entity);

    protected abstract boolean deleteEntity(Long entity);

    protected abstract List<ENTITY> findEntities();

    /**
     * Must be overwritten for being usable
     */
    @Override
    @SuppressWarnings("unchecked")
    public <SUB_ENTITY extends PersistentEntity> EntityContainer<SUB_ENTITY> getSubContainer(String propertyId) {
        Class<SUB_ENTITY> subEntityClass = (Class<SUB_ENTITY>) getType(propertyId);
        return new JPAEntityContainer<>(subEntityClass, jpaEntityProvider);
    }

    @Override
    public EntityItem<ENTITY> getItem(Long itemId) {
        ENTITY fromEntityCache = getFromEntityCache(itemId);
        return new EntityItem<ENTITY>(this, fromEntityCache);
    }

    @Override
    public void refreshItem(EntityItem<ENTITY> item) {
        item.setEntity(getFromEntityCache(item.getEntity().getId()));
    }

    @Override
    public EntityItem<ENTITY> addItem(ENTITY entity) {
        ENTITY createdEntity = createEntity(entity);
        addToEntityCache(createdEntity);
        EntityItem<ENTITY> newItem = new EntityItem<ENTITY>(this, createdEntity);
        notifyItemSetChanged();
        return newItem;
    }

    @Override
    public Object addItem() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateItem(EntityItem<ENTITY> item) {
        ENTITY updatedEntity = updateEntity(item.getEntity());
        addToEntityCache(updatedEntity);
        item.setEntity(updatedEntity);
        notifyItemSetChanged();
    }

    @Override
    public boolean removeAllItems() throws UnsupportedOperationException {
        for (ENTITY entity : findAllEntities()) {
            removeItem(entity.getId());
        }
        notifyItemSetChanged();
        return true;
    }

    @Override
    public boolean removeItem(Object itemId) throws UnsupportedOperationException {
        deleteEntity((Long) itemId);
        notifyItemSetChanged();
        return true;
    }

    @Override
    public List<ENTITY> findAllEntities() {
        return getFromListCache();
    }

    @Override
    protected void notifyItemSetChanged() {
        refreshListCacheNeeded = true;
        super.notifyItemSetChanged();
    }

    private void addToEntityCache(ENTITY entity) {
        entityCache.put(entity.getId(), entity);
    }

    private ENTITY getFromEntityCache(Long id) {
        if (!entityCache.containsKey(id)) {
            ENTITY entity = getEntity(id);
            entityCache.put(entity.getId(), entity);
        }
        ENTITY entity = entityCache.get(id);
        return entity;
    }

    private List<ENTITY> getFromListCache() {
        if (refreshListCacheNeeded) {
            refreshCache();
        }
        return listCache;
    }

    public void refreshCache() {
        listCache.clear();
        List<ENTITY> unsortedList = findEntities();
        sort(unsortedList);
        filter(unsortedList);
        listCache = unsortedList;
        refreshListCacheNeeded = false;
    }

    private void filter(List<ENTITY> unsortedList) {
        // TODO implement this
    }

    @SuppressWarnings("unchecked")
    private void sort(List<ENTITY> unsortedList) {
        if (sortDefinitions == null || sortDefinitions.isEmpty()) {
            return;
        }
        ComparatorChain chain = new ComparatorChain();
        for (SortDefinition sortDefinition : sortDefinitions) {
            BeanComparator comparator = new BeanComparator(sortDefinition.getKey());
            chain.addComparator(comparator, !sortDefinition.isAscending());
        }
        try {
            Collections.sort(unsortedList, chain);
        } catch (Exception e) {
            log.error("Could not sort by " + sortDefinitions, e);
        }
    }

}
