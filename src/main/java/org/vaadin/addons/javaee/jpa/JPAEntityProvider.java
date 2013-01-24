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

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.vaadin.addons.javaee.jpa.filter.FilterToQueryTranslator;

import com.googlecode.javaeeutils.jpa.JPAConstants;
import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.googlecode.javaeeutils.jpa.PersistentEntity_;
import com.vaadin.data.Container.Filter;

@Stateless
public class JPAEntityProvider {

    private static Log log = LogFactory.getLog(JPAEntityProvider.class);

    @PersistenceContext
    EntityManager entityManager;

    public <ENTITY extends PersistentEntity> ENTITY get(Class<ENTITY> entityClass, Long id) {
        return entityManager.find(entityClass, id);
    }

    public <ENTITY extends PersistentEntity> List<ENTITY> find(Class<ENTITY> entityClass, Filter filter) {
        CriteriaQuery<ENTITY> criteriaQuery = createCriteriaQuery(entityClass, filter);
        TypedQuery<ENTITY> query = createQuery(criteriaQuery);
        List<ENTITY> resultList = query.getResultList();
        return resultList;
    }

    public <ENTITY extends PersistentEntity> ENTITY getFirst(Class<ENTITY> entityClass, Filter filter) {
        CriteriaQuery<ENTITY> criteriaQuery = createCriteriaQuery(entityClass, filter);
        criteriaQuery = orderByPk(criteriaQuery, true);
        TypedQuery<ENTITY> query = createQuery(criteriaQuery);
        query.setMaxResults(1);
        return getSingleResultOrNull(query);
    }

    public <ENTITY extends PersistentEntity> ENTITY getLast(Class<ENTITY> entityClass, Filter filter) {
        CriteriaQuery<ENTITY> criteriaQuery = createCriteriaQuery(entityClass, filter);
        criteriaQuery = orderByPk(criteriaQuery, false);
        TypedQuery<ENTITY> query = createQuery(criteriaQuery);
        query.setMaxResults(1);
        return getSingleResultOrNull(query);
    }

    public <ENTITY extends PersistentEntity> ENTITY getNext(Class<ENTITY> entityClass, Long id, Filter filter) {
        CriteriaQuery<ENTITY> criteriaQuery = createCriteriaQuery(entityClass, filter);
        criteriaQuery = orderByPk(criteriaQuery, true);
        criteriaQuery = greaterThan(criteriaQuery, id);
        TypedQuery<ENTITY> query = createQuery(criteriaQuery);
        query.setMaxResults(1);
        return getSingleResultOrNull(query);
    }

    public <ENTITY extends PersistentEntity> ENTITY getPrev(Class<ENTITY> entityClass, Long id, Filter filter) {
        CriteriaQuery<ENTITY> criteriaQuery = createCriteriaQuery(entityClass, filter);
        criteriaQuery = orderByPk(criteriaQuery, false);
        criteriaQuery = lessThan(criteriaQuery, id);
        TypedQuery<ENTITY> query = createQuery(criteriaQuery);
        query.setMaxResults(1);
        return getSingleResultOrNull(query);
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

    public <ENTITY extends PersistentEntity> boolean removeAll(Class<ENTITY> entityClass, Filter filter) {
        List<ENTITY> list = find(entityClass, filter);
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

    private <ENTITY> TypedQuery<ENTITY> createQuery(CriteriaQuery<ENTITY> criteriaQuery) {
        TypedQuery<ENTITY> query = entityManager.createQuery(criteriaQuery);
        query.setHint(JPAConstants.HINT_HIBERNATE_CACHABLE, JPAConstants.CACHABLE_TRUE);
        return query;
    }

    @SuppressWarnings("unchecked")
    private <ENTITY> CriteriaQuery<ENTITY> createCriteriaQuery(Class<ENTITY> entityClass, Filter filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ENTITY> query = builder.createQuery(entityClass);
        Root<ENTITY> root = query.from(entityClass);
        query = (CriteriaQuery<ENTITY>) addFilterCriteria(filter, builder, root, query);
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

    @SuppressWarnings("unchecked")
    private <ENTITY extends PersistentEntity> CriteriaQuery<ENTITY> orderByPk(CriteriaQuery<ENTITY> criteriaQuery, boolean asc) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        Root<ENTITY> root = (Root<ENTITY>) criteriaQuery.getRoots().iterator().next();
        if (asc) {
            return criteriaQuery.orderBy(builder.asc(root.get(PersistentEntity_.id)));
        }
        return criteriaQuery.orderBy(builder.desc(root.get(PersistentEntity_.id)));
    }

    @SuppressWarnings("unchecked")
    private <ENTITY extends PersistentEntity> CriteriaQuery<ENTITY> greaterThan(CriteriaQuery<ENTITY> criteriaQuery, Long id) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        Root<ENTITY> root = (Root<ENTITY>) criteriaQuery.getRoots().iterator().next();
        Predicate gt = builder.greaterThan(root.get(PersistentEntity_.id), id);
        Predicate newWhere = null;
        if (criteriaQuery.getRestriction() == null) {
            newWhere = gt;
        } else {
            newWhere = builder.and(criteriaQuery.getRestriction(), gt);
        }
        return criteriaQuery.where(newWhere);
    }

    @SuppressWarnings("unchecked")
    private <ENTITY extends PersistentEntity> CriteriaQuery<ENTITY> lessThan(CriteriaQuery<ENTITY> criteriaQuery, Long id) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        Root<ENTITY> root = (Root<ENTITY>) criteriaQuery.getRoots().iterator().next();
        Predicate lt = builder.lessThan(root.get(PersistentEntity_.id), id);
        Predicate newWhere = null;
        if (criteriaQuery.getRestriction() == null) {
            newWhere = lt;
        } else {
            newWhere = builder.and(criteriaQuery.getRestriction(), lt);
        }
        return criteriaQuery.where(newWhere);
    }

    private <ENTITY> ENTITY getSingleResultOrNull(TypedQuery<ENTITY> query) {
        List<ENTITY> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

}
