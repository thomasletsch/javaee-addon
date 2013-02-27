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
package org.vaadin.addons.javaee.form;

import javax.enterprise.context.Dependent;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.util.BeanItem;

@Dependent
public abstract class BasicSearchForm<ENTITY extends PersistentEntity> extends BasicForm<ENTITY> {

    private static final long serialVersionUID = 1L;

    public BasicSearchForm(Class<ENTITY> entityClass) {
        super(entityClass);
    }

    @Override
    protected void initFields() {
        BeanItem<ENTITY> itemDataSource = new BeanItem<ENTITY>(getDefaultValue());
        fieldGroup.setItemDataSource(itemDataSource);
        super.initFields();
    }

}
