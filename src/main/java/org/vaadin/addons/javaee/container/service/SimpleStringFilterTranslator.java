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
package org.vaadin.addons.javaee.container.service;

import java.util.Map;

import jodd.bean.BeanUtil;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.filter.SimpleStringFilter;

public class SimpleStringFilterTranslator implements ExampleFilterTranslator<SimpleStringFilter> {

    @Override
    public Class<SimpleStringFilter> getAcceptedClass() {
        return SimpleStringFilter.class;
    }

    @Override
    public <ENTITY extends PersistentEntity> ENTITY translate(SimpleStringFilter filter, ENTITY example,
            Map<Class<? extends Filter>, ExampleFilterTranslator<?>> filters) {
        String propertyId = (String) filter.getPropertyId();
        BeanUtil.setPropertyForced(example, propertyId, filter.getFilterString());
        return example;
    }

}
