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
package org.vaadin.addons.javaee.container.jpa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.addons.javaee.container.SortDefinition;
import org.vaadin.addons.javaee.container.jpa.filter.FilterToQueryTranslator;

import com.googlecode.javaeeutils.jpa.JPAConstants;
import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.Container.Filter;

@Stateless
public class JPAEntityProvider {

    private static Logger log = LoggerFactory.getLogger(JPAEntityProvider.class);

    @PersistenceContext
    EntityManager entityManager;

    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

    private Map<Class<?>, EntityType<?>> entityTypeCache;

    public <ENTITY extends PersistentEntity> ENTITY get(Class<ENTITY> entityClass, Long id) {
        ENTITY entity = entityManager.find(entityClass, id);
        return entity;
    }

    public <ENTITY extends PersistentEntity> ENTITY loadEntityWithRelations(ENTITY entity) {
        ENTITY mergedEntity = entityManager.merge(entity);
        mergedEntity.loadNeededRelations();
        return mergedEntity;
    }

    public <ENTITY extends PersistentEntity> List<ENTITY> find(Class<ENTITY> entityClass, Filter filter,
            List<SortDefinition> sortDefinitions) {
        CriteriaQuery<ENTITY> criteriaQuery = createCriteriaQuery(entityClass, filter, sortDefinitions);
        TypedQuery<ENTITY> query = createQuery(criteriaQuery);
        List<ENTITY> resultList = query.getResultList();
        return resultList;
    }

    @SuppressWarnings("unchecked")
    public <ENTITY extends PersistentEntity> Long size(Class<ENTITY> entityClass, Filter filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<ENTITY> root = query.from(entityClass);
        query = query.select(builder.count(root));
        query = (CriteriaQuery<Long>) addFilterCriteria(filter, builder, root, query);
        Long count = createQuery(query).getSingleResult();
        return count;
    }

    public <ENTITY extends PersistentEntity> boolean contains(Class<ENTITY> entityClass, Long id) {
        return get(entityClass, id) != null;
    }

    public <ENTITY extends PersistentEntity> boolean remove(Class<ENTITY> entityClass, Long itemId) {
        PersistentEntity entity = get(entityClass, itemId);
        if (entity == null) {
            return false;
        }
        entityManager.remove(entity);
        return true;
    }

    public <ENTITY extends PersistentEntity> boolean removeAll(Class<ENTITY> entityClass, Filter filter,
            List<SortDefinition> sortDefinitions) {
        List<ENTITY> list = find(entityClass, filter, sortDefinitions);
        for (ENTITY entity : list) {
            entityManager.remove(entity);
        }
        return true;
    }

    public <ENTITY extends PersistentEntity> ENTITY createEntity(ENTITY entity) {
        entityManager.persist(entity);
        entityManager.flush();
        return entity;
    }

    public <ENTITY extends PersistentEntity> ENTITY createEntity(Class<ENTITY> entityClass) {
        ENTITY entity;
        try {
            entity = entityClass.newInstance();
            entityManager.persist(entity);
            entityManager.flush();
            return entity;
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("Could not instanciate " + entityClass, e);
            throw new RuntimeException(e);
        }
    }

    public <ENTITY extends PersistentEntity> ENTITY updateEntity(ENTITY entity) {
        return entityManager.merge(entity);
    }

    public void refreshEntity(PersistentEntity entity) {
        entityManager.refresh(entity);
    }

    private <ENTITY> TypedQuery<ENTITY> createQuery(CriteriaQuery<ENTITY> criteriaQuery) {
        TypedQuery<ENTITY> query = entityManager.createQuery(criteriaQuery);
        query.setHint(JPAConstants.HINT_HIBERNATE_CACHABLE, JPAConstants.CACHABLE_TRUE);
        return query;
    }

    @SuppressWarnings("unchecked")
    private <ENTITY> CriteriaQuery<ENTITY> createCriteriaQuery(Class<ENTITY> entityClass, Filter filter,
            List<SortDefinition> sortDefinitions) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ENTITY> query = builder.createQuery(entityClass);
        Root<ENTITY> root = query.from(entityClass);
        query = (CriteriaQuery<ENTITY>) addFilterCriteria(filter, builder, root, query);
        query = (CriteriaQuery<ENTITY>) addSortCriteria(sortDefinitions, builder, root, query);
        return query;
    }

    private <ENTITY> CriteriaQuery<?> addSortCriteria(List<SortDefinition> sortDefinitions, CriteriaBuilder builder, Root<ENTITY> root,
            CriteriaQuery<?> query) {
        List<Order> orders = new ArrayList<>();
        for (SortDefinition sortDefinition : sortDefinitions) {
            Order order;
            if (sortDefinition.isAscending()) {
                order = builder.asc(root.get(sortDefinition.getKey()));
            } else {
                order = builder.desc(root.get(sortDefinition.getKey()));
            }
            orders.add(order);
        }
        query.orderBy(orders);
        return query;

    }

    private <ENTITY> CriteriaQuery<?> addFilterCriteria(Filter filter, CriteriaBuilder builder, Root<ENTITY> root, CriteriaQuery<?> query) {
        if (filter != null) {
            FilterToQueryTranslator translator = new FilterToQueryTranslator();
            Predicate filterPredicate = translator.translate(filter, builder, root);
            query = query.where(filterPredicate);
        }
        return query;
    }

    public Class<?> getType(Class<?> entityClass, String propertyId) {
        if (entityTypeCache == null) {
            initializeEntityTypeCache();
        }
        Class<?> result = getClassOfProperty(propertyId, entityTypeCache.get(entityClass));
        return result;
    }

    private Class<?> getClassOfProperty(String propertyId, EntityType<?> entityType) {
        if (propertyId.contains(".")) {
            String[] parts = propertyId.split("\\.", 2);
            Attribute<?, ?> attribute = entityType.getAttribute(parts[0]);
            Class<?> javaType = attribute.getJavaType();
            return getType(javaType, parts[1]);
        }
        Class<?> result;
        Attribute<?, ?> attribute = entityType.getAttribute(propertyId);
        if (attribute instanceof PluralAttribute) {
            PluralAttribute<?, ?, ?> pAttribute = (PluralAttribute<?, ?, ?>) attribute;
            Type<?> elementType = pAttribute.getElementType();
            result = elementType.getJavaType();
        } else {
            result = attribute.getJavaType();
        }
        return result;
    }

    private void initializeEntityTypeCache() {
        Metamodel metamodel = entityManager.getMetamodel();
        entityTypeCache = new HashMap<Class<?>, EntityType<?>>();
        for (EntityType<?> entityType : metamodel.getEntities()) {
            entityTypeCache.put(entityType.getJavaType(), entityType);
        }
    }

}
