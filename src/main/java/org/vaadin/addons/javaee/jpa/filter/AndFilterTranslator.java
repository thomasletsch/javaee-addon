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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.filter.And;

public class AndFilterTranslator implements FilterTranslator<And> {

    @Override
    public Class<And> getAcceptedClass() {
        return And.class;
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <ENTITY extends PersistentEntity> Predicate translate(And filter, CriteriaBuilder builder, Root<ENTITY> root,
            Map<Class<? extends Filter>, FilterTranslator<?>> filters) {
        List<Predicate> predicates = new ArrayList<>();
        for (Filter innerFilter : filter.getFilters()) {
            FilterTranslator translator = filters.get(innerFilter.getClass());
            predicates.add(translator.translate(innerFilter, builder, root, filters));
        }
        return builder.and(predicates.toArray(new Predicate[] {}));
    }

}
