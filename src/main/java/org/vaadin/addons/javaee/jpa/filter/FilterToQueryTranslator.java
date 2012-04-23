package org.vaadin.addons.javaee.jpa.filter;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.javaeeutils.jpa.PersistentEntity;

import com.vaadin.data.Container.Filter;


public class FilterToQueryTranslator {

    private Map<Class<? extends Filter>, FilterTranslator<?>> filters = new HashMap<>();

    public FilterToQueryTranslator() {
        addFilterTranslator(new SimpleStringFilterTranslator());
        addFilterTranslator(new AndFilterTranslator());
        addFilterTranslator(new EqualFilterTranslator());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <ENTITY extends PersistentEntity> Predicate translate(Filter filter, CriteriaBuilder builder, Root<?> root) {
        FilterTranslator translator = filters.get(filter.getClass());
        return translator.translate(filter, builder, root, filters);
    }

    private void addFilterTranslator(FilterTranslator<?> filterTranslator) {
        filters.put(filterTranslator.getAcceptedClass(), filterTranslator);
    }

}
