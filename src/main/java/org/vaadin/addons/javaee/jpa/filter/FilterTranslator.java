package org.vaadin.addons.javaee.jpa.filter;

import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.Container.Filter;


public interface FilterTranslator<FILTER extends Filter> {

    Class<FILTER> getAcceptedClass();

    /**
     * 
     * @param filter
     *            The actual filter to translate
     * @param builder
     * @param root
     * @param filters
     *            All configured builder for recursive call of sub filter translation
     * @return
     */
    <ENTITY extends PersistentEntity> Predicate translate(FILTER filter, CriteriaBuilder builder, Root<ENTITY> root,
            Map<Class<? extends Filter>, FilterTranslator<?>> filters);
}
