package org.vaadin.addons.javaee.jpa.filter;

import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.javaeeutils.jpa.PersistentEntity;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.filter.Compare.Equal;


public class EqualFilterTranslator implements FilterTranslator<Equal> {

    @Override
    public Class<Equal> getAcceptedClass() {
        return Equal.class;
    }

    @Override
    public <ENTITY extends PersistentEntity> Predicate translate(Equal filter, CriteriaBuilder builder, Root<ENTITY> root,
            Map<Class<? extends Filter>, FilterTranslator<?>> filters) {
        Expression<String> property = root.get((String) filter.getPropertyId());
        return builder.equal(property, filter.getValue());
    }

}
