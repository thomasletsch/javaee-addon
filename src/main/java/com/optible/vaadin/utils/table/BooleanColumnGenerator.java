package com.optible.vaadin.utils.table;

import com.vaadin.data.Property;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;

public class BooleanColumnGenerator implements Table.ColumnGenerator {

    @Override
    public Component generateCell(Table source, Object itemId, Object columnId) {
        Property<?> prop = source.getItem(itemId).getItemProperty(columnId);
        CheckBox checkBox = new CheckBox(null, prop);
        if (source.isEditable()) {
            checkBox.setReadOnly(false);
        } else {
            checkBox.setReadOnly(true);
        }
        return checkBox;
    }
}