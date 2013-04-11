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

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.filter.And;

public class AndFilterTranslator implements ExampleFilterTranslator<And> {

    @Override
    public Class<And> getAcceptedClass() {
        return And.class;
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <ENTITY extends PersistentEntity> ENTITY translate(And filter, ENTITY example,
            Map<Class<? extends Filter>, ExampleFilterTranslator<?>> filters) {
        for (Filter innerFilter : filter.getFilters()) {
            ExampleFilterTranslator translator = filters.get(innerFilter.getClass());
            translator.translate(innerFilter, example, filters);
        }
        return example;
    }

}
