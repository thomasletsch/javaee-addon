package org.vaadin.addons.javaee.jpa.filter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.javaeeutils.jpa.PersistentEntity;
import org.junit.Test;
import org.vaadin.addons.javaee.jpa.filter.SimpleStringFilterTranslator;

import com.vaadin.data.util.filter.SimpleStringFilter;


public class SimpleStringFilterTranslatorTest {

    private SimpleStringFilterTranslator translator = new SimpleStringFilterTranslator();

    @Test
    public void testGetAcceptedClass() {
        assertEquals(SimpleStringFilter.class, translator.getAcceptedClass());
    }

    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void testTranslate() {
        SimpleStringFilter filter = new SimpleStringFilter("firstName", "Thomas", false, false);
        Path path = mock(Path.class);
        Expression<String> upper = mock(Expression.class);
        CriteriaBuilder builder = mock(CriteriaBuilder.class);
        when(builder.upper(path)).thenReturn(upper);
        Root<PersistentEntity> root = mock(Root.class);
        when(root.get("firstName")).thenReturn(path);
        Predicate predicate = mock(Predicate.class);
        when(builder.like(path, "%Thomas%")).thenReturn(predicate);
        Predicate translate = translator.translate(filter, builder, root, null);
        assertEquals(predicate, translate);
    }

    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void testTranslateWithIgnoreCase() {
        SimpleStringFilter filter = new SimpleStringFilter("firstName", "Thomas", true, false);
        Path path = mock(Path.class);
        Expression<String> upper = mock(Expression.class);
        CriteriaBuilder builder = mock(CriteriaBuilder.class);
        when(builder.upper(path)).thenReturn(upper);
        Root<PersistentEntity> root = mock(Root.class);
        when(root.get("firstName")).thenReturn(path);
        Predicate predicate = mock(Predicate.class);
        when(builder.like(upper, "%THOMAS%")).thenReturn(predicate);
        Predicate translate = translator.translate(filter, builder, root, null);
        assertEquals(predicate, translate);
    }

}
