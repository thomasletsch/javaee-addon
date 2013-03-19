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

import javax.validation.constraints.Digits;

import org.vaadin.addons.javaee.fields.NumberField;

import com.vaadin.server.Sizeable.Unit;

public class NumberFieldCreator<FIELD extends NumberField> extends AbstractFieldCreator<FIELD> {

    static final int DEFAULT_SIZE_EM = 4;

    @Override
    protected void initializeField(FIELD field) {
        super.initializeField(field);
        Digits digits = container.getAnnotation(fieldSpec.getName(), Digits.class);
        if (digits != null) {
            field.setWidth(digits.integer() + digits.fraction() + 1, Unit.EM);
        } else {
            field.setWidth(DEFAULT_SIZE_EM, Unit.EM);
        }
        field.setNullRepresentation("");
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Class<FIELD> getDefaultFieldType() {
        return (Class<FIELD>) NumberField.class;
    };

}
