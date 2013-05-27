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

import javax.validation.constraints.Size;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class TextFieldCreator<FIELD extends AbstractTextField> extends AbstractFieldCreator<FIELD> {

    private static final int MAX_SIZE = 15;

    /**
     * If field max size is greater than 255, create a TextArea instead of TextField
     */
    private static final int MIN_SIZE_TEXT_AREA = 150;

    @Override
    protected void initializeField(FIELD field) {
        field.setNullRepresentation("");
        Size size = container.getAnnotation(fieldSpec.getName(), Size.class);
        if (size != null && size.max() < Integer.MAX_VALUE) {
            int maxSize = Math.min(size.max(), MAX_SIZE);
            field.setWidth(maxSize, Unit.EM);
        }
        if (field instanceof TextArea) {
            TextArea textArea = (TextArea) field;
            if (fieldSpec.getRows() > 1) {
                textArea.setRows(fieldSpec.getRows());
            } else {
                textArea.setRows(2);
            }
        }
    };

    @Override
    @SuppressWarnings("unchecked")
    protected Class<FIELD> getDefaultFieldType() {
        Size size = container.getAnnotation(fieldSpec.getName(), Size.class);
        if (size != null && size.max() < Integer.MAX_VALUE && size.max() > MIN_SIZE_TEXT_AREA) {
            return (Class<FIELD>) TextArea.class;
        }
        return (Class<FIELD>) TextField.class;
    }
}
