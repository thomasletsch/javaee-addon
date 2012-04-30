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
package org.vaadin.addons.javaee.table;

import org.vaadin.addons.javaee.jpa.EntityContainer;

import com.googlecode.javaeeutils.jpa.PersistentEntity;

public class GenericEntityTable<ENTITY extends PersistentEntity> extends BasicEntityTable<ENTITY> {

    private EntityContainer<ENTITY> entityContainer;

    public GenericEntityTable(Class<ENTITY> entityClass) {
        super(entityClass);
    }

    @Override
    public EntityContainer<ENTITY> getContainer() {
        return entityContainer;
    }

    public void setContainer(EntityContainer<ENTITY> entityContainer) {
        this.entityContainer = entityContainer;
    }

}
