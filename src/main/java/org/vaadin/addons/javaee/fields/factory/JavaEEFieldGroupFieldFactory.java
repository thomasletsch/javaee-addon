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
package org.vaadin.addons.javaee.fields.factory;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.addons.javaee.jpa.EntityContainer;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Field;

@SessionScoped
@SuppressWarnings({ "rawtypes", "unchecked" })
public class JavaEEFieldGroupFieldFactory implements FieldFactory {

    @Inject
    private TranslationService translationService;

    @Override
    public <T extends Field<?>> T createField(EntityContainer<?> container, String fieldName) {
        return createField(container, fieldName, null);
    }

    @Override
    public <T extends Field<?>> T createField(EntityContainer<?> container, String fieldName, Class<T> fieldType) {
        Class<?> dataType = container.getType(fieldName);
        if (Boolean.class.isAssignableFrom(dataType) || boolean.class.isAssignableFrom(dataType)) {
            return (T) new BooleanFieldCreator(translationService, container, fieldName, (Class<CheckBox>) fieldType).createField();
        } else if (BigDecimal.class.isAssignableFrom(dataType) || Float.class.isAssignableFrom(dataType)
                || Double.class.isAssignableFrom(dataType)) {
            return (T) new DecimalFieldCreator(translationService, container, fieldName, fieldType).createField();
        } else if (Number.class.isAssignableFrom(dataType)) {
            return (T) new NumberFieldCreator(translationService, container, fieldName, fieldType).createField();
        } else if (String.class.isAssignableFrom(dataType)) {
            return (T) new TextFieldCreator(translationService, container, fieldName, fieldType).createField();
        } else if (Enum.class.isAssignableFrom(dataType)) {
            return (T) new EnumFieldCreator(translationService, container, fieldName, fieldType).createField();
        } else if (Calendar.class.isAssignableFrom(dataType)) {
            return (T) new DateFieldCreator(translationService, container, fieldName, fieldType).createField();
        } else {
            return (T) new TextFieldCreator(translationService, container, fieldName, fieldType).createField();
        }
    }

}
