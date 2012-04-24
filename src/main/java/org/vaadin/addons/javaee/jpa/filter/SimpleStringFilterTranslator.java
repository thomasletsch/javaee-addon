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

import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.filter.SimpleStringFilter;


public class SimpleStringFilterTranslator implements FilterTranslator<SimpleStringFilter> {

    @Override
    public Class<SimpleStringFilter> getAcceptedClass() {
        return SimpleStringFilter.class;
    }

    @Override
    public <ENTITY extends PersistentEntity> Predicate translate(SimpleStringFilter filter, CriteriaBuilder builder, Root<ENTITY> root,
            Map<Class<? extends Filter>, FilterTranslator<?>> filters) {
        final SimpleStringFilter ssf = filter;
        String value = ssf.getFilterString();
        Expression<String> property = root.get((String) ssf.getPropertyId());
        if (ssf.isIgnoreCase()) {
            property = builder.upper(property);
            value = value.toUpperCase();
        }
        if (ssf.isOnlyMatchPrefix()) {
            return builder.like(property, value + "%");
        } else {
            return builder.like(property, "%" + value + "%");
        }
    }

}
