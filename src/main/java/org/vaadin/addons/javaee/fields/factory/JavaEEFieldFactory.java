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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.joda.time.LocalDate;
import org.vaadin.addons.javaee.fields.spec.FieldSpecification;
import org.vaadin.addons.javaee.jpa.EntityContainer;
import org.wamblee.inject.InjectorBuilder;

import com.vaadin.data.Container;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.TableFieldFactory;

@SessionScoped
@SuppressWarnings({ "rawtypes", "unchecked" })
public class JavaEEFieldFactory implements FieldFactory, TableFieldFactory {

    private static final long serialVersionUID = 1L;

    private TableFieldFactory defaultTableFieldFactory = DefaultFieldFactory.get();

    @Override
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

    @Override
    public Field<?> createField(Container container, Object itemId, Object propertyId, Component uiContext) {
        if (container instanceof EntityContainer<?>) {
            EntityContainer<?> entityContainer = (EntityContainer<?>) container;
            FieldSpecification fieldSpec = new FieldSpecification((String) propertyId);
            return createField(entityContainer, fieldSpec);
        }
        return defaultTableFieldFactory.createField(container, itemId, propertyId, uiContext);
    }

}
