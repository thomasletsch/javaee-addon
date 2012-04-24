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
