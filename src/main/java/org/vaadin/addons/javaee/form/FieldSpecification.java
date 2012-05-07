package org.vaadin.addons.javaee.form;

import com.vaadin.ui.Field;

public class FieldSpecification {

    private String name;

    private boolean endRow = false;

    private int columns = 1;

    private int rows = 1;

    private String labelWidth;

    private String fieldWidth;

    private Class<? extends Field<?>> fieldType;

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

}
