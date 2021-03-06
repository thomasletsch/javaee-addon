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
package org.vaadin.addons.javaee.fields.factory;

import org.vaadin.addons.javaee.container.EntityContainer;
import org.vaadin.addons.javaee.fields.ManyToOneRelationField;
import org.vaadin.addons.javaee.fields.converter.SelectEntityConverter;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.ui.AbstractSelect;

public class ManyToOneRelationFieldCreator<FIELD extends AbstractSelect, ENTITY extends PersistentEntity> extends
        AbstractFieldCreator<FIELD> {

    @Override
    @SuppressWarnings("unchecked")
    protected Class<FIELD> getDefaultFieldType() {
        return (Class<FIELD>) ManyToOneRelationField.class;
    }

    @Override
    protected void initializeField(FIELD field) {
        super.initializeField(field);
        EntityContainer<ENTITY> subContainer = container.getSubContainer(fieldSpec.getName());
        field.setItemCaptionPropertyId(fieldSpec.getVisibleProperty());
        field.setContainerDataSource(subContainer);
        field.setConverter(new SelectEntityConverter<ENTITY>(subContainer, field));
    }

}
