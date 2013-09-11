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

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.DateField;
import com.vaadin.ui.PopupDateField;

public class DateFieldCreator<FIELD extends DateField> extends AbstractFieldCreator<FIELD> {

    @Override
    protected void initializeField(FIELD field) {
        super.initializeField(field);
        Temporal temporal = container.getAnnotation(fieldSpec.getName(), Temporal.class);
        TemporalType type = TemporalType.DATE;
        if (temporal != null) {
            type = temporal.value();
        }
        switch (type) {
            case DATE:
                field.setResolution(Resolution.DAY);
                break;
            case TIME:
                field.setResolution(Resolution.SECOND);
                field.setDateFormat("HH:mm:ss");
                break;
            case TIMESTAMP:
                field.setResolution(Resolution.SECOND);
                break;
        }
    };

    @Override
    @SuppressWarnings("unchecked")
    protected Class<FIELD> getDefaultFieldType() {
        return (Class<FIELD>) PopupDateField.class;
    }

}
