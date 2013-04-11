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

import java.util.HashMap;
import java.util.Map;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.Container.Filter;

public class FilterToExampleTranslator {

    private Map<Class<? extends Filter>, ExampleFilterTranslator<?>> filters = new HashMap<>();

    public FilterToExampleTranslator() {
        addFilterTranslator(new SimpleStringFilterTranslator());
        addFilterTranslator(new AndFilterTranslator());
        addFilterTranslator(new EqualFilterTranslator());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <ENTITY extends PersistentEntity> ENTITY translate(Filter filter, ENTITY example) {
        ExampleFilterTranslator translator = filters.get(filter.getClass());
        return (ENTITY) translator.translate(filter, example, filters);
    }

    private void addFilterTranslator(ExampleFilterTranslator<?> filterTranslator) {
        filters.put(filterTranslator.getAcceptedClass(), filterTranslator);
    }

}
