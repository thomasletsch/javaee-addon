package org.vaadin.addons.javaee.jpa.filter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.Test;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.filter.SimpleStringFilter;

public class FilterToQueryTranslatorTest {

    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void testTranslate() {
        FilterToQueryTranslator queryTranslator = new FilterToQueryTranslator();
        Filter filter = new SimpleStringFilter("firstName", "Thomas", false, false);
        Path path = mock(Path.class);
        Expression<String> upper = mock(Expression.class);
        CriteriaBuilder builder = mock(CriteriaBuilder.class);
        when(builder.upper(path)).thenReturn(upper);
        Root<PersistentEntity> root = mock(Root.class);
        when(root.get("firstName")).thenReturn(path);
        Predicate predicate = mock(Predicate.class);
        when(builder.like(path, "%Thomas%")).thenReturn(predicate);
        Predicate translate = queryTranslator.translate(filter, builder, root);
        assertEquals(predicate, translate);
    }

}
