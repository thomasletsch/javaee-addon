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

import java.util.Map;

import javax.inject.Inject;

import org.vaadin.addons.javaee.fields.ComboBoxTranslated;
import org.vaadin.addons.javaee.form.MultiColumnStyle;
import org.vaadin.addons.javaee.i18n.TranslationService;

import com.vaadin.ui.AbstractSelect;

public class ListValueFieldCreator<FIELD extends AbstractSelect> extends AbstractFieldCreator<FIELD> {

    @Inject
    private TranslationService translationService;

    @Override
    @SuppressWarnings("unchecked")
    protected Class<FIELD> getDefaultFieldType() {
        return (Class<FIELD>) ComboBoxTranslated.class;
    }

    @Override
    protected void initializeField(FIELD field) {
        populateSelect(field);
    };

    protected void populateSelect(FIELD field) {
        Map<String, String> valueMap = null;
        if (fieldSpec.getValueMap() != null) {
            valueMap = fieldSpec.getValueMap();
        } else {
            valueMap = translationService.get(fieldSpec.getName(), fieldSpec.getValues());
        }
        for (String value : valueMap.keySet()) {
            field.addItem(value);
            field.setItemCaption(value, valueMap.get(value));
        }
        if (MultiColumnStyle.HORIZONTAL.equals(fieldSpec.getMultiColumnStyle()) || (valueMap.size() <= 3)) {
            field.setStyleName("horizontal");
        }
    }

}
