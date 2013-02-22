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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.joda.time.LocalDate;
import org.vaadin.addons.javaee.fields.spec.FieldSpecification;
import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.addons.javaee.jpa.EntityContainer;

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
        if (fieldSpec.getValues() != null || fieldSpec.getValueMap() != null) {
            return (T) new ListValueFieldCreator(container, fieldSpec, translationService).createField();
        } else if (Boolean.class.isAssignableFrom(dataType)) {
            return (T) new BooleanFieldCreator(container, fieldSpec).createField();
        } else if (BigDecimal.class.isAssignableFrom(dataType)) {
            return (T) new DecimalFieldCreator(container, fieldSpec).createField();
        } else if (Number.class.isAssignableFrom(dataType)) {
            return (T) new NumberFieldCreator(container, fieldSpec).createField();
        } else if (String.class.isAssignableFrom(dataType)) {
            return (T) new TextFieldCreator(container, fieldSpec).createField();
        } else if (Enum.class.isAssignableFrom(dataType)) {
            return (T) new EnumFieldCreator(container, fieldSpec, translationService).createField();
        } else if (Calendar.class.isAssignableFrom(dataType) || Date.class.isAssignableFrom(dataType)) {
            return (T) new DateFieldCreator(container, fieldSpec).createField();
        } else if (LocalDate.class.isAssignableFrom(dataType)) {
            return (T) new LocalDateFieldCreator(container, fieldSpec).createField();
        } else if (container.getAnnotation(fieldSpec.getName(), OneToOne.class) != null) {
            return (T) new OneToOneRelationFieldCreator(container, fieldSpec).createField();
        } else if (container.getAnnotation(fieldSpec.getName(), OneToMany.class) != null) {
            return (T) new OneToManyRelationFieldCreator(container, translationService, fieldSpec).createField();
        } else if (container.getAnnotation(fieldSpec.getName(), ManyToOne.class) != null) {
            return (T) new ManyToOneRelationFieldCreator(container, fieldSpec).createField();
        } else {
            return (T) new TextFieldCreator(container, fieldSpec).createField();
        }
    }

}
