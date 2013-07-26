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

import org.apache.commons.lang.StringUtils;
import org.vaadin.addons.javaee.container.EntityContainer;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.util.converter.Converter;

public class StringToEntityConverter<ENTITY extends PersistentEntity> implements Converter<String, ENTITY> {

    private static final long serialVersionUID = 1L;

    private EntityContainer<ENTITY> container;

    public StringToEntityConverter(EntityContainer<ENTITY> container) {
        this.container = container;
    }

    @Override
    public ENTITY convertToModel(String value, Class<? extends ENTITY> targetType, Locale locale) throws Converter.ConversionException {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return container.getItem(Long.parseLong(value)).getEntity();
    }

    @Override
    public String convertToPresentation(ENTITY value, Class<? extends String> targetType, Locale locale)
            throws Converter.ConversionException {
        if (value == null) {
            return "";
        }
        return "" + value.getId();
    }

    @Override
    public Class<ENTITY> getModelType() {
        return container.getEntityClass();
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }

}
