/*******************************************************************************
 * Copyright 2013 Thomas Letsch (contact@thomas-letsch.de)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.vaadin.addons.javaee.fields.factory;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.vaadin.addons.javaee.container.EntityContainer;
import org.vaadin.addons.javaee.fields.spec.FieldSpecification;

import com.vaadin.data.Container;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.TableFieldFactory;

@Singleton
public class DefaultEntityTableFieldFactory implements TableFieldFactory {

    private static final long serialVersionUID = 1L;

    @Inject
    private GlobalFieldFactory entityFieldFactory;

    private TableFieldFactory defaultTableFieldFactory = DefaultFieldFactory.get();

    @Override
    public Field<?> createField(Container container, Object itemId, Object propertyId, Component uiContext) {
        if (container instanceof EntityContainer<?>) {
            EntityContainer<?> entityContainer = (EntityContainer<?>) container;
            FieldSpecification fieldSpec = new FieldSpecification((String) propertyId);
            return entityFieldFactory.createField(entityContainer, fieldSpec);
        }
        return defaultTableFieldFactory.createField(container, itemId, propertyId, uiContext);
    }

}
