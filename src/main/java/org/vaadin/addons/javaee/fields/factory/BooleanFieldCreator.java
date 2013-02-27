package org.vaadin.addons.javaee.fields.factory;

import com.vaadin.ui.CheckBox;

public class BooleanFieldCreator extends AbstractFieldCreator<CheckBox> {

    @Override
    protected Class<CheckBox> getDefaultFieldType() {
        return CheckBox.class;
    }

}
