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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.Container.Filter;


public interface FilterTranslator<FILTER extends Filter> {

    Class<FILTER> getAcceptedClass();

    /**
     * 
     * @param filter
     *            The actual filter to translate
     * @param builder
     * @param root
     * @param filters
     *            All configured builder for recursive call of sub filter translation
     * @return
     */
    <ENTITY extends PersistentEntity> Predicate translate(FILTER filter, CriteriaBuilder builder, Root<ENTITY> root,
            Map<Class<? extends Filter>, FilterTranslator<?>> filters);
}
