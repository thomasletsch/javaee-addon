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
package org.vaadin.addons.javaee.fields.factory;

import java.util.Iterator;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.vaadin.addons.javaee.container.EntityContainer;
import org.vaadin.addons.javaee.fields.spec.FieldSpecification;
import org.vaadin.addons.javaee.jpa.JPAEntityContainer;

import com.vaadin.data.Container;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.TableFieldFactory;

/**
 * Checks all FieldFactories and TableFieldFactories in the CDI scope to find the corrent one to use.
 * 
 * For extending with own factories, just create a CDI bean which implements FieldFactory or TableFieldFactory and return null for standard
 * fields.
 * 
 * @author thomas
 * 
 */
@Singleton
public class GlobalFieldFactory implements TableFieldFactory {

    private static final long serialVersionUID = 1L;

    @Inject
    private Instance<EntityFieldFactory> fieldFactories;

    @Inject
    private DefaultEntityFieldFactory defaultEntityFieldFactory;

    private TableFieldFactory defaultTableFieldFactory = DefaultFieldFactory.get();

    @SuppressWarnings("unchecked")
    public <T extends Field<?>> T createField(EntityContainer<?> container, FieldSpecification fieldSpec) {
        Iterator<EntityFieldFactory> iterator = fieldFactories.iterator();
        while (iterator.hasNext()) {
            EntityFieldFactory fieldFactory = iterator.next();
            if (fieldFactory instanceof DefaultEntityFieldFactory) {
                continue;
            }
            Field<?> field = fieldFactory.createField(container, fieldSpec);
            if (field != null) {
                return (T) field;
            }
        }
        return defaultEntityFieldFactory.createField(container, fieldSpec);
    }

    @Override
    public Field<?> createField(Container container, Object itemId, Object propertyId, Component uiContext) {
        if (container instanceof EntityContainer<?>) {
            JPAEntityContainer<?> entityContainer = (JPAEntityContainer<?>) container;
            FieldSpecification fieldSpec = new FieldSpecification((String) propertyId);
            Field<?> field = createField(entityContainer, fieldSpec);
            if (field != null) {
                return field;
            }
        }
        return defaultTableFieldFactory.createField(container, itemId, propertyId, uiContext);
    }

}
