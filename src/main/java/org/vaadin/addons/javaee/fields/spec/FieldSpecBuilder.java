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
package org.vaadin.addons.javaee.fields.spec;

import java.util.Collection;
import java.util.Map;

import org.vaadin.addons.javaee.form.MultiColumnStyle;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.ui.Field;

public class FieldSpecBuilder {

    private FieldSpecification spec;

    public FieldSpecBuilder(String fieldName) {
        spec = new FieldSpecification(fieldName);
    }

    public FieldSpecification build() {
        return spec;
    }

    public FieldSpecBuilder endRow() {
        spec.setEndRow(true);
        return this;
    }

    public FieldSpecBuilder cols(int columns) {
        spec.setColumns(columns);
        return this;
    }

    public FieldSpecBuilder rows(int rows) {
        spec.setRows(rows);
        return this;
    }

    public FieldSpecBuilder type(Class<? extends Field<?>> fieldType) {
        spec.setFieldType(fieldType);
        return this;
    }

    public FieldSpecBuilder vertical() {
        spec.setMultiColumnStyle(MultiColumnStyle.VERTICAL);
        return this;
    }

    public FieldSpecBuilder horizontal() {
        spec.setMultiColumnStyle(MultiColumnStyle.HORIZONTAL);
        return this;
    }

    public FieldSpecBuilder labelWidth(String labelWidth) {
        spec.setLabelWidth(labelWidth);
        return this;
    }

    public FieldSpecBuilder fieldWidth(String fieldWidth) {
        spec.setFieldWidth(fieldWidth);
        return this;
    }

    public FieldSpecBuilder values(Collection<String> values) {
        spec.setValues(values);
        return this;
    }

    public FieldSpecBuilder values(Map<String, String> values) {
        spec.setValues(values);
        return this;
    }

    public FieldSpecBuilder visibleProperty(String visibleProperty) {
        spec.setVisibleProperty(visibleProperty);
        return this;
    }

    public FieldSpecBuilder propertyType(Class<? extends PersistentEntity> propertyType) {
        spec.setPropertyType(propertyType);
        return this;
    }

}
