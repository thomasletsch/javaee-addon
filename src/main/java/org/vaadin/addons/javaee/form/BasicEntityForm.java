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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.addons.javaee.container.EntityItem;
import org.vaadin.addons.javaee.table.BasicEntityTable;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;

public abstract class BasicEntityForm<ENTITY extends PersistentEntity> extends BasicForm<ENTITY> {

    private static final long serialVersionUID = 1L;

    private static Logger log = LoggerFactory.getLogger(BasicEntityForm.class);

    public BasicEntityForm(Class<ENTITY> entityClass) {
        super(entityClass);
    }

    public void edit(EntityItem<ENTITY> item) {
        fieldGroup.setItem(item);
    }

    public void editNew() {
        EntityItem<ENTITY> item = new EntityItem<ENTITY>(getContainer(), getDefaultValue());
        edit(item);
    }

    /**
     * Sets the columns of the default section (first section)
     */
    protected void setColumns(int columns) {
        getDefaultSection().setColumns(columns);
    }

    public ENTITY getEntity() {
        EntityItem<ENTITY> item = fieldGroup.getItem();
        return item.getEntity();
    }

    public void connectWith(BasicEntityTable<ENTITY> table) {
        table.addItemClickListener(new ItemClickListener() {

            private static final long serialVersionUID = 1L;

            @Override
            @SuppressWarnings("unchecked")
            public void itemClick(ItemClickEvent event) {
                EntityItem<ENTITY> item = (EntityItem<ENTITY>) event.getItem();
                edit(item);
            }
        });
    }

    public void save() {
        try {
            fieldGroup.commit();
        } catch (CommitException e) {
            log.error("Could not save " + entityClass.getSimpleName(), e);
        }
    }

    public boolean containsUnsavedValues() {
        return fieldGroup.isModified();
    }
}
