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
package org.vaadin.addons.javaee.fields;

import org.vaadin.addons.javaee.container.EntityContainer;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.Property;
import com.vaadin.ui.ComboBox;

public class ManyToOneRelationField<ENTITY extends PersistentEntity> extends ComboBox {

    private static final long serialVersionUID = 1L;

    public ManyToOneRelationField() {
    }

    @SuppressWarnings("unchecked")
    public ENTITY getSelectedEntity() {
        if (getValue() != null && getValue() instanceof Long && getContainerDataSource() instanceof EntityContainer) {
            Long id = (Long) getValue();
            EntityContainer<ENTITY> containerDataSource = (EntityContainer<ENTITY>) getContainerDataSource();
            return containerDataSource.getItem(id).getEntity();
        }
        return null;
    }

    @Override
    protected void setValue(Object newValue, boolean repaintIsNotNeeded) throws Property.ReadOnlyException {
        if (newValue instanceof PersistentEntity) {
            PersistentEntity persistentEntity = (PersistentEntity) newValue;
            super.setValue(persistentEntity.getId(), repaintIsNotNeeded);
        } else {
            super.setValue(newValue, repaintIsNotNeeded);
        }
    }

}
