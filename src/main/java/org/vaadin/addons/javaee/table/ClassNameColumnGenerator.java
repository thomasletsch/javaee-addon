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

import javax.inject.Inject;
import javax.inject.Singleton;

import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.addons.javaee.jpa.EntityItem;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

@Singleton
public class ClassNameColumnGenerator implements Table.ColumnGenerator {

    @Inject
    private TranslationService translationService;

    @Override
    public Component generateCell(Table source, Object itemId, Object columnId) {
        EntityItem<?> item = (EntityItem<?>) source.getItem(itemId);
        String name = item.getBean().getClass().getSimpleName();
        Label field = new Label();
        field.setValue(translationService.get(name));
        return field;
    }

}
