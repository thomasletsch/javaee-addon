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
package org.vaadin.addons.javaee.container.jpa.filter;

import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.filter.Not;

public class NotFilterTranslator implements FilterTranslator<Not> {

    @Override
    public Class<Not> getAcceptedClass() {
        return Not.class;
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <ENTITY extends PersistentEntity> Predicate translate(Not filter, CriteriaBuilder builder, Root<ENTITY> root,
            Map<Class<? extends Filter>, FilterTranslator<?>> filters) {
        FilterTranslator translator = filters.get(filter.getFilter().getClass());
        Expression<Boolean> predicate = translator.translate(filter.getFilter(), builder, root, filters);
        return builder.not(predicate);
    }

}
