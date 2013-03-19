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

import java.util.Map;

import javax.inject.Inject;

import org.vaadin.addons.javaee.i18n.TranslationService;

import com.vaadin.ui.ComboBox;

public class ComboBoxTranslated extends ComboBox {

    private static final long serialVersionUID = 1L;

    @Inject
    private TranslationService translationService;

    public ComboBoxTranslated() {
    }

    /**
     * @param caption
     *            The internationalized caption
     * @param values
     *            A Map of value to display value
     * 
     */
    public ComboBoxTranslated(String caption, Map<String, String> values) {
        super(caption, values.keySet());
        for (String value : values.keySet()) {
            setItemCaption(value, values.get(value));
        }
        setNullSelectionAllowed(false);
        setTextInputAllowed(false);
    }

    public void setValues(Map<String, String> values) {
        for (String value : values.keySet()) {
            addItem(value);
            setItemCaption(value, values.get(value));
        }
    }

    @Override
    public void addValueChangeListener(com.vaadin.data.Property.ValueChangeListener listener) {
        super.addValueChangeListener(listener);
        setImmediate(true);
    }

}
