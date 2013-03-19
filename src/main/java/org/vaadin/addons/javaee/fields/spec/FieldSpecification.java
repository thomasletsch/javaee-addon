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

public class FieldSpecification {

    private String name;

    private boolean endRow = false;

    private int columns = 1;

    private int rows = 1;

    private String labelWidth;

    private String fieldWidth;

    private Class<? extends Field<?>> fieldType;

    private Class<? extends PersistentEntity> propertyType;

    private MultiColumnStyle multiColumnStyle;

    /**
     * For select fields like ComboBox, the caption property
     */
    private String visibleProperty;

    /**
     * For Lists which have predefined values
     */
    private Collection<String> values;

    /**
     * For Lists which have predefined and translated values
     */
    private Map<String, String> valueMap;

    public FieldSpecification(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isEndRow() {
        return endRow;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEndRow(boolean endRow) {
        this.endRow = endRow;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public Class<? extends Field<?>> getFieldType() {
        return fieldType;
    }

    public void setFieldType(Class<? extends Field<?>> fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldWidth() {
        return fieldWidth;
    }

    public void setFieldWidth(String fieldWidth) {
        this.fieldWidth = fieldWidth;
    }

    public String getLabelWidth() {
        return labelWidth;
    }

    public void setLabelWidth(String labelWidth) {
        this.labelWidth = labelWidth;
    }

    public MultiColumnStyle getMultiColumnStyle() {
        return multiColumnStyle;
    }

    public void setMultiColumnStyle(MultiColumnStyle multiColumnStyle) {
        this.multiColumnStyle = multiColumnStyle;
    }

    public Collection<String> getValues() {
        return values;
    }

    public void setValues(Collection<String> values) {
        this.values = values;
    }

    public void setValues(Map<String, String> valueMap) {
        this.setValueMap(valueMap);
    }

    public Map<String, String> getValueMap() {
        return valueMap;
    }

    public void setValueMap(Map<String, String> valueMap) {
        this.valueMap = valueMap;
    }

    public String getVisibleProperty() {
        return visibleProperty;
    }

    public void setVisibleProperty(String visibleProperty) {
        this.visibleProperty = visibleProperty;
    }

    public Class<? extends PersistentEntity> getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(Class<? extends PersistentEntity> propertyType) {
        this.propertyType = propertyType;
    }

}
