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
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import org.joda.time.LocalDate;
import org.vaadin.addons.javaee.form.FieldSpecification;
import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.addons.javaee.jpa.EntityContainer;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Field;

@SessionScoped
@SuppressWarnings({ "rawtypes", "unchecked" })
public class JavaEEFieldFactory implements FieldFactory {

    private static final long serialVersionUID = 1L;

    @Inject
    private TranslationService translationService;

    @Override
    public <T extends Field<?>> T createField(EntityContainer<?> container, FieldSpecification fieldSpec) {
        Class<?> dataType = container.getType(fieldSpec.getName());
        if (Boolean.class.isAssignableFrom(dataType)) {
            return (T) new BooleanFieldCreator(translationService, container, fieldSpec.getName(),
                    (Class<CheckBox>) fieldSpec.getFieldType()).createField();
        } else if (BigDecimal.class.isAssignableFrom(dataType)) {
            return (T) new DecimalFieldCreator(translationService, container, fieldSpec.getName(), fieldSpec.getFieldType()).createField();
        } else if (Number.class.isAssignableFrom(dataType)) {
            return (T) new NumberFieldCreator(translationService, container, fieldSpec.getName(), fieldSpec.getFieldType()).createField();
        } else if (String.class.isAssignableFrom(dataType)) {
            return (T) new TextFieldCreator(translationService, container, fieldSpec.getName(), fieldSpec.getFieldType()).createField();
        } else if (Enum.class.isAssignableFrom(dataType)) {
            return (T) new EnumFieldCreator(translationService, container, fieldSpec).createField();
        } else if (Calendar.class.isAssignableFrom(dataType) || Date.class.isAssignableFrom(dataType)) {
            return (T) new DateFieldCreator(translationService, container, fieldSpec.getName(), fieldSpec.getFieldType()).createField();
        } else if (LocalDate.class.isAssignableFrom(dataType)) {
            return (T) new LocalDateFieldCreator(translationService, container, fieldSpec.getName(), fieldSpec.getFieldType())
                    .createField();
        } else if (PersistentEntity.class.isAssignableFrom(dataType)) {
            return (T) new RelationFieldCreator(translationService, container, fieldSpec.getName(), fieldSpec.getFieldType()).createField();
        } else {
            return (T) new TextFieldCreator(translationService, container, fieldSpec.getName(), fieldSpec.getFieldType()).createField();
        }
    }

}
