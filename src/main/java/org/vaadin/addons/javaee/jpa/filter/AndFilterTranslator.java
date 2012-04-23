package org.vaadin.addons.javaee.jpa.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.javaeeutils.jpa.PersistentEntity;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.filter.And;


public class AndFilterTranslator implements FilterTranslator<And> {

    @Override
    public Class<And> getAcceptedClass() {
        return And.class;
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <ENTITY extends PersistentEntity> Predicate translate(And filter, CriteriaBuilder builder, Root<ENTITY> root,
            Map<Class<? extends Filter>, FilterTranslator<?>> filters) {
        List<Predicate> predicates = new ArrayList<>();
        for (Filter innerFilter : filter.getFilters()) {
            FilterTranslator translator = filters.get(innerFilter.getClass());
            predicates.add(translator.translate(innerFilter, builder, root, filters));
        }
        return builder.and(predicates.toArray(new Predicate[] {}));
    }

}
