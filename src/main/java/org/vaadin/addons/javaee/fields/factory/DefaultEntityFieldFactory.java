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

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Singleton;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.vaadin.addons.javaee.container.EntityContainer;
import org.vaadin.addons.javaee.fields.spec.FieldSpecification;
import org.wamblee.inject.InjectorBuilder;

import com.vaadin.ui.Field;

@Singleton
public class DefaultEntityFieldFactory implements EntityFieldFactory {

    private static final long serialVersionUID = 1L;

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <T extends Field<?>> T createField(EntityContainer<?> container, FieldSpecification fieldSpec) {
        Class<?> dataType = container.getType(fieldSpec.getName());
        AbstractFieldCreator<?> fieldCreator;
        if (fieldSpec.getValues() != null || fieldSpec.getValueMap() != null) {
            fieldCreator = new ListValueFieldCreator();
        } else if (Boolean.class.isAssignableFrom(dataType)) {
            fieldCreator = new BooleanFieldCreator();
        } else if (BigDecimal.class.isAssignableFrom(dataType)) {
            fieldCreator = new DecimalFieldCreator();
        } else if (Number.class.isAssignableFrom(dataType)) {
            fieldCreator = new NumberFieldCreator();
        } else if (String.class.isAssignableFrom(dataType)) {
            fieldCreator = new TextFieldCreator();
        } else if (Enum.class.isAssignableFrom(dataType)) {
            fieldCreator = new EnumFieldCreator();
        } else if (Calendar.class.isAssignableFrom(dataType) || Date.class.isAssignableFrom(dataType)) {
            fieldCreator = new DateFieldCreator();
        } else if (LocalDate.class.isAssignableFrom(dataType)) {
            fieldCreator = new LocalDateFieldCreator();
        } else if (LocalTime.class.isAssignableFrom(dataType)) {
            fieldCreator = new LocalTimeFieldCreator();
        } else if (container.getAnnotation(fieldSpec.getName(), OneToOne.class) != null) {
            fieldCreator = new OneToOneRelationFieldCreator();
        } else if (container.getAnnotation(fieldSpec.getName(), OneToMany.class) != null) {
            fieldCreator = new OneToManyRelationFieldCreator();
        } else if (container.getAnnotation(fieldSpec.getName(), ManyToOne.class) != null) {
            fieldCreator = new ManyToOneRelationFieldCreator();
        } else {
            fieldCreator = new TextFieldCreator();
        }
        fieldCreator.setContainer(container);
        fieldCreator.setFieldSpec(fieldSpec);
        InjectorBuilder.getInjector().inject(fieldCreator);
        return (T) fieldCreator.createField();
    }

}
