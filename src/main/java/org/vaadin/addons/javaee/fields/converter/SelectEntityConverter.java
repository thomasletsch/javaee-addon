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
package org.vaadin.addons.javaee.fields.converter;

import java.util.Locale;

import org.vaadin.addons.javaee.container.EntityContainer;
import org.vaadin.addons.javaee.container.EntityItem;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.AbstractSelect;

public class SelectEntityConverter<ENTITY extends PersistentEntity> implements Converter<Object, ENTITY> {

    private static final long serialVersionUID = 1L;

    private EntityContainer<ENTITY> container;

    private AbstractSelect select;

    public SelectEntityConverter(EntityContainer<ENTITY> subContainer, AbstractSelect select) {
        this.container = subContainer;
        this.select = select;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ENTITY convertToModel(Object value, Class<? extends ENTITY> targetType, Locale locale) throws Converter.ConversionException {
        if (value == null) {
            return null;
        }
        if (!(value instanceof Long)) {
            return null;
        }
        return ((EntityItem<ENTITY>) container.getItem(value)).getEntity();
    }

    @Override
    public Object convertToPresentation(ENTITY value, Class<? extends Object> targetType, Locale locale)
            throws Converter.ConversionException {
        if (value == null) {
            return select.getNullSelectionItemId();
        }
        return value.getId();
    }

    @Override
    public Class<ENTITY> getModelType() {
        return container.getEntityClass();
    }

    @Override
    public Class<Object> getPresentationType() {
        return Object.class;
    }

}
