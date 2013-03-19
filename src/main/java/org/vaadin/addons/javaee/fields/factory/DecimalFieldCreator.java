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

import org.apache.commons.lang.StringUtils;
import org.vaadin.addons.javaee.fields.NumberField;
import org.vaadin.addons.javaee.fields.converter.StringToBigDecimalConverter;

public class DecimalFieldCreator<FIELD extends NumberField> extends NumberFieldCreator<FIELD> {

    @Override
    protected void initializeField(FIELD field) {
        super.initializeField(field);
        Digits digits = container.getAnnotation(fieldSpec.getName(), Digits.class);
        if (digits != null) {
            String pattern = "0" + ((digits.fraction() > 0) ? "." + StringUtils.repeat("0", digits.fraction()) : "");
            field.setConverter(new StringToBigDecimalConverter(pattern));
        }
    }

}
