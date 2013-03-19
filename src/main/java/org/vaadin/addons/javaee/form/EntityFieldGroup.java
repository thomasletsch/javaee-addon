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
package org.vaadin.addons.javaee.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.vaadin.addons.javaee.container.EntityItem;
import org.vaadin.addons.javaee.fields.NumberField;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.filter.And;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Field;

public class EntityFieldGroup<ENTITY extends PersistentEntity> extends FieldGroup {

    private static final long serialVersionUID = 1L;

    private EntityItem<ENTITY> item;

    public EntityFieldGroup() {
    }

    public void setItem(EntityItem<ENTITY> item) {
        this.item = item;
        setItemDataSource(item);
    }

    public EntityItem<ENTITY> getItem() {
        return item;
    }

    public Field<?> getFirstField() {
        return getFields().iterator().next();
    }

    public Filter getValuesAsFilter() {
        List<Filter> filters = new ArrayList<>();
        for (Field<?> field : getFields()) {
            if (field == null || field.getValue() == null) {
                continue;
            }
            if (field instanceof NumberField) {
                NumberField numberField = (NumberField) field;
                filters.add(new Compare.Equal(getPropertyId(field), numberField.getValue()));
            } else if (field instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) field;
                filters.add(new Compare.Equal(getPropertyId(field), checkBox.getValue()));
            } else if (field instanceof AbstractSelect) {
                AbstractSelect abstractSelect = (AbstractSelect) field;
                filters.add(new Compare.Equal(getPropertyId(field), abstractSelect.getValue()));
            } else if (!StringUtils.isBlank(getStringValue(field))) {
                filters.add(new SimpleStringFilter(getPropertyId(field), getStringValue(field), false, false));
            }
        }
        Filter filter = new And(filters.toArray(new Filter[] {}));
        return filter;
    }

    @Override
    public void commit() throws CommitException {
        super.commit();
        item.commit();
    }

    private String getStringValue(Field<?> field) {
        if (field.getValue() == null) {
            return null;
        }
        return field.getValue().toString();
    }

}
